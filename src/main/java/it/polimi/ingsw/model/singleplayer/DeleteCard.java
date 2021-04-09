package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.gameboard.DevelopmentCardGrid;

public class DeleteCard extends ActionToken {

    private CardColor colorType;

    public DeleteCard(CardColor colorType) {
        this.colorType = colorType;
    }

    public CardColor getColorType() {
        return colorType;
    }

    public void draw(CardColor color, DevelopmentCardGrid developmentCardGrid) throws InvalidChoiceException {
        if(!developmentCardGrid.getDeck(color, 1).isEmpty()){
            developmentCardGrid.removeCard(color, 1);
        } else if(!developmentCardGrid.getDeck(color, 2).isEmpty()){
            developmentCardGrid.removeCard(color, 2);
        } else if(!developmentCardGrid.getDeck(color, 3).isEmpty()){
            developmentCardGrid.removeCard(color, 3);
        }
    }
}
