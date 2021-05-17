package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.exceptions.EmptyDecksException;
import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.gameboard.DevelopmentCardGrid;

import java.io.Serializable;

/**
 * This class extends ActionToken and represents the action token
 * that imposes the elimination of two Development Card from colorType
 * deck
 *
 * @author Nicola Landini
 */

public class DeleteCard extends ActionToken implements Serializable {

    private final CardColor colorType;

    /**
     * DeleteCard constructor: creates a new instance of DeleteCard
     * @param colorType indicates cards color
     */
    public DeleteCard(CardColor colorType) {
        this.colorType = colorType;
    }

    public CardColor getColorType() {
        return colorType;
    }

    /**
     * This method draw 1 card from color deck
     * @param color card color deck from which card has to be drawn
     * @param developmentCardGrid development card grid
     * @throws InvalidChoiceException if color is not present or level is invalid
     * @throws EmptyDecksException if color deck is empty
     */
    public void draw(CardColor color, DevelopmentCardGrid developmentCardGrid) throws InvalidChoiceException, EmptyDecksException {
        if(!developmentCardGrid.getDeck(color, 1).isEmpty()){
            developmentCardGrid.removeCard(color, 1);
        } else if(!developmentCardGrid.getDeck(color, 2).isEmpty()){
            developmentCardGrid.removeCard(color, 2);
        } else if(!developmentCardGrid.getDeck(color, 3).isEmpty()){
            developmentCardGrid.removeCard(color, 3);
        } else {
            throw new EmptyDecksException();
        }
    }
}
