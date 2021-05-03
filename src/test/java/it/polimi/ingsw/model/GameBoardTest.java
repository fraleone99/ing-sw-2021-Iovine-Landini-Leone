package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyDecksException;
import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.model.singleplayer.BlackCrossMover;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GameBoardTest{

    @Test
    public void testDrawActionToken() throws InvalidChoiceException, EmptyDecksException {
        ArrayList<String> nickname=new ArrayList<>();
        String player1="player1";
        nickname.add(player1);

        Game game = new Game(1,nickname);
        game.createPlayer(player1);

        game.drawActionToken();
        game.drawActionToken();
        game.drawActionToken();
        game.drawActionToken();
        ActionToken element=game.drawActionToken();

        if(element instanceof BlackCrossMover && ((BlackCrossMover) element).haveToBeShuffled()){
            assertEquals(game.getGameBoard().getLorenzoMagnifico().getTokens().size(), 7);
        } else {
            assertEquals(game.getGameBoard().getLorenzoMagnifico().getTokens().get(6), element);
        }
    }
}