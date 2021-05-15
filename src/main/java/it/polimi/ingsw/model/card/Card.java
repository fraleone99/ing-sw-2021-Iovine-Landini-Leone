package it.polimi.ingsw.model.card;


/**
 * General class used for organized the game's cards.
 * The only thing in common for all the cards are the Victory Points,
 * the other characteristics will be described in the subclasses.
 *
 * @author Lorenzo Iovine.
 */

public abstract class Card{
    private final int victoryPoints;
    private final int cardID;


    /**
     * Constructor Card creates a new Card instance
     * @param VictoryPoints is the number of the victory points of the card
     * @param CardID is the ID of the card
     */
    public Card(int VictoryPoints, int CardID) {
        this.victoryPoints = VictoryPoints;
        this.cardID = CardID;
    }


    /**
     * Get the victory points of the card
     * @return the victory points
     */
    public int getVictoryPoints(){
        return victoryPoints;
    }


    /**
     * Get the ID of the card
     * @return the ID
     */
    public int getCardID() {
        return cardID;
    }
}
