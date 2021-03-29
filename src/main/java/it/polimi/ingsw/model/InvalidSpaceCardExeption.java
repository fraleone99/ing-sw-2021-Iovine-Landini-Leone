package it.polimi.ingsw.model;

public class InvalidSpaceCardExeption extends Exception {
    public InvalidSpaceCardExeption(){
        super("You cannot add this card in the chosen space.");
    }
}
