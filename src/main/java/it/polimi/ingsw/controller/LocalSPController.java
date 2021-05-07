package it.polimi.ingsw.controller;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.client.view.CLI.SinglePlayerCLI;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.card.leadercard.StorageLeader;
import it.polimi.ingsw.model.card.leadercard.WhiteBallLeader;
import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.Ball;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.server.answer.DevCardsSpaceInfo;
import it.polimi.ingsw.server.answer.FaithPathInfo;
import it.polimi.ingsw.server.answer.StorageInfo;
import it.polimi.ingsw.server.answer.turnanswer.ActiveLeader;
import it.polimi.ingsw.server.answer.turnanswer.DiscardLeader;
import it.polimi.ingsw.server.answer.turnanswer.SeeBall;

import java.util.ArrayList;

public class LocalSPController {

    private final Game gameModel;
    private final TurnController turncontroller;
    private final EndGameController endgame;
    private final ArrayList<String> players=new ArrayList<>();
    private boolean isEnd=false;
    private SinglePlayerCLI spCLI;

    public LocalSPController(String nickname) {
        this.players.add(nickname);
        gameModel=new Game(players.size(), players);
        turncontroller=new TurnController(gameModel, players);
        endgame=new EndGameController();
        spCLI = new SinglePlayerCLI();
    }

    //LOCAL SINGLE PLAYER

    public void localGame() {
        ActionToken currentActionToken;

        gameModel.createPlayer(players.get(0));
        spCLI.localHandShake(players.get(0));

        turncontroller.setPlayers(0);

        try{
            turncontroller.setInitialLeaderCards(0);

            localDiscardFirstLeader();

            spCLI.writeMessage("The game start!\n");

            while(!isEnd) {
                localSeePlayerDashboard();
                localSeeGameBoard();
                localChooseTurn();

                try {
                    currentActionToken = gameModel.drawActionToken();
                    spCLI.writeMessage("Drawn action token: ");
                    spCLI.printActionToken(currentActionToken);
                } catch (EmptyDecksException e) {
                    break;
                } catch (InvalidChoiceException e) {
                    e.printStackTrace();
                }
                isEnd = endgame.SinglePlayerIsEndGame(gameModel);
            }
        } catch(NotExistingPlayerException | InterruptedException | InvalidChoiceException e){
            e.printStackTrace();
        }

        try {
            if(endgame.SinglePlayerLose(gameModel)){
                localLose();
            } else {
                localWin();
            }
        } catch (InvalidChoiceException e) {
            e.printStackTrace();
        }
    }

    public void localDiscardFirstLeader() throws NotExistingPlayerException {
        int card;

        card=spCLI.discardFirstLeader(1, gameModel.getPlayers().get(0).getLeaders().IdDeck());
        gameModel.getPlayer(players.get(0)).getPlayerDashboard().getLeaders().remove(card-1);

        card=spCLI.discardFirstLeader(2, gameModel.getPlayers().get(0).getLeaders().IdDeck());
        gameModel.getPlayer(players.get(0)).getPlayerDashboard().getLeaders().remove(card-1);
    }

    public void localSeePlayerDashboard(){
        spCLI.printFaithPath(new FaithPathInfo(("This is the Dashboard of "+players.get(0)+" :"), gameModel.getPlayers().get(0).getPlayerDashboard().getFaithPath(), true));
        spCLI.printStorage(new StorageInfo(gameModel.getPlayers().get(0).getPlayerDashboard().getStorage(),
                gameModel.getPlayers().get(0).getPlayerDashboard().getVault()));
        spCLI.printDevelopmentCardsSpace(new DevCardsSpaceInfo(gameModel.getPlayers().get(0).getPlayerDashboard().getDevCardsSpace()));
    }

    public void localSeeGameBoard() throws NotExistingPlayerException {
        //1) Leader Cards, 2) Market, 3) Grid, 4) Possible Production
        int answer=spCLI.seeGameBoard("What do you want to see from the game board:");

        int finish;

        switch (answer){
            case 1: finish=spCLI.seeLeaderCards(gameModel.getPlayer(players.get(0)).getLeaders().IdDeck());
                if(finish==1) localSeeGameBoard();
                break;
            case 2: finish=spCLI.seeMarket(gameModel.getGameBoard().getMarket());
                if(finish==1) localSeeGameBoard();
                break;
            case 3: int choice=spCLI.chooseLine("Please choose what you want to see from the Development Cards Grid");
                finish=spCLI.seeGrid(gameModel.getGameBoard().getDevelopmentCardGrid().getLine(choice).IdDeck());
                if(finish==1) localSeeGameBoard();
                break;
            case 4:finish=spCLI.seeProductions(gameModel.getPlayer(players.get(0)).getProductions());
                if(finish==1) localSeeGameBoard();
                break;
            case 5 : break;
        }
    }

    public void localChooseTurn() throws NotExistingPlayerException, InterruptedException, InvalidChoiceException {
        int answer;

        int pos;
        int type=0;

        do{
            answer = spCLI.askTurnType("Choose what you want to do in this turn:");
            switch (answer) {
                case 1:
                    pos = spCLI.activeLeader(new ActiveLeader("Which leader do you want to activate?", gameModel.getPlayer(players.get(0)).getLeaders().IdDeck()));
                    try{
                        turncontroller.activeLeader(0, pos);
                    } catch (InvalidChoiceException e) {
                        spCLI.writeMessage("Invalid choice.");
                        spCLI.resetCard(gameModel.getPlayer(players.get(0)).getLeaders().get(pos - 1).getCardID());
                        localChooseTurn();
                    }
                    break;

                case 2:
                    pos = spCLI.discardLeader(new DiscardLeader("Which leader do you want to discard?", gameModel.getPlayer(players.get(0)).getLeaders().IdDeck()));
                    try{
                        turncontroller.discardLeader(0, pos);
                    } catch (InvalidChoiceException e) {
                        spCLI.writeMessage("Invalid choice.");
                        spCLI.resetCard(gameModel.getPlayer(players.get(0)).getLeaders().get(pos - 1).getCardID());
                        localChooseTurn();
                    }
                    break;

                case 3:
                    int choice = spCLI.localManageStorage(1);
                    if(choice==1) localManageStorage();
                    int line = spCLI.useMarket("Which market line do you want?");
                    localUseMarket(0, line);
                    break;

                case 4:
                    int color = spCLI.askColor("Choose the color of the card you want to buy.\n1) Purple\n2) Yellow\n3) Blue\n4) Green");
                    int level = spCLI.askLevel("Choose the level of the card you want to buy");
                    int space = spCLI.askSpace("Choose the space where to insert the card");
                    localBuyCard(color, level, space);
                    break;

                case 5:
                    do{
                        type=spCLI.askType("What kind of production do you want to activate?\n1) Basic Production\n2) Development Card\n3) Leader Card\n4) It's okay, do productions");
                        localActiveProduction(type);
                    } while (type != 4);
                    break;

            }
        } while (answer==1 || answer==2);

        answer=spCLI.endTurn("What do you want to do?\n1) Active Leader\n2) Discard Leader\n3) End turn");

        while(answer==1 || answer==2) {
            if (answer == 1) {
                pos = spCLI.activeLeader(new ActiveLeader("Which leader do you want to activate?", gameModel.getPlayer(players.get(0)).getLeaders().IdDeck()));
                try{
                    turncontroller.activeLeader(0, pos);
                } catch (InvalidChoiceException e) {
                    spCLI.writeMessage("Invalid choice.");
                    spCLI.resetCard(gameModel.getPlayer(players.get(0)).getLeaders().get(pos - 1).getCardID());
                    localChooseTurn();
                }
            } else {
                pos = spCLI.discardLeader(new DiscardLeader("Which leader do you want to discard?", gameModel.getPlayer(players.get(0)).getLeaders().IdDeck()));
                try{
                    turncontroller.discardLeader(0, pos);
                } catch (InvalidChoiceException e) {
                    spCLI.writeMessage("Invalid choice.");
                    spCLI.resetCard(gameModel.getPlayer(players.get(0)).getLeaders().get(pos - 1).getCardID());
                    localChooseTurn();
                }
            }
            answer=spCLI.endTurn("What do you want to do?\n1) Active Leader\n2) Discard Leader\n3) End turn");
        }
    }


    public void localManageStorage() throws NotExistingPlayerException {
        int action;

        do{
            ArrayList<Integer> choice = spCLI.MoveShelves("Which shelves do you want to reverse?");

            try {
                gameModel.getPlayer(players.get(0)).getPlayerDashboard().getStorage().InvertShelvesContent(choice.get(0),choice.get(1));
            } catch (NotEnoughSpaceException e) {
                System.out.println("Invalid choice");
            }


            action=spCLI.localManageStorage(2);
        } while (action==1);

    }

    public void localUseMarket(int player, int line) throws InvalidChoiceException, NotExistingPlayerException, InterruptedException {
        ArrayList<Integer> choice=new ArrayList<>();
        Resource resource = null;

        ArrayList<Ball> market = new ArrayList<>(gameModel.getGameBoard().getMarket().getChosenColor(line));
        ArrayList<Ball> balls = new ArrayList<>();
        ArrayList<Ball> toPlace = new ArrayList<>();

        if (gameModel.getPlayer(players.get(player)).WhiteBallLeader() == 2) {
            int ball = spCLI.chooseWhiteBallLeader("You have 2 active WhiteBall leaders, which one do you want to use in this turn? (1 or 2)");
            resource = ((WhiteBallLeader) gameModel.getPlayer(players.get(player)).getLeaders().get(ball - 1)).getConversionType();
        } else if (gameModel.getPlayer(players.get(player)).WhiteBallLeader() == 1) {
            if (gameModel.getPlayer(players.get(player)).getLeaders().get(0) instanceof WhiteBallLeader)
                resource = ((WhiteBallLeader) gameModel.getPlayer(players.get(player)).getLeaders().get(0)).getConversionType();
            else
                resource = ((WhiteBallLeader) gameModel.getPlayer(players.get(player)).getLeaders().get(1)).getConversionType();
        }

        for(Ball b : market) {
            if (b.getType().equals(BallColor.RED)) {
                gameModel.getPlayer(players.get(player)).getPlayerDashboard().getFaithPath().moveForward(1);
            } else if (b.getType().equals(BallColor.WHITE)) {
                if (resource != null) {
                    switch (resource) {
                        case COIN:
                            balls.add(new Ball(BallColor.YELLOW));
                        case SERVANT:
                            balls.add(new Ball(BallColor.PURPLE));
                        case SHIELD:
                            balls.add(new Ball(BallColor.BLUE));
                        case STONE:
                            balls.add(new Ball(BallColor.GREY));
                    }
                }
            } else {
                balls.add(new Ball(b.getType()));
            }
        }

        for(Ball b : balls) {
            int i = gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().typePresent(b.getCorrespondingResource());

            if (gameModel.getPlayer(players.get(player)).StorageLeader(b.getCorrespondingResource())) {
                toPlace.add(new Ball(b.getType()));
            } else {
                if (i != 0) {
                    if (gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().getShelves().get(i - 1).getAvailableSpace() == 0) {
                        for (int j = 0; j < players.size(); j++) {
                            if (j != player) gameModel.getPlayer(players.get(j)).move(1);
                        }
                    } else {
                        toPlace.add(new Ball(b.getType()));
                        //gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().getShelves().get(i-1).setResourceAmount(1);
                    }
                } else {
                    if (gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().emptyShelves() == 0) {
                        for (int j = 0; j < players.size(); j++) {
                            if (j != player) gameModel.getPlayer(players.get(j)).move(1);
                        }
                    } else {
                        toPlace.add(new Ball(b.getType()));
                    }
                }
            }
        }

        if(!toPlace.isEmpty()) {
            do {
                choice.addAll(spCLI.localSeeBall(new SeeBall(toPlace)));

                try {
                    if(choice.get(1)==4) {
                        int card=gameModel.getPlayer(players.get(player)).indexOfStorageLeader(toPlace.get((choice.get(0)) - 1).getCorrespondingResource());
                        ((StorageLeader) gameModel.getPlayer(players.get(player)).getLeaders().get(card - 1)).AddResources(toPlace.get((choice.get(0)) - 1).getCorrespondingResource(), 1);
                    } else {
                        gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(choice.get(1), toPlace.get((choice.get(0)) - 1).getCorrespondingResource(), 1);
                    }
                    toPlace.remove(choice.get(0) - 1);
                    ArrayList<Ball> temp=new ArrayList<>(turncontroller.checkEmptyShelves(0,toPlace));
                    toPlace.clear();
                    toPlace.addAll(temp);
                } catch (NotEnoughSpaceException | ShelfHasDifferentTypeException | AnotherShelfHasTheSameTypeException | InvalidChoiceException e) {
                    System.out.println("Invalid choice");
                }

                choice.clear();
            } while (toPlace.size() > 0);
        }
    }

    public void localBuyCard(int color, int level, int space) throws InvalidChoiceException, NotExistingPlayerException, InterruptedException {
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

        DevelopmentCard card=gameModel.getGameBoard().getDevelopmentCardGrid().getCard(cardColor,level);

        try {
            gameModel.getPlayer(players.get(0)).buyCard(card, space);
            gameModel.getGameBoard().getDevelopmentCardGrid().removeCard(cardColor,level);
        } catch(InvalidSpaceCardException e) {
            System.out.println("Invalid choice");
            localChooseTurn();
        }
    }

    public void localActiveProduction(int type) throws NotExistingPlayerException, InterruptedException {
        //1) Basic Production, 2) Development Card, 3) Production Leader, 4) Do production

        switch(type) {
            case 1 : Resource input1=spCLI.localAskInput("Choose the input:\n1) COIN\n2) SERVANT\n3) SHIELD\n4) STONE");
                Resource input2=spCLI.localAskInput("Choose the input:\n1) COIN\n2) SERVANT\n3) SHIELD\n4) STONE");
                Resource output=spCLI.localAskOutput("Choose the output:\n1) COIN\n2) SERVANT\n3) SHIELD\n4) STONE");
                gameModel.getPlayer(players.get(0)).getPlayerDashboard().getDevCardsSpace().setInputBasicProduction(input1, input2);
                gameModel.getPlayer(players.get(0)).getPlayerDashboard().getDevCardsSpace().setOutputBasicProduction(output);
                try {
                    gameModel.getPlayer(players.get(0)).ActiveProductionBase();
                } catch (NotEnoughResourceException e) {
                    System.out.println("Invalid choice");
                }
                break;

            case 2 : int space=spCLI.askDevelopmentCard("Insert the number of the space containing the development card");
                try {
                    gameModel.getPlayer(players.get(0)).ActiveProductionDevCard(space);
                } catch (InvalidChoiceException | NotEnoughResourceException e) {
                    System.out.println("Invalid choice");
                }
                break;

            case 3 : int index=spCLI.askLeaderCard("Insert the number of the production leader that you want to use");
                try {
                    gameModel.getPlayer(players.get(0)).ActiveProductionLeader(index);
                } catch (InvalidChoiceException | NotEnoughResourceException e) {
                    System.out.println("Invalid choice");
                }
                break;

            case 4 : try {
                gameModel.getPlayer(players.get(0)).doProduction();
            } catch (NotEnoughResourceException e) {
                System.out.println("Invalid choice");
            }
                break;
        }
    }

    public void localLose(){
        int victoryPoints = endgame.totalVictoryPoints(gameModel.getCurrentPlayer());
        spCLI.lose("You had accumulated "+ Constants.ANSI_BLUE + victoryPoints + Constants.ANSI_RESET +" victory points");
    }

    public void localWin(){
        int victoryPoints = endgame.totalVictoryPoints(gameModel.getCurrentPlayer());
        spCLI.win("You had accumulated "+ Constants.ANSI_BLUE + victoryPoints + Constants.ANSI_RESET +" victory points...that's amazing.");
    }


}
