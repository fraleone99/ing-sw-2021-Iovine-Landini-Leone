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

    /**
     * Shelf constructor: creates a new instance of shelf
     * @param shelfDimension is the dimension of the shelf
     * @param resourceAmount is the amount of resources contained in the shelf
     * @param resourceType is the type of resources contained in the shelf
     */
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

    /**
     * This method calculate the available spaces in the shelf
     * @return number of resources that can be added to the shelf
     */
    public int getAvailableSpace(){
        return shelfDimension - resourceAmount;
    }

    /**
     * This method changes the resource type in the shelf
     * @param newType new resource type of the shelf
     */
    public void changeType(Resource newType) {
        resourceType=newType;
    }

    /**
     * This method changes the resource type in the shelf, setting its amount
     * to zero
     * @param newType new resource type of the shelf
     */
    public void ChangeResourceType(Resource newType){
        resourceType = newType;
        resourceAmount = 0;
    }

    /**
     * This method remove resources from the shelf for an amount equal to the parameter
     * @param amount amount of resources to remove
     */
    public void discardResource(int amount){
        resourceAmount -= amount;
    }

    /**
     * This method add resources from the shelf for an amount equal to the parameter
     * @param amount amount of resources to add
     */
    public void AddResource(int amount){
        resourceAmount += amount;
    }

    /**
     * This method returns if the shelf is empty
     * @return true if the shelf is free
     */
    public boolean isFree(){ return  resourceAmount == 0;}
}
