package it.polimi.ingsw.model.gameboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.card.leadercard.EconomyLeader;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.Goods;
import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;

import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * In this class we create the instances of the development
 * cards before inserting them ordered by color and level.
 *
 * @author Lorenzo Iovine, Nicola Landini, Francesco Leone.
 */

public class DevelopmentCardGrid {
    private final ArrayList<DevelopmentCardDeck> devCardsDecks = new ArrayList<>();

    public ArrayList<DevelopmentCardDeck> getDevCardsDecks() {
        return devCardsDecks;
    }

    /**
     * DevelopmentCardGrid constructor: creates new instance of DevelopmentCardGrid
     */
    public DevelopmentCardGrid() {
        try {
            initializeDevCards();
            shufflesAllDecks();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method shuffles all decks of the development card grid
     */
    public void shufflesAllDecks(){
        for(DevelopmentCardDeck d: devCardsDecks){
            d.shuffle();
        }
    }

    public DevelopmentCardDeck getGrid() {
        DevelopmentCardDeck deck = new DevelopmentCardDeck();
        try {
            for(int i=1; i<4; i++) {
                    deck.addAll(addFromLevel(i));
            }
        } catch (InvalidChoiceException e) {
            e.printStackTrace();
        }
        return deck;
    }

    /**
     * This method gets the line of the grid requested by the player
     * @param choice player choice
     * @return the line of the grid requested by the player
     */
    public DevelopmentCardDeck getLine(int choice) {
        // 1-3 level, 4-7 color
        DevelopmentCardDeck deck = new DevelopmentCardDeck();
        try {
            switch (choice) {
                case 1:
                    deck.addAll(addFromLevel(1));
                    break;
                case 2:
                    deck.addAll(addFromLevel(2));
                    break;
                case 3:
                    deck.addAll(addFromLevel(3));
                    break;
                case 4:
                    deck.addAll(addFromColor(CardColor.PURPLE));
                    break;
                case 5:
                    deck.addAll(addFromColor(CardColor.YELLOW));
                    break;
                case 6:
                    deck.addAll(addFromColor(CardColor.BLUE));
                    break;
                case 7:
                    deck.addAll(addFromColor(CardColor.GREEN));
                    break;
            }
        }catch(InvalidChoiceException e) {
            e.printStackTrace();
        }

        return deck;
    }

    /**
     * This method returns a deck containing the cards on the top of the grid for the requested level
     * @param level cards level
     * @return a deck containing the cards on the top of the grid for the requested level
     * @throws InvalidChoiceException if level is invalid
     */
    public DevelopmentCardDeck addFromLevel(int level) throws InvalidChoiceException {
        DevelopmentCardDeck devCards = new DevelopmentCardDeck();


        devCards.add(getCard(CardColor.PURPLE,level));
        devCards.add(getCard(CardColor.YELLOW,level));
        devCards.add(getCard(CardColor.BLUE,level));
        devCards.add(getCard(CardColor.GREEN,level));

        return devCards;
    }

    /**
     * This method returns a deck containing the cards on the top of the grid for the requested color
     * @param color cards color
     * @return a deck containing the cards on the top of the grid for the requested color
     * @throws InvalidChoiceException if color is invalid
     */
    public DevelopmentCardDeck addFromColor(CardColor color) throws InvalidChoiceException {
        DevelopmentCardDeck devCards = new DevelopmentCardDeck();

        devCards.add(getCard(color,1));
        devCards.add(getCard(color,2));
        devCards.add(getCard(color,3));

        return devCards;
    }

    public CardColor parserColor(int color) {
        CardColor cardColor;

        switch (color) {
            case 1 : cardColor=CardColor.PURPLE;
                break;
            case 2 : cardColor=CardColor.YELLOW;
                break;
            case 3 : cardColor=CardColor.BLUE;
                break;
            case 4 : cardColor=CardColor.GREEN;
                break;
            default: cardColor=null;
        }

        return cardColor;
    }

    /**
     * This method returns the corresponding deck by color and level
     * @param color card color
     * @param level card level
     * @return the corresponding deck
     * @throws InvalidChoiceException if color or level are invalid
     */
    public DevelopmentCardDeck getDeck(CardColor color, int level) throws InvalidChoiceException {
        switch (color){
            case PURPLE: switch (level){
                case 1: return devCardsDecks.get(0);
                case 2: return devCardsDecks.get(1);
                case 3: return devCardsDecks.get(2);
            }
            case YELLOW: switch (level){
                case 1: return devCardsDecks.get(3);
                case 2: return devCardsDecks.get(4);
                case 3: return devCardsDecks.get(5);
            }
            case BLUE: switch (level){
                case 1: return devCardsDecks.get(6);
                case 2: return devCardsDecks.get(7);
                case 3: return devCardsDecks.get(8);
            }
            case GREEN: switch (level){
                case 1: return devCardsDecks.get(9);
                case 2: return devCardsDecks.get(10);
                case 3: return devCardsDecks.get(11);
            }
            default: throw new InvalidChoiceException();
        }
    }

    /**
     * This method remove a card of given color and level
     * @param color card color
     * @param level card level
     * @throws InvalidChoiceException if color or level are invalid
     */
    public void removeCard(CardColor color, int level) throws InvalidChoiceException {
        getDeck(color, level).draw();
    }

    /**
     * This method returns a card of given color and level
     * @param color card color
     * @param level card level
     * @return the card corresponding to color and level
     * @throws InvalidChoiceException if color or level are invalid
     */
    public DevelopmentCard getCard(CardColor color, int level) throws InvalidChoiceException{
        if(!getDeck(color,level).isEmpty()) return getDeck(color,level).get();
        else return null;
    }

    /**
     * This method returns true if all decks of the same selected color, are empty
     * @param color card color
     * @return true if all decks of the same selected color are empty
     * @throws InvalidChoiceException if color is invalid
     */
    public boolean sameColorDeckAreEmpty(CardColor color) throws InvalidChoiceException {
        return getDeck(color, 1).isEmpty() && getDeck(color, 2).isEmpty() && getDeck(color, 3).isEmpty();
    }

    /**
     * This method initializes the development cards
     * @throws FileNotFoundException if the file can't be found
     */
    public void initializeDevCards() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader jsonReader= new JsonReader(new InputStreamReader(DevelopmentCard.class.getResourceAsStream("/JSON/DevelopmentCards.json"), StandardCharsets.UTF_8));

        ArrayList<DevelopmentCard> data = gson.fromJson(jsonReader, new TypeToken<ArrayList<DevelopmentCard>>(){}.getType());

        DevelopmentCardDeck PurpleOne = new DevelopmentCardDeck();
        DevelopmentCardDeck PurpleTwo = new DevelopmentCardDeck();
        DevelopmentCardDeck PurpleThree = new DevelopmentCardDeck();

        DevelopmentCardDeck YellowOne = new DevelopmentCardDeck();
        DevelopmentCardDeck YellowTwo = new DevelopmentCardDeck();
        DevelopmentCardDeck YellowThree = new DevelopmentCardDeck();

        DevelopmentCardDeck BlueOne = new DevelopmentCardDeck();
        DevelopmentCardDeck BlueTwo = new DevelopmentCardDeck();
        DevelopmentCardDeck BlueThree = new DevelopmentCardDeck();

        DevelopmentCardDeck GreenOne = new DevelopmentCardDeck();
        DevelopmentCardDeck GreenTwo = new DevelopmentCardDeck();
        DevelopmentCardDeck GreenThree = new DevelopmentCardDeck();

        PurpleOne.setDeck(data.subList(0,4));
        PurpleTwo.setDeck(data.subList(4,8));
        PurpleThree.setDeck(data.subList(8,12));

        YellowOne.setDeck(data.subList(12,16));
        YellowTwo.setDeck(data.subList(16,20));
        YellowThree.setDeck(data.subList(20,24));

        BlueOne.setDeck(data.subList(24,28));
        BlueTwo.setDeck(data.subList(28,32));
        BlueThree.setDeck(data.subList(32,36));

        GreenOne.setDeck(data.subList(36,40));
        GreenTwo.setDeck(data.subList(40,44));
        GreenThree.setDeck(data.subList(44,48));

        devCardsDecks.add(PurpleOne);
        devCardsDecks.add(PurpleTwo);
        devCardsDecks.add(PurpleThree);

        devCardsDecks.add(YellowOne);
        devCardsDecks.add(YellowTwo);
        devCardsDecks.add(YellowThree);

        devCardsDecks.add(BlueOne);
        devCardsDecks.add(BlueTwo);
        devCardsDecks.add(BlueThree);

        devCardsDecks.add(GreenOne);
        devCardsDecks.add(GreenTwo);
        devCardsDecks.add(GreenThree);
    }
}
