package it.polimi.ingsw.model.gameboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.card.deck.LeaderCardDeck;
import it.polimi.ingsw.model.card.leadercard.*;
import it.polimi.ingsw.model.singleplayer.LorenzoMagnifico;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;


/**
 * This class represents the game board of the game, which contains
 * the market, the development cards grid and the boards of each player.
 * In this class the leader cards are initialized and the Lorenzo
 * il Magnifico board can be present if the game is in single player.
 *
 * @author Lorenzo Iovine, Francesco Leone, Nicola Landini
 */
public class GameBoard {
    private int playersNumber;
    private final Market market;
    private final DevelopmentCardGrid developmentCardGrid;
    private final LeaderCardDeck LeaderDeck;
    private final LorenzoMagnifico lorenzoMagnifico;


    /**
     * Constructor GameBoard create a new GameBoard instance
     * @param playersNumber is the number of players in the game
     */
    public GameBoard(int playersNumber) {
        this.playersNumber = playersNumber;
        market = new Market();
        developmentCardGrid = new DevelopmentCardGrid();
        lorenzoMagnifico=new LorenzoMagnifico();
        LeaderDeck = new LeaderCardDeck();

        try {
            initializeLeaderCards();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets Lorenzo il Magnifico
     * @return lorenzoMagnifico variable
     */
    public LorenzoMagnifico getLorenzoMagnifico(){ return lorenzoMagnifico; }


    /**
     * Gets the development cards grid of the game board
     * @return developmentCardGrid variable
     */
    public DevelopmentCardGrid getDevelopmentCardGrid() {
        return developmentCardGrid;
    }


    /**
     * Get the market of the game board
     * @return market variable
     */
    public Market getMarket() {
        return market;
    }


    /**
     * Initialize the leader cards
     * @throws FileNotFoundException if the file wasn't found
     */
    public void initializeLeaderCards() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader jsonReaderEcon = new JsonReader(new FileReader("src/main/java/it/polimi/ingsw/model/resources/EconomyLeaders.json"));
        JsonReader jsonReaderProd = new JsonReader(new FileReader("src/main/java/it/polimi/ingsw/model/resources/ProductionLeaders.json"));
        JsonReader jsonReaderStorage = new JsonReader(new FileReader("src/main/java/it/polimi/ingsw/model/resources/StorageLeaders.json"));
        JsonReader jsonReaderWhite = new JsonReader(new FileReader("src/main/java/it/polimi/ingsw/model/resources/WhiteBallLeaders.json"));

        ArrayList<EconomyLeader> leadersEcon ;
        ArrayList<ProductionLeader> leadersProd;
        ArrayList<StorageLeader> leadersStorage;
        ArrayList<WhiteBallLeader> leadersWhite;


        leadersEcon = gson.fromJson(jsonReaderEcon, new TypeToken<ArrayList<EconomyLeader>>(){}.getType());
        leadersProd = gson.fromJson(jsonReaderProd, new TypeToken<ArrayList<ProductionLeader>>(){}.getType());
        leadersStorage = gson.fromJson(jsonReaderStorage, new TypeToken<ArrayList<StorageLeader>>(){}.getType());
        leadersWhite = gson.fromJson(jsonReaderWhite, new TypeToken<ArrayList<WhiteBallLeader>>(){}.getType());

        for(EconomyLeader e: leadersEcon)
            LeaderDeck.add(e);
        for(ProductionLeader p: leadersProd)
            LeaderDeck.add(p);
        for(StorageLeader s: leadersStorage)
            LeaderDeck.add(s);
        for(WhiteBallLeader w: leadersWhite)
            LeaderDeck.add(w);

        LeaderDeck.shuffle();
    }


    /**
     * Get the leader deck of the game
     * @return LeaderDeck variable
     */
    public LeaderCardDeck getLeaderDeck() {
        return LeaderDeck;
    }
}
