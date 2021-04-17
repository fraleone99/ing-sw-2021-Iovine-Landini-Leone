package it.polimi.ingsw.client.message.action;

import it.polimi.ingsw.model.enumeration.CardColor;

public class BuyCard implements UserAction{
    private final CardColor cardColor;
    private final int level;

    public BuyCard(CardColor cardColor, int level) {
        this.cardColor = cardColor;
        this.level = level;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public int getLevel() {
        return level;
    }
}
