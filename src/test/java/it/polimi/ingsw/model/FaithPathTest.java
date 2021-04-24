package it.polimi.ingsw.model;


import it.polimi.ingsw.exceptions.NotExistingSpaceException;
import it.polimi.ingsw.model.gameboard.playerdashboard.FaithPath;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class FaithPathTest {
    private FaithPath faithPath;

    @Test
    public void testActivatePapalPawn() throws NotExistingSpaceException{
        faithPath=new FaithPath();
        faithPath.setPositionFaithPath(12);
        faithPath.activatePapalPawn(2);
        assert(faithPath.isPapalPawn2());
    }

    @Test(expected = NotExistingSpaceException.class)
    public void testActivatePapalPawn_NotExistingSpace() throws NotExistingSpaceException{
        faithPath=new FaithPath();
        faithPath.setPositionFaithPath(12);
        faithPath.activatePapalPawn(5);
    }

    @Test
    public void testGetTotalPoint() throws NotExistingSpaceException{
        faithPath=new FaithPath();
        faithPath.setPositionFaithPath(19);
        faithPath.activatePapalPawn(3);
        assertEquals(faithPath.getTotalPoint(), 15);
    }
}