package it.polimi.ingsw.model;


import it.polimi.ingsw.exceptions.NotExistingSpaceException;
import it.polimi.ingsw.model.gameboard.playerdashboard.FaithPath;
import org.junit.Test;

public class FaithPathTest {
    private FaithPath faithPath;

    @Test
    public void testIsPopeRegion() throws NotExistingSpaceException {
        faithPath=new FaithPath();
        faithPath.setPositionFaithPath(12);
        assert(faithPath.isPopeRegion(2));
    }

    @Test(expected = NotExistingSpaceException.class)
    public void testIsPopeRegion_NotExistingSpace() throws NotExistingSpaceException {
        faithPath=new FaithPath();
        faithPath.setPositionFaithPath(12);
        faithPath.isPopeRegion(5);
    }
}