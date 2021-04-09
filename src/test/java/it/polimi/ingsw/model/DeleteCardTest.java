package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import junit.framework.TestCase;

import java.io.FileNotFoundException;

public class DeleteCardTest extends TestCase {

    public void testDraw() throws InvalidChoiceException, FileNotFoundException {
        DevelopmentCardGrid developmentCardGrid=new DevelopmentCardGrid();
        DeleteCard deleteCard=new DeleteCard(CardColor.GREEN);

        developmentCardGrid.removeCard(CardColor.GREEN, 1);
        developmentCardGrid.removeCard(CardColor.GREEN, 1);
        developmentCardGrid.removeCard(CardColor.GREEN, 1);
        developmentCardGrid.removeCard(CardColor.GREEN, 1);
        developmentCardGrid.removeCard(CardColor.GREEN, 2);
        developmentCardGrid.removeCard(CardColor.GREEN, 2);
        developmentCardGrid.removeCard(CardColor.GREEN, 2);

        deleteCard.draw(CardColor.GREEN, developmentCardGrid);
        deleteCard.draw(CardColor.GREEN, developmentCardGrid);

        assertEquals(developmentCardGrid.getDeck(CardColor.GREEN, 2).size(), 0);
        assertEquals(developmentCardGrid.getDeck(CardColor.GREEN, 3).size(), 3);
    }

}