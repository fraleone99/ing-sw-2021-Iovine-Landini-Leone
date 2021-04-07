package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;

    @Before public void initialize(){
        player = new Player("TestPlayer");
    }

    @Test
    public void ProductionLeader() {

    }

    @Test
    public void DiscardLeader() throws InvalidChoiceException {
        Shelf shelf=new Shelf(2,0,Resource.COIN);
        Goods cost=new Goods(Resource.SHIELD,5);
        Requirements req1=new Requirements(CardColor.PURPLE,0,0, cost);
        LeaderCard leader=new StorageLeader(3,req1,shelf);
        player.getPlayerDashboard().getLeaders().add(leader);

        req1=new Requirements(CardColor.GREEN,0,2, cost);
        Requirements req2=new Requirements(CardColor.PURPLE,0,1, cost);
        leader=new WhiteBallLeader(5,Resource.SHIELD,req1,req2);
        player.getPlayerDashboard().getLeaders().add(leader);

        player.DiscardLeader(0);
        player.DiscardLeader(1);

        assert(player.getPlayerDashboard().getLeaders().get(0).getIsDiscarded());
        assert(player.getPlayerDashboard().getLeaders().get(1).getIsDiscarded());
    }

    @Test
    public void testActiveLeader() throws ShelfHasDifferentTypeException, AnotherShelfHasTheSameTypeException, NotEnoughSpaceException, InvalidSpaceCardExeption, InvalidChoiceException {
        player.getPlayerDashboard().getStorage().AddResource(3,Resource.SHIELD,3);
        player.getPlayerDashboard().getVault().AddResource(Resource.SHIELD,4);
        player.getPlayerDashboard().getVault().AddResource(Resource.COIN,1);

        DevelopmentCardDeck PurpleOne = new DevelopmentCardDeck();
        ArrayList<Goods> cost1=new ArrayList<>();
        cost1.add(new Goods(Resource.SERVANT, 3));
        ArrayList<Goods> input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 2));
        ArrayList<Goods> output=new ArrayList<>();
        output.add(new Goods(Resource.SERVANT,1));
        output.add(new Goods(Resource.SHIELD,1));
        output.add(new Goods(Resource.STONE,1));
        Production production=new Production(input, output);
        DevelopmentCard devCard=new DevelopmentCard(3, CardColor.PURPLE, 1, cost1, production, 0);
        player.getPlayerDashboard().getDevCardsSpace().AddCard(devCard,1);

        DevelopmentCardDeck GreenOne = new DevelopmentCardDeck();
        cost1=new ArrayList<>();
        cost1.add(new Goods(Resource.SHIELD, 2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        output=new ArrayList<>();
        production=new Production(input, output);
        devCard=new DevelopmentCard(1, CardColor.GREEN, 1, cost1, production, 1);
        player.getPlayerDashboard().getDevCardsSpace().AddCard(devCard,2);

        cost1=new ArrayList<>();
        cost1.add(new Goods(Resource.SHIELD, 2));
        cost1.add(new Goods(Resource.COIN, 2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        input.add(new Goods(Resource.SERVANT, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN, 2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(4, CardColor.GREEN, 1, cost1, production, 1);
        player.getPlayerDashboard().getDevCardsSpace().AddCard(devCard,3);

        cost1 = new ArrayList<>();
        cost1.add(new Goods(Resource.COIN, 3));
        cost1.add(new Goods(Resource.STONE, 2));
        input= new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        input.add(new Goods(Resource.STONE, 1));
        output= new ArrayList<>();
        output.add(new Goods(Resource.SERVANT, 3));
        production=new Production(input, output);
        devCard=new DevelopmentCard(6, CardColor.BLUE, 2, cost1, production, 0);
        player.getPlayerDashboard().getDevCardsSpace().AddCard(devCard,1);

        player.getPlayerDashboard().getStorage().AddResource(2,Resource.COIN,2);
        player.getPlayerDashboard().getVault().AddResource(Resource.SHIELD,4);
        player.getPlayerDashboard().getVault().AddResource(Resource.COIN,1);

        Shelf shelf=new Shelf(2,0,Resource.COIN);
        Goods cost=new Goods(Resource.SHIELD,5);
        Requirements req1=new Requirements(CardColor.PURPLE,0,0, cost);
        LeaderCard leader=new StorageLeader(3,req1,shelf);
        player.getPlayerDashboard().getLeaders().add(leader);

        cost=new Goods(Resource.COIN, 0);
        req1=new Requirements(CardColor.GREEN,0,2, cost);
        Requirements req2=new Requirements(CardColor.PURPLE,0,1, cost);
        leader=new WhiteBallLeader(5,Resource.SHIELD,req1,req2);
        player.getPlayerDashboard().getLeaders().add(leader);

        req1=new Requirements(CardColor.GREEN,0,1, cost);
        req2=new Requirements(CardColor.BLUE,0,1, cost);
        leader=new EconomyLeader(2,Resource.STONE,req1,req2);
        player.getPlayerDashboard().getLeaders().add(leader);

        input = new ArrayList<>();
        Goods g1 = new Goods(Resource.SERVANT,1);
        input.add(g1);
        output = new ArrayList<>();
        Goods g2 = new Goods(Resource.UNKNOWN,1);
        output.add(g2);
        production = new Production(input,output);
        req1=new Requirements(CardColor.BLUE,2,1, cost);
        leader=new ProductionLeader(4,production,req1);
        player.getPlayerDashboard().getLeaders().add(leader);

        player.ActiveLeader(0);
        player.ActiveLeader(1);
        player.ActiveLeader(2);
        player.ActiveLeader(3);

        assertEquals((player.getPlayerDashboard().getStorage().getAmountShelf(3)),0);
        assertEquals((player.getPlayerDashboard().getStorage().getAmountShelf(2)),2);
        assertEquals(player.getPlayerDashboard().getVault().getResource(Resource.SHIELD),6);
        assertEquals(player.getPlayerDashboard().getVault().getResource(Resource.COIN),2);
        assert(player.getPlayerDashboard().getLeaders().get(0).getIsActive());
        assert(player.getPlayerDashboard().getLeaders().get(1).getIsActive());
        assert(player.getPlayerDashboard().getLeaders().get(2).getIsActive());
        assert(player.getPlayerDashboard().getLeaders().get(3).getIsActive());
    }

    @Test
    public void ProductionBase() throws ShelfHasDifferentTypeException, AnotherShelfHasTheSameTypeException, NotEnoughSpaceException, NotEnoughResourceException {
        player.getPlayerDashboard().getStorage().AddResource(1,Resource.STONE,1);
        player.getPlayerDashboard().getVault().AddResource(Resource.STONE,1);
        player.getPlayerDashboard().getVault().AddResource(Resource.SHIELD,1);
        player.getPlayerDashboard().getDevCardsSpace().setInputBasicProduction(Resource.STONE,Resource.SHIELD);
        player.getPlayerDashboard().getDevCardsSpace().setOutputBasicProduction(Resource.COIN);

        player.ActiveProductionBase();
        player.doProduction();

        assertEquals(player.getPlayerDashboard().getStorage().getAmountShelf(1),0);
        assertEquals(player.getPlayerDashboard().getVault().getResource(Resource.COIN),1);
        assertEquals(player.getPlayerDashboard().getVault().getResource(Resource.STONE),1);
        assertEquals(player.getPlayerDashboard().getVault().getResource(Resource.SHIELD),0);
    }

    @Test
    public void ProductionDevCard() throws InvalidSpaceCardExeption, ShelfHasDifferentTypeException, AnotherShelfHasTheSameTypeException, NotEnoughSpaceException, NotEnoughResourceException, InvalidChoiceException {
        ArrayList<Goods> input=new ArrayList<>();
        ArrayList<Goods> cost=new ArrayList<>();
        cost.add(new Goods(Resource.SERVANT, 3));
        input.add(new Goods(Resource.COIN, 2));
        ArrayList<Goods> output=new ArrayList<>();
        output.add(new Goods(Resource.SERVANT,1));
        output.add(new Goods(Resource.SHIELD,1));
        output.add(new Goods(Resource.STONE,1));
        Production production=new Production(input, output);
        DevelopmentCard devCard=new DevelopmentCard(3, CardColor.PURPLE, 1, cost, production, 0);

        player.getPlayerDashboard().getDevCardsSpace().AddCard(devCard,1);

        player.getPlayerDashboard().getStorage().AddResource(1,Resource.STONE,1);
        player.getPlayerDashboard().getStorage().AddResource(2,Resource.COIN,1);
        player.getPlayerDashboard().getVault().AddResource(Resource.STONE,1);
        player.getPlayerDashboard().getVault().AddResource(Resource.SHIELD,1);
        player.getPlayerDashboard().getVault().AddResource(Resource.COIN,2);
        player.getPlayerDashboard().getDevCardsSpace().setInputBasicProduction(Resource.STONE,Resource.SHIELD);
        player.getPlayerDashboard().getDevCardsSpace().setOutputBasicProduction(Resource.COIN);

        player.ActiveProductionDevCard(1);
        player.doProduction();

        assertEquals(player.getPlayerDashboard().getStorage().getAmountShelf(1),1);
        assertEquals(player.getPlayerDashboard().getStorage().getAmountShelf(2),0);
        assertEquals(player.getPlayerDashboard().getStorage().getAmountShelf(3),0);
        assertEquals(player.getPlayerDashboard().getVault().getResource(Resource.COIN),1);
        assertEquals(player.getPlayerDashboard().getVault().getResource(Resource.STONE),2);
        assertEquals(player.getPlayerDashboard().getVault().getResource(Resource.SHIELD),2);
        assertEquals(player.getPlayerDashboard().getVault().getResource(Resource.SERVANT),1);
    }

}