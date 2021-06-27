package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.model.singleplayer.BlackCrossMover;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;


    @Test
    public void getPlayer() throws NotExistingPlayerException {
        ArrayList<String> nickname=new ArrayList<>();
        game=new Game();
        Player player=new Player("TestPlayer");
        game.getPlayers().add(player);
        assertEquals(game.getPlayer("TestPlayer").getNickname(),"TestPlayer");
    }

    @Test(expected = NotExistingPlayerException.class)
    public void getPlayer_NotExistingPlayer() throws NotExistingPlayerException {
        ArrayList<String> nickname=new ArrayList<>();
        game=new Game();
        Player player=new Player("TestPlayer");
        game.getPlayers().add(player);
        assertEquals(game.getPlayer("Nicola").getNickname(),"TestPlayer");
    }

    @Test
    public void testDrawActionToken() throws InvalidChoiceException, EmptyDecksException {
        ArrayList<String> nickname=new ArrayList<>();
        String player1="player1";
        nickname.add(player1);

        Game game = new Game();
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

    @Test
    public void testCreatePlayer(){
        ArrayList<String> nickname=new ArrayList<>();
        game=new Game();
        String nick="nick";

        game.createPlayer(nick);

        assertEquals(1, game.getPlayers().size());
        assertEquals("nick", game.getPlayers().get(0).getNickname());
    }

}