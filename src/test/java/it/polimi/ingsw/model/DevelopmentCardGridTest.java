package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.gameboard.DevelopmentCardGrid;
import junit.framework.TestCase;

public class DevelopmentCardGridTest extends TestCase {
    DevelopmentCardGrid devCardGrid=new DevelopmentCardGrid();
    DevelopmentCard card;

    public void testShufflesAllDecks() {
        devCardGrid.ShufflesAllDecks();
    }

    public void testRemoveCard() throws InvalidChoiceException {
        devCardGrid.removeCard(CardColor.GREEN, 3);
        devCardGrid.removeCard(CardColor.GREEN, 3);
        devCardGrid.removeCard(CardColor.GREEN, 3);
        devCardGrid.removeCard(CardColor.GREEN, 3);

        assert(devCardGrid.getDevCardsDecks().get(11).isEmpty());
    }

    public void testGetCard() throws InvalidChoiceException {
        card=devCardGrid.getCard(CardColor.GREEN, 2);
    }
}