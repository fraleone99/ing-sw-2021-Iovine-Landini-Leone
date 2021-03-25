package it.polimi.ingsw.model;

public class ShelfHasDifferentTypeException extends Exception {
    public ShelfHasDifferentTypeException(){
        super("The selected shelf has resources of another Type");
    }
}
