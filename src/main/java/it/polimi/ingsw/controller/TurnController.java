package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.view.ProductionType;
import it.polimi.ingsw.client.view.ToSeeFromGameBoard;
import it.polimi.ingsw.client.view.TurnType;
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
import it.polimi.ingsw.server.VirtualView;

import java.util.ArrayList;

/**
 * TurnController class manages the actions of player's turn
 * @author Lorenzo Iovine
 */
public class TurnController {
    private final Game game;
    private final VirtualView view;
    private final ArrayList<String> players = new ArrayList<>();
    private boolean Catch=false;


    /**
     * TurnController constructor: creates a new instance of TurnController
     * @param game is an instance of class Game
     * @param players are the players of the match
     * @param view is an instance of class VirtualView
     */
    public TurnController(Game game, ArrayList<String> players, VirtualView view) {
        this.game = game;
        this.players.addAll(players);
        this.view=view;
    }


    /**
     * This method handles the call of player dashboard print in the CLI
     */
    public void seePlayerDashboards(int player) throws NotExistingPlayerException {
        for (String s : players) {
            view.seeFaithPath(players.get(player), s, game.getPlayer(s).getPlayerDashboard().getFaithPath(), players.size() == 1);
            view.seeStorage(players.get(player), game.getPlayer(s).getPlayerDashboard().getStorage(),
                    game.getPlayer(s).getPlayerDashboard().getVault(), s);
            view.seeDevCardsSpace(players.get(player), game.getPlayer(s).getPlayerDashboard().getDevCardsSpace());
        }
    }


    /**
     * This method handles the call of game board print in the CLI
     * @param first is a boolean that is true only in the first call of the method,
     *              and is false in the recursive calls
     * @param player is the player index in array list players
     * @throws NotExistingPlayerException if the selected player doesn't exists
     * @throws InterruptedException is due to multithreading message send
     */
    public void seeGameBoard(boolean first, int player) throws  NotExistingPlayerException, InterruptedException {
        int answer=view.seeGameBoard(first, players.get(player));
        //1) Leader Cards, 2) Market, 3) Grid, 4) Possible Production, 5) Active Leader Cards of the other players
        //6) Development Cards of the other players, 7) Nothing
        int finish;

        ToSeeFromGameBoard toSee = ToSeeFromGameBoard.fromInteger(answer);

        switch(toSee){
            case LEADER_CARDS:
                finish=view.seeLeaderCards(players.get(player), game.getPlayer(players.get(player)).getLeaders().IdDeck());
                if(finish==1) seeGameBoard(false, player);
                break;
            case MARKET:
                finish=view.seeMarket(players.get(player), game.getGameBoard().getMarket());
                if(finish==1) seeGameBoard(false, player);
                break;
            case DEVELOPMENT_CARD_GRID:
                int choice=view.chooseLine(players.get(player));
                if(choice==8) {
                    finish=view.seeGrid(players.get(player), game.getGameBoard().getDevelopmentCardGrid().getGrid().IdDeck());
                } else {
                    finish = view.seeGrid(players.get(player), game.getGameBoard().getDevelopmentCardGrid().getLine(choice).IdDeck());
                }
                if(finish==1) seeGameBoard(false, player);
                break;
            case POSSIBLE_PRODUCTION:
                finish=view.seeProduction(players.get(player), game.getPlayer(players.get(player)).getProductions());
                if(finish==1) seeGameBoard(false, player);
                break;
            case LEADER_CARDS_OTHER_PLAYER:
                finish=leaderCard(player);
                if(finish==1) seeGameBoard(false, player);
                break;
            case DEVELOPMENT_CARDS_OTHER_PLAYER:
                finish=devCard(player);
                if(finish==1) seeGameBoard(false, player);
                break;
            case NOTHING:
                break;
        }
    }


    /**
     * This method allows the selected player to see the leader cards belonging to other player
     * @param player is the player index in array list players
     * @return player's choice (1 if he wants to see more from the game board, 2 otherwise)
     * @throws NotExistingPlayerException if the selected player doesn't exists
     * @throws InterruptedException is due to multithreading message send
     */
    public int leaderCard(int player) throws NotExistingPlayerException, InterruptedException {
        for(String s : players) {
            if(!s.equals(players.get(player))) {
                view.seeOtherLeader(players.get(player), s, game.getPlayer(s).getLeaders().idDeckActive());
            }
        }

        return view.askChoice(players.get(player));
    }


    /**
     * This method allows the selected player to see the development cards belonging to other player
     * @param player is the player index in array list players
     * @return player's choice (1 if he wants to see more from the game board, 2 otherwise)
     * @throws NotExistingPlayerException if the selected player doesn't exists
     * @throws InterruptedException is due to multithreading message send
     */
    public int devCard(int player) throws NotExistingPlayerException, InterruptedException {
        for(String s : players) {
            if(!s.equals(players.get(player))) {
                view.seeOtherDev(players.get(player), s, game.getPlayer(s).getDevCards());
            }
        }

        return view.askChoice(players.get(player));
    }


    /**
     * This method allows the selected player to choose what he wants to do in his turn
     * @param player is the player index in array list players
     * @throws InterruptedException is due to multithreading message send
     * @throws NotExistingPlayerException if the selected player doesn't exists
     * @throws InvalidChoiceException if the choice is invalid
     */
    public void chooseTurn(int player) throws InterruptedException, NotExistingPlayerException, InvalidChoiceException {
        int answer;
        //1) Active Leader, 2) Discard Leader, 3) Use Market, 4) Buy development card, 5) Do production

        int pos;
        int type;

        do {
            if(Catch=true) Catch=false;
            answer=view.chooseTurn(players.get(player));
            TurnType turnType = TurnType.fromInteger(answer);
            switch (turnType) {
                case ACTIVE_LEADER:
                    pos = view.activeLeader(players.get(player), game.getPlayer(players.get(player)).getLeaders().IdDeck());
                    if(pos==3) break;
                    try {
                        activeLeader(player, pos);
                        for(String nickname: players){
                            view.seeStorage(nickname, game.getPlayer(nickname).getPlayerDashboard().getStorage(), game.getPlayer(nickname).getPlayerDashboard().getVault(), players.get(player));
                        }
                    } catch (InvalidChoiceException e) {
                        view.sendErrorMessage(players.get(player), "ACTIVE_LEADER");
                        view.resetCard(players.get(player), game.getPlayer(players.get(player)).getLeaders().get(pos - 1).getCardID());
                    }
                    break;

                case DISCARD_LEADER:
                    pos = view.discardLeader(players.get(player), game.getPlayer(players.get(player)).getLeaders().IdDeck());
                    if(pos==3) break;
                    try {
                        discardLeader(player, pos);
                        for(String s: players){
                            view.updateFaithPath(s, players.get(player), game.getPlayer(players.get(player)).getPlayerDashboard().getFaithPath().getPositionFaithPath(), false);
                        }
                        ArrayList<String> nick=new ArrayList<>(checkPapalPawn());
                        if(!nick.isEmpty()) {
                            view.papalPawn(nick);
                        }
                    } catch (InvalidChoiceException e) {
                        view.sendErrorMessage(players.get(player), "DISCARD_LEADER");
                        view.resetCard(players.get(player), game.getPlayer(players.get(player)).getLeaders().get(pos - 1).getCardID());
                    }
                    break;

                case MARKET:
                    int choice = view.manageStorage(1, players.get(player));
                    if (choice == 1) manageStorage(player);
                    int line = view.useMarket(players.get(player));
                    useMarket(player, line);
                    for(String nickname: players){
                        view.seeStorage(nickname, game.getPlayer(nickname).getPlayerDashboard().getStorage(), game.getPlayer(nickname).getPlayerDashboard().getVault(), players.get(player));
                    }
                    break;

                case BUY_DEVELOPMENT:
                    ArrayList<Integer> card = new ArrayList<>(view.askCardToBuy(players.get(player), game.getGameBoard().getDevelopmentCardGrid().getGrid().IdDeck(), game.getPlayer(players.get(player)).getDevCardsForGUI()));
                    int space = view.askSpace(players.get(player));
                    buyCard(player, card.get(0), card.get(1), space);
                    for(String nickname: players){
                        view.seeStorage(nickname, game.getPlayer(nickname).getPlayerDashboard().getStorage(), game.getPlayer(nickname).getPlayerDashboard().getVault(), players.get(player));
                    }
                    break;

                case ACTIVE_PRODUCTION:
                    do {
                        type = view.askType(players.get(player));
                        activeProduction(player, type);
                    } while (type != 4);
                    for(String nickname: players){
                        view.seeStorage(nickname, game.getPlayer(nickname).getPlayerDashboard().getStorage(), game.getPlayer(nickname).getPlayerDashboard().getVault(), players.get(player));
                    }
                    break;

            }
        } while(answer==1 || answer==2 || Catch);

        Catch=false;

        answer=view.endTurn(players.get(player));

        while(answer==1 || answer==2) {
            if (answer == 1) {
                pos = view.activeLeader(players.get(player), game.getPlayer(players.get(player)).getLeaders().IdDeck());
                if(pos==3) break;
                try {
                    activeLeader(player, pos);
                    for(String nickname: players){
                        view.seeStorage(nickname, game.getPlayer(nickname).getPlayerDashboard().getStorage(), game.getPlayer(nickname).getPlayerDashboard().getVault(), players.get(player));
                    }
                } catch (InvalidChoiceException e) {
                    view.sendErrorMessage(players.get(player), "ACTIVE_LEADER");
                    view.resetCard(players.get(player), game.getPlayer(players.get(player)).getLeaders().get(pos - 1).getCardID());
                }
            } else {
                pos = view.discardLeader(players.get(player), game.getPlayer(players.get(player)).getLeaders().IdDeck());
                if(pos==3) break;
                try {
                    discardLeader(player, pos);
                    for(String s: players){
                        view.updateFaithPath(s, players.get(player), game.getPlayer(players.get(player)).getPlayerDashboard().getFaithPath().getPositionFaithPath(), false);
                    }
                    ArrayList<String> nick=new ArrayList<>(checkPapalPawn());
                    if(!nick.isEmpty()) {
                        view.papalPawn(nick);
                    }
                } catch (InvalidChoiceException e) {
                    view.sendErrorMessage(players.get(player), "DISCARD_LEADER");
                    view.resetCard(players.get(player), game.getPlayer(players.get(player)).getLeaders().get(pos - 1).getCardID());
                }
            }
            answer=view.endTurn(players.get(player));
        }
    }


    /**
     * This method allows the selected player to activate every type of production
     * @param player is the player index in array list players
     * @param type is the number corresponding to the the type of the production that the player decided to activate
     * @throws NotExistingPlayerException if the selected player doesn't exists
     * @throws InterruptedException is due to multithreading message send
     */
    public void activeProduction(int player, int type) throws NotExistingPlayerException, InterruptedException {
        //1) Basic Production, 2) Development Card, 3) Production Leader, 4) Do production


        ProductionType productionType = ProductionType.fromInteger(type);
        switch(productionType) {
            case BASIC:
                Resource input1=view.askInput(players.get(player));
                Resource input2=view.askInput(players.get(player));
                Resource output=view.askOutput(players.get(player));
                game.getPlayer(players.get(player)).getPlayerDashboard().getDevCardsSpace().setInputBasicProduction(input1, input2);
                game.getPlayer(players.get(player)).getPlayerDashboard().getDevCardsSpace().setOutputBasicProduction(output);
                try {
                    game.getPlayer(players.get(player)).ActiveProductionBase();
                    view.sendBasicProduction(players.get(player), input1, input2, output);
                } catch (NotEnoughResourceException e) {
                    view.sendErrorMessage(players.get(player), "ACTIVE_BASE_PRODUCTION");
                }
                break;

            case DEVELOPMENT_CARD:
                int space=view.askDevCard(players.get(player));
                try {
                    game.getPlayer(players.get(player)).ActiveProductionDevCard(space);
                } catch (InvalidChoiceException | NotEnoughResourceException e) {
                    view.sendErrorMessage(players.get(player), "ACTIVE_DEV_CARD");
                }
                break;

            case PRODUCTION_LEADER:
                int index=view.askLeaderCard(players.get(player));
                Resource outputProduction=view.askOutput(players.get(player));
                try {
                    game.getPlayer(players.get(player)).ActiveProductionLeader(index, outputProduction);
                } catch (InvalidChoiceException | NotEnoughResourceException e) {
                    view.sendErrorMessage(players.get(player), "ACTIVE_PROD_LEADER");
                }
                break;

            case DO_PRODUCTION:
                try {
                    if(game.getPlayer(players.get(player)).getActivatedProduction().isEmpty()) {
                        view.sendErrorMessage(players.get(player), "DO_PRODUCTION_INVALID");
                        Catch=true;
                    } else {
                    game.getPlayer(players.get(player)).doProduction();
                    for(String s: players){
                        view.updateFaithPath(s, players.get(player), game.getPlayer(players.get(player)).getPlayerDashboard().getFaithPath().getPositionFaithPath(), false);
                    }
                    ArrayList<String> nick=new ArrayList<>(checkPapalPawn());
                    if(!nick.isEmpty()) {
                        view.papalPawn(nick);
                    }
                }
            } catch (NotEnoughResourceException e) {
                view.sendErrorMessage(players.get(player), "DO_PRODUCTION_NOT_ENOUGH_RES");
                Catch=true;
            }
                break;
        }
    }


    /**
     * This method allows the player to buy a development card from the grid
     * @param player is the player index in array list players
     * @param color is the selected card color
     * @param level is the selected card level
     * @param space is the development card space where the player want to place the selected card
     * @throws InvalidChoiceException if the choice is invalid
     * @throws NotExistingPlayerException if the selected player doesn't exists
     */
    public void buyCard(int player, int color, int level, int space) throws InvalidChoiceException, NotExistingPlayerException {
        CardColor cardColor;

        switch (color) {
            case 1 : cardColor=CardColor.PURPLE;
                break;
            case 2 : cardColor=CardColor.YELLOW;
                break;
            case 3 : cardColor=CardColor.BLUE;
                break;
            case 4 : cardColor=CardColor.GREEN;
                break;
            default: cardColor=null;
        }

        DevelopmentCard card=game.getGameBoard().getDevelopmentCardGrid().getCard(cardColor,level);

        try {
            game.getPlayer(players.get(player)).buyCard(card, space);
            game.getGameBoard().getDevelopmentCardGrid().removeCard(cardColor,level);
            for(String s : players){
                view.updateDevCardSpace(s,players.get(player),level,space,card.getCardID());
                view.updateGrid(s, game.getGameBoard().getDevelopmentCardGrid().getGrid().IdDeck());
            }
        } catch(InvalidSpaceCardException e) {
            view.sendInvalidInput(players.get(player));
            Catch=true;
        }
    }


    /**
     * This method allows the player to buy a market matrix row or column, and places the resources
     * corresponding to the balls of the chosen line
     * @param player is the player index in array list players
     * @param line is the int corresponding to the line chosen by the player
     * @throws InvalidChoiceException if the choice is invalid
     * @throws NotExistingPlayerException if the selected player doesn't exists
     * @throws InterruptedException is due to multithreading message send
     */
    public void useMarket(int player, int line) throws InvalidChoiceException, NotExistingPlayerException, InterruptedException {
        ArrayList<Integer> choice=new ArrayList<>();
        Resource resource = null;

        ArrayList<Ball> market = new ArrayList<>(game.getGameBoard().getMarket().getChosenColor(line));
        ArrayList<Ball> balls = new ArrayList<>();

        if (game.getPlayer(players.get(player)).WhiteBallLeader() == 2) {
            int ball = view.askWhiteBallLeader(players.get(player));
            resource = ((WhiteBallLeader) game.getPlayer(players.get(player)).getLeaders().get(ball - 1)).getConversionType();
        } else if (game.getPlayer(players.get(player)).WhiteBallLeader() == 1) {
            if (game.getPlayer(players.get(player)).getLeaders().get(0) instanceof WhiteBallLeader)
                resource = ((WhiteBallLeader) game.getPlayer(players.get(player)).getLeaders().get(0)).getConversionType();
            else
                resource = ((WhiteBallLeader) game.getPlayer(players.get(player)).getLeaders().get(1)).getConversionType();
        }

        for(Ball b : market) {
            if (b.getType().equals(BallColor.RED)) {
                game.getPlayer(players.get(player)).getPlayerDashboard().getFaithPath().moveForward(1);
                for(String s: players){
                    view.updateFaithPath(s, players.get(player), game.getPlayer(players.get(player)).getPlayerDashboard().getFaithPath().getPositionFaithPath(), false);
                }
                ArrayList<String> nick=new ArrayList<>(checkPapalPawn());
                if(!nick.isEmpty()) {
                    view.papalPawn(nick);
                }
            } else if (b.getType().equals(BallColor.WHITE)) {
                if (resource != null) {
                    switch (resource) {
                        case COIN:
                            balls.add(new Ball(BallColor.YELLOW));
                        case SERVANT:
                            balls.add(new Ball(BallColor.PURPLE));
                        case SHIELD:
                            balls.add(new Ball(BallColor.BLUE));
                        case STONE:
                            balls.add(new Ball(BallColor.GREY));
                    }
                }
            } else {
                balls.add(new Ball(b.getType()));
            }
        }


        ArrayList<Ball> toPlace = new ArrayList<>(checkEmptyShelves(player, balls));


        if(!toPlace.isEmpty()) {
            do {
                choice.addAll(view.seeBall(players.get(player), toPlace));

                try {
                    if(choice.get(1)==4) {
                        int card=game.getPlayer(players.get(player)).indexOfStorageLeader(toPlace.get((choice.get(0)) - 1).getCorrespondingResource());
                        ((StorageLeader) game.getPlayer(players.get(player)).getLeaders().get(card - 1)).AddResources(toPlace.get((choice.get(0)) - 1).getCorrespondingResource(), 1);
                    } else {
                        game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(choice.get(1), toPlace.get((choice.get(0)) - 1).getCorrespondingResource(), 1);
                        view.seeStorage(players.get(player),game.getPlayer(players.get(player)).getPlayerDashboard().getStorage(),null, players.get(player));

                    }
                    toPlace.remove(choice.get(0) - 1);
                    ArrayList<Ball> temp=new ArrayList<>(checkEmptyShelves(player,toPlace));
                    toPlace.clear();
                    toPlace.addAll(temp);
                } catch (NotEnoughSpaceException | ShelfHasDifferentTypeException | AnotherShelfHasTheSameTypeException | InvalidChoiceException e) {
                    view.sendErrorMessage(players.get(player), "MARKET_INVALID_STORAGE_LEADER");
                }

                choice.clear();
            } while (toPlace.size() > 0);

            for(String nickname: players){
                view.sendUpdateMarket(nickname, game.getGameBoard().getMarket());
            }
        }
    }


    /**
     * This method allows the player to move his shelves
     * @param player is the player index in array list players
     * @throws InterruptedException is due to multithreading message send
     * @throws NotExistingPlayerException if the selected player doesn't exists
     */
    public void manageStorage(int player) throws InterruptedException, NotExistingPlayerException {
        int action;
        do{
            ArrayList<Integer> choice = view.moveShelves(players.get(player));

            try {
                game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().InvertShelvesContent(choice.get(0),choice.get(1));
                view.seeStorage(players.get(player),game.getPlayer(players.get(player)).getPlayerDashboard().getStorage(),null, players.get(player));
            } catch (NotEnoughSpaceException e) {
                view.sendErrorMessage(players.get(player), "MARKET_INVALID_SHELF");
            }


            action=view.manageStorage(2, players.get(player));
        } while(action==1);
    }


    /**
     * This method allows the player to activate a leader during the match
     * @param player is the player index in array list players
     * @param pos is the position of the chosen leader card in leaders card in leader cards deck
     * @throws InvalidChoiceException if the choice is invalid
     * @throws NotExistingPlayerException if the selected player doesn't exists
     */
    public void activeLeader(int player, int pos) throws InvalidChoiceException, NotExistingPlayerException {
        game.getPlayer(players.get(player)).ActiveLeader(pos);
    }


    /**
     * This method allows the player to discard a leader during the match
     * @param player is the player index in array list players
     * @param pos is the position of the chosen leader card in leader cards deck
     * @throws InvalidChoiceException if the choice is invalid
     * @throws NotExistingPlayerException if the selected player doesn't exists
     */
    public void discardLeader(int player, int pos) throws InvalidChoiceException, NotExistingPlayerException {
        game.getPlayer(players.get(player)).DiscardLeader(pos);
    }


    /**
     * This method checks the spaces for balls that need to be placed and returns and returns the
     * only ones that can be add (to the storage or to the storage leader if present)
     * @param player is the player index in array list players
     * @param balls balls that need to be placed
     * @return balls that can be placed
     * @throws NotExistingPlayerException if the selected player doesn't exists
     */
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
                                for(String s: players){
                                    view.updateFaithPath(s, players.get(j), game.getPlayer(players.get(j)).getPlayerDashboard().getFaithPath().getPositionFaithPath(), false);
                                }
                                ArrayList<String> nick=new ArrayList<>(checkPapalPawn());
                                if(!nick.isEmpty()) {
                                    view.papalPawn(nick);
                                }
                            }
                        }
                    } else {
                        toPlace.add(new Ball(b.getType()));
                    }
                } else {
                    if (game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().emptyShelves() == 0) {
                        for (int j = 0; j < players.size(); j++) {
                            if (j != player) {
                                game.getPlayer(players.get(j)).move(1);
                                for(String s: players){
                                    view.updateFaithPath(s, players.get(j), game.getPlayer(players.get(j)).getPlayerDashboard().getFaithPath().getPositionFaithPath(), false);
                                }
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


    /**
     * This method is called every time the faith path indicator (red or black cross) moves forward
     * and it checks firstly if a papal favor has been triggered, if so it returns the nicknames of the
     * players that have the rights of activating the corresponding papal pawn
     * @return the array list of nicknames of the players that have the rights of activating the papal pawn
     */
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
