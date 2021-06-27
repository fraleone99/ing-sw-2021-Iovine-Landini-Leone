package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.card.deck.LeaderCardDeck;
import it.polimi.ingsw.model.card.leadercard.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.model.singleplayer.BlackCrossMover;
import it.polimi.ingsw.server.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller class handles single and multi player matches
 * @author Lorenzo Iovine
 */
public class Controller {
    private final Game gameModel;
    private final TurnController turncontroller;
    private final EndGameController endgame;
    private final ArrayList <String> players = new ArrayList<>();
    private final Map <String, Boolean> clientConnected = new HashMap<>();
    private final VirtualView view;
    private boolean isEnd = false;
    ActionToken actionToken;


    /**
     * Controller constructor: creates a new instance of Controller
     * @param players are the players of the match
     * @param view is an instance of class VirtualView
     */
    public Controller(ArrayList<String> players, VirtualView view) {
        this.players.addAll(players);
        gameModel=new Game();
        turncontroller=new TurnController(gameModel, players, view);
        endgame=new EndGameController();
        this.view=view;
    }


    /**
     * This method handles the multi and single player connected match
     */
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
                view.initialInfo(players.get(i), players.size(), players);
                startGame(i);
                view.initializeGameBoard(false, players.get(i), gameModel.getGameBoard().getMarket(),gameModel.getGameBoard().getDevelopmentCardGrid().getGrid().idDeck(), gameModel.getPlayer(players.get(i)).getLeaders().idDeck(), false, false, false, false);

                for(String player: players){
                    view.seeStorage(players.get(i), gameModel.getPlayer(player).getPlayerDashboard(), player, false, false);
                }
            }

            if(players.size()==1){
                view.updateFaithPath(players.get(0), null, 0, true);
            }
            for(String s: players){
                for(String n: players){
                    if(n.equals(players.get(0)) || n.equals(players.get(1))) {
                        view.updateFaithPath(s, n, 0, false);
                    } else {
                        view.updateFaithPath(s, n, 1, false);
                    }
                }

            }

            while(!isEnd){
                for(int i=0; i<players.size();i++){
                    if(clientConnected.get(players.get(i))) {
                        view.sendTurnStatus("START", players.get(i));
                        turncontroller.seePlayerDashboards(i);
                        turncontroller.seeGameBoard(true, i);
                        turncontroller.chooseTurn(i);
                        if(clientConnected.get(players.get(i)))
                            view.sendTurnStatus("END", players.get(i));
                    }
                }

                if(players.size()==1){
                    try{
                        actionToken = gameModel.drawActionToken();
                        ArrayList<String> nick = new ArrayList<>(turncontroller.checkPapalPawn());
                        if (!nick.isEmpty()) {
                            view.papalPawn(nick);
                        }
                        view.seeActionToken(players.get(0), actionToken);
                        if(actionToken instanceof BlackCrossMover) {
                            for(String n: players){
                                view.updateFaithPath(n, null, gameModel.getPlayer(n).getPlayerDashboard().getFaithPath().getPositionLorenzo(), true);
                            }
                        }
                    } catch (EmptyDecksException e){
                        break;
                    } catch (InvalidChoiceException e){
                        e.printStackTrace();
                    }
                    isEnd = endgame.singlePlayerIsEndGame(gameModel);
                } else {
                    isEnd = isEndGame();
                }
            }

            endGame();

        } catch (NotExistingPlayerException | InvalidChoiceException e){
            e.printStackTrace();
        }
    }


    /**
     * This method is used to set the player in Game class in the model
     * @param player is the player index in array list players
     */
    public void setPlayers(int player) {
        gameModel.createPlayer(players.get(player));
    }

    public void setClientConnection(String nickname, boolean connection, boolean crashed) {
        if(crashed) {
            if(connection) {
                clientConnected.replace(nickname, true);
                setGameBoardForReconnection(nickname);
            } else {
                clientConnected.replace(nickname, false);
            }
        } else {
            clientConnected.put(nickname, true);
        }

        turncontroller.setClientConnection(nickname, connection, crashed);
    }

    public void setGameBoardForReconnection(String nickname) {
        try {
            view.initialInfo(nickname, players.size(), players);
            LeaderCardDeck deck = gameModel.getPlayer(nickname).getPlayerDashboard().getLeaders();
            boolean active1 = deck.get(0).getIsActive();
            boolean discarded1 = deck.get(0).getIsDiscarded();
            boolean active2 = deck.get(1).getIsActive();
            boolean discarded2 = deck.get(1).getIsDiscarded();
            view.initializeGameBoard(true, nickname, gameModel.getGameBoard().getMarket(), gameModel.getGameBoard().getDevelopmentCardGrid().getGrid().idDeck(), gameModel.getPlayer(nickname).getLeaders().idDeck(), active1, discarded1, active2, discarded2);
            for(String player : players) {
                view.seeStorage(nickname, gameModel.getPlayer(player).getPlayerDashboard(), player, false, true);
                view.updateFaithPath(nickname, player, gameModel.getPlayer(player).getPlayerDashboard().getFaithPath().getPositionFaithPath(), false);
                view.setDevCardsSpaceForReconnection(nickname, gameModel.getPlayer(player).getPlayerDashboard().getDevCardsSpace().getSpace(), player);

                LeaderCard leader1 = gameModel.getPlayer(player).getLeaders().get(0);
                if(leader1.getIsActive()) {
                    view.activeOtherLeaderCard(nickname, leader1.getCardID(), player, 1);
                } else if(leader1.getIsDiscarded()) {
                    view.discardOtherLeaderCard(nickname, leader1.getCardID(), player,1);
                }

                LeaderCard leader2 = gameModel.getPlayer(player).getLeaders().get(1);
                if(leader2.getIsActive()) {
                    view.activeOtherLeaderCard(nickname, leader2.getCardID(), player,2);
                } else if(leader2.getIsDiscarded()) {
                    view.discardOtherLeaderCard(nickname, leader2.getCardID(), player,2);
                }
            }
        } catch (NotExistingPlayerException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method set the initial benefits based on corresponding player position:
     * 1st player-nothing
     * 2nd player-1 resource of his choice
     * 3rd player-1 resource of his choice and 1 faith point
     * 4th player-2 resource of his choice and 1 faith point
     * @param i is the player index in array list players
     * @throws NotExistingPlayerException if the selected player doesn't exists
     */
    public void setInitialBenefits(int i) throws NotExistingPlayerException {
        switch(i){
            case 0: view.firstPlayer(players.get(i));
                    break;
            case 1:
                int resource1 = view.chooseResource(players.get(i), "second", 1);
                    addInitialResource(i, resource1,1);
                    break;
            case 2: resource1 =view.chooseResource(players.get(i), "third",1);
                    addInitialResource(i, resource1,1);
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


    /**
     * This method is used to add the initial chosen resources to the storage
     * @param player is the player index in array list players
     * @param resource is the number corresponding to the chosen resource
     * @param shelf is the number corresponding to the shelf where to place the resource on
     * @throws NotExistingPlayerException if the selected player doesn't exists
     */
    public void addInitialResource(int player, int resource, int shelf) throws NotExistingPlayerException {
        try {
            switch (resource) {
                case 1:
                    gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().addResources(shelf, Resource.COIN, 1);
                    break;
                case 2:
                    gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().addResources(shelf, Resource.STONE, 1);
                    break;
                case 3:
                    gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().addResources(shelf, Resource.SHIELD, 1);
                    break;
                case 4:
                    gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().addResources(shelf, Resource.SERVANT, 1);
                    break;
            }
        } catch (NotEnoughSpaceException | AnotherShelfHasTheSameTypeException | ShelfHasDifferentTypeException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is used to give the initial 4 leader cards to each player of the game
     * @param player is the player index in array list players
     * @throws NotExistingPlayerException if the selected player doesn't exists
     */
    public void setInitialLeaderCards(int player) throws NotExistingPlayerException {
        for (int i = 0; i < 4; i++) {
            gameModel.getGameBoard().getLeaderDeck().shuffle();
            gameModel.getPlayer(players.get(player)).getLeaders().add(gameModel.getGameBoard().getLeaderDeck().drawFromTail());
        }
    }


    /**
     * This method is used to ask to the player, two leader cards of the initial four, that he
     * wants to discard
     * @param player is the player index in array list players
     * @throws NotExistingPlayerException if the selected player doesn't exists
     */
    public void discardFirstLeaders(int player) throws NotExistingPlayerException {
        int card;
        card=view.discardFirstLeaders(players.get(player), 1, gameModel.getPlayer(players.get(player)).getLeaders().idDeck());
        gameModel.getPlayer(players.get(player)).getPlayerDashboard().getLeaders().remove(card-1);
        card=view.discardFirstLeaders(players.get(player), 2, gameModel.getPlayer(players.get(player)).getLeaders().idDeck());
        gameModel.getPlayer(players.get(player)).getPlayerDashboard().getLeaders().remove(card-1);
    }


    /**
     * This method is used to notify the player that the game started
     * @param player is the player index in array list players
     */
    public void startGame(int player) {
        view.startGame(players.get(player));
    }


    /**
     * This method handles end game notifications
     * @throws NotExistingPlayerException if the selected player doesn't exists
     */
    public void endGame() throws NotExistingPlayerException {
        if(players.size()==1){
            try {
                if(endgame.singlePlayerLose(gameModel)){
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

            if(clientConnected.get(winner.getNickname())) {
                view.win(winner.getNickname(), endgame.totalVictoryPoints(winner));
            }

            for(int i=0; i<players.size(); i++) {
                if(i!=players.indexOf(winner.getNickname()) && clientConnected.get(players.get(i))) {
                    view.lose(players.get(i),endgame.totalVictoryPoints(gameModel.getPlayer(players.get(i))));
                }
            }
        }
        view.closeConnection();
    }


    /**
     * This method checks if the game is over
     * @return true if the match is over, false otherwise
     */
    public boolean isEndGame(){
        for(Player p : gameModel.getPlayers()) {
            if (p.getPlayerDashboard().getDevCardsSpace().getAmountCards() == 7 || p.getPlayerDashboard().getFaithPath().getPositionFaithPath() == 24) {
                return true;
            }
        }

        return false;
    }


    /**
     * This method gets the instance of EndGameController
     * @return the instance of EndGameController
     */
    public EndGameController getEndgame() {
        return endgame;
    }
}
