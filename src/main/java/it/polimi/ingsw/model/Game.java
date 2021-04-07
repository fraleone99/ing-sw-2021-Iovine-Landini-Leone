package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.exceptions.InvalidSpaceCardExeption;
import it.polimi.ingsw.exceptions.NotEnoughResourceException;
import it.polimi.ingsw.exceptions.NotExistingPlayerException;

import java.util.ArrayList;

/**
 * In this class we create the instances of Leader Card.
 *
 * @author Lorenzo Iovine.
 */

public class Game {

    private final ArrayList<Player> players;
    private Player currentPlayer;
    private GameBoard gameBoard;

    public Game() {
        players = new ArrayList<> ();
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

    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }

    public void choiceDevCard(CardColor color, int level, int space) throws InvalidChoiceException, InvalidSpaceCardExeption, NotEnoughResourceException {
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

}
