package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.DevelopmentCardGridTest;
import it.polimi.ingsw.model.GameBoardTest;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;
import it.polimi.ingsw.model.card.deck.LeaderCardDeck;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.card.leadercard.LeaderCard;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.gameboard.DevelopmentCardGrid;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.playerdashboard.Market;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static org.junit.Assert.*;

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

        Integer code = 18;
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
}