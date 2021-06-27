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

    /**
     * Storage constructor: creates a new instance of storage
     * @param shelf1 is the first shelf
     * @param shelf2 is the second shelf
     * @param shelf3 is the third shelf
     */
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
     * This method returns the shelf of the resource type given as parameter or 0
     * if not present
     * @param type resource that we are looking for
     * @return the number of the shelf that contains the resource, otherwise returns 0
     */
    public int typePresent(Resource type){
        for(Shelf s : shelves){
            if(s.getResourceType().equals(type) && s.getAmount() > 0){
                return (shelves.indexOf(s)+1);
            }
        }
        return 0;
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
     * Method DiscardResources discards the selected amount of resources from the storage
     * @param type is the resource type
     * @param amount is the number of resources that have to be removed
     * @return how many resources still need to be removed
     */
    public int discardResources(Resource type, int amount){
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
     * @throws ShelfHasDifferentTypeException when the selected shelf contains already some resources of a different type
     */
    public void addResources(int s, Resource type, int amount) throws NotEnoughSpaceException, AnotherShelfHasTheSameTypeException,
            ShelfHasDifferentTypeException {


        if (isThisTypePresent(type) != null && !isThisTypePresent(type).equals(shelves.get(s-1)))  throw new AnotherShelfHasTheSameTypeException();

        if (!shelves.get(s-1).getResourceType().equals(type) && !shelves.get(s-1).isFree()) throw new ShelfHasDifferentTypeException();

        if (shelves.get(s-1).getAvailableSpace() < amount) throw new NotEnoughSpaceException();

        shelves.get(s-1).changeType(type);
        shelves.get(s-1).addResource(amount);
    }

    /**
     * Method InvertShelvesContent moves resources form shelf1 to shelf2 and from shelf2 to shelf1
     *
     * @param s1 first shelf
     * @param s2 second shelf
     * @throws NotEnoughSpaceException when in one of the two shelves there isn't enough spaces for storing all the
     * resources of the other shelf
     */
    public void invertShelvesContent(int s1, int s2) throws NotEnoughSpaceException{
        if(shelves.get(s1-1).getShelfDimension() < shelves.get(s2-1).getAmount() || shelves.get(s2-1).getShelfDimension() < shelves.get(s1-1).getAmount())
            throw new NotEnoughSpaceException();

        Resource TempType = shelves.get(s1-1).getResourceType();
        int TempAmount = shelves.get(s1-1).getAmount();

        shelves.get(s1-1).changeResourceType(shelves.get(s2-1).getResourceType());
        shelves.get(s1-1).addResource(shelves.get(s2-1).getAmount());

        shelves.get(s2-1).changeResourceType(TempType);
        shelves.get(s2-1).addResource(TempAmount);

    }

    /**
     * Method checkInput return the difference between the amount in the storage and the amount needed
     *
     * @param needed is the good of which we check the input.
     * @return the difference between the amount in the storage and the amount needed
     */
    public int checkInput(Goods needed){
        Shelf shelf = isThisTypePresent(needed.getType());

        if(shelf == null) return needed.getAmount();
        else if(shelf.getAmount() > needed.getAmount()) return 0;
        else return needed.getAmount() - shelf.getAmount();
    }

    /**
     * This method return the amount of resources in a selected shelf
     * @param s number of the shelf
     * @return the amount of resources of the shelf
     */
    public int getAmountShelf(int s){
        return shelves.get(s-1).getAmount();
    }

    public ArrayList<Shelf> getShelves() {
        return shelves;
    }

    /**
     * This method calculates the number of empty shelves
     * @return the total amount of empty shelves
     */
    public int emptyShelves() {
        int cont=0;
        for(Shelf s : shelves) {
            if(s.isFree()) cont++;
        }
        return cont;
    }

    /**
     * This method return the resource type of a selected shelf
     * @param s number of the shelf
     * @return the resource of the selected shelf
     */
    public Resource getTypeShelf(int s){
        return shelves.get(s-1).getResourceType();
    }

}
