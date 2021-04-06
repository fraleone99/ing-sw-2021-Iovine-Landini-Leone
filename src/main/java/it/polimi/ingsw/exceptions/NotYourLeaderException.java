package it.polimi.ingsw.exceptions;

public class NotYourLeaderException extends Exception {
    public NotYourLeaderException(){
        super("The chosen leader is not in your possession!");
    }
}
