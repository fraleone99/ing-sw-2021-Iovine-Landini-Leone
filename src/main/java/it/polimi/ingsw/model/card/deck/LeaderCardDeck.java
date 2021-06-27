package it.polimi.ingsw.model.card.deck;

import it.polimi.ingsw.model.card.leadercard.LeaderCard;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for decks of Leader Cards
 *
 * @author Francesco Leone, Lorenzo Iovine
 */
public class LeaderCardDeck {
    private final ArrayList<LeaderCard> deck;


    /**
     * Constructor LeaderCardDeck creates a new LeaderCardDeck instance
     */
    public LeaderCardDeck() {
        deck = new ArrayList<>();
    }


    /**
     * Gets the deck
     * @return the deck
     */
    public ArrayList<LeaderCard> getDeck() {
        return deck;
    }


    /**
     * {@inheritDoc}
     */
    public void shuffle(){
        Collections.shuffle(deck);
    }


    /**
     * Removes a card from the deck
     * @param pos is the index of the card that will be removed
     */
    public void remove(int pos) {deck.remove(pos);}


    /**
     * Draws a card from the deck. The Card is removed from the deck
     * @return the last card of the deck.
     */
    public LeaderCard drawFromTail(){
        return deck.isEmpty() ? null : deck.remove(deck.size()-1);
    }


    /**
     * Adds a card to the deck
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


    /**
     * Searches the ID of the cards of the deck that are active
     * @return an ArrayList with the ID of the active cards
     */
    public ArrayList<Integer> idDeckActive() {
        ArrayList<Integer> IdDeck=new ArrayList<>();
        for (LeaderCard leaderCard : deck) {
            if(leaderCard.getIsActive()) {
                IdDeck.add(leaderCard.getCardID());
            }
        }
        return IdDeck;
    }


    /**
     * Gets all the cards of the deck with their IDs
     * @return an ArrayList of the IDs of the cards
     */
    public ArrayList<Integer> idDeck(){
        ArrayList<Integer> IdDeck=new ArrayList<>();
        for (LeaderCard leaderCard : deck) {
            IdDeck.add(leaderCard.getCardID());
        }
        return IdDeck;
    }


    /**
     * Searches a card in the deck by an ID
     * @param ID is the ID of the card that we are looking for
     * @return the card if it is present in the deck
     */
    public LeaderCard getFromID(int ID){
        for(LeaderCard card:deck){
            if(card.getCardID()==ID)
                return card;
        }
        return null;
    }


    /**
     * Gets a card from the deck by index
     * @param pos is the index of the card that we are looking for
     * @return the card that we are looking for
     */
    public LeaderCard get(int pos){
        return deck.get(pos);
    }


    /**
     * Calculates the size of the deck
     * @return the size of the deck
     */
    public int size(){
        return deck.size();
    }


    /**
     * Checks if the deck is empty
     * @return true if the deck is empty, false if it is not empty
     */
    public boolean isEmpty(){
        return deck.isEmpty();
    }


    /**
     * Calculates the total victory points of the cards in the deck
     * @return the victory points
     */
    public int victoryPointsAmount(){
        int amount=0;
        for(LeaderCard d: deck){
            if(d.getIsActive()){
                amount=amount+d.getVictoryPoints();
            }
        }
        return amount;
    }
}
