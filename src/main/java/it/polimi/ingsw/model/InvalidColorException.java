package it.polimi.ingsw.model;

public class InvalidColorException extends Exception {
    InvalidColorException(){
            super("The selected color is invalid!");
        }
}
