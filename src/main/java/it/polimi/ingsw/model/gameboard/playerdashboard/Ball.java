package it.polimi.ingsw.model.gameboard.playerdashboard;

import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.enumeration.Resource;

import java.io.Serializable;

/**
 * Ball Class manages every ball of the market
 *
 * @author Nicola Landini
 */

public class Ball implements Serializable {

    private final BallColor type;

    public Ball(BallColor type) {
        this.type = type;
    }

    public Ball(Ball ball){
        this.type=ball.getType();
    }


    public boolean increaseResources(){
        return !type.equals(BallColor.WHITE);
    }

    public boolean moveForwardFaith(){
        return type.equals(BallColor.RED);
    }

    public BallColor getType() {
        return type;
    }

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
