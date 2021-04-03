package it.polimi.ingsw.model;


public interface Deck {

    /**
     * Shuffles the Deck
     */
    void shuffle();


    /**
     *
     * @return the last card of the deck
     */
    Card get();

    /**
     *
     * @return if the deck is empty or not
     */
    boolean isEmpty();

}
