package it.polimi.ingsw.model;

/**
 * This is a subclass of Card, which describes the Leader Card.
 * The things in common for each leader card are the fact that they can be activated or discarded;
 * so here we have two methods to activate or discard them,
 * and two methods that inform us if they are activated or discarded.
 *
 * @author Lorenzo Iovine.
 */

public class LeaderCard extends Card{
    private boolean isDiscarded;
    private boolean isActive;

    public LeaderCard(int VictoryPoints) {
        super(VictoryPoints);
    }

    public void setIsDiscarded(){
        isDiscarded=true;
    }

    public boolean getIsDiscarded(){
        return isDiscarded;
    }

    public void setIsActive(){
        isActive=true;
    }

    public boolean getIsActive(){
        return isActive;
    }
}
