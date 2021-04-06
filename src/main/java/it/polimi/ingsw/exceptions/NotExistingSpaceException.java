package it.polimi.ingsw.exceptions;

public class NotExistingSpaceException extends Exception {
    public NotExistingSpaceException(){
        super("This popeSpace doesn't exist!");
    }
}
