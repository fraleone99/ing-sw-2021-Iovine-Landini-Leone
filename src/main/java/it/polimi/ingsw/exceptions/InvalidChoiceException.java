package it.polimi.ingsw.exceptions;

/**
 * Class InvalidChoiceException is thrown when your choice is invalid
 */
public class InvalidChoiceException extends Exception {
    public InvalidChoiceException(){
        super("The selected choice is invalid!");
    }
}
