package it.polimi.ingsw.model;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for decks of Leader Cards
 *
 * @author Francesco Leone
 */
public class LeaderCardDeck {
    private ArrayList<LeaderCard> deck = new ArrayList<>();

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
    public LeaderCard drawFromTail(){
        return deck.isEmpty() ? null : deck.remove(deck.size()-1);
    }

    /**
     * Add a card to the deck
     * @param card will be added to the deck
     */
    public void add(LeaderCard card){
        deck.add(card);
    }

    /**
     * {@inheritDoc}
     */
    public LeaderCard get(){
        return deck.get(deck.size()-1);
    }

    public LeaderCard get(int pos){
        return deck.get(pos);
    }

    public boolean isEmpty(){
        return deck.isEmpty();
    }

    public void DrawFromPosition(int position) throws InvalidChoiceException{
        if( position <= deck.size()-1 ) {
            deck.remove(position);
        } else
            throw new InvalidChoiceException();
    }

}
