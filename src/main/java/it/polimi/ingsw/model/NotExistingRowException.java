package it.polimi.ingsw.model;

public class NotExistingRowException extends Exception {
    NotExistingRowException(){
        super("The selected row does not exist!");
    }
}
