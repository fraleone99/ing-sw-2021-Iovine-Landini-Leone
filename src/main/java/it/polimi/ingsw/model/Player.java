package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author  Francesco Leone, Lorenzo Iovine, Nicola Landini
 */

public class Player {

    private final String nickname;
    private PlayerDashboard playerDashboard;
    private int victoryPoints;
    private boolean first;
    private ArrayList <Production> activatedProduction = new ArrayList<>();


    public PlayerDashboard getPlayerDashboard() {
        return playerDashboard;
    }

    public LeaderCardDeck getLeaders(){
        return playerDashboard.getLeaders();
    }

    public Player(String nickname) {
        this.nickname = nickname;
        this.playerDashboard = new PlayerDashboard(nickname);
    }

    public boolean isFirst() {
        return first;
    }

    public void discardLeaderFirstRound(int pos1, int pos2) throws InvalidChoiceException {
        playerDashboard.getLeaders().DrawFromPosition(pos1);
        playerDashboard.getLeaders().DrawFromPosition(pos2);
    }

    public void buyCard(DevelopmentCard Card, int space) throws InvalidSpaceCardExeption {
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

    public void ActiveLeader(LeaderCard leader){
        if(leader instanceof StorageLeader){
            if(leader.checkRequirements(playerDashboard) && !leader.getIsDiscarded()){
                leader.setIsActive();
            }
        }
        else{
            if(leader.checkRequirements(playerDashboard) && !leader.getIsDiscarded()){
                leader.setIsActive();
            }
        }
    }

    public void DiscardLeader(int pos) throws InvalidChoiceException {
        playerDashboard.getLeaders().DrawFromPosition(pos);
        playerDashboard.getLeaders().get(pos).setIsDiscarded();
        playerDashboard.getFaithPath().moveForward(1);
    }


    public String getNickname() {
        return nickname;
    }

   /*public void ActiveProductionLeader(int pos) throws InvalidChoiceException, NotEnoughResourceException {
        if(playerDashboard.getLeaders().get(pos- 1) instanceof ProductionLeader){
            if(playerDashboard.CheckResource(((ProductionLeader) playerDashboard.getLeaders().get(pos- 1)).getInputProduction()))
                activatedProduction.add(((ProductionLeader) playerDashboard.getLeaders().get(pos-1)).getProduction());
            else throw new NotEnoughResourceException();
        }
        else
            throw new InvalidChoiceException();
    }*/

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

        for(Production p: activatedProduction){
            TotInput.addAll(p.getInputProduction());
            TotOutput.addAll(p.getOutputProduction());
        }

        if(!playerDashboard.CheckResource(TotInput)) throw new NotEnoughResourceException();
        else{
            playerDashboard.RemoveResource(TotInput);
        }

        playerDashboard.AddResources(TotOutput);



    }
}