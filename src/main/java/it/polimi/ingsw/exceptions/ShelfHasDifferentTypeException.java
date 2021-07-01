package it.polimi.ingsw.exceptions;

/**
 * Class ShelfHasDifferentTypeException is thrown when there's a try to put
 * a resource on a shelf with another type of resources
 */
public class ShelfHasDifferentTypeException extends Exception {
    public ShelfHasDifferentTypeException(){
        super("The selected shelf has resources of another Type");
    }
}
