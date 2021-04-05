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