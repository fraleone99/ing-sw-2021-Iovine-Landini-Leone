package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * In this class we create the instances of Leader Card.
 *
 * @author Lorenzo Iovine.
 */

public class Game {

    private final ArrayList<Player> players;
    private ArrayList<LeaderCard> Leader;
    private Player currentPlayer;
    private GameBoard gameBoard;

    public Game() {
        players = new ArrayList<> ();

        Shelf shelf=new Shelf(2,0,Resource.COIN);
        Goods cost=new Goods(Resource.SHIELD,5);
        LeaderCard leader=new StorageLeader(3,cost,shelf);
        Leader.add(leader);

        shelf=new Shelf(2,0,Resource.STONE);
        cost=new Goods(Resource.COIN,5);
        leader=new StorageLeader(3,cost,shelf);
        Leader.add(leader);

        shelf=new Shelf(2,0,Resource.SERVANT);
        cost=new Goods(Resource.STONE,5);
        leader=new StorageLeader(3,cost,shelf);
        Leader.add(leader);

        shelf=new Shelf(2,0,Resource.SHIELD);
        cost=new Goods(Resource.SERVANT,5);
        leader=new StorageLeader(3,cost,shelf);
        Leader.add(leader);

        Requirements req1=new Requirements(CardColor.PURPLE,0,2);
        Requirements req2=new Requirements(CardColor.GREEN,0,1);
        leader=new WhiteBallLeader(5,Resource.COIN,req1,req2);
        Leader.add(leader);

        req1=new Requirements(CardColor.BLUE,0,2);
        req2=new Requirements(CardColor.YELLOW,0,1);
        leader=new WhiteBallLeader(5,Resource.STONE,req1,req2);
        Leader.add(leader);

        req1=new Requirements(CardColor.YELLOW,0,2);
        req2=new Requirements(CardColor.BLUE,0,1);
        leader=new WhiteBallLeader(5,Resource.SERVANT,req1,req2);
        Leader.add(leader);

        req1=new Requirements(CardColor.GREEN,0,2);
        req2=new Requirements(CardColor.PURPLE,0,1);
        leader=new WhiteBallLeader(5,Resource.SHIELD,req1,req2);
        Leader.add(leader);

        req1=new Requirements(CardColor.BLUE,0,1);
        req2=new Requirements(CardColor.PURPLE,0,1);
        leader=new EconomyLeader(2,Resource.SHIELD,req1,req2);
        Leader.add(leader);

        req1=new Requirements(CardColor.GREEN,0,1);
        req2=new Requirements(CardColor.BLUE,0,1);
        leader=new EconomyLeader(2,Resource.STONE,req1,req2);
        Leader.add(leader);

        req1=new Requirements(CardColor.YELLOW,0,1);
        req2=new Requirements(CardColor.GREEN,0,1);
        leader=new EconomyLeader(2,Resource.SERVANT,req1,req2);
        Leader.add(leader);

        req1=new Requirements(CardColor.YELLOW,0,1);
        req2=new Requirements(CardColor.PURPLE,0,1);
        leader=new EconomyLeader(2,Resource.COIN,req1,req2);
        Leader.add(leader);

        req1=new Requirements(CardColor.BLUE,2,1);
        leader=new ProductionLeader(4,Resource.SERVANT,Resource.UNKNOWN,req1);
        Leader.add(leader);

        req1=new Requirements(CardColor.YELLOW,2,1);
        leader=new ProductionLeader(4,Resource.SHIELD,Resource.UNKNOWN,req1);
        Leader.add(leader);

        req1=new Requirements(CardColor.GREEN,2,1);
        leader=new ProductionLeader(4,Resource.COIN,Resource.UNKNOWN,req1);
        Leader.add(leader);

        req1=new Requirements(CardColor.PURPLE,2,1);
        leader=new ProductionLeader(4,Resource.STONE,Resource.UNKNOWN,req1);
        Leader.add(leader);
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
