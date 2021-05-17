package it.polimi.ingsw.model.gameboard.playerdashboard;

import it.polimi.ingsw.model.Goods;
import it.polimi.ingsw.model.enumeration.Resource;

/**
 * Vault Class describes the Vault of a player that can store unlimited Resources of any type
 *
 * @author Francesco Leone
 */
public class Vault {
    private int coinsAmount;
    private int servantsAmount;
    private int stoneAmount;
    private int shieldsAmount;

    /**
     * Vault constructor: creates a new instance of vault
     */
    public Vault() {
        coinsAmount = 0;
        servantsAmount = 0;
        stoneAmount = 0;
        shieldsAmount = 0;
    }

    /**
     * This method increases the selected resources amount of a number equal to amount
     * @param type resource type
     * @param amount of resource to add
     */
    public void AddResource(Resource type, int amount){
        if(type.equals(Resource.COIN)) coinsAmount += amount;
        else if(type.equals(Resource.SERVANT)) servantsAmount += amount;
        else if(type.equals(Resource.SHIELD)) shieldsAmount += amount;
        else if(type.equals(Resource.STONE)) stoneAmount += amount;
    }

    /**
     * This method returns the amount of a selected resource
     * @param type resource type
     * @return resource amount
     */
    public int getResource(Resource type){
        if(type.equals(Resource.COIN)) return coinsAmount;
        else if(type.equals(Resource.SERVANT)) return servantsAmount;
        else if(type.equals(Resource.SHIELD)) return shieldsAmount;
        else return stoneAmount;
    }

    /**
     * This method decreases the selected resources amount of a number equal to amount
     * @param type resource type
     * @param amount of resource to add
     */
    public void removeResource(Resource type, int amount){
        if(type.equals(Resource.COIN)) coinsAmount -= amount;
        else if(type.equals(Resource.SERVANT)) servantsAmount -= amount;
        else if(type.equals(Resource.SHIELD)) shieldsAmount -= amount;
        else if(type.equals(Resource.STONE)) stoneAmount -= amount;
    }

    /**
     * This method remove all resources that can be removed from the vault
     * @param needed resources that have to be removed
     * @return number of remaining resources that cannot be removed
     */
    public int checkInput(Goods needed){

        if(getResource(needed.getType()) == 0) return needed.getAmount();
        else if(getResource(needed.getType()) > needed.getAmount()) return 0;
        else return needed.getAmount() - getResource(needed.getType());
    }

    /**
     * This method calculates the total amount of resources
     * @return the total amount of resources in the vault
     */
    public int getTotalResources(){
        return coinsAmount+servantsAmount+stoneAmount+shieldsAmount;
    }
}
