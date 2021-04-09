package it.polimi.ingsw.exceptions;

public class NotEnoughResourceException extends Exception {
    public NotEnoughResourceException(){
        super("There aren't enough resources");
    }
}
