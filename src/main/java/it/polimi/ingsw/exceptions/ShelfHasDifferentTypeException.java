package it.polimi.ingsw.exceptions;

public class ShelfHasDifferentTypeException extends Exception {
    public ShelfHasDifferentTypeException(){
        super("The selected shelf has resources of another Type");
    }
}
