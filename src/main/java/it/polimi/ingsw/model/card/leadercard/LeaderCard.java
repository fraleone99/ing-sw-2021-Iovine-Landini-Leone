package it.polimi.ingsw.model.card.leadercard;

import it.polimi.ingsw.model.gameboard.playerdashboard.PlayerDashboard;
import it.polimi.ingsw.model.card.Card;

import java.util.ArrayList;

/**
 * This is a subclass of Card, which describes the Leader Card.
 * The things in common for each leader card are the fact that they can be activated or discarded;
 * so here we have two methods to activate or discard them,
 * and two methods that inform us if they are activated or discarded.
 *
 * @author Lorenzo Iovine.
 */

public abstract class LeaderCard extends Card {
    private boolean isDiscarded;
    private boolean isActive;

    public LeaderCard(int VictoryPoints, int CardID) {
        super(VictoryPoints, CardID);
    }

    public void setIsDiscarded(){
        isDiscarded=true;
    }

    public void setIsNotDiscarded() {isDiscarded=false;}

    public void setIsNotActive() {isActive=false;}

    public boolean getIsDiscarded(){
        return isDiscarded;
    }

    public void setIsActive(){
        isActive=true;
    }

    public boolean getIsActive(){
        return isActive;
    }

    public abstract ArrayList<Requirements> getRequirements();

    public abstract boolean checkRequirements(PlayerDashboard playerDashboard);

}
