package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;
    private final ArrayList<Player> players=new ArrayList<>();


    @Test
    public void getPlayer() throws NotExistingPlayerException {
        game=new Game();
        Player player=new Player("TestPlayer");
        game.getPlayers().add(player);
        assertEquals(game.getPlayer("TestPlayer").getNickname(),"TestPlayer");
    }

    @Test(expected = NotExistingPlayerException.class)
    public void getPlayer_NotExistingPlayer() throws NotExistingPlayerException {
        game=new Game();
        Player player=new Player("TestPlayer");
        game.getPlayers().add(player);
        assertEquals(game.getPlayer("Nicola").getNickname(),"TestPlayer");
    }
}