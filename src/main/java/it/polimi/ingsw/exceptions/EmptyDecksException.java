package it.polimi.ingsw.exceptions;

/**
 * Class EmptyDecksException is thrown when there's a try to draw cards by empty deck.
 */
public class EmptyDecksException extends Exception {
    public EmptyDecksException(){
        super("The selected deck is empty!");
    }
}
