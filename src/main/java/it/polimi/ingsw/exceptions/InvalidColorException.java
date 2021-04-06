package it.polimi.ingsw.exceptions;

public class InvalidColorException extends Exception {
    public InvalidColorException(){
            super("The selected color is invalid!");
        }
}
