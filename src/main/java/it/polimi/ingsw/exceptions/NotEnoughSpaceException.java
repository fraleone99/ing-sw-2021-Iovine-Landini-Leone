package it.polimi.ingsw.exceptions;

public class NotEnoughSpaceException extends Exception {
    public NotEnoughSpaceException(){
        super("There is not enough space!");
    }
}
