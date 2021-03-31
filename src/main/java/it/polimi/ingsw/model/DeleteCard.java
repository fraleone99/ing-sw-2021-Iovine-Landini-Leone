package it.polimi.ingsw.model;

public class DeleteCard extends ActionToken {

    CardColor colorType;

    public DeleteCard(CardColor colorType) {
        this.colorType = colorType;
    }

    public CardColor getColorType() {
        return colorType;
    }
}
