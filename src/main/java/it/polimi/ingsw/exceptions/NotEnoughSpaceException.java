package it.polimi.ingsw.exceptions;

/**
 * Class NotEnoughSpaceException is thrown when there's a try to put
 * a resource on a shelf without free spaces.
 */
public class NotEnoughSpaceException extends Exception {
    public NotEnoughSpaceException(){
        super("There is not enough space!");
    }
}
