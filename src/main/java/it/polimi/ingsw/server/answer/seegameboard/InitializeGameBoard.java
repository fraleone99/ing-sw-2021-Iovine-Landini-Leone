package it.polimi.ingsw.server.answer.seegameboard;

import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;

public class InitializeGameBoard implements Answer {
    private final Market market;
    private final ArrayList<Integer> IdDevCards = new ArrayList<>();
    private final ArrayList<Integer> leaderCards = new ArrayList<>();


    public InitializeGameBoard(Market market, ArrayList<Integer> idCards, ArrayList<Integer> leader) {
        this.market=market;
        this.IdDevCards.addAll(idCards);
        this.leaderCards.addAll(leader);
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
