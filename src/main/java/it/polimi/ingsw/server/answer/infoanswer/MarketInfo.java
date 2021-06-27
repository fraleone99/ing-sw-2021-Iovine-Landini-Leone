package it.polimi.ingsw.server.answer.infoanswer;

import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.server.answer.Answer;

public class MarketInfo implements Answer {
    private final Market market;

    public MarketInfo(Market market) {
        this.market = market;
    }

    @Override
    public Object getMessage() {
        return market;
    }
}
