package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Player {

    private String nickname;
    private ArrayList<LeaderCard> leaders = new ArrayList<>();
    private PlayerDashboard playerDashboard;
    private int victoryPoints;
    private boolean first;


    public Player(String nickname) {
        this.nickname = nickname;
    }

    public boolean isFirst() {
        return first;
    }

    public void discardLeaderFirstRound(LeaderCard leader1, LeaderCard leader2) throws NotYourLeaderException {
        if (!(leaders.contains(leader1)) || !(leaders.contains(leader2))) {
            throw new NotYourLeaderException();
        }
        leaders.remove(leader1);
        leaders.remove(leader2);
    }

    public void buyCard(int row, int col){ //to change return type in devCard
        //TODO
    }

    public void UseMarket(int choice){
        //TODO
    }

    public void ActiveProduction(){
        //TODO
    }

    public void ActiveLeader(LeaderCard leader){
        //TODO
    }

    public void DiscardLeader(LeaderCard leader) throws  NotYourLeaderException{
        if (!(leaders.contains(leader))) {
            throw new NotYourLeaderException();
        }

        leaders.remove(leader);

        //TODO moveForwardFaithPath()
    }


    //move resource from s1 to s2 that is empty
    public void MoveResourceToEmptyShelf(Shelf s1, Shelf s2) throws ShelfNotEmptyException, NotEnoughSpaceException{
        if(!s2.isFree()) throw new ShelfNotEmptyException();
        if(s2.getAvailableSpace() < s1.getAmount()) throw new NotEnoughSpaceException();

        s2.ChangeResourceType(s1.getResourceType());
        s2.AddResource(s1.getAmount());
        s1.discardResource(s1.getAmount());
    }


    //move the resource of s1 in s2 and the ones in s2 to s1;
    public void InvertShelvesContent(Shelf s1, Shelf s2) throws NotEnoughSpaceException{
        if(s1.getShelfDimension() < s2.getAmount() || s2.getShelfDimension() < s1.getAmount()) throw new NotEnoughSpaceException();
        Resource TempType = s1.getResourceType();
        int TempAmount = s1.getAmount();

        s1.ChangeResourceType(s2.getResourceType());
        s1.AddResource(s2.getAmount());

        s2.ChangeResourceType(TempType);
        s2.AddResource(TempAmount);

    }

    public String getNickname() {
        return nickname;
    }
}
