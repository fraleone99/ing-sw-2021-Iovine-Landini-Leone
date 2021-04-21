package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class Controller {
    private final Game gameModel;
    private final TurnController turncontroller;
    private final EndGameController endgame;
    private final ArrayList<String> players=new ArrayList<>();
    private boolean isOver;
    private boolean toEnd;

    public Controller(ArrayList<String> players) {
        this.players.addAll(players);
        gameModel=new Game();
        turncontroller=new TurnController();
        endgame=new EndGameController();
    }

    public void play(){
        setPlayers();
    }

    public void setPlayers(){
        gameModel.createPlayers(players);
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
