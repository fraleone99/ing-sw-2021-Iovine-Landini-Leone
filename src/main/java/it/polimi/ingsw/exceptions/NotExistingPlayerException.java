package it.polimi.ingsw.exceptions;

public class NotExistingPlayerException extends Exception {
    public NotExistingPlayerException(){
        super("This Player does not exist! Try another nickname");
    }
}
