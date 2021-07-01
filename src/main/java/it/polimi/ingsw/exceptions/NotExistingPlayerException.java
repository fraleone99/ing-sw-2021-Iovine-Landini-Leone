package it.polimi.ingsw.exceptions;

/**
 * Class NotExistingPlayerException is thrown when the called player doesn't exist.
 */
public class NotExistingPlayerException extends Exception {
    public NotExistingPlayerException(){
        super("This Player does not exist! Try another nickname");
    }
}
