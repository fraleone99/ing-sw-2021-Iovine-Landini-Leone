package it.polimi.ingsw.model.gameboard.playerdashboard;

import it.polimi.ingsw.model.enumeration.Resource;

/**
 * Shelf Class used for managing the Storage that is composed of 3 shelves
 *
 * @author Francesco Leone
 */

public class Shelf {
    private final int shelfDimension;
    private Resource resourceType;
    private int resourceAmount;

    public Shelf(int shelfDimension, int resourceAmount, Resource resourceType) {
        this.shelfDimension = shelfDimension;
        this.resourceAmount = resourceAmount;
        this.resourceType = resourceType;
    }

    public int getShelfDimension() {
        return shelfDimension;
    }
    public Resource getResourceType() {
        return resourceType;
    }

    public int getAmount(){
        return resourceAmount;
    }

    public int getAvailableSpace(){
        return shelfDimension - resourceAmount;
    }

    public void ChangeResourceType(Resource newType){
        resourceType = newType;
        resourceAmount = 0;
    }

    public void discardResource(int amount){
        resourceAmount -= amount;
    }

    public void AddResource(int amount){
        resourceAmount += amount;
    }

    public boolean isFree(){ return  resourceAmount == 0;}
}
