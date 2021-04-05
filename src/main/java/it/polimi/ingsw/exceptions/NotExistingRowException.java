package it.polimi.ingsw.exceptions;

public class NotExistingRowException extends Exception {
    public NotExistingRowException(){
        super("The selected row does not exist!");
    }
}
