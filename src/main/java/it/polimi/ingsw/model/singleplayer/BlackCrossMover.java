package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.singleplayer.ActionToken;

public class BlackCrossMover extends ActionToken {

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
