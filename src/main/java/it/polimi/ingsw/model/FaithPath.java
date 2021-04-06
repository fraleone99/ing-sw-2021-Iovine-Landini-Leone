package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NotExistingSpaceException;

import java.util.ArrayList;

/**
 * FaithPath Class is used for handling the position changes of each player
 *
 * @author Nicola Landini
 */

public class FaithPath {
    private ArrayList<Cell> Space;
    private int positionFaithPath;
    private boolean papalPawn1;
    private boolean papalPawn2;
    private boolean papalPawn3;

    public void moveForward(int move){
        positionFaithPath = positionFaithPath+move;
    }

    //spaceIndicator indicates which papal space
    public boolean isPopeRegion(int spaceIndicator) throws NotExistingSpaceException {
        if (spaceIndicator != 1 && spaceIndicator != 2 && spaceIndicator != 3) {
            throw new NotExistingSpaceException();
        }

        if (positionFaithPath > 4 && spaceIndicator == 1) {
            return true;
        } else if (positionFaithPath > 11 && spaceIndicator == 2) {
            return true;
        } else return positionFaithPath > 18;
    }
}
