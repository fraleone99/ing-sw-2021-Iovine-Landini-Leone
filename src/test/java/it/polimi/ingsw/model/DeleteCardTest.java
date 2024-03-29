package it.polimi.ingsw.model;


import it.polimi.ingsw.exceptions.EmptyDecksException;
import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.gameboard.DevelopmentCardGrid;
import it.polimi.ingsw.model.singleplayer.DeleteCard;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeleteCardTest{

    @Test
    public void testDraw() throws InvalidChoiceException, EmptyDecksException {
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

    @Test(expected = EmptyDecksException.class)
    public void testDraw_EmptyDecksException() throws InvalidChoiceException, EmptyDecksException {
        DevelopmentCardGrid developmentCardGrid=new DevelopmentCardGrid();
        DeleteCard deleteCard=new DeleteCard(CardColor.GREEN);

        developmentCardGrid.removeCard(CardColor.GREEN, 1);
        developmentCardGrid.removeCard(CardColor.GREEN, 1);
        developmentCardGrid.removeCard(CardColor.GREEN, 1);
        developmentCardGrid.removeCard(CardColor.GREEN, 1);
        developmentCardGrid.removeCard(CardColor.GREEN, 2);
        developmentCardGrid.removeCard(CardColor.GREEN, 2);
        developmentCardGrid.removeCard(CardColor.GREEN, 2);
        developmentCardGrid.removeCard(CardColor.GREEN, 2);
        developmentCardGrid.removeCard(CardColor.GREEN, 3);
        developmentCardGrid.removeCard(CardColor.GREEN, 3);
        developmentCardGrid.removeCard(CardColor.GREEN, 3);
        developmentCardGrid.removeCard(CardColor.GREEN, 3);

        deleteCard.draw(CardColor.GREEN, developmentCardGrid);
    }
}