package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.enumeration.Resource;

import java.io.Serializable;

/**
 * Ball Class manages every marble of the market
 *
 * @author Nicola Landini, Francesco Leone
 */

public class Ball implements Serializable {

    private final BallColor type;

    /**
     * Ball constructor: create a new instance of ball by the color
     * @param type color of the ball
     */
    public Ball(BallColor type) {
        this.type = type;
    }

    /**
     * Ball constructor: creates a new instance of ball by a ball
     * @param ball ball
     */
    public Ball(Ball ball){
        this.type=ball.getType();
    }


    public BallColor getType() {
        return type;
    }

    /**
     * This method is used to get the corresponding resource of this marble
     * @return the resource corresponding to the color of the marble
     */
    public Resource getCorrespondingResource(){
        switch (this.type){
            case RED:
                return null;
            case BLUE:
                return Resource.SHIELD;
            case GREY:
                return Resource.STONE;
            case WHITE:
                return Resource.UNKNOWN;
            case PURPLE:
                return Resource.SERVANT;
            case YELLOW:
                return Resource.COIN;
        }
        return  null;
    }
}
