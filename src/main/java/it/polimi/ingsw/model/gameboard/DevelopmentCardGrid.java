package it.polimi.ingsw.model.gameboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.Goods;
import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;

import javax.swing.text.DefaultEditorKit;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * In this class we create the instances of the development
 * cards before inserting them ordered by color and level.
 *
 * @author Lorenzo Iovine, Nicola Landini, Francesco Leone.
 */

//tested class
public class DevelopmentCardGrid {
    private final ArrayList<DevelopmentCardDeck> devCardsDecks = new ArrayList<>();

    public ArrayList<DevelopmentCardDeck> getDevCardsDecks() {
        return devCardsDecks;
    }

    public DevelopmentCardGrid() throws FileNotFoundException {
        initializeDevCards();
    }

    public void ShufflesAllDecks(){
        for(DevelopmentCardDeck d: devCardsDecks){
            d.shuffle();
        }
    }

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

    public void removeCard(CardColor color, int level) throws InvalidChoiceException{
        getDeck(color, level).draw();
    }

    public DevelopmentCard getCard(CardColor color, int level) throws InvalidChoiceException{
        return getDeck(color, level).get();
    }

    public void initializeDevCards() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("src/main/java/it/polimi/ingsw/model/resources/DevelopmentCards.json"));
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
