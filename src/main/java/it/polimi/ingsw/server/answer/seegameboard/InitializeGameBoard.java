package it.polimi.ingsw.server.answer.seegameboard;

import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;

public class InitializeGameBoard implements Answer {
    private final boolean crashed;
    private final Market market;
    private final ArrayList<Integer> IdDevCards = new ArrayList<>();
    private final ArrayList<Integer> leaderCards = new ArrayList<>();
    private final boolean isActive1;
    private final boolean isDiscarded1;
    private final boolean isActive2;
    private final boolean isDiscarded2;


    public InitializeGameBoard(boolean crashed, Market market, ArrayList<Integer> idCards, ArrayList<Integer> leader, boolean isActive1, boolean isDiscarded1, boolean isActive2, boolean isDiscarded2) {
        this.crashed = crashed;
        this.market = market;
        this.IdDevCards.addAll(idCards);
        this.leaderCards.addAll(leader);
        this.isActive1 = isActive1;
        this.isDiscarded1 = isDiscarded1;
        this.isActive2 = isActive2;
        this.isDiscarded2 = isDiscarded2;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public boolean isActive1() {
        return isActive1;
    }

    public boolean isActive2() {
        return isActive2;
    }

    public boolean isDiscarded1() {
        return isDiscarded1;
    }

    public boolean isDiscarded2() {
        return isDiscarded2;
    }

    public ArrayList<Integer> getIdDevCards() {
        return IdDevCards;
    }

    public Market getMarket() {
        return market;
    }

    public ArrayList<Integer> getLeaderCards() {
        return leaderCards;
    }

    public Market getMessage() {
        return null;
    }
}
