package it.polimi.ingsw.exceptions;

public class EmptyDecksException extends Exception {
    public EmptyDecksException(){
        super("The selected deck is empty!");
    }
}
