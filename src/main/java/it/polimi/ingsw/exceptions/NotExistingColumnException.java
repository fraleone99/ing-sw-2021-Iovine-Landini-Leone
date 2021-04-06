package it.polimi.ingsw.exceptions;

public class NotExistingColumnException extends Exception {
    public NotExistingColumnException(){
        super("The selected column does not exist!");
    }
}
