package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.Ball;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.model.singleplayer.BlackCrossMover;
import it.polimi.ingsw.model.singleplayer.DeleteCard;
import it.polimi.ingsw.server.answer.infoanswer.DevCardsSpaceInfo;
import it.polimi.ingsw.server.answer.infoanswer.FaithPathInfo;
import it.polimi.ingsw.server.answer.infoanswer.StorageInfo;

import java.util.HashMap;

public class CLIPrint {
    private final CLIInitialize initialize;

    public CLIPrint(CLIInitialize initialize) {
        this.initialize = initialize;
    }

    public String shelfToString(int capacity, Resource type, int amount) {
        StringBuilder shelfBuilder = new StringBuilder();

        if (amount == 0) {
            shelfBuilder.append(Constants.EMPTY_SPACE.repeat(Math.max(0, capacity)));
        } else {
            for (int i = 0; i < amount; i++) {
                shelfBuilder.append(initialize.getResourceToString().get(type));
            }
            shelfBuilder.append(Constants.EMPTY_SPACE.repeat(Math.max(0, capacity - amount)));
        }

        return shelfBuilder.toString();
    }

    public void printStorage(StorageInfo storageInfo) {
        if(storageInfo.isToPrint()) {
            System.out.println("This is the new configuration of your storage:");
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
                    Constants.STORAGE_TOP_BOTTOM_EDGE;

            System.out.println(storageBuilder);
        }

    }

    public void printStorageAndVault(StorageInfo storageInfo) {
        if (storageInfo.isToPrint()) {
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
    }


    public void printDevelopmentCardsSpace(DevCardsSpaceInfo devCardsSpaceInfo) {
        StringBuilder devCardsSpaceBuilder = new StringBuilder();

        HashMap<String, Integer> cards = devCardsSpaceInfo.getNumberOfCardByColor();

        devCardsSpaceBuilder.append("Victory points by dev Cards: ").append(devCardsSpaceInfo.getVictoryPoints()).append("\n");
        devCardsSpaceBuilder.append("Development Cards of the player: \n");


        cards.forEach((k, v) -> {
            if (v > 0) {
                devCardsSpaceBuilder.append(k).append(":").append(v).append("\n");
            }
        });

        System.out.println(devCardsSpaceBuilder);
    }


    public void printFaithPath(FaithPathInfo path) {
        System.out.println(path.getMessage());

        StringBuilder faithPathBuilder = new StringBuilder();

        faithPathBuilder.append(Constants.FAITH_LEGEND);

        faithPathBuilder.append(Constants.FAITH_TOP_EDGE);
        for (int i = 0; i < 13; i++) {
            faithPathBuilder.append(printFaithPathSupport(i, path.getPosition()));
        }
        faithPathBuilder.append("\n").append(Constants.FAITH_MIDDLE_EDGE).append("\n");
        for (int j = 24; j > 12; j--) {
            faithPathBuilder.append(printFaithPathSupport(j, path.getPosition()));
        }
        faithPathBuilder.append("\n").append(Constants.FAITH_BOTTOM_EDGE);

        if (path.isPapal1() || path.isPapal2() || path.isPapal3()) {
            faithPathBuilder.append("\n       --> Active papal pawn: ");
            if (path.isPapal1()) faithPathBuilder.append(Constants.PapalPOINT1).append(" ");
            if (path.isPapal2()) faithPathBuilder.append(Constants.PapalPOINT2).append(" ");
            if (path.isPapal3()) faithPathBuilder.append(Constants.PapalPOINT3).append(" ");
        }
        faithPathBuilder.append("\n");

        if (path.isSinglePlayer()) {
            faithPathBuilder.append(printLorenzoFaith(path.getLorenzoPos()));
        }

        System.out.println(faithPathBuilder);
    }

    public String printFaithPathSupport(int num, int position) {
        StringBuilder faithPathSupport = new StringBuilder();

        if (num == position) {
            faithPathSupport.append("   " + "+ " + Constants.ColorFAITH + " +");
        } else if (num == 8 || num == 16 || num == 24) {
            faithPathSupport.append("   " + "+ " + Constants.PapalCELL + " +");
        } else {
            if ((num >= 5 && num <= 7) || (num >= 12 && num <= 15) || (num >= 19 && num <= 23)) {
                faithPathSupport.append("   " + "+ " + Constants.ANSI_RED).append(num).append(Constants.ANSI_RESET).append(" +");
            } else {
                faithPathSupport.append("   " + "+ " + Constants.ANSI_CYAN).append(num).append(Constants.ANSI_RESET).append(" +");
            }
        }

        return faithPathSupport.toString();
    }

    public String printLorenzoFaith(int pos) {
        StringBuilder lorenzoBuilder = new StringBuilder();

        lorenzoBuilder.append(Constants.ANSI_WHITE + "\n   This is Lorenzo Il Magnifico's faith path: " + Constants.ANSI_RESET);

        lorenzoBuilder.append("\n").append(Constants.FAITH_MIDDLE_EDGE).append("\n");
        for (int i = 0; i < 13; i++) {
            lorenzoBuilder.append(printFaithPathSupport(i, pos));
        }
        lorenzoBuilder.append("\n").append(Constants.FAITH_MIDDLE_EDGE).append("\n");
        for (int j = 24; j > 12; j--) {
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
            actionTokenBuilder.append("-2  ").append(initialize.getTokenToString().get(((DeleteCard) actionToken).getColorType()));
            actionTokenBuilder.append(Constants.BOTTOM_CARD);
            actionTokenBuilder.append(Constants.AT_TOP_BOTTOM_EDGE+Constants.ANSI_WHITE+"***\n"+Constants.ANSI_RESET);
        }

        System.out.println(actionTokenBuilder);
    }

    public String printMarket(Market market) {
        StringBuilder marketBuilder = new StringBuilder();

        marketBuilder.append("Ramp: ").append(initialize.getBallToString().get(market.getRamp().getType())).append("\n");

        marketBuilder.append(Constants.BALL_TOP_BOTTOM_EDGE + "\n");

        int i = 5;
        for (Ball[] a : market.getMatrix()) {
            marketBuilder.append("+");
            for (Ball b : a) {
                marketBuilder.append(" ").append(initialize.getBallToString().get(b.getType()));
            }
            marketBuilder.append(" +  " + Constants.LEFT_ARROW + " ").append(i).append("\n");
            i++;
        }

        marketBuilder.append(Constants.BALL_TOP_BOTTOM_EDGE).append("\n");

        marketBuilder.append("  ");
        for (i = 1; i <= 4; i++) {
            marketBuilder.append(Constants.UPPER_ARROW).append("  ");
        }
        marketBuilder.append("\n").append("  ");

        for (i = 1; i <= 4; i++) {
            marketBuilder.append(i).append("  ");
        }

        return marketBuilder.toString();
    }
}
