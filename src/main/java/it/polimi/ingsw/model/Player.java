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
    private LeaderCardDeck leaders;
    private PlayerDashboard playerDashboard;
    private int victoryPoints;
    private boolean first;
    private ArrayList <Production> activatedProduction;


    public Player(String nickname) {
        this.nickname = nickname;
    }

    public boolean isFirst() {
        return first;
    }

    public void discardLeaderFirstRound(int pos1, int pos2) throws InvalidChoiceException {
        leaders.DrawFromPosition(pos1);
        leaders.DrawFromPosition(pos2);
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

    }

    public void DiscardLeader(int pos) throws InvalidChoiceException {
        leaders.DrawFromPosition(pos);
    }


    public String getNickname() {
        return nickname;
    }

    public void ActiveProductionLeader(int pos) throws InvalidChoiceException, NotEnoughResourceException {
        if(leaders.get(pos- 1) instanceof ProductionLeader){
            if(CheckResource(((ProductionLeader) leaders.get(pos- 1)).getInputProduction()))
                activatedProduction.add(((ProductionLeader) leaders.get(pos-1)).getProduction);
            else throw new NotEnoughResourceException();
        }
        else
            throw new InvalidChoiceException();
    }

    public void ActiveProductionBase() throws NotEnoughResourceException {
        if(CheckResource(playerDashboard.getDevCardsSpace().getBasicProduction().getInputProduction()))
            activatedProduction.add(playerDashboard.getDevCardsSpace().getBasicProduction());
        else throw new NotEnoughResourceException();
    }
    public void ActiveProductionDevCard(int space) throws InvalidChoiceException, NotEnoughResourceException {
        if(CheckResource(playerDashboard.getDevCardsSpace().getCard(space).getInputProduction()))
            activatedProduction.add(playerDashboard.getDevCardsSpace().getCard(space).getProduction());
        else throw new NotEnoughResourceException();
    }

    public boolean CheckResource(ArrayList<Goods> needed){
        ArrayList<Goods> neededClone = new ArrayList<>();
        boolean RequirementsSatisfied = true;

        for(Goods g: needed){
            neededClone.add(new Goods(g));
        }


        //Storage check
        for(Goods g1: neededClone){
            g1.setAmount(playerDashboard.getStorage().checkInput(g1));
        }

        //StorageLeader check
        for(int i = 0; i <= 1; i++) {
            if (leaders.get(i) instanceof StorageLeader && leaders.get(i).getIsActive()) {
                for (Goods g1 : neededClone) {
                    if (g1.getType().equals(((StorageLeader) leaders.get(i)).getType())) {
                        if (((StorageLeader) leaders.get(i)).getAmount() >= g1.getAmount()) g1.setAmount(0);
                        else g1.setAmount(g1.getAmount() - ((StorageLeader) leaders.get(i)).getAmount());
                    }
                }
            }
        }

        //vaultCheck
        for(Goods g1: neededClone){
            g1.setAmount(playerDashboard.getVault().checkInput(g1));
        }

        for(Goods g1: neededClone){
            if (g1.getAmount() > 0) {
                RequirementsSatisfied = false;
                break;
            }
        }

        return  RequirementsSatisfied;

        
    }

    public void doProduction() throws NotEnoughResourceException {
        ArrayList<Goods> TotInput = new ArrayList<>();
        ArrayList<Goods> TotOutput = new ArrayList<>();

        for(Production p: activatedProduction){
            TotInput.addAll(p.getInputProduction());
            TotOutput.addAll(p.getOutputProduction());
        }

        if(!CheckResource(TotInput)) throw new NotEnoughResourceException();
        else{
            RemoveResource(TotInput);
        }

        AddResources(TotOutput);



    }

    private void RemoveResource(ArrayList<Goods> toRemove) {

        HashMap<Resource, Integer> ResToRemove = new HashMap<>();
        ResToRemove.put(Resource.COIN, 0);
        ResToRemove.put(Resource.SERVANT, 0);
        ResToRemove.put(Resource.SHIELD, 0);
        ResToRemove.put(Resource.STONE, 0);

        for(Goods g: toRemove){
            ResToRemove.replace(g.getType(),ResToRemove.get(g.getType()), ResToRemove.get(g.getType()) + g.getAmount());

        }

        //Storage remove
        for(Map.Entry<Resource,Integer> mapElement: ResToRemove.entrySet()){
            Resource r = mapElement.getKey();
            int remaining = playerDashboard.getStorage().DiscardResources(mapElement.getKey(),mapElement.getValue());
            mapElement.setValue(remaining);
        }

        //StorageLeader remove
        for(int i = 0; i <= 1; i++) {
            if (leaders.get(i) instanceof StorageLeader && leaders.get(i).getIsActive()) {
                Resource type = ((StorageLeader) leaders.get(i)).getType();
                int remaining = ((StorageLeader) leaders.get(i)).DiscardResources(ResToRemove.get(type));
                ResToRemove.replace(type, ResToRemove.get(type), remaining);
            }
        }

        //vault remove
        for(Map.Entry<Resource,Integer> mapElement: ResToRemove.entrySet()){
            Resource r = mapElement.getKey();
            playerDashboard.getVault().removeResource(r, mapElement.getValue());
        }
    }

    private void AddResources(ArrayList<Goods> toAdd){
        HashMap<Resource, Integer> ResToAdd = new HashMap<>();
        ResToAdd.put(Resource.COIN, 0);
        ResToAdd.put(Resource.SERVANT, 0);
        ResToAdd.put(Resource.SHIELD, 0);
        ResToAdd.put(Resource.STONE, 0);

        for(Goods g: toAdd){
            ResToAdd.replace(g.getType(),ResToAdd.get(g.getType()), ResToAdd.get(g.getType()) + g.getAmount());
        }

        for(Map.Entry<Resource,Integer> mapElement: ResToAdd.entrySet()){
            Resource r = mapElement.getKey();
            playerDashboard.getVault().AddResource(r, mapElement.getValue());
        }
    }
}