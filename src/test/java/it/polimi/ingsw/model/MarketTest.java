package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import junit.framework.TestCase;

import java.util.ArrayList;

public class MarketTest extends TestCase {
    Market market = new Market();

    public void testFillMatrix() {
       market.fillMatrix(market.getRandomBall());
    }

    public void testGetChosenColor() throws InvalidChoiceException {
        market.fillMatrix(market.getRandomBall());
        ArrayList<Ball> testChosenBalls=market.getChosenColor(1);
    }
}