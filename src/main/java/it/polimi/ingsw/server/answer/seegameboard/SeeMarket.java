package it.polimi.ingsw.server.answer.seegameboard;

import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.server.answer.Answer;


public class SeeMarket implements Answer {
    private final Market market;

    public SeeMarket(Market market) {
        this.market=market;
    }

    public Market getMessage() {
        return market;
    }
}