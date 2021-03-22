package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> players;
    private Player currentPlayer;
    private GameBoard gameBoard;

    public Game() {
        players = new ArrayList<Player> ();
    }

    public void createNewPlayer(String nickname){
        Player NewPlayer = new Player(nickname);
        players.add(NewPlayer);
    }

    public Player getPlayer(String nickname) throws NotExistingPlayerException{
        for (Player p:players) {
            if(p.getNickname().equals(nickname))
                return  p;
        }
        throw  new NotExistingPlayerException();
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPlayer(){
        //TODO
    }

}
