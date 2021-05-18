package it.polimi.ingsw.client.view.CLI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Constants;
import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.message.SendDoubleInt;
import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.message.SendString;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.model.Goods;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;
import it.polimi.ingsw.model.card.deck.LeaderCardDeck;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.card.leadercard.*;
import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.Ball;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.server.answer.infoanswer.DevCardsSpaceInfo;
import it.polimi.ingsw.server.answer.infoanswer.FaithPathInfo;
import it.polimi.ingsw.server.answer.infoanswer.StorageInfo;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.model.singleplayer.BlackCrossMover;
import it.polimi.ingsw.model.singleplayer.DeleteCard;
import it.polimi.ingsw.server.answer.request.RequestInt;
import it.polimi.ingsw.server.answer.turnanswer.ActiveLeader;
import it.polimi.ingsw.server.answer.turnanswer.DiscardLeader;
import it.polimi.ingsw.server.answer.seegameboard.SeeBall;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CLI implements View{
    private final Scanner in;
    private final HashMap<CardColor, String> CardColorToString = new HashMap<>();
    private final HashMap<Resource, String> ResourceToString = new HashMap<>();
    private final HashMap<BallColor, String> BallToString = new HashMap<>();
    private final HashMap<CardColor, String> TokenToString = new HashMap<>();

    private final LeaderCardDeck LeaderDeck = new LeaderCardDeck();
    private final DevelopmentCardDeck developmentCardDeck = new DevelopmentCardDeck();

    private  int gameMode;
    private  String ip;
    private int portNumber;
    private NetworkHandler handler;

    public CLI() {
        this.in = new Scanner(System.in);
        initializeCardColorToString();
        initializeResourceToString();
        initializeBallToString();
        initializeTokenToString();

        try {
            initializeDevelopmentCard();
            initializeLeaderCard();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setHandler(NetworkHandler handler) {
        this.handler = handler;
    }

    private void initializeDevelopmentCard() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("src/main/java/it/polimi/ingsw/model/resources/DevelopmentCards.json"));
        ArrayList<DevelopmentCard> data = gson.fromJson(jsonReader, new TypeToken<ArrayList<DevelopmentCard>>(){}.getType());

        developmentCardDeck.setDeck(data);
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

    public void initializeBallToString(){
        BallToString.put(BallColor.WHITE, Constants.WhiteBALL);
        BallToString.put(BallColor.PURPLE, Constants.PurpleBALL);
        BallToString.put(BallColor.BLUE, Constants.BlueBALL);
        BallToString.put(BallColor.YELLOW, Constants.YellowBALL);
        BallToString.put(BallColor.GREY, Constants.GreyBALL);
        BallToString.put(BallColor.RED, Constants.RedBALL);
    }

    public void startGame() {
        System.out.println(Constants.MAESTRI_RINASCIMENTO + Constants.AUTHORS);
        }


    @Override
    public void setupConnection() {
        System.out.println("Default configuration? (localhost) [y/n]");
        String config = in.nextLine();

        if(config.equals("y")){
            ip = "127.0.0.1";
            portNumber = 3456;
        }
        else{
            System.out.println("ip:");
            ip = in.nextLine();
            portNumber = askInt(1024, 65535, "Insert Port number: ");
        }

    }

    public String getIp() {
        return ip;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public int gameType(){
        return askInt(1,2 ,"Choose game mode:\n1) Local \n2) Connected match");
    }

    @Override
    public void handShake(String welcome) {
        Thread t=new Thread( () -> {
            startGame();
            System.out.println(welcome);
            handler.send(new SendString("Client connected!"));
        }
        );
        t.start();
    }

    @Override
    public void askPlayerNumber(String message) {
        Thread t=new Thread( () -> {
            int number;

            number = askInt(1, 4, message);

            handler.send(new SendInt(number));
        }
        );
        t.start();
    }

    @Override
    public void askNickname(String message) {
        Thread t=new Thread( () -> {
            String nickname;
            System.out.println(message);
            nickname = in.nextLine();
            handler.send(new SendString(nickname));
        }
        );
        t.start();
    }

    public int askInt (int min, int max, String message){
        String input;
        System.out.println(message);
        while (true)
            try
            {
                input = in.nextLine();
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

    public void askResource(String message) {
        Thread t=new Thread( () -> {
            int resource;

            System.out.println(message);
            //System.out.println("Press the corresponding number:\n1) COIN\n2) STONE\n3) SHIELD\n4) SERVANT");

            resource = askInt(1, 4, "Press the corresponding number:" +
                    "\n1) COIN\n2) STONE\n3) SHIELD\n4) SERVANT");

            handler.send(new SendInt(resource));
        }
        );
        t.start();
    }

    public void readMessage(String message) {
        System.out.println(message);
    }

    public void askLeaderToDiscard(ArrayList<Integer> IdLeaders) {
        Thread t=new Thread( () -> {
            int card;

            for (int i = 0; i < IdLeaders.size(); i++) {
                System.out.println(i + 1 + ")");
                System.out.println(printLeaderCard(LeaderDeck.getFromID(IdLeaders.get(i))));
            }

            card = askInt(1, IdLeaders.size(), "Enter the corresponding number:");

            handler.send(new SendInt(card));
        }
        );
        t.start();
    }


    public void seeGameBoard(String message) {
        Thread t=new Thread( () -> {
            int choice;
            System.out.println(message);

            choice = askInt(1, 7, "1) Leader Cards.\n2) Market.\n3) Development cards grid.\n" +
                    "4) Cards for production.\n5) Active Leader Cards of the other players.\n6) Development Cards" +
                    " that can be used by the other players.\n7) Nothing");

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void seeLeaderCards(ArrayList<Integer> leaderCards) {
        Thread t=new Thread( () -> {
            int choice;

            for (Integer leaderCard : leaderCards) {
                System.out.println(printLeaderCard(LeaderDeck.getFromID(leaderCard)));
            }

            choice = askInt(1, 2, "Do you want to see more from the Game Board?\n1) Yes\n2) No");

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void seeMarket(Market market) {
        Thread t=new Thread( () -> {
            int choice;

            System.out.println(printMarket(market));

            choice = askInt(1, 2, "Do you want to see more from the Game Board?\n1) Yes\n2) No");

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void chooseLine(String message) {
        Thread t=new Thread( () -> {
            int choice;

            System.out.println(message);

            choice = askInt(1, 7, "1) Level one cards\n2) Level two cards\n3) Level three cards\n" +
                    "4) Purple cards\n5) Yellow Cards\n6) Blue Cards\n7) Green Cards");

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void seeGrid(ArrayList<Integer> devCards) {
        Thread t=new Thread( () -> {
            int choice;

            for (Integer devCard : devCards) {
                System.out.println(printDevelopmentCard(developmentCardDeck.getCardByID(devCard)));
            }

            choice = askInt(1, 2, "Do you want to see more from the Game Board?\n1) Yes\n2) No");

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void seeProductions(ArrayList<Integer> productions) {
        Thread t=new Thread( () -> {
            int choice;

            if (productions.size() == 0) {
                System.out.println("You haven't cards to make productions.");
            } else {
                for (Integer production : productions) {
                    if (production < 17)
                        System.out.println(printLeaderCard(LeaderDeck.getFromID(production)));
                    else
                        System.out.println(printDevelopmentCard(developmentCardDeck.getCardByID(production)));
                }
            }

            choice = askInt(1, 2, "Do you want to see more from the Game Board?\n1) Yes\n2) No");

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void seeOtherCards(ArrayList<Integer> cards) {
        for(Integer card : cards) {
            if (card < 17)
                System.out.println(printLeaderCard(LeaderDeck.getFromID(card)));
            else
                System.out.println(printDevelopmentCard(developmentCardDeck.getCardByID(card)));
        }
    }

    public int choice() {
        int choice;

        choice = askInt(1,2,"Do you want to see more from the Game Board?\n1) Yes\n2) No");

        return choice;
    }

    public void askTurnType(String message) {
        Thread t=new Thread( () -> {
            int choose;

            System.out.println(message);

            choose = askInt(1, 5, "1) Active Leader Card.\n2) Discard Leader Card.\n3) Use Market.\n" +
                    "4) Buy Development Card.\n5) Active Productions");

            handler.send(new SendInt(choose));
        }
        );
        t.start();
    }

    public void activeLeader(ActiveLeader message){
        Thread t=new Thread( () -> {
            int leaderCard;

            leaderCard = askInt(1, 2, message.getMessage());

            int id = message.getLeaders().get(leaderCard - 1);

            LeaderDeck.getFromID(id).setIsActive();

            handler.send(new SendInt(leaderCard));
        }
        );
        t.start();
    }

    public void discardLeader(DiscardLeader message) {
        Thread t=new Thread( () -> {
            int leaderCard;

            leaderCard = askInt(1, 2, message.getMessage());

            int id = message.getLeaders().get(leaderCard - 1);

            LeaderDeck.getFromID(id).setIsDiscarded();

            handler.send(new SendInt(id));
        }
        );
        t.start();
    }

    public void resetCard(int pos) {
        LeaderDeck.getFromID(pos).setIsNotActive();
        LeaderDeck.getFromID(pos).setIsNotDiscarded();
    }

    public void ManageStorage(String message) {
        Thread t=new Thread( () -> {
            int choice;


            choice = askInt(1, 2, "Resources that cannot be placed, will be automatically discarded.\n" +
                    message + "\n1) Yes\n2) No");

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void MoveShelves(String message) {
        Thread t=new Thread( () -> {
            int firstShelf;
            int secondShelf;

            System.out.println(message);

            firstShelf = askInt(1, 3, "FirstShelf:");
            secondShelf = askInt(1, 3, "Second Shelf:");

            handler.send(new SendDoubleInt(firstShelf,secondShelf));
        }
        );
        t.start();
    }

    public void useMarket(String message) {
        Thread t=new Thread( () -> {
            int line;

            line = askInt(1, 7, message);

            handler.send(new SendInt(line));
        }
        );
        t.start();
    }

    public void chooseWhiteBallLeader(String message) {
        Thread t=new Thread( () -> {
            int choice;

            choice = askInt(1, 2, message);

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public int seeBall(SeeBall ball) {
        int choice;

        System.out.println("These are the resources taken from the market, which one do you want to place in the storage?");

        for(int i=0;i<ball.getBalls().size();i++) {
            System.out.println((i+1) + ")" + BallToString.get(ball.getBalls().get(i).getType()));
        }

        choice = askInt(1, ball.getBalls().size(), "");

        return choice;
    }

    public int chooseShelf() {
        int shelf;

        shelf = askInt(1,4,"Which shelf do you want to put this resource on? " +
                "(Press 4 if you want to use the Storage leaders)");

        return shelf;
    }

    public int askColor(String message) {
        int color;
        color = askInt(1,4, message);
        return color;
    }

    public int askLevel(String message) {
        int level;
        level = askInt(1,3,message);
        return level;
    }

    public int askSpace(String message) {
        int space;
        space = askInt(1,3, message);
        return space;
    }

    public int askType(String message) {
        int type;
        type = askInt(1,4,message);
        return type;
    }

    public int askInput(String message) {
        int input;
        input = askInt(1,4,message);
        return input;
    }

    public int askOutput(String message) {
        int output;
        output = askInt(1,4,message);
        return output;
    }

    public int askDevelopmentCard(String message) {
        int space;
        space = askInt(1,3,message);
        return space;
    }

    public int askLeaderCard(String message) {
        int index;
        index = askInt(1,2,message);
        return index;
    }

    public int endTurn(String message) {
        int choice;
        choice = askInt(1,3,message);
        return choice;
    }

    public String printGood(Goods goods , String color){
        //good
        StringBuilder goodBuilder = new StringBuilder();
        goodBuilder.append(color).append("+").append(Constants.ANSI_RESET);
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

    public String shelfToString(int capacity, Resource type, int amount) {
        StringBuilder shelfBuilder = new StringBuilder();

        if (amount == 0) {
            for (int i = 0; i < capacity; i++) {
                shelfBuilder.append(Constants.EMPTY_SPACE);
            }
        } else {
            for (int i = 0; i < amount; i++) {
                shelfBuilder.append(ResourceToString.get(type));
            }
            for (int i = 0; i < capacity - amount; i++) {
                shelfBuilder.append(Constants.EMPTY_SPACE);
            }
        }

        return shelfBuilder.toString();
    }

    public void printStorage(StorageInfo storageInfo){


        String storageBuilder = Constants.STORAGE_TOP_BOTTOM_EDGE +

                //FIRST SHELF
                Constants.EDGE + Constants.FOUR_EMPTY_SPACE +
                shelfToString(1, storageInfo.getShelf1Type(), storageInfo.getShelf1Amount()) +
                Constants.FIVE_EMPTY_SPACE + Constants.RIGHT_EDGE +

                //SECOND SHELF
                Constants.EDGE + Constants.THREE_EMPTY_SPACE +
                shelfToString(2, storageInfo.getShelf2Type(), storageInfo.getShelf2Amount()) +
                Constants.FIVE_EMPTY_SPACE + Constants.RIGHT_EDGE +

                //THIRD SHELF
                Constants.EDGE + Constants.TWO_EMPTY_SPACE +
                shelfToString(3, storageInfo.getShelf3Type(), storageInfo.getShelf3Amount()) +
                Constants.FOUR_EMPTY_SPACE + Constants.RIGHT_EDGE +
                Constants.STORAGE_TOP_BOTTOM_EDGE +

                //VAULT
                "\n" + Constants.VAULT + "\n" +
                Constants.COIN + ":" + storageInfo.getCoinsAmount() + "\n" +
                Constants.STONE + ":" + storageInfo.getStoneAmount() + "\n" +
                Constants.SERVANT + ":" + storageInfo.getServantsAmount() + "\n" +
                Constants.SHIELD + ":" + storageInfo.getShieldsAmount() + "\n";

        System.out.println(storageBuilder);

    }


    public void printDevelopmentCardsSpace(DevCardsSpaceInfo devCardsSpaceInfo){
        StringBuilder devCardsSpaceBuilder = new StringBuilder();

        HashMap<String, Integer> cards = devCardsSpaceInfo.getNumberOfCardByColor();

        devCardsSpaceBuilder.append("Victory points by dev Cards: ").append(devCardsSpaceInfo.getVictoryPoints()).append("\n");
        devCardsSpaceBuilder.append("Development Cards of the player: \n");


        cards.forEach((k,v)-> {
            if(v>0){
                devCardsSpaceBuilder.append(k).append(":").append(v).append("\n");
            }
        });

        System.out.println(devCardsSpaceBuilder);

    }


    public String printMarket(Market market){
        StringBuilder marketBuilder = new StringBuilder();

        marketBuilder.append("Ramp: ").append(BallToString.get(market.getRamp().getType())).append("\n");

        marketBuilder.append(Constants.BALL_TOP_BOTTOM_EDGE+"\n");

        int i = 5;
        for(Ball[] a: market.getMatrix()){
            marketBuilder.append("+");
            for(Ball b: a){
                marketBuilder.append(" ").append(BallToString.get(b.getType()));
            }
            marketBuilder.append(" +  " + Constants.LEFT_ARROW + " ").append(i).append("\n");
            i++;
        }

        marketBuilder.append(Constants.BALL_TOP_BOTTOM_EDGE).append("\n");

        marketBuilder.append("  ");
        for(i=1; i<=4; i++){
            marketBuilder.append(Constants.UPPER_ARROW).append("  ");
        }
        marketBuilder.append("\n").append("  ");

        for(i=1; i<=4; i++){
            marketBuilder.append(i).append("  ");
        }

        return marketBuilder.toString();
    }


    public String printDevelopmentCardGrid(ArrayList<Integer> cardsID){
        StringBuilder gridByLevelBuilder = new StringBuilder();

        for(int num: cardsID){
            gridByLevelBuilder.append(printDevelopmentCard(developmentCardDeck.getCardByID(num)));
        }

        return gridByLevelBuilder.toString();
    }


    public void printFaithPath(FaithPathInfo path){
        System.out.println(path.getMessage());

        StringBuilder faithPathBuilder = new StringBuilder();

        faithPathBuilder.append(Constants.FAITH_LEGEND);

        faithPathBuilder.append(Constants.FAITH_TOP_EDGE);
        for(int i=0; i<13; i++){
            faithPathBuilder.append(printFaithPathSupport(i, path.getPosition()));
        }
        faithPathBuilder.append("\n").append(Constants.FAITH_MIDDLE_EDGE).append("\n");
        for(int j=24; j>12; j--){
            faithPathBuilder.append(printFaithPathSupport(j, path.getPosition()));
        }
        faithPathBuilder.append("\n").append(Constants.FAITH_BOTTOM_EDGE);

        if(path.isPapal1() || path.isPapal2() || path.isPapal3()){
            faithPathBuilder.append("\n       --> Active papal pawn: ");
            if(path.isPapal1()) faithPathBuilder.append(Constants.PapalPOINT1).append(" ");
            if(path.isPapal2()) faithPathBuilder.append(Constants.PapalPOINT2).append(" ");
            if(path.isPapal3()) faithPathBuilder.append(Constants.PapalPOINT3).append(" ");
        }
        faithPathBuilder.append("\n");

        if(path.isSinglePlayer()){
            faithPathBuilder.append(printLorenzoFaith(path.getLorenzoPos()));
        }

        System.out.println(faithPathBuilder);
    }

    public String printFaithPathSupport(int num, int position){
        StringBuilder faithPathSupport = new StringBuilder();

        if(num==position){
            faithPathSupport.append("   "+"+ "+Constants.ColorFAITH+" +");
        } else if (num==8 || num==16 || num==24){
            faithPathSupport.append("   "+"+ "+Constants.PapalCELL+" +");
        } else {
            if((num>=5 && num<=7) || (num>=12 && num<=15) || (num>=19 && num<=23)){
                faithPathSupport.append("   " + "+ " + Constants.ANSI_RED).append(num).append(Constants.ANSI_RESET).append(" +");
            } else {
                faithPathSupport.append("   " + "+ " + Constants.ANSI_CYAN).append(num).append(Constants.ANSI_RESET).append(" +");
            }
        }

        return faithPathSupport.toString();
    }

    public String printLorenzoFaith(int pos){
        StringBuilder lorenzoBuilder = new StringBuilder();

        lorenzoBuilder.append(Constants.ANSI_WHITE+"\n   This is Lorenzo Il Magnifico's faith path: "+Constants.ANSI_RESET);

        lorenzoBuilder.append("\n").append(Constants.FAITH_MIDDLE_EDGE).append("\n");
        for(int i=0; i<13; i++){
            lorenzoBuilder.append(printFaithPathSupport(i, pos));
        }
        lorenzoBuilder.append("\n").append(Constants.FAITH_MIDDLE_EDGE).append("\n");
        for(int j=24; j>12; j--){
            lorenzoBuilder.append(printFaithPathSupport(j, pos));
        }
        lorenzoBuilder.append("\n").append(Constants.FAITH_MIDDLE_EDGE).append("\n");

        return lorenzoBuilder.toString();
    }

    public void printActionToken(ActionToken actionToken){
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
            actionTokenBuilder.append("-2  ").append(TokenToString.get(((DeleteCard) actionToken).getColorType()));
            actionTokenBuilder.append(Constants.BOTTOM_CARD);
            actionTokenBuilder.append(Constants.AT_TOP_BOTTOM_EDGE+Constants.ANSI_WHITE+"***\n"+Constants.ANSI_RESET);
        }

        System.out.println(actionTokenBuilder);
    }

    public void initializeTokenToString(){
        TokenToString.put(CardColor.PURPLE, Constants.PurpleCARD);
        TokenToString.put(CardColor.BLUE, Constants.BlueCARD);
        TokenToString.put(CardColor.GREEN, Constants.GreenCARD);
        TokenToString.put(CardColor.YELLOW, Constants.YellowCARD);
    }

    public DevelopmentCardDeck getDevelopmentCardDeck() {
        return developmentCardDeck;
    }

    public void win(String message){

        System.out.println(Constants.WIN +"\n" + Constants.ANSI_WHITE + message + Constants.ANSI_RESET + "\n" + Constants.THANKS_FOR_PLAYING);

    }

    public void lose(String message){
        System.out.println(Constants.LOSE + "\n" + Constants.ANSI_WHITE + message + Constants.ANSI_RESET + "\n" + Constants.THANKS_FOR_PLAYING);
    }

    public void setBackgroundColor(){
        System.out.println("\u001B[48;5;232m");
    }


}
