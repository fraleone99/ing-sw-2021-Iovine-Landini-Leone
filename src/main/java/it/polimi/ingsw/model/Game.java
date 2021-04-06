package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.exceptions.InvalidSpaceCardExeption;
import it.polimi.ingsw.exceptions.NotEnoughResourceException;
import it.polimi.ingsw.exceptions.NotExistingPlayerException;

import javax.print.attribute.standard.PrinterURI;
import java.util.ArrayList;

/**
 * In this class we create the instances of Leader Card.
 *
 * @author Lorenzo Iovine.
 */

public class Game {

    private final ArrayList<Player> players;
    private LeaderCardDeck LeaderDeck;
    private Player currentPlayer;
    private GameBoard gameBoard;

    public Game() {
        players = new ArrayList<> ();
        initializeLeaderCards();
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

    public void initializeLeaderCards(){
        Shelf shelf=new Shelf(2,0,Resource.COIN);
        Goods cost=new Goods(Resource.SHIELD,5);
        Requirements req1=new Requirements(CardColor.PURPLE,0,0, cost);
        LeaderCard leader=new StorageLeader(3,req1,shelf);
        LeaderDeck.add(leader);

        shelf=new Shelf(2,0,Resource.STONE);
        cost=new Goods(Resource.COIN,5);
        req1=new Requirements(CardColor.PURPLE,0,0, cost);
        leader=new StorageLeader(3,req1,shelf);
        LeaderDeck.add(leader);

        shelf=new Shelf(2,0,Resource.SERVANT);
        cost=new Goods(Resource.STONE,5);
        req1=new Requirements(CardColor.PURPLE,0,0, cost);
        leader=new StorageLeader(3,req1,shelf);
        LeaderDeck.add(leader);

        shelf=new Shelf(2,0,Resource.SHIELD);
        cost=new Goods(Resource.SERVANT,5);
        req1=new Requirements(CardColor.PURPLE,0,0, cost);
        leader=new StorageLeader(3,req1,shelf);
        LeaderDeck.add(leader);

        cost=new Goods(Resource.COIN, 0);
        req1=new Requirements(CardColor.PURPLE,0,2, cost);
        Requirements req2=new Requirements(CardColor.GREEN,0,1, cost);
        leader=new WhiteBallLeader(5,Resource.COIN,req1,req2);
        LeaderDeck.add(leader);

        req1=new Requirements(CardColor.BLUE,0,2, cost);
        req2=new Requirements(CardColor.YELLOW,0,1, cost);
        leader=new WhiteBallLeader(5,Resource.STONE,req1,req2);
        LeaderDeck.add(leader);

        req1=new Requirements(CardColor.YELLOW,0,2, cost);
        req2=new Requirements(CardColor.BLUE,0,1, cost);
        leader=new WhiteBallLeader(5,Resource.SERVANT,req1,req2);
        LeaderDeck.add(leader);

        req1=new Requirements(CardColor.GREEN,0,2, cost);
        req2=new Requirements(CardColor.PURPLE,0,1, cost);
        leader=new WhiteBallLeader(5,Resource.SHIELD,req1,req2);
        LeaderDeck.add(leader);

        req1=new Requirements(CardColor.BLUE,0,1, cost);
        req2=new Requirements(CardColor.PURPLE,0,1, cost);
        leader=new EconomyLeader(2,Resource.SHIELD,req1,req2);
        LeaderDeck.add(leader);

        req1=new Requirements(CardColor.GREEN,0,1, cost);
        req2=new Requirements(CardColor.BLUE,0,1, cost);
        leader=new EconomyLeader(2,Resource.STONE,req1,req2);
        LeaderDeck.add(leader);

        req1=new Requirements(CardColor.YELLOW,0,1, cost);
        req2=new Requirements(CardColor.GREEN,0,1, cost);
        leader=new EconomyLeader(2,Resource.SERVANT,req1,req2);
        LeaderDeck.add(leader);

        req1=new Requirements(CardColor.YELLOW,0,1, cost);
        req2=new Requirements(CardColor.PURPLE,0,1, cost);
        leader=new EconomyLeader(2,Resource.COIN,req1,req2);
        LeaderDeck.add(leader);


        ArrayList<Goods> input = new ArrayList<>();
        Goods g1 = new Goods(Resource.SERVANT,1);
        input.add(g1);
        ArrayList<Goods> output = new ArrayList<>();
        Goods g2 = new Goods(Resource.UNKNOWN,1);
        output.add(g2);
        Production production = new Production(input,output);
        req1=new Requirements(CardColor.BLUE,2,1, cost);
        leader=new ProductionLeader(4,production,req1);
        LeaderDeck.add(leader);

        input = new ArrayList<>();
        g1 = new Goods(Resource.SHIELD,1);
        input.add(g1);
        output = new ArrayList<>();
        g2 = new Goods(Resource.UNKNOWN,1);
        output.add(g2);
        production = new Production(input,output);
        req1=new Requirements(CardColor.YELLOW,2,1, cost);
        leader=new ProductionLeader(4,production,req1);
        LeaderDeck.add(leader);

        input = new ArrayList<>();
        g1 = new Goods(Resource.COIN,1);
        input.add(g1);
        output = new ArrayList<>();
        g2 = new Goods(Resource.UNKNOWN,1);
        output.add(g2);
        production = new Production(input,output);
        req1=new Requirements(CardColor.GREEN,2,1, cost);
        leader=new ProductionLeader(4,production,req1);
        LeaderDeck.add(leader);

        input = new ArrayList<>();
        g1 = new Goods(Resource.STONE,1);
        input.add(g1);
        output = new ArrayList<>();
        g2 = new Goods(Resource.UNKNOWN,1);
        output.add(g2);
        production = new Production(input,output);
        req1=new Requirements(CardColor.PURPLE,2,1, cost);
        leader=new ProductionLeader(4,production,req1);
        LeaderDeck.add(leader);
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
