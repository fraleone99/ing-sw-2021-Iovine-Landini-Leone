package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.model.singleplayer.BlackCrossMover;
import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameBoardTest extends TestCase {

    public void testDrawActionToken() throws InvalidChoiceException, FileNotFoundException {
        ArrayList<Player> players = new ArrayList<>();
        Player player1=new Player("player1");
        players.add(player1);
        Player player2=new Player("player2");
        players.add(player2);
        Player player3=new Player("player3");
        players.add(player3);

        GameBoard gameBoard=new GameBoard(3, players);

        gameBoard.drawActionToken();
        gameBoard.drawActionToken();
        gameBoard.drawActionToken();
        gameBoard.drawActionToken();
        ActionToken element=gameBoard.drawActionToken();

        if(element instanceof BlackCrossMover && ((BlackCrossMover) element).haveToBeShuffled()){
            assertEquals(gameBoard.getLorenzoMagnifico().getTokens().size(), 7);
        } else {
            assertEquals(gameBoard.getLorenzoMagnifico().getTokens().get(6), element);
        }
    }
}