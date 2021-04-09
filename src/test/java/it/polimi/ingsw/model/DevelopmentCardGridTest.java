package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import junit.framework.TestCase;

import java.awt.*;
import java.io.FileNotFoundException;

public class DevelopmentCardGridTest extends TestCase {
    DevelopmentCardGrid devCardGrid=new DevelopmentCardGrid();
    DevelopmentCard card;

    public DevelopmentCardGridTest() throws FileNotFoundException, InvalidChoiceException {
        System.out.println(devCardGrid.getCard(CardColor.GREEN,2));
    }

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