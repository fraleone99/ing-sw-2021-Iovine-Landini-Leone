package it.polimi.ingsw.model;

import java.util.ArrayList;


/**
 * Class for the storage of the player dashboard
 * The storage contains 3 shelves with different dimension (1,2,3)
 * @author Francesco Leone
 */
public class Storage {
    private ArrayList<Shelf> shelves = new ArrayList<>();

    public Storage() {
        Shelf shelf1 = new Shelf(1,0);
        Shelf shelf2 = new Shelf(2,0);
        Shelf shelf3 = new Shelf(3,0);

        shelves.add(shelf1);
        shelves.add(shelf2);
        shelves.add(shelf3);
    }


    public int GetTotalResources(){
        int sum  = 0;
        for(Shelf s : shelves){
            sum += s.getAmount();
        }
        return  sum;
    }

    public Shelf isThisTypePresent( Resource type){
        for(Shelf s : shelves){
            if(s.getResourceType().equals(type)){
                return s;
            }
        }
        return null;
    }

    public void DiscardResources(Resource type, int amount) {
        for (Shelf s : shelves) {
            if (s.getResourceType().equals(type)) {
                s.discardResource(amount);
                //TODO: others players must go ahead in the faith path MoveForwardOtherPlayersFaith(amount)
            }
        }


    }
    public void AddResource(Resource type, int amount) throws NotEnoughSpaceException, TooMuchResourceForStorageException {

        //it is impossible to add more than 3 resources of the same type in the Storage
        if(amount > 3) {
            throw new TooMuchResourceForStorageException();
        }

        //Search for a shelf with the same resource type and add the resources if possible
        for (Shelf s : shelves) {
            if(s.getResourceType().equals(type)) {
                    if (s.getAvailableSpace() >= amount) {
                        s.AddResource(amount);
                    } else {
                        throw new NotEnoughSpaceException();
                    }
            }
        }

        //Use a free shelf if present
        if(isThisTypePresent(type) == null){
            for (Shelf s : shelves) {
                if(s.isFree() && amount <= s.getShelfDimension()) {
                    s.ChangeResourceType(type);
                    s.AddResource(amount);
                }
            }
        }


    }

    public int getAmountOfaType(Resource type){
        for (Shelf s : shelves) {
            if(s.getResourceType().equals(type)) {
              return s.getAmount();
            }
        }
        return 0;
    }

}
