package it.polimi.ingsw.model.singleplayer;

import java.io.Serializable;

/**
 * This class extends ActionToken and represents the action token
 * that imposes Lorenzo's faith path move forward or action token shuffle
 *
 * @author Nicola Landini
 */

public class BlackCrossMover extends ActionToken implements Serializable {

    int steps;
    boolean shuffle;

    /**
     * BlackCrossMover constructor: creates a new instance of BlackCrossMover
     * @param steps indicates how many steps are imposes to Lorenzo's faith path
     * @param shuffle indicates if action token have to be shuffled
     */
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
