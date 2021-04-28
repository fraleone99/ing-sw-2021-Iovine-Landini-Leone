package it.polimi.ingsw.model;

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
    public void testDrawActionToken() throws InvalidChoiceException, FileNotFoundException {
        ArrayList<String> nickname=new ArrayList<>();
        String player1="player1";
        nickname.add(player1);
        String player2="player2";
        nickname.add(player2);
        String player3="player3";
        nickname.add(player3);

        GameBoard gameBoard=new GameBoard(3);
        Game game = new Game(3,nickname);
        for(String nick: nickname){
            game.createPlayer(nick);
        }
        //game.setGameBoard(gameBoard);

        game.drawActionToken();
        game.drawActionToken();
        game.drawActionToken();
        game.drawActionToken();
        ActionToken element=game.drawActionToken();

        if(element instanceof BlackCrossMover && ((BlackCrossMover) element).haveToBeShuffled()){
            assertEquals(gameBoard.getLorenzoMagnifico().getTokens().size(), 7);
        } else {
            assertEquals(gameBoard.getLorenzoMagnifico().getTokens().get(6), element);
        }
    }
}