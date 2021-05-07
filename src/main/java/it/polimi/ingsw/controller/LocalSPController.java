package it.polimi.ingsw.controller;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.client.view.CLI.SinglePlayerCLI;
import it.polimi.ingsw.exceptions.EmptyDecksException;
import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.exceptions.NotExistingPlayerException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.server.answer.DevCardsSpaceInfo;
import it.polimi.ingsw.server.answer.FaithPathInfo;
import it.polimi.ingsw.server.answer.StorageInfo;
import it.polimi.ingsw.server.answer.turnanswer.ActiveLeader;
import it.polimi.ingsw.server.answer.turnanswer.DiscardLeader;

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
        } catch(NotExistingPlayerException e){
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

    public void localChooseTurn() throws NotExistingPlayerException {
        int answer = spCLI.askTurnType("Choose what you want to do in this turn:");

        switch (answer) {
            case 1:
                try {
                    int pos = spCLI.activeLeader(new ActiveLeader("Which leader do you want to activate?", gameModel.getPlayer(players.get(0)).getLeaders().IdDeck()));
                    turncontroller.activeLeader(0, pos);
                } catch (InvalidChoiceException e) {
                    spCLI.writeMessage("Invalid choice.");
                    localChooseTurn();
                }
                break;

            case 2:
                try {
                    int pos = spCLI.discardLeader(new DiscardLeader("Which leader do you want to discard?", gameModel.getPlayer(players.get(0)).getLeaders().IdDeck()));
                    turncontroller.discardLeader(0, pos);
                } catch (InvalidChoiceException e) {
                    spCLI.writeMessage("Invalid choice.");
                    localChooseTurn();
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
