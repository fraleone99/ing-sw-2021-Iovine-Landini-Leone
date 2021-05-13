package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.card.leadercard.EconomyLeader;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.playerdashboard.Shelf;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.model.singleplayer.BlackCrossMover;
import it.polimi.ingsw.model.singleplayer.DeleteCard;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * In this class we create the instances of Leader Card.
 *
 * @author Lorenzo Iovine.
 */

public class Game {
    private final ArrayList<Player> players;
    private ArrayList<String> nicknames=new ArrayList<>();
    private Player currentPlayer;
    private GameBoard gameBoard;
    private int papalPawn=0;

    public Game(int playersNumber, ArrayList<String> nickname) {
        players = new ArrayList<> ();
        nicknames.addAll(nickname);

        try {
            gameBoard=new GameBoard(playersNumber);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updatePapalPawn() {
        papalPawn++;
    }

    public int getPapalPawn() {
        return papalPawn;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void createPlayer(String nickname){
        Player p = new Player(nickname);
        players.add(p);

        if(players.size()==1){
            players.get(0).setFirst(true);
        }
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

    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }

    public void choiceDevCard(CardColor color, int level, int space) throws InvalidChoiceException, InvalidSpaceCardException, NotEnoughResourceException {
        ArrayList<Goods> cost=new ArrayList<>();
        Shelf shelf;
        /*cost=gameBoard.getDevelopmentCardGrid().getCard(color, level).getCost();*/

        for(int i=0; i<gameBoard.getDevelopmentCardGrid().getCard(color, level).getCost().size(); i++) {
            cost.add(new Goods(gameBoard.getDevelopmentCardGrid().getCard(color, level).getCost().get(i)));
        }

        //check if currentPlayer has an economy leader and set discount
        for(int i=0; i<currentPlayer.getLeaders().size(); i++){
            if(currentPlayer.getLeaders().get(i) instanceof EconomyLeader){
                for(Goods g: cost){
                    if(g.getType().equals(((EconomyLeader) currentPlayer.getLeaders().get(i)).getDiscountType()) && currentPlayer.getLeaders().get(i).getIsActive()){
                        g.setAmount(g.getAmount()-1);
                    }
                }
            }
        }

        if(!currentPlayer.getPlayerDashboard().CheckResource(cost)){
            throw new NotEnoughResourceException();
        }

        currentPlayer.buyCard(gameBoard.getDevelopmentCardGrid().getCard(color, level),space);

        gameBoard.getDevelopmentCardGrid().removeCard(color, level);

        currentPlayer.getPlayerDashboard().RemoveResource(cost);

    }

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

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
