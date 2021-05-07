package it.polimi.ingsw.model.singleplayer;

import java.io.Serializable;

public class BlackCrossMover extends ActionToken implements Serializable {

    int steps;
    boolean shuffle;

    public BlackCrossMover(int steps, boolean shuffle) {
        this.steps = steps;
        this.shuffle=shuffle;
    }

    public int getSteps(){
        return steps;
    }

    public boolean haveToBeShuffled(){
        return shuffle;
    }
}
