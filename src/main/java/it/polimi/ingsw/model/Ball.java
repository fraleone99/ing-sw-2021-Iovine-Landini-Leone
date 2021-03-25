package it.polimi.ingsw.model;

/**
 * Ball Class manages every ball of the market
 *
 * @author Nicola Landini
 */

public class Ball {

    private BallColor type;

    public Ball(BallColor type) {
        this.type = type;
    }


    public boolean increaseResources(){
        return !type.equals(BallColor.WHITE);
    }

    public boolean moveForwardFaith(){
        return type.equals(BallColor.RED);
    }
}
