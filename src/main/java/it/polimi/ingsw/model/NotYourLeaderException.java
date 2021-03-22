package it.polimi.ingsw.model;

public class NotYourLeaderException extends Exception {
    NotYourLeaderException(){
        super("The chosen leader is not in your possession!");
    }
}
