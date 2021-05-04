package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Goods;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.playerdashboard.Ball;

import java.util.ArrayList;
import java.util.Arrays;

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

    //TODO
    private final ArrayList<String> players=new ArrayList<>();

    int marketChoice;
    String storageOrg;
    int shelfToAdd; /*This is the shelf where the player wants to put the resource chosen from the market this should be
                      updated everytime an addition goes well until the resources are ended*/
    CardColor color;
    int level;
    int space;

    public TurnController(Game game, ArrayList<String> players) {
        this.game = game;
        this.players.addAll(players);
    }

    /*At this moment i use a string for what the player wants to do. And i assume that somewhere the variable of this class
    * are properly set*/
    public void playerAction(String action) throws InvalidChoiceException {
        switch (action) {
            case ("production"):
                try {
                    production(basicProduction, basicInput1, basicInput2, basicOutput, DevCardsSpace, ProductionLeader);
                } catch (NotEnoughResourceException | InvalidChoiceException e) {
                    //TODO
                }
            case ("market"):
                market(marketChoice);
            case ("buyDevCard"):
                try {
                    buyCard();
                } catch (InvalidSpaceCardException e) {
                    //TODO
                }
            default:
                throw new InvalidChoiceException();
        }
    }

    /*if the player choose to do production i need to know what production he wants to activate.
    * I now suppose that i receive an indication of basic production, of the three spaces of DevCards Space
    * and the production leader.*/
    public void production(boolean basicProduction, Resource basicInput1, Resource basicInput2, Resource basicOutput,
                           Boolean[] DevCardsSpace, Boolean[] ProductionLeader) throws NotEnoughResourceException, InvalidChoiceException {
        if(basicProduction){
            player.getPlayerDashboard().getDevCardsSpace().setInputBasicProduction(basicInput1, basicInput2);
            player.getPlayerDashboard().getDevCardsSpace().setOutputBasicProduction(basicOutput);
            player.ActiveProductionBase();
        }

        for(int i = 0; i < 3; i++){
            if(DevCardsSpace[i]){
                player.ActiveProductionDevCard(i); //there might be an issue of index if i starts from 0 and getCard does -1
            }
        }

        for(int i = 0; i < player.getPlayerDashboard().getLeaders().size() ; i++){
            if(ProductionLeader[i]) {
                //TODO: Ask the player the unknown resource
                player.ActiveProductionLeader(i); //same problem of the previous for
                }
        }

        player.doProduction();
    }


    /*if the player wants to use the market he needs to communicate the choice and in case he can't add all the
    * resources he needs to discard or reorganize res*/
    public void market(int marketChoice) throws InvalidChoiceException {
        ArrayList<Ball> balls = gameBoard.getMarket().getChosenColor(marketChoice);

        while(!balls.isEmpty()) {
            for (Ball b : balls) {
                if (b.getType().equals(BallColor.RED)) {
                    player.getPlayerDashboard().getFaithPath().moveForward(1);
                    balls.remove(b);
                } else if (b.getType().equals(BallColor.WHITE)) {
                    if (player.getPlayerDashboard().bothWhiteLeader()) {
                        //TODO: User needs to chose which of the white Leader he wants to activate
                    } else if (player.getLeaders().isWhitePresent() != null) {
                        /*The player needs to choose where to put this resource something must happen to update the shelfToAdd variable */
                        try {
                            player.getPlayerDashboard().getStorage().AddResource(shelfToAdd, player.getLeaders().isWhitePresent().getConversionType(), 1);
                            balls.remove(b);
                        } catch (NotEnoughSpaceException | AnotherShelfHasTheSameTypeException | ShelfHasDifferentTypeException e) {
                            //TODO: if the resource can't be added where the player wants he must discard something or reorganize the shelves
                        }
                    }
                } else {
                    /*The player needs to choose where to put this resource something must happen to update the shelfToAdd variable */
                    try {
                        player.getPlayerDashboard().getStorage().AddResource(shelfToAdd, b.getCorrespondingResource(), 1);
                        balls.remove(b);
                    } catch (NotEnoughSpaceException | AnotherShelfHasTheSameTypeException | ShelfHasDifferentTypeException e) {
                        //TODO: if the resource can't be added where the player wants he must discard something or reorganize the shelves
                    }
                }
            }
        }
    }

    /*There might be a problem because if the buyCards doesn't goes well we have used the draw method and so we have
    * removed the card from the devCardsSpace*/
    public void buyCard() throws InvalidChoiceException, InvalidSpaceCardException {
        player.getPlayerDashboard().getDevCardsSpace().AddCard(gameBoard.getDevelopmentCardGrid().getCard(color,level), space);
    }




    //FROM CONTROLLER

    public void setPlayers(int player){
        game.createPlayer(players.get(player));
    }

    public void addInitialResource(int player, int resource, int shelf) throws NotExistingPlayerException{
        try {
            switch (resource) {
                case 1: game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.COIN, 1);
                    break;
                case 2: game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.STONE, 1);
                    break;
                case 3: game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.SHIELD, 1);
                    break;
                case 4: game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(shelf, Resource.SERVANT, 1);
                    break;
            }
        } catch(NotEnoughSpaceException | AnotherShelfHasTheSameTypeException| ShelfHasDifferentTypeException e) {
            e.printStackTrace();
        }
    }

    public void setInitialLeaderCards(int player) throws NotExistingPlayerException {
        for(int i=0; i<4; i++){
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

}
