package it.polimi.ingsw.model;

public class NotExistingColumnException extends Exception {
    NotExistingColumnException(){
        super("The selected column does not exist!");
    }
}
