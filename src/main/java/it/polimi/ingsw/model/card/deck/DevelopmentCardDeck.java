package it.polimi.ingsw.model.card.deck;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for decks of Development Cards
 *
 * @author Francesco Leone, Lorenzo Iovine
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
     * Draws a card from the deck. The Card is removed from the deck
     * @return the last card of the deck.
     */
    public DevelopmentCard draw() throws InvalidChoiceException {
        if(deck.isEmpty()) {
            throw new InvalidChoiceException();
        }
        return deck.remove(deck.size()-1);
    }


    /**
     * Adds a card to the deck
     * @param card will be added to the deck
     */
    public void add(DevelopmentCard card){
        deck.add(card);
    }


    /**
     * Adds all the card to the deck
     * @param devCards will be added to the deck
     */
    public void addAll(DevelopmentCardDeck devCards) {deck.addAll(devCards.getDeck());}


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


    /**
     * Checks how many cards of a certain type are in the deck
     * @param color is the color of the cards we are looking for
     * @param level is the level of the cards we are looking for
     * @return the number of cards of this type present in the deck
     */
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


    /**
     * Calculates the size of the deck
     * @return the size of the deck
     */
    public int size(){ return deck.size(); }


    /**
     * Sets the initial deck
     * @param deck is the initial deck
     */
    public void setDeck(List<DevelopmentCard> deck){
        this.deck.addAll(deck);
    }


    /**
     * Calculates the total victory points of the cards in the deck
     * @return the victory points
     */
    public int victoryPointsAmount(){
        int amount=0;
        for(DevelopmentCard d: deck){
            amount=amount+d.getVictoryPoints();
        }

        return amount;
    }


    /**
     * Gets the deck
     * @return the deck
     */
    public List<DevelopmentCard> getDeck() {
        return deck;
    }


    /**
     * Searches a card in the deck by an ID
     * @param ID is the ID of the card that we are looking for
     * @return the card if it is present in the deck
     */
    public DevelopmentCard getCardByID(int ID){
        for(DevelopmentCard c: deck){
            if (c.getCardID() == ID){
                return c;
            }
        }
        return null;
    }


    /**
     * Gets all the cards of the deck with their IDs
     * @return an ArrayList of the IDs of the cards
     */
    public ArrayList<Integer> IdDeck(){
        ArrayList<Integer> IdDeck=new ArrayList<>();
        for (DevelopmentCard dev : deck) {
            IdDeck.add(dev.getCardID());
        }
        return IdDeck;
    }
}
