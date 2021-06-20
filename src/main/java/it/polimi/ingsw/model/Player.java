package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.exceptions.InvalidSpaceCardException;
import it.polimi.ingsw.exceptions.NotEnoughResourceException;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;
import it.polimi.ingsw.model.card.deck.LeaderCardDeck;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.card.leadercard.LeaderCard;
import it.polimi.ingsw.model.card.leadercard.ProductionLeader;
import it.polimi.ingsw.model.card.leadercard.StorageLeader;
import it.polimi.ingsw.model.card.leadercard.WhiteBallLeader;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.playerdashboard.PlayerDashboard;

import java.util.ArrayList;


/**
 * This class manages all the main actions that can be done by a player.
 *
 * @author  Francesco Leone, Lorenzo Iovine, Nicola Landini
 */
public class Player {
    private final String nickname;
    private final PlayerDashboard playerDashboard;
    private int victoryPoints;
    private boolean first=false;
    private final ArrayList <Production> activatedProduction = new ArrayList<>();


    /**
     * Gets the playerDashboard of the player
     * @return playerDashboard variable
     */
    public PlayerDashboard getPlayerDashboard() {
        return playerDashboard;
    }


    /**
     * Gets the leader cards of the player
     * @return leader card deck
     */
    public LeaderCardDeck getLeaders(){
        return playerDashboard.getLeaders();
    }


    /**
     * Sets the variable first to parameter first
     * @param first is the value to be assigned to the variable first
     */
    public void setFirst(boolean first) {
        this.first = first;
    }


    /**
     * Calculates the player's victory points given by the card
     * @return the player's victory points
     */
    public int calculateVictoryPoints() {
        victoryPoints=0;

        //total points from cards in devCardsSpace
        victoryPoints += getPlayerDashboard().getDevCardsSpace().pointsByDevCards();

        //total points from player's leader cards
        victoryPoints += getLeaders().victoryPointsAmount();

        return victoryPoints;
    }


    /**
     * Constructor Player creates a new Player instance
     * @param nickname is the nickname of the Player
     */
    public Player(String nickname) {
        this.nickname = nickname;
        this.playerDashboard = new PlayerDashboard();
        victoryPoints=0;
    }


    /**
     * Manages the purchase of a development card from the grid and insertion in a space
     * @param Card is the card that we want to buy
     * @param space is the space where we want to insert the card
     * @throws InvalidSpaceCardException if the card cannot be insert in this space
     */
    public void buyCard(DevelopmentCard Card, int space) throws InvalidSpaceCardException {
        boolean check=getPlayerDashboard().CheckResource(Card.getCost());
        if(playerDashboard.getDevCardsSpace().checkSpace(Card, space) && check) {
            playerDashboard.getDevCardsSpace().addCard(Card, space);
            playerDashboard.RemoveResource(Card.getCost());
        }
        else throw new InvalidSpaceCardException();
    }


    /**
     * Manages the activation of a Leader Card
     * @param pos is the position of the leader card that we want to activate
     * @throws InvalidChoiceException if the card cannot be activated
     */
    public void ActiveLeader(int pos) throws InvalidChoiceException{
        if(playerDashboard.getLeaders().size()>=pos && pos>=0 && !playerDashboard.getLeaders().get(pos-1).getIsDiscarded()) {
            if (playerDashboard.getLeaders().get(pos-1) instanceof StorageLeader) {
                if (playerDashboard.getLeaders().get(pos-1).checkRequirements(playerDashboard)) {
                    playerDashboard.getLeaders().get(pos-1).setIsActive();
                    playerDashboard.RemoveResource(playerDashboard.getLeaders().get(pos-1).getRequirements().get(0).getCost());
                }
                else throw new InvalidChoiceException();
            } else {
                if (playerDashboard.getLeaders().get(pos-1).checkRequirements(playerDashboard)) {
                    playerDashboard.getLeaders().get(pos-1).setIsActive();
                }
                else throw new InvalidChoiceException();
            }
        }
        else throw new InvalidChoiceException();
    }


    /**
     * Manages the waste of a Leader Card
     * @param pos is the position of the leader card that we want to discard
     * @throws InvalidChoiceException if the card cannot be discarded
     */
    public void DiscardLeader(int pos) throws InvalidChoiceException {
        //playerDashboard.getLeaders().DrawFromPosition(pos);
        if(playerDashboard.getLeaders().size()>=pos-1 && pos-1>=0 && !playerDashboard.getLeaders().get(pos-1).getIsActive() && !playerDashboard.getLeaders().get(pos-1).getIsDiscarded()) {
            playerDashboard.getLeaders().get(pos-1).setIsDiscarded();
            playerDashboard.getFaithPath().moveForward(1);
        } else throw new InvalidChoiceException();
    }


    /**
     * Manages the movement of the faith indicator in the faith path
     * @param pos is the number of steps to take in the faith path
     */
    public void move(int pos) {
        playerDashboard.getFaithPath().moveForward(pos);
    }


    /**
     * Gets usable development cards of the player
     * @return an IDs ArrayList of the usable development cards
     */
    public ArrayList<Integer> getDevCards() {
        ArrayList<Integer> Id=new ArrayList<>();

        for(int i=0; i<3; i++) {
            if(!playerDashboard.getDevCardsSpace().getSpace().get(i).isEmpty()) {
                Id.add(playerDashboard.getDevCardsSpace().getSpace().get(i).get().getCardID());
            }
        }

        return Id;
    }


    public ArrayList<Integer> getDevCardsForGUI() {
        ArrayList<Integer> Id = new ArrayList<>();

        for(int i=0; i<3; i++) {
            if(!playerDashboard.getDevCardsSpace().getSpace().get(i).isEmpty()) {
                Id.add(i, playerDashboard.getDevCardsSpace().getSpace().get(i).get().getCardID());
            }
            else {
                Id.add(i, -1);
            }
        }

        return Id;
    }


    /**
     * Gets the player's active Production Leader
     * @return an IDs Arraylist of the active production leader
     */
    public ArrayList<Integer> getProductions() {
        ArrayList<Integer> Id=new ArrayList<>();

        for(LeaderCard card : playerDashboard.getLeaders().getDeck()){
            if(card instanceof ProductionLeader && card.getIsActive())
            {
                Id.add(card.getCardID());
            }
        }

        for(int i=0; i<3; i++) {
            if(!playerDashboard.getDevCardsSpace().getSpace().get(i).isEmpty()) {
                Id.add(playerDashboard.getDevCardsSpace().getSpace().get(i).get().getCardID());
            }
        }

        return Id;
    }


    /**
     * Gets the nickname of the player
     * @return nickname variable
     */
    public String getNickname() {
        return nickname;
    }


    /**
     * Manages the activation of a production of a production leader
     * @param pos is the position of the leader card that we want to use
     * @throws InvalidChoiceException if the choice is not valid
     * @throws NotEnoughResourceException if we can't do the production
     */
    public void ActiveProductionLeader(int pos, Resource output) throws InvalidChoiceException, NotEnoughResourceException {
        if(playerDashboard.getLeaders().get(pos- 1) instanceof ProductionLeader && playerDashboard.getLeaders().get(pos-1).getIsActive()){
            if(playerDashboard.CheckResource(((ProductionLeader) playerDashboard.getLeaders().get(pos- 1)).getInputProduction())) {
                ((ProductionLeader) playerDashboard.getLeaders().get(pos-1)).setOutputProduction(output);
                activatedProduction.add(((ProductionLeader) playerDashboard.getLeaders().get(pos - 1)).getProduction());
            }
            else throw new NotEnoughResourceException();
        }
        else
            throw new InvalidChoiceException();
    }


    /**
     * Manages the activation of a base production
     * @throws NotEnoughResourceException if we can't do the production
     */
    public void ActiveProductionBase() throws NotEnoughResourceException {
        if(playerDashboard.CheckResource(playerDashboard.getDevCardsSpace().getBasicProduction().getInputProduction()))
            activatedProduction.add(playerDashboard.getDevCardsSpace().getBasicProduction());
        else throw new NotEnoughResourceException();
    }


    /**
     * Manages the activation of a production of a development card
     * @param space is the position of the development card that we want to use
     * @throws InvalidChoiceException if the choice is not valid
     * @throws NotEnoughResourceException if we can't do the production
     */
    public void ActiveProductionDevCard(int space) throws InvalidChoiceException, NotEnoughResourceException {
        if(playerDashboard.CheckResource(playerDashboard.getDevCardsSpace().getCard(space).getInputProduction()))
            activatedProduction.add(playerDashboard.getDevCardsSpace().getCard(space).getProduction());
        else throw new NotEnoughResourceException();
    }


    /**
     * Makes all productions requested by the player in the turn
     * @throws NotEnoughResourceException if we can't do the productions
     */
    public void doProduction() throws NotEnoughResourceException {
        ArrayList<Goods> TotInput = new ArrayList<>();
        ArrayList<Goods> TotOutput = new ArrayList<>();
        int TotFaithSteps = 0;

        for(Production p: activatedProduction){
            TotInput.addAll(p.getInputProduction());
            TotOutput.addAll(p.getOutputProduction());
            TotFaithSteps += p.getFaithSteps();
        }

        if(!playerDashboard.CheckResource(TotInput)) throw new NotEnoughResourceException();
        else{
            playerDashboard.RemoveResource(TotInput);
        }

        playerDashboard.AddResources(TotOutput);
        playerDashboard.getFaithPath().moveForward(TotFaithSteps);

        activatedProduction.clear();
    }


    /**
     * Counts how many active White Ball Leader Cards the player has
     * @return the number of active White Ball Leader cards
     */
    public int WhiteBallLeader () {
        int cards=0;

        for(LeaderCard card:getLeaders().getDeck()){
            if((card instanceof WhiteBallLeader) && card.getIsActive())
                cards++;
        }

        return cards;
    }


    /**
     * Gets the index of the requested storage leader card if it still has free spaces
     * @param resource is the type of storage leader card that we are looking for
     * @return the index of the storage leader card
     * @throws InvalidChoiceException if the choice is not valid
     */
    public int indexOfStorageLeader(Resource resource) throws InvalidChoiceException {
        int index=0;

        for(LeaderCard card:getLeaders().getDeck()) {
            if((card instanceof StorageLeader) && card.getIsActive() &&  ((StorageLeader) card).getType()==resource && ((StorageLeader) card).getAmount()<2) {
                index=getLeaders().getDeck().indexOf(card)+1;
            }
        }

        if(index!=0) {
            return index;
        } else {
            throw new InvalidChoiceException();
        }
    }


    /**
     * Checks if there are any storage leader cards that can hold the requested resource
     * @param resource is the resource we want to place
     * @return true if there is a leader card who can hold this resource, false otherwise
     */
    public boolean StorageLeader(Resource resource) {
        for(LeaderCard card:getLeaders().getDeck()) {
            if((card instanceof StorageLeader) && card.getIsActive() &&  ((StorageLeader) card).getType()==resource && ((StorageLeader) card).getAmount()<2) {
                return true;
            }
        }

        return false;
    }


    /**
     * Gets the productions activated by the player during the turn
     * @return activatedProduction variable
     */
    public ArrayList<Production> getActivatedProduction() {
        return activatedProduction;
    }
}