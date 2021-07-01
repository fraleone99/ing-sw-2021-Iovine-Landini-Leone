package it.polimi.ingsw.client.view.CLI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Constants;
import it.polimi.ingsw.model.Goods;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;
import it.polimi.ingsw.model.card.deck.LeaderCardDeck;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.card.leadercard.*;
import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.enumeration.Resource;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class initializes all maps and decks in CLI.
 * See CLI.
 */
public class CLIInitialize {
    private final LeaderCardDeck LeaderDeck = new LeaderCardDeck();
    private final DevelopmentCardDeck developmentCardDeck = new DevelopmentCardDeck();
    private final HashMap<CardColor, String> CardColorToString = new HashMap<>();
    private final HashMap<Resource, String> ResourceToString = new HashMap<>();
    private final HashMap<BallColor, String> BallToString = new HashMap<>();
    private final HashMap<CardColor, String> TokenToString = new HashMap<>();

    public CLIInitialize() {
        initializeCardColorToString();
        initializeResourceToString();
        initializeBallToString();
        initializeTokenToString();

        initializeDevelopmentCard();
        initializeLeaderCard();
    }

    private void initializeDevelopmentCard(){
        Gson gson = new Gson();
        JsonReader jsonReader= new JsonReader(new InputStreamReader(Objects.requireNonNull(DevelopmentCard.class.getResourceAsStream("/JSON/DevelopmentCards.json")), StandardCharsets.UTF_8));
        ArrayList<DevelopmentCard> data = gson.fromJson(jsonReader, new TypeToken<ArrayList<DevelopmentCard>>(){}.getType());

        developmentCardDeck.setDeck(data);
    }

    private void initializeLeaderCard(){
        jsonReader(LeaderDeck);
    }

    public static void jsonReader(LeaderCardDeck leaderDeck) {
        Gson gson = new Gson();
        JsonReader jsonReaderEcon = new JsonReader(new InputStreamReader(Objects.requireNonNull(EconomyLeader.class.getResourceAsStream("/JSON/EconomyLeaders.json")), StandardCharsets.UTF_8));
        JsonReader jsonReaderProd = new JsonReader(new InputStreamReader(Objects.requireNonNull(ProductionLeader.class.getResourceAsStream("/JSON/ProductionLeaders.json")), StandardCharsets.UTF_8));
        JsonReader jsonReaderStorage = new JsonReader(new InputStreamReader(Objects.requireNonNull(StorageLeader.class.getResourceAsStream("/JSON/StorageLeaders.json")), StandardCharsets.UTF_8));
        JsonReader jsonReaderWhite = new JsonReader(new InputStreamReader(Objects.requireNonNull(WhiteBallLeader.class.getResourceAsStream("/JSON/WhiteBallLeaders.json")), StandardCharsets.UTF_8));

        ArrayList<EconomyLeader> leadersEcon ;
        ArrayList<ProductionLeader> leadersProd;
        ArrayList<StorageLeader> leadersStorage;
        ArrayList<WhiteBallLeader> leadersWhite;


        leadersEcon = gson.fromJson(jsonReaderEcon, new TypeToken<ArrayList<EconomyLeader>>(){}.getType());
        leadersProd = gson.fromJson(jsonReaderProd, new TypeToken<ArrayList<ProductionLeader>>(){}.getType());
        leadersStorage = gson.fromJson(jsonReaderStorage, new TypeToken<ArrayList<StorageLeader>>(){}.getType());
        leadersWhite = gson.fromJson(jsonReaderWhite, new TypeToken<ArrayList<WhiteBallLeader>>(){}.getType());

        for(EconomyLeader e: leadersEcon)
            leaderDeck.add(e);
        for(ProductionLeader p: leadersProd)
            leaderDeck.add(p);
        for(StorageLeader s: leadersStorage)
            leaderDeck.add(s);
        for(WhiteBallLeader w: leadersWhite)
            leaderDeck.add(w);
    }

    private void initializeResourceToString() {
        ResourceToString.put(Resource.COIN, Constants.COIN);
        ResourceToString.put(Resource.STONE, Constants.STONE);
        ResourceToString.put(Resource.SHIELD, Constants.SHIELD);
        ResourceToString.put(Resource.SERVANT, Constants.SERVANT);
        ResourceToString.put(Resource.UNKNOWN, Constants.UNKNOWN);
    }

    private void initializeCardColorToString() {
        CardColorToString.put(CardColor.BLUE, Constants.ANSI_BLUE);
        CardColorToString.put(CardColor.GREEN, Constants.ANSI_GREEN);
        CardColorToString.put(CardColor.PURPLE, Constants.ANSI_PURPLE);
        CardColorToString.put(CardColor.YELLOW, Constants.ANSI_YELLOW);

    }

    public void initializeBallToString(){
        BallToString.put(BallColor.WHITE, Constants.WhiteBALL);
        BallToString.put(BallColor.PURPLE, Constants.PurpleBALL);
        BallToString.put(BallColor.BLUE, Constants.BlueBALL);
        BallToString.put(BallColor.YELLOW, Constants.YellowBALL);
        BallToString.put(BallColor.GREY, Constants.GreyBALL);
        BallToString.put(BallColor.RED, Constants.RedBALL);
    }

    public void initializeTokenToString(){
        TokenToString.put(CardColor.PURPLE, Constants.PurpleCARD);
        TokenToString.put(CardColor.BLUE, Constants.BlueCARD);
        TokenToString.put(CardColor.GREEN, Constants.GreenCARD);
        TokenToString.put(CardColor.YELLOW, Constants.YellowCARD);
    }

    public String printGood(Goods goods , String color){
        //good
        StringBuilder goodBuilder = new StringBuilder();
        goodBuilder.append(color).append("+").append(Constants.ANSI_RESET);
        for(int i = 0; i < goods.getAmount(); i++){
            goodBuilder.append(ResourceToString.get((goods.getType())));
        }
        if(goods.getAmount()<0) {
            goodBuilder.append(" ".repeat(Math.max(0, 12 - goods.getAmount())));
        }
        if(goods.getAmount()<=2){
            goodBuilder.append(" ");
        }
        goodBuilder.append(color).append("+\n").append(Constants.ANSI_RESET);

        return goodBuilder.toString();
    }

    public String printDevelopmentCard(DevelopmentCard developmentCard){
        String Color = CardColorToString.get(developmentCard.getColor());
        String fourteenSpace = "              ";
        int MAX_LENGTH = 14;

        //cost
        StringBuilder costBuilder = new StringBuilder();
        costBuilder.append("Cost:");
        costBuilder.append("         ").append(Color).append("+\n").append(Constants.ANSI_RESET);
        for(Goods g: developmentCard.getCost()){
            costBuilder.append(printGood(g,Color));
        }
        String cost = costBuilder.toString();

        //level
        StringBuilder levelBuilder = new StringBuilder();
        levelBuilder.append("  Level: ");
        levelBuilder.append(developmentCard.getLevel());
        levelBuilder.append(" ".repeat(Math.max(0, MAX_LENGTH - levelBuilder.length())));
        String level = levelBuilder.toString();

        //victoryPoints
        StringBuilder pointsBuilder = new StringBuilder();
        pointsBuilder.append(" Points: ");
        pointsBuilder.append(developmentCard.getVictoryPoints());
        pointsBuilder.append(" ".repeat(Math.max(0, MAX_LENGTH - pointsBuilder.length())));
        String points = pointsBuilder.toString();


        //production
        StringBuilder productionBuilder = new StringBuilder();
        productionBuilder.append("Production:");
        productionBuilder.append("   ").append(Color).append("+\n").append(Constants.ANSI_RESET);
        for(Goods g : developmentCard.getInputProduction()){
            productionBuilder.append(printGood(g, Color));
        }
        productionBuilder.append(Color).append("+").append(Constants.ANSI_RESET);
        productionBuilder.append("----->");
        productionBuilder.append("        ").append(Color).append("+\n").append(Constants.ANSI_RESET);
        for(Goods g : developmentCard.getOutputProduction()){
            productionBuilder.append(printGood(g, Color));
        }
        if(developmentCard.getFaithSteps() > 0) {
            productionBuilder.append(Color).append("+").append(Constants.ANSI_RESET);
            int k;
            for (k = 0; k < developmentCard.getFaithSteps(); k++){
                productionBuilder.append(Constants.FAITH);
            }
            for(; k<MAX_LENGTH; k++){
                pointsBuilder.append(" ");
            }
            productionBuilder.append(Color).append("+\n").append(Constants.ANSI_RESET);
        }
        String production = productionBuilder.toString();


        String DEV_CARD_EDGE = Color + "++++++++++++++++\n" + Constants.ANSI_RESET;
        String BODY =
                Color + "+" + Constants.ANSI_RESET + level  +  Color + "+\n" + Constants.ANSI_RESET +
                        Color + "+" + Constants.ANSI_RESET + points +  Color + "+\n" + Constants.ANSI_RESET +
                        Color + "+" + Constants.ANSI_RESET + fourteenSpace +   Color + "+\n" + Constants.ANSI_RESET +
                        Color + "+" + Constants.ANSI_RESET + cost +   Color  + Constants.ANSI_RESET +
                        Color + "+" + Constants.ANSI_RESET + production +   Color  + Constants.ANSI_RESET;
        return DEV_CARD_EDGE + BODY + DEV_CARD_EDGE;
    }


    public String printLeaderCard(LeaderCard leaderCard){
        StringBuilder leaderCardBuilder = new StringBuilder();
        String LEADER_CARD_TOP_EDGE = "++++++++++++++++\n";
        String LEADER_CARD_LEFT_EDGE = "+";
        String LEADER_CARD_RIGHT_EDGE = "+\n";
        String FIVE_WITHE_SPACE = "     ";

        //TOP
        leaderCardBuilder.append(LEADER_CARD_TOP_EDGE);

        //victory points
        String victoryPoints = LEADER_CARD_LEFT_EDGE+ "Points: " + leaderCard.getVictoryPoints() +
                FIVE_WITHE_SPACE + LEADER_CARD_RIGHT_EDGE;
        leaderCardBuilder.append(victoryPoints);

        //requirements
        leaderCardBuilder.append(requirementsToString(leaderCard.getRequirements()));

        //power
        if (leaderCard instanceof WhiteBallLeader){
            leaderCardBuilder.append(printWhiteBallLeader((WhiteBallLeader) leaderCard));
        }
        else if (leaderCard instanceof ProductionLeader){
            leaderCardBuilder.append(printProductionLeader((ProductionLeader) leaderCard));
        }
        else if (leaderCard instanceof StorageLeader){
            leaderCardBuilder.append(printStorageLeader((StorageLeader) leaderCard));
        }
        else if(leaderCard instanceof  EconomyLeader){
            leaderCardBuilder.append(printEconomyLeader((EconomyLeader) leaderCard));
        }

        if(leaderCard.getIsDiscarded()){
            leaderCardBuilder.append("+"+Constants.ANSI_RED+"  Discarded   "+Constants.ANSI_RESET+"+\n");
        } else if(leaderCard.getIsActive()){
            leaderCardBuilder.append("+"+Constants.ANSI_GREEN+"    Active    "+Constants.ANSI_RESET+"+\n");
        } else {
            leaderCardBuilder.append("+"+Constants.ANSI_WHITE+"Not played yet"+Constants.ANSI_RESET+"+\n");
        }

        leaderCardBuilder.append(LEADER_CARD_TOP_EDGE);

        return leaderCardBuilder.toString();
    }

    public String printWhiteBallLeader(WhiteBallLeader leaderCard){

        return Constants.LEADER_CARD_LEFT_EDGE +
                "+Conversion : +\n" +
                Constants.LEADER_CARD_LEFT_EDGE +
                ResourceToString.get(leaderCard.getConversionType()) +
                Constants.LEADER_CARD_RIGHT_EDGE;
    }

    public String printProductionLeader(ProductionLeader leaderCard){

        //production
        StringBuilder productionBuilder = new StringBuilder();
        productionBuilder.append("+Production:");
        productionBuilder.append("   " + "+\n" + Constants.ANSI_RESET);
        for(Goods g : leaderCard.getInputProduction()){
            productionBuilder.append(printGood(g, Constants.ANSI_RESET));
        }
        productionBuilder.append("+" + Constants.ANSI_RESET);
        productionBuilder.append("----->");
        productionBuilder.append("        " + "+\n" + Constants.ANSI_RESET);
        for(Goods g : leaderCard.getOutputProduction()){
            productionBuilder.append(printGood(g, Constants.ANSI_RESET));
        }
        if(leaderCard.getFaithSteps() > 0) {
            productionBuilder.append( "+" + Constants.ANSI_RESET);
            int k;
            for (k = 0; k < leaderCard.getFaithSteps(); k++){
                productionBuilder.append(Constants.FAITH);
            }
            productionBuilder.append("+\n" + Constants.ANSI_RESET);
        }
        return productionBuilder.toString();
    }

    public String printStorageLeader(StorageLeader leaderCard){

        return Constants.LEADER_CARD_LEFT_EDGE +
                "Shelf: " +
                ResourceToString.get(leaderCard.getType()) +
                Constants.LEADER_CARD_RIGHT_EDGE +
                Constants.LEADER_CARD_LEFT_EDGE +
                "Free space: " + (2 - leaderCard.getAmount()) + " " +
                Constants.LEADER_CARD_RIGHT_EDGE;

    }

    public String printEconomyLeader(EconomyLeader leaderCard){

        return Constants.LEADER_CARD_LEFT_EDGE +
                "Discount: " +
                "-1" + ResourceToString.get(leaderCard.getDiscountType()) +
                Constants.LEADER_CARD_RIGHT_EDGE;
    }

    public String requirementsToString(ArrayList<Requirements> requirements){
        StringBuilder requirementsBuilder = new StringBuilder();
        requirementsBuilder.append(Constants.LEADER_CARD_LEFT_EDGE);
        requirementsBuilder.append("Requirements: +\n");
        requirementsBuilder.append(Constants.LEADER_CARD_LEFT_EDGE);
        if(requirements.get(0).getAmount() > 0){
            for(Requirements requirements1: requirements) {
                String color = CardColorToString.get(requirements1.getColor());
                if(requirements1.getLevel()!= 0) requirementsBuilder.append("Level:").append(requirements1.getLevel()).append(" ");
                requirementsBuilder.append(color);
                requirementsBuilder.append(Constants.CIRCLE);

                requirementsBuilder.append(Constants.ANSI_RESET);
            }
        }
        else{
            for(Goods g: requirements.get(0).getCost()){
                requirementsBuilder.append(printGood(g,Constants.ANSI_RESET));
            }
        }
        requirementsBuilder.append(Constants.LEADER_CARD_RIGHT_EDGE);

        return  requirementsBuilder.toString();
    }

    public HashMap<Resource, String> getResourceToString() {
        return ResourceToString;
    }

    public HashMap<CardColor, String> getTokenToString() {
        return TokenToString;
    }

    public HashMap<BallColor, String> getBallToString() {
        return BallToString;
    }

    public LeaderCardDeck getLeaderDeck() {
        return LeaderDeck;
    }

    public DevelopmentCardDeck getDevelopmentCardDeck() {
        return developmentCardDeck;
    }

    public int askInt (int min, int max, String message){
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println(message);
        while (true)
            try
            {
                input = scanner.nextLine();
                if (Integer.parseInt(input) < min || Integer.parseInt(input) > max) {
                    System.out.println("Incorrect number, please try again:");
                }
                else
                    break;
            }
            catch (NumberFormatException e){
                System.out.println("You should insert a number!\n" + message);

            }
        return Integer.parseInt(input);
    }
}
