package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.model.singleplayer.BlackCrossMover;
import it.polimi.ingsw.model.singleplayer.DeleteCard;

import java.util.ArrayList;

/**
 * In this class we manage the main actions of the match.
 *
 * @author Lorenzo Iovine, Francesco Leone, Nicola Landini
 */

public class Game {
    private final ArrayList<Player> players;
    private final GameBoard gameBoard;
    private int papalPawn=0;


    /**
     * Constructor Game creates a new Game instance
     */
    public Game() {
        players = new ArrayList<>();
        gameBoard = new GameBoard();
    }


    /**
     * Increments the papalPawn variable
     */
    public void updatePapalPawn() {
        papalPawn++;
    }


    /**
     * Gets the papalPawn variable
     * @return papalPawn variable
     */
    public int getPapalPawn() {
        return papalPawn;
    }


    /**
     * Gets the players of the match
     * @return players variable
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }


    /**
     * Creates a new Player instance
     * @param nickname is the nickname of the player
     */
    public void createPlayer(String nickname){
        Player p = new Player(nickname);
        players.add(p);
    }


    /**
     * Gets a player by nickname
     * @param nickname is the nickname of the player we are looking for
     * @return the player we are looking for
     * @throws NotExistingPlayerException if the player doesn't exist
     */
    public Player getPlayer(String nickname) throws NotExistingPlayerException{
        for (Player p:players) {
            if(p.getNickname().equals(nickname))
                return  p;
        }
        throw  new NotExistingPlayerException();
    }


    /**
     * Draws an action Token from the arrayList of LorenzoIlMagnifico
     * @return the action token drawn
     * @throws InvalidChoiceException if the choice is invalid
     * @throws EmptyDecksException if the deck is empty
     */
    public ActionToken drawActionToken() throws InvalidChoiceException, EmptyDecksException {
        ActionToken element = gameBoard.getLorenzoMagnifico().draw();

        if(element instanceof BlackCrossMover){
            players.get(0).getPlayerDashboard().getFaithPath().moveForwardBCM(((BlackCrossMover) element).getSteps());
            if(((BlackCrossMover) element).haveToBeShuffled()){
                gameBoard.getLorenzoMagnifico().shuffle();
            }
        }
        else{
            ((DeleteCard) element).draw(((DeleteCard) element).getColorType(), gameBoard.getDevelopmentCardGrid());
            ((DeleteCard) element).draw(((DeleteCard) element).getColorType(), gameBoard.getDevelopmentCardGrid());
        }

        return element;
    }


    /**
     * Gets the gameBoard instance
     * @return gameBoard variable
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
