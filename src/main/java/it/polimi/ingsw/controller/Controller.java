package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.server.VirtualView;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private final Game gameModel;
    private final TurnController turncontroller;
    private final EndGameController endgame;
    private final ArrayList<String> players=new ArrayList<>();
    private boolean isOver;
    private final VirtualView view;
    private boolean isEnd=false;

    public Controller(ArrayList<String> players, VirtualView view) {
        this.players.addAll(players);
        gameModel=new Game(players.size(), players);
        turncontroller=new TurnController(gameModel, players);
        endgame=new EndGameController();
        this.view=view;
    }

    public void play() {
        try {
            for(int i=0; i<players.size(); i++) {
                turncontroller.setPlayers(i);
            }
            for (int i = 0; i < players.size(); i++) {
                setInitialBenefits(i);
                turncontroller.setInitialLeaderCards(i);
                discardFirstLeaders(i);
            }
            for(int i=0; i<players.size(); i++){
                startGame(i);
            }
            while(!isEnd){
                for(int i=0; i<players.size();i++){
                    seePlayerDashboards(i);
                    seeGameBoard(i);
                    chooseTurn(i);
                }
            }

        } catch (NotExistingPlayerException | IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public void setInitialBenefits(int i) throws NotExistingPlayerException, InterruptedException {
        switch(i){
            case 0: view.firstPlayer(players.get(i));
                    break;
            case 1:
                int resource1 = view.chooseResource(players.get(i), "second", 1);
                    turncontroller.addInitialResource(i, resource1,1);
                    break;
            case 2: resource1 =view.chooseResource(players.get(i), "third",1);
                    turncontroller.addInitialResource(i, resource1,1);
                    gameModel.getPlayer(players.get(i)).getPlayerDashboard().getFaithPath().moveForward(1);
                    break;
            case 3: resource1 =view.chooseResource(players.get(i), "fourth",1);
                    turncontroller.addInitialResource(i, resource1,2);
                int resource2 = view.chooseResource(players.get(i), "fourth", 2);
                    if(resource1 == resource2) {
                        turncontroller.addInitialResource(i, resource2, 2);
                    } else {
                        turncontroller.addInitialResource(i, resource2,1);
                    }
                    gameModel.getPlayer(players.get(i)).getPlayerDashboard().getFaithPath().moveForward(1);
        }
    }

    public void discardFirstLeaders(int player) throws  InterruptedException, NotExistingPlayerException {
        int card;
        card=view.discardFirstLeaders(players.get(player), 1, gameModel.getPlayer(players.get(player)).getLeaders().IdDeck());
        gameModel.getPlayer(players.get(player)).getPlayerDashboard().getLeaders().remove(card-1);
        card=view.discardFirstLeaders(players.get(player), 2, gameModel.getPlayer(players.get(player)).getLeaders().IdDeck());
        gameModel.getPlayer(players.get(player)).getPlayerDashboard().getLeaders().remove(card-1);
    }

    public void startGame(int player) {
        view.startGame(players.get(player));
    }

    public void seePlayerDashboards(int player) throws NotExistingPlayerException {
        for (String s : players) {
            view.seeFaithPath(players.get(player), s, gameModel.getPlayer(s).getPlayerDashboard().getFaithPath());
            view.seeStorage(players.get(player), gameModel.getPlayer(s).getPlayerDashboard().getStorage(),
                    gameModel.getPlayer(players.get(player)).getPlayerDashboard().getVault());
            view.seeDevCardsSpace(players.get(player), gameModel.getPlayer(s).getPlayerDashboard().getDevCardsSpace());
        }
    }

    public void seeGameBoard(int player) throws IOException, NotExistingPlayerException, InterruptedException {
        int answer=view.seeGameBoard(players.get(player));
        //1) Leader Cards, 2) Market, 3) Grid, 4) Possible Production
        int finish;

        switch(answer){
            case 1 : finish=view.seeLeaderCards(players.get(player), gameModel.getPlayer(players.get(player)).getLeaders().IdDeck());
                     if(finish==1) seeGameBoard(player);
                     break;
            case 2 : finish=view.seeMarket(players.get(player), gameModel.getGameBoard().getMarket());
                     if(finish==1) seeGameBoard(player);
                     break;
            case 3 : int choice=view.chooseLine(players.get(player));
                     finish=view.seeGrid(players.get(player), gameModel.getGameBoard().getDevelopmentCardGrid().getLine(choice).IdDeck());
                     if(finish==1) seeGameBoard(player);
                     break;
            case 4 : finish=view.seeProduction(players.get(player), gameModel.getPlayer(players.get(player)).getProductions());
                     if(finish==1) seeGameBoard(player);
                     break;
        }
    }


    public void chooseTurn(int player) throws IOException, InterruptedException, NotExistingPlayerException {
        int answer=view.chooseTurn(players.get(player));

        switch(answer){
            case 1 : try {
                int pos = view.activeLeader(players.get(player));
                turncontroller.activeLeader(player, pos);
            } catch (InvalidChoiceException e) {
                view.sendErrorMessage(players.get(player));
                chooseTurn(player);
            }
                    break;
            case 2 : try {
                int pos = view.discardLeader(players.get(player));
                turncontroller.discardLeader(player, pos);
            } catch (InvalidChoiceException e) {
                view.sendErrorMessage(players.get(player));
                chooseTurn(player);
            }
                    break;
        }
    }


    public EndGameController getEndgame() {
        return endgame;
    }

    public void endGame(){
        if(isOver){
            if(gameModel.getPlayers().indexOf(gameModel.getCurrentPlayer())==gameModel.getPlayers().size()-1) {
                Player winner = getEndgame().getWinner(gameModel);
                /*send the winner to the server*/
            }
        }
        else{
            for(Player p : gameModel.getPlayers()) {
                if (p.getPlayerDashboard().getDevCardsSpace().getAmountCards() == 7 || p.getPlayerDashboard().getFaithPath().getPositionFaithPath() == 24) {
                    isOver = true;
                }
            }
        }
    }

    //SINGLE PLAYER

    public void singlePlayerLose(){
        int victoryPoints = endgame.totalVictoryPoints(gameModel.getCurrentPlayer());
        //TODO send that Lorenzo is the winner to the server
    }

    public void singlePlayerWins(){
        Player winner = gameModel.getCurrentPlayer();
        int victoryPoints = endgame.totalVictoryPoints(gameModel.getCurrentPlayer());
        //TODO send the winner to the server
    }

}
