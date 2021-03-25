package it.polimi.ingsw.model;

import java.util.ArrayList;


/**
 * Class for the storage of the player dashboard
 * The storage contains 3 shelves with different dimension (1,2,3)
 * Two shelves cannot contains the same type of resource
 *
 * @author Francesco Leone
 */
public class Storage {
    private final ArrayList<Shelf> shelves = new ArrayList<>();

    public Storage(Shelf shelf1, Shelf shelf2, Shelf shelf3) {
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
            if(s.getResourceType().equals(type) && s.getAmount() > 0){
                return s;
            }
        }
        return null;
    }

    public void DiscardResources(int s, int amount) throws NotEnoughResourceException {
        if(shelves.get(s-1).getAmount() >= amount){
            shelves.get(s-1).discardResource(amount);
        }
        else throw new NotEnoughResourceException();
    }

    public void AddResource(int s, Resource type, int amount) throws NotEnoughSpaceException, AnotherShelfHasTheSameTypeException,
            ShelfHasDifferentTypeException {


        if (isThisTypePresent(type) != null && !isThisTypePresent(type).equals(shelves.get(s-1)))  throw new AnotherShelfHasTheSameTypeException();

        if (!shelves.get(s-1).getResourceType().equals(type) && !shelves.get(s-1).isFree()) throw new ShelfHasDifferentTypeException();

        if (shelves.get(s-1).getAvailableSpace() < amount) throw new NotEnoughSpaceException();

        shelves.get(s-1).ChangeResourceType(type);
        shelves.get(s-1).AddResource(amount);
    }

    //move resource from s1 to s2 that is empty
    public void MoveResourceToEmptyShelf(int s1, int s2) throws ShelfNotEmptyException, NotEnoughSpaceException{
        if(!shelves.get(s2-1).isFree()) throw new ShelfNotEmptyException();
        if(shelves.get(s2-1).getAvailableSpace() < shelves.get(s1-1).getAmount()) throw new NotEnoughSpaceException();

        shelves.get(s2-1).ChangeResourceType(shelves.get(s1-1).getResourceType());
        shelves.get(s2-1).AddResource(shelves.get(s1-1).getAmount());
        shelves.get(s1-1).discardResource(shelves.get(s1-1).getAmount());
    }


    //move the resource of s1 in s2 and the ones in s2 to s1;
    public void InvertShelvesContent(int s1, int s2) throws NotEnoughSpaceException{
        if(shelves.get(s1-1).getShelfDimension() < shelves.get(s2-1).getAmount() || shelves.get(s2-1).getShelfDimension() < shelves.get(s2-1).getAmount())
            throw new NotEnoughSpaceException();

        Resource TempType = shelves.get(s1-1).getResourceType();
        int TempAmount = shelves.get(s1-1).getAmount();

        shelves.get(s1-1).ChangeResourceType(shelves.get(s2-1).getResourceType());
        shelves.get(s1-1).AddResource(shelves.get(s2-1).getAmount());

        shelves.get(s2-1).ChangeResourceType(TempType);
        shelves.get(s2-1).AddResource(TempAmount);

    }

}
