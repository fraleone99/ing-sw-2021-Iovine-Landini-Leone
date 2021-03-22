package it.polimi.ingsw.model;

public class ShelfNotEmptyException extends Exception {
    ShelfNotEmptyException(){
        super("The Shelf is not empty!");
    }
}
