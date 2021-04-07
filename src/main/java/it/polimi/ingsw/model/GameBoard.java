package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;

import java.util.ArrayList;
import java.util.Collections;

public class GameBoard {
    private int playersNumber;
    private Market market;
    private ArrayList<PlayerDashboard> playersDashboards = new ArrayList<>();
    private DevelopmentCardGrid developmentCardGrid;
    private LeaderCardDeck LeaderDeck;
    private LorenzoMagnifico lorenzoMagnifico;

    public GameBoard(int playersNumber, ArrayList<Player> players) {
        this.playersNumber = playersNumber;
        market = new Market();
        developmentCardGrid = new DevelopmentCardGrid();

        for(int i = 0; i<playersNumber; i++){
            playersDashboards.add(new PlayerDashboard(players.get(i).getNickname()));
        }
        initializeLeaderCards();
    }

    public void drawActionToken() throws InvalidChoiceException {
        ActionToken element=lorenzoMagnifico.draw();

        if(element instanceof BlackCrossMover){
            playersDashboards.get(0).getFaithPath().moveForwardBCM(((BlackCrossMover) element).getSteps());
            if(((BlackCrossMover) element).haveToBeShuffled()){
                lorenzoMagnifico.shuffle();
            }
        }
        else{
            ((DeleteCard) element).draw(((DeleteCard) element).getColorType(), developmentCardGrid);
            ((DeleteCard) element).draw(((DeleteCard) element).getColorType(), developmentCardGrid);
        }
    }

    public DevelopmentCardGrid getDevelopmentCardGrid() {
        return developmentCardGrid;
    }

    public Market getMarket() {
        return market;
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
}
