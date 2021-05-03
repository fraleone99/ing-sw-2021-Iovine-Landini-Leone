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
import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.playerdashboard.Ball;
import it.polimi.ingsw.model.gameboard.playerdashboard.PlayerDashboard;

import java.util.ArrayList;


/**
 *
 * @author  Francesco Leone, Lorenzo Iovine, Nicola Landini
 */

public class Player {
    private final String nickname;
    private final PlayerDashboard playerDashboard;
    private int victoryPoints;
    private boolean first=false;
    private final ArrayList <Production> activatedProduction = new ArrayList<>();


    public PlayerDashboard getPlayerDashboard() {
        return playerDashboard;
    }

    public LeaderCardDeck getLeaders(){
        return playerDashboard.getLeaders();
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int calculateVictoryPoints() {
        //total points from cards in devCardsSpace
        for(DevelopmentCardDeck deck: getPlayerDashboard().getDevCardsSpace().getSpace()){
            victoryPoints += deck.victoryPointsAmount();
        }

        //total points from player's leader cards
        victoryPoints += getLeaders().victoryPointsAmount();

        return victoryPoints;
    }

    public Player(String nickname) {
        this.nickname = nickname;
        this.playerDashboard = new PlayerDashboard();
    }

    public boolean isFirst() {
        return first;
    }

    public void discardLeaderFirstRound(int pos1, int pos2) throws InvalidChoiceException {
        playerDashboard.getLeaders().DrawFromPosition(pos1);
        playerDashboard.getLeaders().DrawFromPosition(pos2);
    }

    public void buyCard(DevelopmentCard Card, int space) throws InvalidSpaceCardException {
        playerDashboard.getDevCardsSpace().AddCard(Card, space);
    }

    public ArrayList<Resource> UseMarket(ArrayList<Ball> balls){
        ArrayList<Resource> ris = new ArrayList<>();

        for(Ball b : balls){
            if(b.getType().equals(BallColor.RED)) playerDashboard.getFaithPath().moveForward(1);
            else if(b.getType().equals(BallColor.WHITE)) ris.add(Resource.UNKNOWN); // to check
            else if(b.getType().equals(BallColor.BLUE)) ris.add(Resource.SHIELD);
            else if(b.getType().equals(BallColor.YELLOW)) ris.add(Resource.COIN);
            else if(b.getType().equals(BallColor.GREY)) ris.add(Resource.STONE);
            else if(b.getType().equals(BallColor.PURPLE)) ris.add(Resource.SERVANT);
        }

        return  ris;

    }

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

    public void DiscardLeader(int pos) throws InvalidChoiceException {
        //playerDashboard.getLeaders().DrawFromPosition(pos);
        if(playerDashboard.getLeaders().size()>=pos-1 && pos-1>=0 && !playerDashboard.getLeaders().get(pos-1).getIsActive() && !playerDashboard.getLeaders().get(pos-1).getIsDiscarded()) {
            playerDashboard.getLeaders().get(pos-1).setIsDiscarded();
            playerDashboard.getFaithPath().moveForward(1);
        } else throw new InvalidChoiceException();
    }

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

    public String getNickname() {
        return nickname;
    }

    public void ActiveProductionLeader(int pos) throws InvalidChoiceException, NotEnoughResourceException {
        if(playerDashboard.getLeaders().get(pos- 1) instanceof ProductionLeader){
            if(playerDashboard.CheckResource(((ProductionLeader) playerDashboard.getLeaders().get(pos- 1)).getInputProduction()))
                activatedProduction.add(((ProductionLeader) playerDashboard.getLeaders().get(pos-1)).getProduction());
            else throw new NotEnoughResourceException();
        }
        else
            throw new InvalidChoiceException();
    }

    public void ActiveProductionBase() throws NotEnoughResourceException {
        if(playerDashboard.CheckResource(playerDashboard.getDevCardsSpace().getBasicProduction().getInputProduction()))
            activatedProduction.add(playerDashboard.getDevCardsSpace().getBasicProduction());
        else throw new NotEnoughResourceException();
    }
    public void ActiveProductionDevCard(int space) throws InvalidChoiceException, NotEnoughResourceException {
        if(playerDashboard.CheckResource(playerDashboard.getDevCardsSpace().getCard(space).getInputProduction()))
            activatedProduction.add(playerDashboard.getDevCardsSpace().getCard(space).getProduction());
        else throw new NotEnoughResourceException();
    }



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



    }
}