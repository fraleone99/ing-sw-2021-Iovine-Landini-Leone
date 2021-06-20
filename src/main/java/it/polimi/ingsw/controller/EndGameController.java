package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.card.leadercard.LeaderCard;
import it.polimi.ingsw.model.card.leadercard.StorageLeader;
import it.polimi.ingsw.model.enumeration.CardColor;

/**
 * EndGameController handles the end game
 * @author Nicola Landini
 */
public class EndGameController {

    /**
     * This method is used to get the winner
     * @param game is the instance of game
     * @return the match winner
     */
    public Player getWinner(Game game){
        int winningPoints=totalVictoryPoints(game.getPlayers().get(0));
        int pos=0;

        for(int i=0; i<game.getPlayers().size(); i++){
            if(totalVictoryPoints(game.getPlayers().get(i))>winningPoints){
                winningPoints=totalVictoryPoints(game.getPlayers().get(i));
                pos=i;
            }
        }

        return game.getPlayers().get(pos);
    }

    /**
     * This method calculate selected player's total victory points
     * @param player is the selected player
     * @return player's total victory points
     */
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

    /**
     * This method establishes if, in a single player match, the player lost
     * @param game is the instance of game
     * @return true if player lost, false otherwise
     * @throws InvalidChoiceException if the choice is invalid
     */
    public boolean SinglePlayerLose(Game game) throws InvalidChoiceException {
        boolean green   = game.getGameBoard().getDevelopmentCardGrid().sameColorDeckAreEmpty(CardColor.GREEN);
        boolean purple  = game.getGameBoard().getDevelopmentCardGrid().sameColorDeckAreEmpty(CardColor.PURPLE);
        boolean blue    = game.getGameBoard().getDevelopmentCardGrid().sameColorDeckAreEmpty(CardColor.BLUE);
        boolean yellow  = game.getGameBoard().getDevelopmentCardGrid().sameColorDeckAreEmpty(CardColor.YELLOW);

        return game.getPlayers().get(0).getPlayerDashboard().getFaithPath().getPositionLorenzo() == 24 || green || purple || blue || yellow;
    }

    /**
     * This method establishes if, in a single player match, the player won
     * @param game is the instance of game
     * @return true if player won, false otherwise
     */
    public boolean SinglePlayerWins(Game game) {

        return game.getPlayers().get(0).getPlayerDashboard().getDevCardsSpace().getAmountCards() == 7 || game.getPlayers().get(0).getPlayerDashboard().getFaithPath().getPositionFaithPath() == 24;
    }

    /**
     * This method checks if the single player game is over
     * @param game is the instance of game
     * @return true if the match is over, false otherwise
     */
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
