package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import junit.framework.TestCase;

public class DevelopmentCardGridTest extends TestCase {
    DevelopmentCardGrid devCardGrid=new DevelopmentCardGrid();
    DevelopmentCard card;

    public void testShufflesAllDecks() {
        devCardGrid.ShufflesAllDecks();
    }

    public void testRemoveCard() throws InvalidChoiceException {
        devCardGrid.removeCard(CardColor.GREEN, 3);
    }

    public void testGetCard() throws InvalidChoiceException {
        card=devCardGrid.getCard(CardColor.GREEN, 2);
    }
}