package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.Ball;
import org.junit.Test;

import static org.junit.Assert.*;

public class BallTest {

    @Test
    public void testGetCorrespondingResource(){

        Ball ball = new Ball(BallColor.BLUE);
        assertEquals(Resource.SHIELD, ball.getCorrespondingResource());

        ball = new Ball(BallColor.WHITE);
        assertEquals(Resource.UNKNOWN, ball.getCorrespondingResource());

        ball = new Ball(BallColor.RED);
        assertNull(ball.getCorrespondingResource());

        ball = new Ball(BallColor.GREY);
        assertEquals(Resource.STONE, ball.getCorrespondingResource());

        ball = new Ball(BallColor.PURPLE);
        assertEquals(Resource.SERVANT, ball.getCorrespondingResource());

        ball = new Ball(BallColor.YELLOW);
        assertEquals(Resource.COIN, ball.getCorrespondingResource());

    }

}
