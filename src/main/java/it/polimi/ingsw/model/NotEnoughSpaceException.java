package it.polimi.ingsw.model;

public class NotEnoughSpaceException extends Exception {
    NotEnoughSpaceException(){
        super("There is not enough space!");
    }
}
