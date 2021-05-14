package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.card.leadercard.StorageLeader;
import it.polimi.ingsw.model.card.leadercard.WhiteBallLeader;
import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.Ball;
import it.polimi.ingsw.model.gameboard.playerdashboard.Shelf;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.server.VirtualView;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private final Game gameModel;
    private final TurnController turncontroller;
    private final EndGameController endgame;
    private final ArrayList<String> players=new ArrayList<>();
    private final VirtualView view;
    private boolean isEnd=false;
    private boolean isOver=false;
    ActionToken actionToken;


    public Controller(ArrayList<String> players, VirtualView view) {
        this.players.addAll(players);
        gameModel=new Game(players.size(), players);
        turncontroller=new TurnController(gameModel, players, view);
        endgame=new EndGameController();
        this.view=view;
    }


    public void play() {
        try {
            for(int i=0; i<players.size(); i++) {
                setPlayers(i);
            }
            for (int i = 0; i < players.size(); i++) {
                view.sendTurnStatus("START", players.get(i));
                setInitialBenefits(i);
                setInitialLeaderCards(i);
                discardFirstLeaders(i);
                view.sendTurnStatus("END", players.get(i));
            }
            for(int i=0; i<players.size(); i++){
                startGame(i);
            }
            while(!isEnd){
                for(int i=0; i<players.size();i++){
                    //send start turn
                    view.sendTurnStatus("START", players.get(i));
                    turncontroller.seePlayerDashboards(i);
                    turncontroller.seeGameBoard(i);
                    turncontroller.chooseTurn(i);
                    view.sendTurnStatus("END", players.get(i));
                    //send end turn
                }

                if(players.size()==1){
                    try{
                        actionToken = gameModel.drawActionToken();
                        view.seeActionToken(players.get(0), actionToken);
                    } catch (EmptyDecksException e){
                        break;
                    } catch (InvalidChoiceException e){
                        e.printStackTrace();
                    }
                    isEnd = endgame.SinglePlayerIsEndGame(gameModel);
                } else {
                    isEnd = isEndGame();
                }
            }

            endGame();

        } catch (NotExistingPlayerException | InterruptedException | InvalidChoiceException e){
            e.printStackTrace();
        }
    }


    public void setPlayers(int player) {
        gameModel.createPlayer(players.get(player));
    }


    public void setInitialBenefits(int i) throws NotExistingPlayerException, InterruptedException {
        switch(i){
            case 0: view.firstPlayer(players.get(i));
                    break;
            case 1:
                int resource1 = view.chooseResource(players.get(i), "second", 1);
                    addInitialResource(i, resource1,1);
                    break;
            case 2: resource1 =view.chooseResource(players.get(i), "third",1);
                    addInitialResource(i, resource1,1);
                    gameModel.getPlayer(players.get(i)).getPlayerDashboard().getFaithPath().moveForward(1);
                    break;
            case 3: resource1 =view.chooseResource(players.get(i), "fourth",1);
                    addInitialResource(i, resource1,2);
                int resource2 = view.chooseResource(players.get(i), "fourth", 2);
                    if(resource1 == resource2) {
                        addInitialResource(i, resource2, 2);
                    } else {
                        addInitialResource(i, resource2,1);
                    }
                    gameModel.getPlayer(players.get(i)).getPlayerDashboard().getFaithPath().moveForward(1);
        }
    }


    public void addInitialResource(int player, int resource, int shelf) throws NotExistingPlayerException {
        try {
            switch (resource) {
                case 1:
                    gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.COIN, 1);
                    break;
                case 2:
                    gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.STONE, 1);
                    break;
                case 3:
                    gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.SHIELD, 1);
                    break;
                case 4:
                    gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.SERVANT, 1);
                    break;
            }
        } catch (NotEnoughSpaceException | AnotherShelfHasTheSameTypeException | ShelfHasDifferentTypeException e) {
            e.printStackTrace();
        }
    }


    public void setInitialLeaderCards(int player) throws NotExistingPlayerException {
        for (int i = 0; i < 4; i++) {
            gameModel.getGameBoard().getLeaderDeck().shuffle();
            gameModel.getPlayer(players.get(player)).getLeaders().add(gameModel.getGameBoard().getLeaderDeck().drawFromTail());
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


    public void endGame() throws NotExistingPlayerException {
        if(players.size()==1){
            try {
                if(endgame.SinglePlayerLose(gameModel)){
                    view.lose(players.get(0),endgame.totalVictoryPoints(gameModel.getPlayer(players.get(0))));
                } else {
                    view.win(players.get(0),endgame.totalVictoryPoints(gameModel.getPlayer(players.get(0))));
                }
            } catch (InvalidChoiceException e) {
                e.printStackTrace();
            }
        }
        else {
            Player winner = endgame.getWinner(gameModel);
            view.win(winner.getNickname(),endgame.totalVictoryPoints(winner));

            for(int i=0; i<players.size(); i++) {
                if(i!=players.indexOf(winner.getNickname())) {
                    view.lose(players.get(i),endgame.totalVictoryPoints(gameModel.getPlayer(players.get(i))));
                }
            }
        }
        view.closeConnection();
    }


    public boolean isEndGame(){
        for(Player p : gameModel.getPlayers()) {
            if (p.getPlayerDashboard().getDevCardsSpace().getAmountCards() == 7 || p.getPlayerDashboard().getFaithPath().getPositionFaithPath() == 24) {
                return true;
            }
        }

        return false;
    }


    public EndGameController getEndgame() {
        return endgame;
    }
}
