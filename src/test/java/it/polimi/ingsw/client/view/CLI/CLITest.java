package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;
import it.polimi.ingsw.model.card.deck.LeaderCardDeck;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.card.leadercard.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.DevelopmentCardGrid;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.playerdashboard.*;
import it.polimi.ingsw.server.answer.infoanswer.DevCardsSpaceInfo;
import it.polimi.ingsw.server.answer.infoanswer.FaithPathInfo;
import it.polimi.ingsw.server.answer.infoanswer.StorageInfo;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.model.singleplayer.LorenzoMagnifico;
import org.junit.Test;

import java.util.ArrayList;



public class CLITest {


    @Test
    public void printDevelopmentCard() {
        DevelopmentCardGrid d = new DevelopmentCardGrid();
        CLI cli = new CLI();

        ArrayList<DevelopmentCardDeck> deck = d.getDevCardsDecks();

        for(DevelopmentCardDeck p : deck){
            for(DevelopmentCard c : p.getDeck()){
                System.out.println(cli.getInitialize().printDevelopmentCard(c) + c.toString());

            }
        }
    }

    @Test
    public void printLeaderCard() {
        GameBoard gameBoard = new GameBoard(2);
        CLI cli = new CLI();

        LeaderCardDeck deck = gameBoard.getLeaderDeck();

        for(LeaderCard d: deck.getDeck()){
            System.out.println(cli.getInitialize().printLeaderCard(d));
        }
    }

    @Test
    public void printMarket(){
        CLI cli = new CLI();
        Market market=new Market();

        System.out.println(cli.getPrint().printMarket(market));
    }

    @Test
    public void printFaithPath(){
        CLI cli = new CLI();
        FaithPath path=new FaithPath();
        FaithPathInfo msg=new FaithPathInfo("ciao",path, true);
        cli.printFaithPath( msg);
    }

    @Test
    public void printLorenzoFaith(){
        CLI cli = new CLI();
        FaithPath path=new FaithPath();
        cli.getPrint().printLorenzoFaith(path.getPositionLorenzo());
    }

    @Test
    public void printStorage(){
        CLI cli = new CLI();

        Shelf shelf1 = new Shelf(1, 1, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 1, Resource.COIN);
        Shelf shelf3 = new Shelf(3, 2, Resource.SERVANT);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        Vault vault = new Vault();
        vault.AddResource(Resource.COIN, 5);
        vault.AddResource(Resource.SHIELD, 3);

        StorageInfo  s = new StorageInfo(storage, vault, "nickname", true);

        cli.printStorageAndVault(s);

    }


    @Test
    public void printDevelopmentCardSpace() {
        CLI cli = new CLI();


        DevCardsSpace devCardsSpace = new DevCardsSpace();

        devCardsSpace.checkSpace(cli.getInitialize().getDevelopmentCardDeck().getCardByID(17), 1);
        devCardsSpace.addCard(cli.getInitialize().getDevelopmentCardDeck().getCardByID(17), 1);

        devCardsSpace.checkSpace(cli.getInitialize().getDevelopmentCardDeck().getCardByID(17), 2);
        devCardsSpace.addCard(cli.getInitialize().getDevelopmentCardDeck().getCardByID(17), 2);

        devCardsSpace.checkSpace(cli.getInitialize().getDevelopmentCardDeck().getCardByID(17), 3);
        devCardsSpace.addCard(cli.getInitialize().getDevelopmentCardDeck().getCardByID(17), 3);

        DevCardsSpaceInfo devCardsSpaceInfo = new DevCardsSpaceInfo(devCardsSpace);

        cli.printDevelopmentCardsSpace(devCardsSpaceInfo);

    }

    @Test
    public void printActionToken(){
        LorenzoMagnifico lorenzoMagnifico = new LorenzoMagnifico();
        CLI cli = new CLI();

        cli.printActionToken(lorenzoMagnifico.getTokens().get(0));
        cli.printActionToken(lorenzoMagnifico.getTokens().get(1));
        cli.printActionToken(lorenzoMagnifico.getTokens().get(2));
        cli.printActionToken(lorenzoMagnifico.getTokens().get(3));
        cli.printActionToken(lorenzoMagnifico.getTokens().get(4));
        cli.printActionToken(lorenzoMagnifico.getTokens().get(5));
        cli.printActionToken(lorenzoMagnifico.getTokens().get(6));
    }

    @Test
    public void testLeaderCard() {
        CLI cli = new CLI();
        GameBoard gameBoard = new GameBoard(2);

        LeaderCard leader1 = gameBoard.getLeaderDeck().get(5);
        leader1.setIsActive();

        LeaderCard leader2 = gameBoard.getLeaderDeck().get(7);
        leader2.setIsDiscarded();

        LeaderCard leader3 = gameBoard.getLeaderDeck().get(9);

        System.out.println((cli.getInitialize().printLeaderCard(leader1)));
        System.out.println((cli.getInitialize().printLeaderCard(leader2)));
        System.out.println((cli.getInitialize().printLeaderCard(leader3)));
    }

}