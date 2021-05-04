package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.card.leadercard.LeaderCard;
import it.polimi.ingsw.model.card.leadercard.StorageLeader;
import it.polimi.ingsw.model.enumeration.CardColor;

public class EndGameController {

    public Player getWinner(Game game){
        int winningPoints=0;
        int pos=0;

        for(int i=0; i<game.getPlayers().size(); i++){
            if(totalVictoryPoints(game.getPlayers().get(i))>winningPoints){
                pos=i;
            }
        }

        return game.getPlayers().get(pos);
    }

    public int totalVictoryPoints(Player player) {
        int totalPoints=0;

        //points in Player+total points from the faith path
        totalPoints = player.calculateVictoryPoints() + player.getPlayerDashboard().getFaithPath().getTotalPoint();

        int storageResources=player.getPlayerDashboard().getStorage().getTotalResources();
        int vaultResources=player.getPlayerDashboard().getVault().getTotalResources();
        int storageLeaderResources=0;
        for(LeaderCard leader: player.getLeaders().getDeck()){
            if(leader instanceof StorageLeader){
                storageLeaderResources=storageLeaderResources+((StorageLeader) leader).getAmount();
            }
        }

        totalPoints=totalPoints+(storageResources+vaultResources+storageLeaderResources)/5;

        return totalPoints;
    }

    //SINGLE PLAYER

    public boolean SinglePlayerLose(Game game) throws InvalidChoiceException {
        boolean green   = game.getGameBoard().getDevelopmentCardGrid().sameColorDeckAreEmpty(CardColor.GREEN);
        boolean purple  = game.getGameBoard().getDevelopmentCardGrid().sameColorDeckAreEmpty(CardColor.PURPLE);
        boolean blue    = game.getGameBoard().getDevelopmentCardGrid().sameColorDeckAreEmpty(CardColor.BLUE);
        boolean yellow  = game.getGameBoard().getDevelopmentCardGrid().sameColorDeckAreEmpty(CardColor.YELLOW);

        if(game.getPlayers().get(0).getPlayerDashboard().getFaithPath().getPositionLorenzo()==24 || green || purple || blue || yellow){
            return true;
        }

        return false;
    }

    public boolean SinglePlayerWins(Game game) {

        if(game.getPlayers().get(0).getPlayerDashboard().getDevCardsSpace().getAmountCards()==7 || game.getPlayers().get(0).getPlayerDashboard().getFaithPath().getPositionFaithPath()==24) {
            return true;
        }

        return false;
    }

    public boolean SinglePlayerIsEndGame(Game game){

        try {
            if(SinglePlayerLose(game) || SinglePlayerWins(game)){
                return true;
            }
        } catch (InvalidChoiceException e) {
            e.printStackTrace();
        }
        return false;
    }
}
