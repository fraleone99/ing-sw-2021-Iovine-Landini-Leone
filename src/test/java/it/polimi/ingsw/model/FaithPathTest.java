package it.polimi.ingsw.model;


import it.polimi.ingsw.model.gameboard.playerdashboard.FaithPath;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class FaithPathTest {
    private FaithPath faithPath;

    @Test
    public void testActivatePapalPawn(){
        faithPath=new FaithPath();
        faithPath.setPositionFaithPath(12);
        faithPath.activatePapalPawn(2);
        assert(faithPath.isPapalPawn2());
    }

    @Test
    public void testGetTotalPoint(){
        faithPath=new FaithPath();
        faithPath.setPositionFaithPath(19);
        faithPath.activatePapalPawn(3);
        assertEquals(faithPath.getTotalPoint(), 15);
    }
}