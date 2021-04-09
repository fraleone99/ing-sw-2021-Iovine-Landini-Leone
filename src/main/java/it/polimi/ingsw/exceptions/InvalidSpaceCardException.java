package it.polimi.ingsw.exceptions;

public class InvalidSpaceCardException extends Exception{
    public InvalidSpaceCardException(){
        super("You cannot add this card in the chosen space.");
    }
}
