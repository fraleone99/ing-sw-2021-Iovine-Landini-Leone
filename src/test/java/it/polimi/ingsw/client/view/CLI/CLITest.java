package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.exceptions.InvalidSpaceCardException;
import it.polimi.ingsw.model.DevelopmentCardGridTest;
import it.polimi.ingsw.model.GameBoardTest;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;
import it.polimi.ingsw.model.card.deck.LeaderCardDeck;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.card.leadercard.LeaderCard;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.DevelopmentCardGrid;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.playerdashboard.*;
import it.polimi.ingsw.server.answer.DevCardsSpaceInfo;
import it.polimi.ingsw.server.answer.StorageInfo;
import it.polimi.ingsw.model.gameboard.playerdashboard.Market;
import it.polimi.ingsw.model.singleplayer.LorenzoMagnifico;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;



public class CLITest {


    @Test
    public void printDevelopmentCard() throws FileNotFoundException {
        DevelopmentCardGrid d = new DevelopmentCardGrid();
        CLI cli = new CLI();

        ArrayList<DevelopmentCardDeck> deck = d.getDevCardsDecks();

        for(DevelopmentCardDeck p : deck){
            for(DevelopmentCard c : p.getDeck()){
                System.out.println(cli.printDevelopmentCard(c) + c.toString());

            }
        }
    }

    @Test
    public void printLeaderCard() throws FileNotFoundException {
        GameBoard gameBoard = new GameBoard(2);
        CLI cli = new CLI();

        LeaderCardDeck deck = gameBoard.getLeaderDeck();

        for(LeaderCard d: deck.getDeck()){
            System.out.println(cli.printLeaderCard(d));
        }
    }

    @Test
    public void printMarket(){
        CLI cli = new CLI();
        Market market=new Market();

        System.out.println(cli.printMarket(market));
    }

    @Test
    public void printDevelopmentCardGrid(){
        CLI cli = new CLI();
        ArrayList<Integer> cards = new ArrayList<>();

        int code = 18;
        cards.add(code);

        code = 22;
        cards.add(code);

        code = 45;
        cards.add(code);

        code = 54;
        cards.add(code);

        System.out.println(cli.printDevelopmentCardGrid(cards));
    }

    @Test
    public void printFaithPath(){
        CLI cli = new CLI();
        System.out.println(cli.printFaithPath(8, false, true, true));
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

        StorageInfo  s = new StorageInfo(storage, vault);

        cli.printStorage(s);

    }


    @Test
    public void printDevelopmentCardSpace() throws InvalidSpaceCardException {
        CLI cli = new CLI();


        DevCardsSpace devCardsSpace = new DevCardsSpace();
        devCardsSpace.AddCard(cli.getDevelopmentCardDeck().getCardByID(17), 1);
        devCardsSpace.AddCard(cli.getDevelopmentCardDeck().getCardByID(18), 2);
        devCardsSpace.AddCard(cli.getDevelopmentCardDeck().getCardByID(29), 3);

        DevCardsSpaceInfo devCardsSpaceInfo = new DevCardsSpaceInfo(devCardsSpace);

        cli.printDevelopmentCardsSpace(devCardsSpaceInfo);

    }

    @Test
    public void printActionToken(){
        LorenzoMagnifico lorenzoMagnifico = new LorenzoMagnifico();
        CLI cli = new CLI();

        System.out.println(cli.printActionToken(lorenzoMagnifico.getTokens().get(0)));
        System.out.println(cli.printActionToken(lorenzoMagnifico.getTokens().get(1)));
        System.out.println(cli.printActionToken(lorenzoMagnifico.getTokens().get(2)));
        System.out.println(cli.printActionToken(lorenzoMagnifico.getTokens().get(3)));
        System.out.println(cli.printActionToken(lorenzoMagnifico.getTokens().get(4)));
        System.out.println(cli.printActionToken(lorenzoMagnifico.getTokens().get(5)));
        System.out.println(cli.printActionToken(lorenzoMagnifico.getTokens().get(6)));
    }

    @Test
    public void setBackgroundColor(){
        CLI cli = new CLI();

        cli.setBackgroundColor();
    }
}