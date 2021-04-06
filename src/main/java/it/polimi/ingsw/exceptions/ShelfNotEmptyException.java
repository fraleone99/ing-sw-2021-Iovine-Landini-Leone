package it.polimi.ingsw.exceptions;

public class ShelfNotEmptyException extends Exception {
    public ShelfNotEmptyException(){
        super("The Shelf is not empty!");
    }
}
