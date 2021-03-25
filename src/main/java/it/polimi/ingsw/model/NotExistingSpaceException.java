package it.polimi.ingsw.model;

public class NotExistingSpaceException extends Exception {
    NotExistingSpaceException(){
        super("This popeSpace doesn't exist!");
    }
}
