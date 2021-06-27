package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.gameboard.Ball;
import it.polimi.ingsw.model.gameboard.Market;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

public class MarketTest {
    private final Market market = new Market();

    @Test
    public void testGetChosenColor() throws InvalidChoiceException {
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