package it.polimi.ingsw.model;

/**
 * The storage leader is a subclass of Leader Card.
 * This leader offers a storage of 2 spaces
 * for one type of resource.
 *
 * @author Lorenzo Iovine.
 */
public class StorageLeader extends LeaderCard{
    private Resource activationCost;
    private Resource storageType;
    private int resourceAmount;

    public StorageLeader(int VictoryPoints, Resource cost, Resource type)
    {
        super(VictoryPoints);
        this.activationCost = cost;
        this.storageType = type;
    }

    public Resource getActivationCost(){ return activationCost; }

    public Resource getStorageType(){
        return storageType;
    }

    public int getAmount(){
        return resourceAmount;
    }

    public void addResource(int amount){
        resourceAmount += amount;
    }

    public void removeResource(int amount){
        resourceAmount -= amount;
    }
}
