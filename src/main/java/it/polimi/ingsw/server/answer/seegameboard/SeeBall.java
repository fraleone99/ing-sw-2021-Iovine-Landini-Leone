package it.polimi.ingsw.server.answer.seegameboard;

import it.polimi.ingsw.model.gameboard.Ball;
import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;


/**
 * Message containing the balls requested from market.
 *
 * @author Lorenzo Iovine
 */
public class SeeBall implements Answer {
    private final ArrayList<Ball> balls;

    public SeeBall(ArrayList<Ball> balls) {
        this.balls = balls;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    @Override
    public Object getMessage() {
        return null;
    }
}
