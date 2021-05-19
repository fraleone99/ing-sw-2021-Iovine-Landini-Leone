package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.gameboard.DevelopmentCardGrid;
import org.junit.Test;

import java.io.FileNotFoundException;

public class DevelopmentCardGridTest {
    DevelopmentCardGrid devCardGrid=new DevelopmentCardGrid();
    DevelopmentCard card;

    public DevelopmentCardGridTest() throws InvalidChoiceException {
        System.out.println(devCardGrid.getCard(CardColor.GREEN,2));
    }

    public void testShufflesAllDecks() {
        devCardGrid.ShufflesAllDecks();
    }

    @Test
    public void testRemoveCard() throws InvalidChoiceException {
        devCardGrid.removeCard(CardColor.GREEN, 3);
        devCardGrid.removeCard(CardColor.GREEN, 3);
        devCardGrid.removeCard(CardColor.GREEN, 3);
        devCardGrid.removeCard(CardColor.GREEN, 3);

        assert(devCardGrid.getDevCardsDecks().get(11).isEmpty());
    }

    @Test
    public void testGetCard() throws InvalidChoiceException {
        card=devCardGrid.getCard(CardColor.GREEN, 2);
    }
}