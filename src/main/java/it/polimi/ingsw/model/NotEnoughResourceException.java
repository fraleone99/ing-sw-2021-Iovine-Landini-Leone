package it.polimi.ingsw.model;

public class NotEnoughResourceException extends Exception {
    public NotEnoughResourceException(){
        super("There aren't enough resourorces");
    }
}
