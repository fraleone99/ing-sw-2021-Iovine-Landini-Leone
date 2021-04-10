package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Controller implements PropertyChangeListener {
    private final Game gameModel;
    private final TurnController turncontroller;

    public Controller(Game model, TurnController turncontroller) {
        this.gameModel = model;
        this.turncontroller = turncontroller;
    }

    public void setNickname(String nickname){
        gameModel.createNewPlayer(nickname);
    }

    public void endGame(){
        for(Player p : gameModel.getPlayers()){
            if(p.getPlayerDashboard().getDevCardsSpace().getSpace().size()==7 || p.getPlayerDashboard().getFaithPath().getPositionFaithPath()==21) {
                /*call EndGameController*/
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
