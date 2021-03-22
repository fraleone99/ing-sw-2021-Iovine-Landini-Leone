package it.polimi.ingsw.model;

public class NotExistingPlayerException extends Exception {
    NotExistingPlayerException(){
        super("This Player does not exist! Try another nickname");
    }
}
