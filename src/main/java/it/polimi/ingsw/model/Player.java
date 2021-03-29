package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Player {

    private final String nickname;
    private LeaderCardDeck leaders;
    private PlayerDashboard playerDashboard;
    private int victoryPoints;
    private boolean first;


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

    public void buyCard(int choice){ //to change return type in devCard
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

    public void DiscardLeader(int pos) throws InvalidChoiceException {
        leaders.DrawFromPosition(pos);
    }


    public String getNickname() {
        return nickname;
    }
}