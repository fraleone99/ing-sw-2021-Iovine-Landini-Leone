package it.polimi.ingsw.exceptions;

public class InvalidChoiceException extends Exception {
    public InvalidChoiceException(){
        super("The selected choice is invalid!");
    }
}
