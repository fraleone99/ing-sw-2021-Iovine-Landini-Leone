package it.polimi.ingsw.client.view.CLI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.model.Goods;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;
import it.polimi.ingsw.model.card.deck.LeaderCardDeck;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.card.leadercard.*;
import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.playerdashboard.Ball;
import it.polimi.ingsw.model.gameboard.playerdashboard.Market;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.model.singleplayer.BlackCrossMover;
import it.polimi.ingsw.model.singleplayer.DeleteCard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CLI implements View {
    private final Scanner in;
    private final HashMap<CardColor, String> CardColorToString = new HashMap<>();
    private final HashMap<Resource, String> ResourceToString = new HashMap<>();
    private final HashMap<BallColor, String> BallToString = new HashMap<>();
    private final HashMap<CardColor, String> TokenToString = new HashMap<>();

    private LeaderCardDeck LeaderDeck;
    private final ArrayList<DevelopmentCardDeck> devCardsDecks = new ArrayList<>();


    public CLI() {
        this.in = new Scanner(System.in);
        initializeCardColorToString();
        initializeResourceToString();
        initializeBallToString();
        initializeTokenToString();


        LeaderDeck = new LeaderCardDeck();
        try {
            initializeDevelopmentCard();
            initializeLeaderCard();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initializeDevelopmentCard() throws FileNotFoundException {
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

    private void initializeLeaderCard() throws FileNotFoundException {
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

    public void startGame() {
        System.out.println(Constants.MAESTRI_RINASCIMENTO + Constants.AUTHORS);
        }


    @Override
    public void handShake(String welcome) {
        startGame();
        System.out.println(welcome);
    }

    @Override
    public String askPlayerNumber(String message) {
        String number;
        System.out.println(message);

        do {
            number = in.nextLine();
            if(Integer.parseInt(number)<2 || Integer.parseInt(number)>4){
                System.out.println("Incorrect number, please try again:");
            }
            else if(Integer.parseInt(number) == 1){
                System.out.println("Sorry, single player is still under development. Try again: ");
            }
        } while (Integer.parseInt(number)<2 || Integer.parseInt(number)>4);
        return number;
    }

    @Override
    public String askNickname(String message) {
        String nickname;
        System.out.println(message);
        nickname = in.nextLine();
        return  nickname;
    }

    public int askResource(String message) {
        int resource;

        System.out.println(message);
        System.out.println("Press the corresponding number:\n1) COIN\n2) STONE\n3) SHIELD\n4) SERVANT");

        do {
            resource=Integer.parseInt(in.nextLine());
            if(resource<1 || resource >4) {
                System.out.println("Incorrect number, please try again:");
            }
        } while(resource<1 || resource>4);

        return resource;
    }

    public void readMessage(String message) {
        System.out.println(message);
    }

    public int askLeaderToDiscard(ArrayList<Integer> IdLeaders) {
        int card;

        System.out.println("Entering the corresponding number");

        for(int i=0;i<IdLeaders.size();i++){
            System.out.println(i+1+")");
            System.out.println(printLeaderCard(LeaderDeck.getFromID(IdLeaders.get(i))));
        }

        do {
            card=Integer.parseInt(in.nextLine());
            if(card<1 || card>IdLeaders.size()) {
                System.out.println("Incorrect number, please try again:");
            }
        } while(card<1 || card>IdLeaders.size());

        return card;
    }

    public String printGood(Goods goods , String color){
        //good
        StringBuilder goodBuilder = new StringBuilder();
        goodBuilder.append(color + "+" + Constants.ANSI_RESET);
        for(int i = 0; i < goods.getAmount(); i++){
            goodBuilder.append(ResourceToString.get((goods.getType())));
        }
        if(goods.getAmount()<0) {
            for (int i = 0; i < 12 - goods.getAmount(); i++) {
                goodBuilder.append(" ");
            }
        }
        if(goods.getAmount()<=2){
            goodBuilder.append(" ");
        }
        goodBuilder.append(color + "+\n" + Constants.ANSI_RESET);

        return goodBuilder.toString();
    }

    public String printDevelopmentCard(DevelopmentCard developmentCard){

        String Color = CardColorToString.get(developmentCard.getColor());
        String fourteenSpace = "              ";
        int MAX_LENGTH = 14;
        int nres = 0;



        //cost
        StringBuilder costBuilder = new StringBuilder();
        costBuilder.append("Cost:");
        costBuilder.append("         "+ Color + "+\n" + Constants.ANSI_RESET);
        for(Goods g: developmentCard.getCost()){
            costBuilder.append(printGood(g,Color));
        }
        String cost = costBuilder.toString();

        //level
        StringBuilder levelBuilder = new StringBuilder();
        levelBuilder.append("  Level: ");
        levelBuilder.append(developmentCard.getLevel());
        for(int i = levelBuilder.length(); i< MAX_LENGTH; i++){
            levelBuilder.append(" ");
        }
        String level = levelBuilder.toString();

        //victoryPoints
        StringBuilder pointsBuilder = new StringBuilder();
        pointsBuilder.append(" Points: ");
        pointsBuilder.append(developmentCard.getVictoryPoints());
        for(int i = pointsBuilder.length(); i< MAX_LENGTH; i++){
            pointsBuilder.append(" ");
        }
        String points = pointsBuilder.toString();


        //production
        StringBuilder productionBuilder = new StringBuilder();
        productionBuilder.append("Production:");
        productionBuilder.append("   "+ Color + "+\n" + Constants.ANSI_RESET);
        for(Goods g : developmentCard.getInputProduction()){
            productionBuilder.append(printGood(g, Color));
        }
        productionBuilder.append(Color + "+" + Constants.ANSI_RESET);
        productionBuilder.append("----->");
        productionBuilder.append("        "+ Color + "+\n" + Constants.ANSI_RESET);
        for(Goods g : developmentCard.getOutputProduction()){
            productionBuilder.append(printGood(g, Color));
        }
        if(developmentCard.getFaithSteps() > 0) {
            productionBuilder.append(Color + "+" + Constants.ANSI_RESET);
            int k;
            for (k = 0; k < developmentCard.getFaithSteps(); k++){
                productionBuilder.append(Constants.FAITH);
            }
            for(; k<MAX_LENGTH; k++){
                pointsBuilder.append(" ");
            }
            productionBuilder.append(Color + "+\n" + Constants.ANSI_RESET);
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

        leaderCardBuilder.append(LEADER_CARD_TOP_EDGE);

        return leaderCardBuilder.toString();
    }

    public String printWhiteBallLeader(WhiteBallLeader leaderCard){
        StringBuilder whiteBuilder = new StringBuilder();
        whiteBuilder.append(Constants.LEADER_CARD_LEFT_EDGE);
        whiteBuilder.append("+Conversion : +\n");
        whiteBuilder.append(Constants.LEADER_CARD_LEFT_EDGE);
        whiteBuilder.append(ResourceToString.get(leaderCard.getConversionType()));

        whiteBuilder.append(Constants.LEADER_CARD_RIGHT_EDGE);

        return whiteBuilder.toString();
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
        StringBuilder storageLeader = new StringBuilder();

        storageLeader.append(Constants.LEADER_CARD_LEFT_EDGE);
        storageLeader.append("Shelf: ");
        storageLeader.append(ResourceToString.get(leaderCard.getType()));
        storageLeader.append(Constants.LEADER_CARD_RIGHT_EDGE);

        return storageLeader.toString();

    }

    public String printEconomyLeader(EconomyLeader leaderCard){
        StringBuilder economyBuilder = new StringBuilder();
        economyBuilder.append(Constants.LEADER_CARD_LEFT_EDGE);
        economyBuilder.append("Discount: ");
        economyBuilder.append("-1").append(ResourceToString.get(leaderCard.getDiscountType()));
        economyBuilder.append(Constants.LEADER_CARD_RIGHT_EDGE);


        return economyBuilder.toString();
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


    public String printMarket(Market market){
        StringBuilder marketBuilder = new StringBuilder();

        marketBuilder.append("Ramp: "+BallToString.get(market.getRamp().getType())+"\n");

        marketBuilder.append(Constants.BALL_TOP_BOTTOM_EDGE+"\n");

        int i = 7;
        for(Ball[] a: market.getMatrix()){
            marketBuilder.append("+");
            for(Ball b: a){
                marketBuilder.append(" "+BallToString.get(b.getType()));
            }
            marketBuilder.append(" +  "+Constants.LEFT_ARROW+" "+i+"\n");
            i--;
        }

        marketBuilder.append(Constants.BALL_TOP_BOTTOM_EDGE+"\n");

        marketBuilder.append("  ");
        for(i=1; i<=4; i++){
            marketBuilder.append(Constants.UPPER_ARROW+"  ");
        }
        marketBuilder.append("\n"+"  ");

        for(i=1; i<=4; i++){
            marketBuilder.append(i+"  ");
        }

        return marketBuilder.toString();
    }

    public void initializeBallToString(){
        BallToString.put(BallColor.WHITE, Constants.WhiteBALL);
        BallToString.put(BallColor.PURPLE, Constants.PurpleBALL);
        BallToString.put(BallColor.BLUE, Constants.BlueBALL);
        BallToString.put(BallColor.YELLOW, Constants.YellowBALL);
        BallToString.put(BallColor.GREY, Constants.GreyBALL);
        BallToString.put(BallColor.RED, Constants.RedBALL);
    }


    public String printDevelopmentCardGrid(ArrayList<Integer> cardsID){
        StringBuilder gridByLevelBuilder = new StringBuilder();

        for(DevelopmentCardDeck decks: devCardsDecks){
            for(DevelopmentCard card: decks.getDeck()){
                for(Integer i: cardsID){
                    if(i==card.getCardID()){
                        gridByLevelBuilder.append(printDevelopmentCard(card));
                    }
                }
            }
        }

        return gridByLevelBuilder.toString();
    }


    public String printFaithPath(int position, boolean papalPawn1, boolean papalPawn2, boolean papalPawn3){
        StringBuilder faithPathBuilder = new StringBuilder();

        faithPathBuilder.append(Constants.FAITH_LEGEND);

        faithPathBuilder.append(Constants.FAITH_TOP_EDGE);
        for(int i=0; i<13; i++){
            faithPathBuilder.append(printFaithPathSupport(i, position));
        }
        faithPathBuilder.append("\n"+Constants.FAITH_MIDDLE_EDGE+"\n");
        for(int j=24; j>12; j--){
            faithPathBuilder.append(printFaithPathSupport(j, position));
        }
        faithPathBuilder.append("\n"+Constants.FAITH_BOTTOM_EDGE);

        if(papalPawn1 || papalPawn2 || papalPawn3){
            faithPathBuilder.append("\n       --> Active papal pawn: ");
            if(papalPawn1) faithPathBuilder.append(Constants.PapalPOINT1 + " ");
            if(papalPawn2) faithPathBuilder.append(Constants.PapalPOINT2 + " ");
            if(papalPawn3) faithPathBuilder.append(Constants.PapalPOINT3 + " ");
        }

        return faithPathBuilder.toString();
    }

    public String printFaithPathSupport(int num, int position){
        StringBuilder faithPathSupport = new StringBuilder();

        if(num==position){
            faithPathSupport.append("   "+"+ "+Constants.ColorFAITH+" +");
        } else if (num==8 || num==16 || num==24){
            faithPathSupport.append("   "+"+ "+Constants.PapalCELL+" +");
        } else {
            if((num>=5 && num<=7) || (num>=12 && num<=15) || (num>=19 && num<=23)){
                faithPathSupport.append("   "+"+ "+Constants.ANSI_RED+num+Constants.ANSI_RESET+" +");
            } else {
                faithPathSupport.append("   "+"+ "+Constants.ANSI_CYAN+num+Constants.ANSI_RESET+" +");
            }
        }

        return faithPathSupport.toString();
    }


    public String printActionToken(ActionToken actionToken){
        StringBuilder actionTokenBuilder = new StringBuilder();

        actionTokenBuilder.append("\n"+Constants.AT_TOP_BOTTOM_EDGE);
        if(actionToken instanceof BlackCrossMover){
            if(((BlackCrossMover) actionToken).haveToBeShuffled()){
                actionTokenBuilder.append(Constants.ANSI_WHITE+"**"+Constants.ANSI_RESET+Constants.TOP_CARD);
                actionTokenBuilder.append("+1 "+Constants.BCM+" "+Constants.SHUFFLE+Constants.BOTTOM_CARD);
                actionTokenBuilder.append(Constants.AT_TOP_BOTTOM_EDGE+Constants.ANSI_WHITE+"*****\n"+Constants.ANSI_RESET);
            } else {
                actionTokenBuilder.append(Constants.TOP_CARD+"+2 "+Constants.BCM+Constants.BOTTOM_CARD);
                actionTokenBuilder.append(Constants.AT_TOP_BOTTOM_EDGE+Constants.ANSI_WHITE+"***\n"+Constants.ANSI_RESET);
            }
        } else {
            actionTokenBuilder.append(Constants.TOP_CARD);
            actionTokenBuilder.append("-2  "+TokenToString.get(((DeleteCard)actionToken).getColorType()));
            actionTokenBuilder.append(Constants.BOTTOM_CARD);
            actionTokenBuilder.append(Constants.AT_TOP_BOTTOM_EDGE+Constants.ANSI_WHITE+"***\n"+Constants.ANSI_RESET);
        }

        return actionTokenBuilder.toString();
    }

    public void initializeTokenToString(){
        TokenToString.put(CardColor.PURPLE, Constants.PurpleCARD);
        TokenToString.put(CardColor.BLUE, Constants.BlueCARD);
        TokenToString.put(CardColor.GREEN, Constants.GreenCARD);
        TokenToString.put(CardColor.YELLOW, Constants.YellowCARD);
    }

}
