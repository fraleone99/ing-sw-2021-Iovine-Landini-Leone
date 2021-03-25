package it.polimi.ingsw.model;

public class InvalidChoiceException extends Exception {
    InvalidChoiceException(){
        super("The selected choice is invalid!");
    }
}
