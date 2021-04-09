package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for decks of Development Cards
 *
 * @author Francesco Leone
 */

public class DevelopmentCardDeck implements Deck {

    private ArrayList<DevelopmentCard> deck = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    public void shuffle(){
        Collections.shuffle(deck);
    }

    /**
     * Draw a card from the deck. The Card is removed from the deck
     * @return the last card of the deck.
     */
    public DevelopmentCard draw() throws InvalidChoiceException {
        if(deck.isEmpty()) {
            throw new InvalidChoiceException();
        }
        return deck.remove(deck.size()-1);
    }

    /**
     * Add a card to the deck
     * @param card will be added to the deck
     */
    public void add(DevelopmentCard card){
        deck.add(card);
    }

    /**
     * {@inheritDoc}
     */
    public DevelopmentCard get(){
        return deck.get(deck.size()-1);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmpty(){
        return deck.isEmpty();
    }

    public int checkDeck(CardColor color, int level){
        int ris = 0;
        for(DevelopmentCard d: deck){
            if(level == 0 && d.getColor().equals(color)){
                ris++;
            }
            else if(level != 0 && d.getColor().equals(color) && d.getLevel() == level)
                ris++;
        }
        return ris;
    }

    public int size(){ return deck.size(); }
}
