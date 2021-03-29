package it.polimi.ingsw.model;


public interface Deck {

    /**
     * Shuffles the Deck
     */
    void shuffle();


    Card get();

    boolean isEmpty();

}
