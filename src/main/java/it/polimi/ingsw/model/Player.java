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


    public String getNickname() {
        return nickname;
    }
}
