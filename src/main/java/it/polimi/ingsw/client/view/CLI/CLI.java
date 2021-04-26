package it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.model.Goods;
import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.card.leadercard.*;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.enumeration.Resource;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CLI implements View {
    private final Scanner in;
    private final HashMap<CardColor, String> CardColorToString = new HashMap<>();
    private final HashMap<Resource, String> ResourceToString = new HashMap<>();

    public CLI() {
        this.in = new Scanner(System.in);
        initializeCardColorToString();
        initializeResourceToString();
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
        System.out.println("\n Welcome to" + Constants.MAESTRI_RINASCIMENTO + Constants.AUTHORS);
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
        pointsBuilder.append(developmentCard.getLevel());
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



}
