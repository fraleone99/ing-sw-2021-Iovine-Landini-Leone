package it.polimi.ingsw.model;

import java.util.ArrayList;

import it.polimi.ingsw.exceptions.NotEnoughSpaceException;
import it.polimi.ingsw.exceptions.ShelfHasDifferentTypeException;

/**
 * The storage leader is a subclass of Leader Card.
 * This leader offers a shelf of 2 spaces
 * for one type of resource.
 *
 * @author Lorenzo Iovine.
 */
public class StorageLeader extends LeaderCard{
    private Shelf LeaderShelf;
    private ArrayList<Requirements> requirements = new ArrayList<>();

    public StorageLeader(int VictoryPoints, Requirements req, Shelf s)
    {
        super(VictoryPoints);
        requirements.add(req);
        this.LeaderShelf=s;
    }

    /*
    public void DiscardResources(int amount) throws NotEnoughResourceException {
        if(LeaderShelf.getAmount() >= amount){
            LeaderShelf.discardResource(amount);
        }
        else throw new NotEnoughResourceException();
    }*/
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


    public void AddResources(Resource type, int amount) throws NotEnoughSpaceException, ShelfHasDifferentTypeException {

        if (!LeaderShelf.getResourceType().equals(type)) throw new ShelfHasDifferentTypeException();

        if (LeaderShelf.getAvailableSpace() < amount) throw new NotEnoughSpaceException();

        LeaderShelf.AddResource(amount);
    }

    public Resource getType(){
        return LeaderShelf.getResourceType();
    }

    public int getAmount(){return LeaderShelf.getAmount();}

    public ArrayList<Requirements> getRequirements(){
        return requirements;
    }

    public boolean checkRequirements(PlayerDashboard dashboard){
        return dashboard.CheckResource(requirements.get(0).getCost());
    }

}
