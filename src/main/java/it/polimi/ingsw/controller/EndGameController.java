package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.card.leadercard.LeaderCard;
import it.polimi.ingsw.model.card.leadercard.StorageLeader;

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
}
