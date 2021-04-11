package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.gameboard.playerdashboard.Ball;
import it.polimi.ingsw.model.gameboard.playerdashboard.Market;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

public class MarketTest {
    Market market = new Market();

    @Before public void testFillMatrix() {
       market.fillMatrix(market.getRandomBall());
    }

    @Test
    public void testGetChosenColor() throws InvalidChoiceException {
        market.fillMatrix(market.getRandomBall());
        ArrayList<Ball> testChosenBalls=market.getChosenColor(3);

        assertEquals(testChosenBalls.get(0).getType(), market.getRamp().getType());
        assertEquals(testChosenBalls.get(1).getType(), market.getMatrix()[0][2].getType());
        assertEquals(testChosenBalls.get(2).getType(), market.getMatrix()[1][2].getType());

        testChosenBalls= market.getChosenColor(6);
        assertEquals(testChosenBalls.get(0).getType(), market.getRamp().getType());
        assertEquals(testChosenBalls.get(1).getType(), market.getMatrix()[1][0].getType());
        assertEquals(testChosenBalls.get(2).getType(), market.getMatrix()[1][1].getType());
        assertEquals(testChosenBalls.get(3).getType(), market.getMatrix()[1][2].getType());

    }
}