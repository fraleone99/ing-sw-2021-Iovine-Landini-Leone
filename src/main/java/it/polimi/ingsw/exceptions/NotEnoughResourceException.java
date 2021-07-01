package it.polimi.ingsw.exceptions;

/**
 * Class NotEnoughResourceException is thrown when there's a try to
 * do something but the player hasn't got enough resources.
 */
public class NotEnoughResourceException extends Exception {
    public NotEnoughResourceException(){
        super("There aren't enough resources");
    }
}
