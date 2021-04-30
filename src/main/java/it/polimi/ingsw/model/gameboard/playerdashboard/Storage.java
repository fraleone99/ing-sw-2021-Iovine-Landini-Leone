package it.polimi.ingsw.model.gameboard.playerdashboard;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Goods;
import it.polimi.ingsw.model.enumeration.Resource;

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

    /**
     * Method getTotalResources returns the total number of resources in the storage
     *
     * @return int - the total number of resources in the storage
     */
    public int getTotalResources(){
        int sum  = 0;
        for(Shelf s : shelves){
            sum += s.getAmount();
        }
        return  sum;
    }

    /**
     * Method isThisTypePresent searches if a type is present in the storage and if so returns the Shelf on which are
     * located the resources of that type
     *
     * @param type of type Resource is the type we are looking for
     * @return null if the type is not present null otherwise
     */
    public Shelf isThisTypePresent( Resource type){
        for(Shelf s : shelves){
            if(s.getResourceType().equals(type) && s.getAmount() > 0){
                return s;
            }
        }
        return null;
    }

    /**
     * Method DiscardResources discards the selected amount of resources from the selected shelf
     *
     * @param s is the shelf from which the resources wil be discarded
     * @param amount is the amount of resources that will be discarded from the selected shelf
     * @throws NotEnoughResourceException when amount is grater then the amount of resources on the shelf
     * @throws InvalidChoiceException when the chosen shelf doesn't exist
     */
    public void DiscardResources(int s, int amount) throws NotEnoughResourceException, InvalidChoiceException{
        if(s<0 || s>3) throw new  InvalidChoiceException();

        if(shelves.get(s-1).getAmount() >= amount){
            shelves.get(s-1).discardResource(amount);
        }
        else throw new NotEnoughResourceException();
    }

    public int DiscardResources(Resource type, int amount){
        Shelf s = isThisTypePresent(type);

        if(s == null) return amount;

        else if(s.getAmount() >= amount){
            s.discardResource(amount);
            return 0;
        }
        else{
            int temp = s.getAmount();
            s.discardResource(s.getAmount());
            return amount - temp;
        }
    }

    /**
     * Method AddResources add resources to the selected shelf
     *
     * @param s is the shelf where the resources will be added
     * @param type is the type of resources the will be added
     * @param amount is the amount of resources that will be added
     * @throws NotEnoughSpaceException when there isn't enough space to store all the resources in the selected  shelf
     * @throws AnotherShelfHasTheSameTypeException when there is another shelf with the same type of Resources
     * @throws ShelfHasDifferentTypeException when the selected shelf contains already dome resources of a different type
     */
    public void AddResource(int s, Resource type, int amount) throws NotEnoughSpaceException, AnotherShelfHasTheSameTypeException,
            ShelfHasDifferentTypeException {


        if (isThisTypePresent(type) != null && !isThisTypePresent(type).equals(shelves.get(s-1)))  throw new AnotherShelfHasTheSameTypeException();

        if (!shelves.get(s-1).getResourceType().equals(type) && !shelves.get(s-1).isFree()) throw new ShelfHasDifferentTypeException();

        if (shelves.get(s-1).getAvailableSpace() < amount) throw new NotEnoughSpaceException();

        shelves.get(s-1).ChangeResourceType(type);
        shelves.get(s-1).AddResource(amount);
    }

    /**
     * Method MoveResourceToEmptyShelf moves resources from a shelf to another that is empty
     *
     * @param s1 is the shelf form which the resources will be moved from
     * @param s2 is the shelf form which the resources will be moved to
     * @throws ShelfNotEmptyException when s2 is not empty
     * @throws NotEnoughSpaceException when in s2 there isn't enough space for all the resources in s1
     */
    public void MoveResourceToEmptyShelf(int s1, int s2) throws ShelfNotEmptyException, NotEnoughSpaceException{
        if(!shelves.get(s2-1).isFree()) throw new ShelfNotEmptyException();
        if(shelves.get(s2-1).getAvailableSpace() < shelves.get(s1-1).getAmount()) throw new NotEnoughSpaceException();

        shelves.get(s2-1).ChangeResourceType(shelves.get(s1-1).getResourceType());
        shelves.get(s2-1).AddResource(shelves.get(s1-1).getAmount());
        shelves.get(s1-1).discardResource(shelves.get(s1-1).getAmount());
    }


    /**
     * Method InvertShelvesContent moves resources form shelf1 to shelf2 and from shelf2 to shelf1
     *
     * @param s1 first shelf
     * @param s2 second shelf
     * @throws NotEnoughSpaceException when in one of the two shelves there isn't enough spaces for storing all the
     * resources of the other shelf
     */
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

    public int checkInput(Goods needed){
        Shelf shelf = isThisTypePresent(needed.getType());

        if(shelf == null) return needed.getAmount();
        else if(shelf.getAmount() > needed.getAmount()) return 0;
        else return needed.getAmount() - shelf.getAmount();
    }

    public int getAmountShelf(int s){
        return shelves.get(s-1).getAmount();

    }

    public Resource getTypeShelf(int s){
        return shelves.get(s-1).getResourceType();
    }

}
