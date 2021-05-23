package it.polimi.ingsw.model.card.leadercard;

import it.polimi.ingsw.exceptions.NotEnoughSpaceException;
import it.polimi.ingsw.exceptions.ShelfHasDifferentTypeException;

import java.util.ArrayList;

import it.polimi.ingsw.model.gameboard.playerdashboard.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.playerdashboard.Shelf;

/**
 * The storage leader is a subclass of Leader Card.
 * This leader offers a shelf of 2 spaces
 * for one type of resource.
 *
 * @author Lorenzo Iovine.
 */
public class StorageLeader extends LeaderCard {
    private Shelf LeaderShelf;
    private ArrayList<Requirements> requirements = new ArrayList<>();


    /**
     * Constructor StorageLeader creates a new StorageLeader instance
     * @param VictoryPoints is the number of the victory points of the card
     * @param CardID is the ID of the card
     * @param req is the requirements of the card
     * @param s is the shelf of the card
     */
    public StorageLeader(int VictoryPoints, int CardID, String graphicPath, Requirements req, Shelf s)
    {
        super(VictoryPoints, CardID, graphicPath);
        requirements.add(req);
        this.LeaderShelf=s;
    }


    /**
     * Removes resources from the shelf of the card
     * @param amount is the number of resources to be removed
     * @return the available space of the shelf
     */
    public int DiscardResources(int amount) {
        if(LeaderShelf.getAmount() >= amount){
            LeaderShelf.discardResource(amount);
            return 0;
        }
        else {
            int temp = LeaderShelf.getAmount();
            LeaderShelf.discardResource(temp);
            return  amount - temp;
        }
    }


    /**
     * Adds resources at the shelf of the card
     * @param type is the type of the resource to be added
     * @param amount is the number of resources to be added
     * @throws NotEnoughSpaceException if the shelf doesn't have enough available space
     * @throws ShelfHasDifferentTypeException if the shelf cannot contain this type of resource
     */
    public void AddResources(Resource type, int amount) throws NotEnoughSpaceException, ShelfHasDifferentTypeException {

        if (!LeaderShelf.getResourceType().equals(type)) throw new ShelfHasDifferentTypeException();

        if (LeaderShelf.getAvailableSpace() < amount) throw new NotEnoughSpaceException();

        LeaderShelf.AddResource(amount);
    }


    /**
     * Gets the type of resource that the shelf can contain
     * @return the type of resource
     */
    public Resource getType(){
        return LeaderShelf.getResourceType();
    }


    /**
     * Gets the available space of the shelf
     * @return the number of free spaces
     */
    public int getAmount(){return LeaderShelf.getAmount();}


    /**
     * {@inheritDoc}
     */
    public ArrayList<Requirements> getRequirements(){
        return requirements;
    }


    /**
     * {@inheritDoc}
     */
    public boolean checkRequirements(PlayerDashboard dashboard){
        return dashboard.CheckResource(requirements.get(0).getCost());
    }
}
