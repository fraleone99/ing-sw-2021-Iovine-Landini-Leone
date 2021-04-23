package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.server.VirtualView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private final Game gameModel;
    private final TurnController turncontroller;
    private final EndGameController endgame;
    private final ArrayList<String> players=new ArrayList<>();
    private boolean isOver;
    private VirtualView view;
    private int resource1;
    private int resource2;

    public Controller(ArrayList<String> players, VirtualView view) {
        this.players.addAll(players);
        gameModel=new Game(players.size(), players);
        turncontroller=new TurnController();
        endgame=new EndGameController();
        this.view=view;
    }

    public void play() {
        try {
            for(int i=0; i<players.size(); i++) {
                setPlayers(i);
            }
            for (int i = 0; i < players.size(); i++) {
                setInitialBenefits(i);
            }
        } catch (NotExistingPlayerException | IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public void setPlayers(int player){
        gameModel.createPlayer(players.get(player));
    }

    public void setInitialBenefits(int i) throws NotExistingPlayerException, IOException, InterruptedException {
        switch(i){
            case 0: view.firstPlayer(players.get(i));
                    break;
            case 1: resource1=view.chooseResource(players.get(i), "second",1);
                    addInitialResource(i, resource1,1);
                    break;
            case 2: resource1=view.chooseResource(players.get(i), "third",1);
                    addInitialResource(i,resource1,1);
                    gameModel.getPlayer(players.get(i)).getPlayerDashboard().getFaithPath().moveForward(1);
                    break;
            case 3: resource1=view.chooseResource(players.get(i), "fourth",1);
                    addInitialResource(i,resource1,2);
                    resource2=view.chooseResource(players.get(i), "fourth",2);
                    if(resource1==resource2) {
                        addInitialResource(i, resource2, 2);
                    } else {
                        addInitialResource(i,resource2,1);
                    }
                    gameModel.getPlayer(players.get(i)).getPlayerDashboard().getFaithPath().moveForward(1);
        }
    }

    public void addInitialResource(int player, int resource, int shelf) throws NotExistingPlayerException{
        try {
            switch (resource) {
                case 1: gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.COIN, 1);
                        break;
                case 2: gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.STONE, 1);
                        break;
                case 3: gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.SHIELD, 1);
                        break;
                case 4: gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.SERVANT, 1);
                        break;
            }
        } catch(NotEnoughSpaceException | AnotherShelfHasTheSameTypeException| ShelfHasDifferentTypeException e) {
            e.printStackTrace();
        }
    }

    public EndGameController getEndgame() {
        return endgame;
    }


    public void activeLeader(int pos) throws InvalidChoiceException {
        try {
            gameModel.getCurrentPlayer().ActiveLeader(pos);
        } catch (InvalidChoiceException e) {
            //TODO
        }
    }

    public void discardLeader(int pos) throws InvalidChoiceException {
        try {
            gameModel.getCurrentPlayer().DiscardLeader(pos);
        } catch (InvalidChoiceException e) {
            //TODO
        }
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
                if (p.getPlayerDashboard().getDevCardsSpace().getAmountCards() == 7 || p.getPlayerDashboard().getFaithPath().getPositionFaithPath() == 25) {
                    isOver = true;
                }
            }
        }
    }

}
