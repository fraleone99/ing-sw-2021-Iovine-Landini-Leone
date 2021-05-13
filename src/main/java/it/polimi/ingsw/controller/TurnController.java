package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.Ball;
import it.polimi.ingsw.server.VirtualView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TurnController {

    Player player;
    Game game;
    GameBoard gameBoard;

    Boolean basicProduction;
    Resource basicInput1;
    Resource basicInput2;
    Resource basicOutput;
    Boolean[] DevCardsSpace = new Boolean[3];
    Boolean[] ProductionLeader = new Boolean[2];
    VirtualView view;

    //TODO
    private final ArrayList<String> players = new ArrayList<>();

    int marketChoice;
    String storageOrg;
    int shelfToAdd; /*This is the shelf where the player wants to put the resource chosen from the market this should be
                      updated everytime an addition goes well until the resources are ended*/
    CardColor color;
    int level;
    int space;

    public TurnController(Game game, ArrayList<String> players, VirtualView view) {
        this.game = game;
        this.players.addAll(players);
        this.view=view;
    }

    //FROM CONTROLLER

    public void setPlayers(int player) {
        game.createPlayer(players.get(player));
    }

    public void addInitialResource(int player, int resource, int shelf) throws NotExistingPlayerException {
        try {
            switch (resource) {
                case 1:
                    game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.COIN, 1);
                    break;
                case 2:
                    game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.STONE, 1);
                    break;
                case 3:
                    game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.SHIELD, 1);
                    break;
                case 4:
                    game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.SERVANT, 1);
                    break;
            }
        } catch (NotEnoughSpaceException | AnotherShelfHasTheSameTypeException | ShelfHasDifferentTypeException e) {
            e.printStackTrace();
        }
    }

    public void setInitialLeaderCards(int player) throws NotExistingPlayerException {
        for (int i = 0; i < 4; i++) {
            game.getGameBoard().getLeaderDeck().shuffle();
            game.getPlayer(players.get(player)).getLeaders().add(game.getGameBoard().getLeaderDeck().drawFromTail());
        }
    }

    public void activeLeader(int player, int pos) throws InvalidChoiceException, NotExistingPlayerException {
        game.getPlayer(players.get(player)).ActiveLeader(pos);
    }

    public void discardLeader(int player, int pos) throws InvalidChoiceException, NotExistingPlayerException {
        game.getPlayer(players.get(player)).DiscardLeader(pos);
    }

    public ArrayList<Ball> checkEmptyShelves(int player, ArrayList<Ball> balls) throws NotExistingPlayerException {
        ArrayList<Ball> toPlace = new ArrayList<>();

        for (Ball b : balls) {
            int i = game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().typePresent(b.getCorrespondingResource());

            if (game.getPlayer(players.get(player)).StorageLeader(b.getCorrespondingResource())) {
                toPlace.add(new Ball(b.getType()));
            } else {
                if (i != 0) {
                    if (game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().getShelves().get(i - 1).getAvailableSpace() == 0) {
                        for (int j = 0; j < players.size(); j++) {
                            if (j != player) {
                                game.getPlayer(players.get(j)).move(1);
                                ArrayList<String> nick=new ArrayList<>(checkPapalPawn());
                                if(!nick.isEmpty()) {
                                    view.papalPawn(nick);
                                }
                            }
                        }
                    } else {
                        toPlace.add(new Ball(b.getType()));
                        //gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().getShelves().get(i-1).setResourceAmount(1);
                    }
                } else {
                    if (game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().emptyShelves() == 0) {
                        for (int j = 0; j < players.size(); j++) {
                            if (j != player) {
                                game.getPlayer(players.get(j)).move(1);
                                ArrayList<String> nick=new ArrayList<>(checkPapalPawn());
                                if(!nick.isEmpty()) {
                                    view.papalPawn(nick);
                                }
                            }
                        }
                    } else {
                        toPlace.add(new Ball(b.getType()));
                    }
                }
            }
        }

        return toPlace;
    }

    public ArrayList<String> checkPapalPawn() {
        ArrayList<String> players=new ArrayList<>();
        switch (game.getPapalPawn()) {
            case 0:
                for (Player p : game.getPlayers()) {
                    if (p.getPlayerDashboard().getFaithPath().getPositionFaithPath() > 7) {
                        p.getPlayerDashboard().getFaithPath().setPapalPawn1();
                        for (Player p2 : game.getPlayers()) {
                            if (!(p.getNickname()).equals(p2.getNickname())) {
                                if(p2.getPlayerDashboard().getFaithPath().activatePapalPawn(1)) {
                                    if(!players.contains(p2.getNickname()))
                                        players.add(p2.getNickname());
                                }
                            }
                            game.updatePapalPawn();
                            players.add(p.getNickname());
                            break;
                        }
                    }
                }
            case 1:
                for (Player p : game.getPlayers()) {
                    if (p.getPlayerDashboard().getFaithPath().getPositionFaithPath() > 15) {
                        p.getPlayerDashboard().getFaithPath().setPapalPawn2();
                        for (Player p2 : game.getPlayers()) {
                            if (!(p.getNickname()).equals(p2.getNickname())) {
                                if(p2.getPlayerDashboard().getFaithPath().activatePapalPawn(2)) {
                                    if(!players.contains(p2.getNickname()))
                                        players.add(p2.getNickname());
                                }
                            }
                        }
                        game.updatePapalPawn();
                        players.add(p.getNickname());
                        break;
                    }
                }
            case 2:
                for (Player p : game.getPlayers()) {
                    if (p.getPlayerDashboard().getFaithPath().getPositionFaithPath() > 23) {
                        p.getPlayerDashboard().getFaithPath().setPapalPawn3();
                        for (Player p2 : game.getPlayers()) {
                            if (!(p.getNickname()).equals(p2.getNickname())) {
                                if(p2.getPlayerDashboard().getFaithPath().activatePapalPawn(3)) {
                                    if(!players.contains(p2.getNickname()))
                                        players.add(p2.getNickname());
                                }
                            }
                        }
                        game.updatePapalPawn();
                        players.add(p.getNickname());
                        break;
                    }
                }
        }
        return players;
    }
}
