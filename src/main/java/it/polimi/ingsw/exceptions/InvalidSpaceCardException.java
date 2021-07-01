package it.polimi.ingsw.exceptions;

/**
 * Class InvalidSpaceCardException is thrown when there's a try
 * to insert a card in an invalid space.
 */
public class InvalidSpaceCardException extends Exception{
    public InvalidSpaceCardException(){
        super("You cannot add this card in the chosen space.");
    }
}
