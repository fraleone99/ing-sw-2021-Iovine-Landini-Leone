package it.polimi.ingsw.controller;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.client.HandlerSP;
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
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.server.answer.finalanswer.Lose;
import it.polimi.ingsw.server.answer.finalanswer.Win;
import it.polimi.ingsw.server.answer.infoanswer.*;
import it.polimi.ingsw.server.answer.initialanswer.Connection;
import it.polimi.ingsw.server.answer.request.RequestDoubleInt;
import it.polimi.ingsw.server.answer.request.RequestInt;
import it.polimi.ingsw.server.answer.request.SendMessage;
import it.polimi.ingsw.server.answer.seegameboard.*;
import it.polimi.ingsw.server.answer.turnanswer.*;

import java.util.ArrayList;
import java.util.Objects;

/**
 * LocalSPController class handles local single player match
 * @author Nicola Landini
 */
public class LocalSPController {

    private final Game gameModel;
    private final TurnController turncontroller;
    private final EndGameController endgame;
    private final ArrayList<String> players=new ArrayList<>();
    private boolean isEnd=false;
    private final HandlerSP handler;
    private boolean notHasPerformedAnAction = false;

    private static final int YES = 1;
    private static final int ALL_GRID = 8;
    private static final int INVALID = 3;
    private static final int DO_PRODUCTION = 4;

    /**
     * LocalSPController constructor: creates a new instance of LocalSPController
     * @param nickname player's nickname
     */
    public LocalSPController(String nickname, HandlerSP handler) {
        this.players.add(nickname);
        this.handler=handler;
        gameModel=new Game();
        turncontroller=new TurnController(gameModel, players, null);
        endgame=new EndGameController();
    }

    /**
     * This method handles the single player local match
     */
    public void localGame() {
        ActionToken currentActionToken;

        gameModel.createPlayer(players.get(0));
        handler.handleClient(new Connection("",true));

        setPlayers();

        try{
            localSetInitialLeaderCards();

            localDiscardFirstLeader();

            PlayersInfo playersInfo = new PlayersInfo(1);
            playersInfo.addNick(players.get(0));
            handler.handleClient(playersInfo);
            handler.handleClient(new SendMessage("The game starts!\n"));
            handler.handleClient(new InitializeGameBoard(false, gameModel.getGameBoard().getMarket(), gameModel.getGameBoard().getDevelopmentCardGrid().getGrid().idDeck(), gameModel.getPlayer(players.get(0)).getLeaders().idDeck(), false, false, false, false));

            handler.handleClient(new UpdateFaithPath(null, 0, true));
            handler.handleClient(new UpdateFaithPath(players.get(0), 0, false));

            while(!isEnd) {
                handler.handleClient(new TurnStatus("START"));
                localSeePlayerDashboard();
                localSeeGameBoard();
                localChooseTurn();
                handler.handleClient(new TurnStatus("END"));

                try {
                    currentActionToken = gameModel.drawActionToken();
                    ArrayList<String> nick=new ArrayList<>(localCheckPapalPawn());
                    if(!nick.isEmpty()) {
                        handler.handleClient(new SendMessage("A Vatican report was activated. You will get the points of the Pope's Favor tile"));
                    }
                    handler.handleClient(new GridInfo(gameModel.getGameBoard().getDevelopmentCardGrid().getGrid().idDeck()));
                    handler.handleClient(new SendMessage("Drawn action token: "));
                    handler.handleClient(new ActionTokenInfo(currentActionToken));
                    handler.handleClient(new UpdateFaithPath(null, gameModel.getPlayer(players.get(0)).getPlayerDashboard().getFaithPath().getPositionLorenzo(), true));
                } catch (EmptyDecksException e) {
                    break;
                } catch (InvalidChoiceException e) {
                    e.printStackTrace();
                }
                isEnd = endgame.singlePlayerIsEndGame(gameModel);
            }
        } catch(NotExistingPlayerException | InvalidChoiceException e){
            e.printStackTrace();
        }

        try {
            if(endgame.singlePlayerLose(gameModel)){
                localLose();
            } else {
                localWin();
            }
        } catch (InvalidChoiceException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sets the player
     */
    public void setPlayers() {
        gameModel.createPlayer(players.get(0));
    }


    /**
     * This method sets player's initial leader cards
     * @throws NotExistingPlayerException if the player doesn't exist
     */
    public void localSetInitialLeaderCards() throws NotExistingPlayerException {
        for (int i = 0; i < 4; i++) {
            gameModel.getGameBoard().getLeaderDeck().shuffle();
            gameModel.getPlayer(players.get(0)).getLeaders().add(gameModel.getGameBoard().getLeaderDeck().drawFromTail());
        }
    }

    /**
     * This method allow player to discard 2 of his initial 4 leaders
     * @throws NotExistingPlayerException if the player doesn't exist
     */
    public void localDiscardFirstLeader() throws NotExistingPlayerException {
        int card;

        handler.handleClient(new SendMessage("Please choose the first leader card to discard."));
        handler.handleClient(new PassLeaderCard(gameModel.getPlayers().get(0).getLeaders().idDeck()));
        card=getAnswer();
        gameModel.getPlayer(players.get(0)).getPlayerDashboard().getLeaders().remove(card-1);

        handler.handleClient(new SendMessage("Please choose the second leader card to discard."));
        handler.handleClient(new PassLeaderCard(gameModel.getPlayers().get(0).getLeaders().idDeck()));
        card=getAnswer();
        gameModel.getPlayer(players.get(0)).getPlayerDashboard().getLeaders().remove(card-1);
    }

    /**
     * This method handles the call of player dashboard print in the CLI
     */
    public void localSeePlayerDashboard(){
        handler.handleClient(new FaithPathInfo((Constants.DIVIDING_LINE+"\nThis is the Dashboard of "+players.get(0)+" :"), gameModel.getPlayers().get(0).getPlayerDashboard().getFaithPath(), true));
        handler.handleClient((new StorageInfo(gameModel.getPlayers().get(0).getPlayerDashboard().getStorage(),
                gameModel.getPlayers().get(0).getPlayerDashboard().getVault(),gameModel.getPlayers().get(0).getPlayerDashboard().getLeaders().get(0),
                gameModel.getPlayers().get(0).getPlayerDashboard().getLeaders().get(1),gameModel.getPlayers().get(0).getNickname(), true)));
        handler.handleClient(new DevCardsSpaceInfo(gameModel.getPlayers().get(0).getPlayerDashboard().getDevCardsSpace()));
    }

    /**
     * This method gets the string contained in handler answer
     * @return the string contained in string answer
     */
    public String getString() {
        synchronized (handler.getLock()) {
            while(!handler.isReady()) {
                try {
                    handler.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        handler.setReady(false);
        return handler.getAnswer();
    }

    /**
     * This method gets the choices contained in handler answer
     * @return the numbers contained in double int answer
     */
    public ArrayList<Integer> getDoubleInt() {
        synchronized (handler.getLock()) {
            while(!handler.isReady()) {
                try {
                    handler.getLock().wait();
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        ArrayList<Integer> doubleInt=new ArrayList<>();
        doubleInt.add(handler.getNumber());
        doubleInt.add(handler.getNumber2());
        handler.setReady(false);
        return doubleInt;
    }

    /**
     * This method gets the choice contained in handler answer
     * @return the number contained in int answer
     */
    public int getAnswer()  {
        synchronized (handler.getLock()) {
            while(!handler.isReady()) {
                try {
                    handler.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        handler.setReady(false);
        return handler.getNumber();
    }

    /**
     * This method handles the call of game board print in the CLI
     * @throws NotExistingPlayerException if the player doesn't exist
     */
    public void localSeeGameBoard() throws NotExistingPlayerException {
        //1) Leader Cards, 2) Market, 3) Grid, 4) Possible Production
        handler.handleClient(new RequestInt("GAMEBOARD","What do you want to see from the game board:"));
        int answer=getAnswer();

        int finish;

        ToSeeFromGameBoard toSee = ToSeeFromGameBoard.fromInteger(answer);

        switch (Objects.requireNonNull(toSee)){
            case LEADER_CARDS: handler.handleClient(new SeeLeaderCards(gameModel.getPlayer(players.get(0)).getLeaders().idDeck()));
                finish=getAnswer();
                if(finish==YES) localSeeGameBoard();
                break;
            case MARKET: handler.handleClient(new SeeMarket(gameModel.getGameBoard().getMarket()));
                finish=getAnswer();
                if(finish==YES) localSeeGameBoard();
                break;
            case DEVELOPMENT_CARD_GRID: handler.handleClient(new RequestInt("LINE","Please choose what you want to see from the Development Cards Grid"));
                int choice=getAnswer();
                if(choice==ALL_GRID) {
                    handler.handleClient(new SeeGrid(gameModel.getGameBoard().getDevelopmentCardGrid().getGrid().idDeck()));
                } else {
                    handler.handleClient(new SeeGrid(gameModel.getGameBoard().getDevelopmentCardGrid().getLine(choice).idDeck()));
                }
                finish=getAnswer();
                if(finish==YES) localSeeGameBoard();
                break;
            case POSSIBLE_PRODUCTION: handler.handleClient(new SeeProductions(gameModel.getPlayer(players.get(0)).getProductions()));
                finish=getAnswer();
                if(finish==YES) localSeeGameBoard();
                break;
            case LEADER_CARDS_OTHER_PLAYER:
            case DEVELOPMENT_CARDS_OTHER_PLAYER:
                handler.handleClient(new SendMessage("This functionality is not available in single player matches."));
                break;
            case NOTHING: break;
        }
    }

    /**
     * This method handles player's turn choice
     * @throws NotExistingPlayerException if the player doesn't exist
     * @throws InvalidChoiceException if the choice is invalid
     */
    public void localChooseTurn() throws NotExistingPlayerException, InvalidChoiceException {
        int answer;

        int pos;
        int type;

        do{
            notHasPerformedAnAction =false;
            handler.handleClient(new RequestInt("TURN","Choose what you want to do in this turn:"));
            answer=getAnswer();
            TurnType turnType = TurnType.fromInteger(answer);
            switch (Objects.requireNonNull(turnType)) {
                case ACTIVE_LEADER:
                    handler.handleClient(new ActiveLeader("Which leader do you want to activate?", gameModel.getPlayer(players.get(0)).getLeaders().idDeck()));
                    pos=getAnswer();
                    if(pos==INVALID) break;
                    try{
                        turncontroller.activeLeader(0, pos);
                    } catch (InvalidChoiceException e) {
                        handler.handleClient(new ErrorMessage("ACTIVE_LEADER"));
                        handler.handleClient(new ResetCard(gameModel.getPlayer(players.get(0)).getLeaders().get(pos - 1).getCardID()));
                    }
                    break;

                case DISCARD_LEADER:
                    handler.handleClient(new DiscardLeader("Which leader do you want to discard?", gameModel.getPlayer(players.get(0)).getLeaders().idDeck()));
                    pos=getAnswer();
                    if(pos==INVALID) break;
                    try{
                        turncontroller.discardLeader(0, pos);
                        handler.handleClient(new UpdateFaithPath(players.get(0), gameModel.getPlayer(players.get(0)).getPlayerDashboard().getFaithPath().getPositionFaithPath(), false));
                        ArrayList<String> nick=new ArrayList<>(localCheckPapalPawn());
                        if(!nick.isEmpty()) {
                            handler.handleClient(new SendMessage("A Vatican report was activated. You will get the points of the Pope's Favor tile"));
                        }
                    } catch (InvalidChoiceException e) {
                        handler.handleClient(new ErrorMessage("DISCARD_LEADER"));
                        handler.handleClient(new ResetCard(gameModel.getPlayer(players.get(0)).getLeaders().get(pos - 1).getCardID()));
                    }
                    break;

                case MARKET:
                    handler.handleClient(new RequestInt("STORAGE","Before using the market do you want to reorder your storage? You won't be able to do it later."));
                    int choice=getAnswer();
                    if(choice==YES) localManageStorage();
                    handler.handleClient(new RequestInt("MARKET","Which market line do you want?"));
                    int line=getAnswer();
                    localUseMarket(0, line);
                    break;

                case BUY_DEVELOPMENT:
                    ArrayList<Integer> card;
                    do {
                        handler.handleClient(new RequestDoubleInt("DEVCARD", null, gameModel.getGameBoard().getDevelopmentCardGrid().getGrid().idDeck(), gameModel.getPlayer(players.get(0)).getDevCardsForGUI()));
                        card = getDoubleInt();
                    } while(gameModel.getGameBoard().getDevelopmentCardGrid().getCard(gameModel.getGameBoard().getDevelopmentCardGrid().parserColor(card.get(0)),card.get(1))==null);
                    handler.handleClient(new RequestInt("SPACE", "Choose the space where to insert the card"));
                    int space = getAnswer();
                    localBuyCard(card.get(0), card.get(1), space);
                    break;

                case ACTIVE_PRODUCTION:
                    do{
                        handler.handleClient(new RequestInt("TYPE", "Choose productions\n1) Basic Production\n2) Development Card\n3) Leader Card\n4) It's okay, do productions"));
                        type = getAnswer();
                        localActiveProduction(type);
                    } while (type != DO_PRODUCTION);
                    break;

            }

            handler.handleClient(new StorageInfo(gameModel.getPlayer(players.get(0)).getPlayerDashboard().getStorage(),
                    gameModel.getPlayer(players.get(0)).getPlayerDashboard().getVault(),
                    gameModel.getPlayer(players.get(0)).getLeaders().get(0), gameModel.getPlayer(players.get(0)).getLeaders().get(1),
                    players.get(0), false));

        } while (answer == TurnType.toInteger(TurnType.ACTIVE_LEADER) || answer == TurnType.toInteger(TurnType.DISCARD_LEADER) || notHasPerformedAnAction);

        notHasPerformedAnAction =false;

        handler.handleClient(new RequestInt("END","What do you want to do?\n1) Active Leader\n2) Discard Leader\n3) End turn"));
        answer=getAnswer();

        while(answer == TurnType.toInteger(TurnType.ACTIVE_LEADER) || answer == TurnType.toInteger(TurnType.DISCARD_LEADER) ) {
            if (answer == TurnType.toInteger(TurnType.ACTIVE_LEADER)) {
                handler.handleClient(new ActiveLeader("Which leader do you want to activate?", gameModel.getPlayer(players.get(0)).getLeaders().idDeck()));
                pos=getAnswer();
                if(pos==INVALID) break;
                try{
                    turncontroller.activeLeader(0, pos);
                } catch (InvalidChoiceException e) {
                    handler.handleClient(new ErrorMessage("ACTIVE_LEADER"));
                    handler.handleClient(new ResetCard(gameModel.getPlayer(players.get(0)).getLeaders().get(pos - 1).getCardID()));                }
            } else if (answer == TurnType.toInteger(TurnType.DISCARD_LEADER)){
                handler.handleClient(new DiscardLeader("Which leader do you want to discard?", gameModel.getPlayer(players.get(0)).getLeaders().idDeck()));
                pos=getAnswer();
                if(pos==INVALID) break;
                try{
                    turncontroller.discardLeader(0, pos);
                    handler.handleClient(new UpdateFaithPath(players.get(0), gameModel.getPlayer(players.get(0)).getPlayerDashboard().getFaithPath().getPositionFaithPath(), false));
                    ArrayList<String> nick=new ArrayList<>(localCheckPapalPawn());
                    if(!nick.isEmpty()) {
                        handler.handleClient(new SendMessage("A Vatican report was activated. You will get the points of the Pope's Favor tile"));
                    }
                } catch (InvalidChoiceException e) {
                    handler.handleClient(new ErrorMessage("DISCARD_LEADER"));
                    handler.handleClient(new ResetCard(gameModel.getPlayer(players.get(0)).getLeaders().get(pos - 1).getCardID()));
                }
            }
            handler.handleClient(new RequestInt("END","What do you want to do?\n1) Active Leader\n2) Discard Leader\n3) End turn"));
            answer=getAnswer();
        }
    }

    /**
     * This method allows player to manage his storage
     * @throws NotExistingPlayerException if the player doesn't exist
     */
    public void localManageStorage() throws NotExistingPlayerException {
        int action;

        do{
            handler.handleClient(new RequestDoubleInt("","Which shelves do you want to reverse?"));
            ArrayList<Integer> choice=getDoubleInt();

            try {
                gameModel.getPlayer(players.get(0)).getPlayerDashboard().getStorage().invertShelvesContent(choice.get(0),choice.get(1));
                handler.handleClient(new StorageInfo(gameModel.getPlayer(players.get(0)).getPlayerDashboard().getStorage(),null,
                        gameModel.getPlayer(players.get(0)).getPlayerDashboard().getLeaders().get(0),gameModel.getPlayer(players.get(0)).getPlayerDashboard().getLeaders().get(1),gameModel.getPlayers().get(0).getNickname(), true));
            } catch (NotEnoughSpaceException e) {
                handler.handleClient(new ErrorMessage("MARKET_INVALID_SHELF"));
            }

            handler.handleClient(new RequestInt("STORAGE","Do you want to make other changes to the storage?"));
            action=getAnswer();
        } while (action==1);

    }

    /**
     * This method allows player to buy from the market
     * @param player player position
     * @param line chosen market line
     * @throws InvalidChoiceException if the choice is invalid
     * @throws NotExistingPlayerException if the player doesn't exist
     */
    public void localUseMarket(int player, int line) throws InvalidChoiceException, NotExistingPlayerException {
        ArrayList<Integer> choice=new ArrayList<>();
        Resource resource = null;

        ArrayList<Ball> market = new ArrayList<>(gameModel.getGameBoard().getMarket().getChosenColor(line));
        ArrayList<Ball> balls = new ArrayList<>();

        if (gameModel.getPlayer(players.get(player)).whiteBallLeader() == 2) {
            handler.handleClient(new RequestInt("WHITE","You have 2 active WhiteBall leaders, which one do you want to use in this turn? (1 or 2)"));
            int ball=getAnswer();
            resource = ((WhiteBallLeader) gameModel.getPlayer(players.get(player)).getLeaders().get(ball - 1)).getConversionType();
        } else if (gameModel.getPlayer(players.get(player)).whiteBallLeader() == 1) {
            if (gameModel.getPlayer(players.get(player)).getLeaders().get(0) instanceof WhiteBallLeader)
                resource = ((WhiteBallLeader) gameModel.getPlayer(players.get(player)).getLeaders().get(0)).getConversionType();
            else
                resource = ((WhiteBallLeader) gameModel.getPlayer(players.get(player)).getLeaders().get(1)).getConversionType();
        }

        for(Ball b : market) {
            if (b.getType().equals(BallColor.RED)) {
                gameModel.getPlayer(players.get(player)).getPlayerDashboard().getFaithPath().moveForward(1);
                handler.handleClient(new UpdateFaithPath(players.get(0), gameModel.getPlayer(players.get(0)).getPlayerDashboard().getFaithPath().getPositionFaithPath(), false));
                ArrayList<String> nick=new ArrayList<>(localCheckPapalPawn());
                if(!nick.isEmpty()) {
                    handler.handleClient(new SendMessage("A Vatican report was activated. You will get the points of the Pope's Favor tile"));
                }
            } else if (b.getType().equals(BallColor.WHITE)) {
                if (resource != null) {
                    switch (resource) {
                        case COIN:
                            balls.add(new Ball(BallColor.YELLOW));
                            break;
                        case SERVANT:
                            balls.add(new Ball(BallColor.PURPLE));
                            break;
                        case SHIELD:
                            balls.add(new Ball(BallColor.BLUE));
                            break;
                        case STONE:
                            balls.add(new Ball(BallColor.GREY));
                            break;
                    }
                }
            } else {
                balls.add(new Ball(b.getType()));
            }
        }

        ArrayList<Ball> toPlace = new ArrayList<>(localCheckEmptyShelves(player, balls));

        if(!toPlace.isEmpty()) {
            do {
                handler.handleClient(new SeeBall(toPlace));
                choice.add(getAnswer());
                handler.handleClient(new RequestInt("SHELF",""));
                choice.add(getAnswer());

                try {
                    if(choice.get(1)==4) {
                        int card=gameModel.getPlayer(players.get(player)).indexOfStorageLeader(toPlace.get((choice.get(0)) - 1).getCorrespondingResource());
                        ((StorageLeader) gameModel.getPlayer(players.get(player)).getLeaders().get(card - 1)).addResources(toPlace.get((choice.get(0)) - 1).getCorrespondingResource(), 1);
                    } else {
                        gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().addResources(choice.get(1), toPlace.get((choice.get(0)) - 1).getCorrespondingResource(), 1);
                        handler.handleClient(new StorageInfo(gameModel.getPlayer(players.get(0)).getPlayerDashboard().getStorage(),null,
                                gameModel.getPlayer(players.get(0)).getPlayerDashboard().getLeaders().get(0), gameModel.getPlayer(players.get(0)).getPlayerDashboard().getLeaders().get(1)
                                ,gameModel.getPlayers().get(0).getNickname(), true));
                    }
                    toPlace.remove(choice.get(0) - 1);
                    ArrayList<Ball> temp=new ArrayList<>(localCheckEmptyShelves(0,toPlace));
                    toPlace.clear();
                    toPlace.addAll(temp);
                } catch (NotEnoughSpaceException | ShelfHasDifferentTypeException | AnotherShelfHasTheSameTypeException | InvalidChoiceException e) {
                    handler.handleClient(new ErrorMessage("MARKET_INVALID_STORAGE_LEADER"));
                }

                choice.clear();
            } while (toPlace.size() > 0);

            handler.handleClient(new MarketInfo(gameModel.getGameBoard().getMarket()));
        }
    }

    /**
     * This method checks the spaces for balls that need to be placed and returns and returns the
     * only ones that can be add (to the storage or to the storage leader if present)
     * @param player is the player index in array list players
     * @param balls balls that need to be placed
     * @return balls that can be placed
     * @throws NotExistingPlayerException if the selected player doesn't exists
     */
    public ArrayList<Ball> localCheckEmptyShelves(int player, ArrayList<Ball> balls) throws NotExistingPlayerException {
        ArrayList<Ball> toPlace = new ArrayList<>();

        for (Ball b : balls) {
            int i = gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().typePresent(b.getCorrespondingResource());

            if (gameModel.getPlayer(players.get(player)).storageLeader(b.getCorrespondingResource())) {
                toPlace.add(new Ball(b.getType()));
            } else {
                if (i != 0) {
                    if (gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().getShelves().get(i - 1).getAvailableSpace() == 0) {
                        for (int j = 0; j < players.size(); j++) {
                            if (j != player) {
                                gameModel.getPlayer(players.get(j)).move(1);
                                handler.handleClient(new UpdateFaithPath(players.get(0), gameModel.getPlayer(players.get(0)).getPlayerDashboard().getFaithPath().getPositionFaithPath(), false));
                                ArrayList<String> nick=new ArrayList<>(localCheckPapalPawn());
                                if(!nick.isEmpty()) {
                                    handler.handleClient(new SendMessage("A Vatican report was activated. You will get the points of the Pope's Favor tile"));
                                }
                            }
                        }
                    } else {
                        toPlace.add(new Ball(b.getType()));
                    }
                } else {
                    if (gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().emptyShelves() == 0) {
                        for (int j = 0; j < players.size(); j++) {
                            if (j != player) {
                                gameModel.getPlayer(players.get(j)).move(1);
                                handler.handleClient(new UpdateFaithPath(players.get(0), gameModel.getPlayer(players.get(0)).getPlayerDashboard().getFaithPath().getPositionFaithPath(), false));
                                ArrayList<String> nick=new ArrayList<>(localCheckPapalPawn());
                                if(!nick.isEmpty()) {
                                    handler.handleClient(new SendMessage("A Vatican report was activated. You will get the points of the Pope's Favor tile"));
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
     * This method allows player to buy a card
     * @param color card color
     * @param level card level
     * @param space is the space where we want to insert the card
     * @throws InvalidChoiceException if the choice is invalid
     * @throws NotExistingPlayerException if the player doesn't exist
     */
    public void localBuyCard(int color, int level, int space) throws InvalidChoiceException, NotExistingPlayerException {

        CardColor cardColor = gameModel.getGameBoard().getDevelopmentCardGrid().parserColor(color);

        DevelopmentCard card=gameModel.getGameBoard().getDevelopmentCardGrid().getCard(cardColor,level);

        try {
            gameModel.getPlayer(players.get(0)).buyCard(card, space);
            gameModel.getGameBoard().getDevelopmentCardGrid().removeCard(cardColor,level);

            handler.handleClient(new CardsSpaceInfo(players.get(0), level, space, card.getCardID()));
            handler.handleClient(new GridInfo(gameModel.getGameBoard().getDevelopmentCardGrid().getGrid().idDeck()));
        } catch(InvalidSpaceCardException e) {
            handler.handleClient(new SendMessage("INVALID"));
            notHasPerformedAnAction =true;
        }
    }

    /**
     * This method handles the activation of a production
     * @param type production type
     * @throws NotExistingPlayerException if the player doesn't exist
     */
    public void localActiveProduction(int type) throws NotExistingPlayerException {
        //1) Basic Production, 2) Development Card, 3) Production Leader, 4) Do production

        ProductionType productionType = ProductionType.fromInteger(type);
        switch (Objects.requireNonNull(productionType)) {
            case BASIC:
                handler.handleClient(new RequestInt("INPUT","Choose the input:\n1) COIN\n2) STONE\n3) SHIELD\n4) SERVANT"));
                Resource input1 = parser(getAnswer());
                handler.handleClient(new RequestInt("INPUT","Choose the input:\n1) COIN\n2) STONE\n3) SHIELD\n4) SERVANT"));
                Resource input2 = parser(getAnswer());
                handler.handleClient(new RequestInt("OUTPUT","Choose the output:\n1) COIN\n2) STONE\n3) SHIELD\n4) SERVANT"));
                Resource output = parser(getAnswer());
                gameModel.getPlayer(players.get(0)).getPlayerDashboard().getDevCardsSpace().setInputBasicProduction(input1, input2);
                gameModel.getPlayer(players.get(0)).getPlayerDashboard().getDevCardsSpace().setOutputBasicProduction(output);
                try {
                    gameModel.getPlayer(players.get(0)).activeProductionBase();
                    handler.handleClient(new BasicProductionInfo(input1, input2, output));
                } catch (NotEnoughResourceException e) {
                    handler.handleClient(new ErrorMessage("ACTIVE_BASE_PRODUCTION"));
                }
                break;

            case DEVELOPMENT_CARD:
                handler.handleClient(new RequestInt("DEVCARD","Insert the number of the space containing the development card"));
                int space = getAnswer();
                try {
                    gameModel.getPlayer(players.get(0)).activeProductionDevCard(space);
                } catch (InvalidChoiceException | NotEnoughResourceException e) {
                    handler.handleClient(new ErrorMessage("ACTIVE_DEV_CARD"));
                }
                break;

            case PRODUCTION_LEADER:
                handler.handleClient(new RequestInt("LEADCARD","Insert the number of the production leader that you want to use"));
                int index = getAnswer();
                handler.handleClient(new RequestInt("OUTPUT","Choose the output:\n1) COIN\n2) SERVANT\n3) SHIELD\n4) STONE"));
                Resource outputProduction = parser(getAnswer());
                try {
                    gameModel.getPlayer(players.get(0)).activeProductionLeader(index, outputProduction);
                } catch (InvalidChoiceException | NotEnoughResourceException e) {
                    handler.handleClient(new ErrorMessage("ACTIVE_PROD_LEADER"));
                }
                break;

            case DO_PRODUCTION:
                try {
                    if (gameModel.getPlayer(players.get(0)).getActivatedProduction().isEmpty()) {
                        handler.handleClient(new ErrorMessage("DO_PRODUCTION_INVALID"));
                        notHasPerformedAnAction =true;
                    } else {
                        gameModel.getPlayer(players.get(0)).doProduction();
                        handler.handleClient(new UpdateFaithPath(players.get(0), gameModel.getPlayer(players.get(0)).getPlayerDashboard().getFaithPath().getPositionFaithPath(), false));
                        ArrayList<String> nick=new ArrayList<>(localCheckPapalPawn());
                        if(!nick.isEmpty()) {
                            handler.handleClient(new SendMessage("A Vatican report was activated. You will get the points of the Pope's Favor tile"));
                        }
                    }
                } catch (NotEnoughResourceException e) {
                    handler.handleClient(new ErrorMessage("DO_PRODUCTION_NOT_ENOUGH_RES"));
                    notHasPerformedAnAction =true;
                }
                break;
        }
    }

    /**
     * This method parses player choice
     * @param answer player choice
     * @return resource corresponding to player choice
     */
    public Resource parser(int answer){
        switch(answer) {
            case 1 : return Resource.COIN;
            case 2 : return Resource.STONE;
            case 3 : return Resource.SHIELD;
            case 4 : return Resource.SERVANT;
            default : return null;
        }
    }

    /**
     * This method is called every time the faith path indicator (red or black cross) moves forward
     * and it checks firstly if a papal favor has been triggered, if so it returns the nicknames of the
     * players that have the rights of activating the corresponding papal pawn
     * @return the array list of nicknames of the players that have the rights of activating the papal pawn
     */
    public ArrayList<String> localCheckPapalPawn() {
        ArrayList<String> players=new ArrayList<>();
        switch (gameModel.getPapalPawn()) {
            case 0:
                for (Player p : gameModel.getPlayers()) {
                    if (p.getPlayerDashboard().getFaithPath().getPositionFaithPath() > 7 || p.getPlayerDashboard().getFaithPath().getPositionLorenzo()>7) {
                        p.getPlayerDashboard().getFaithPath().setPapalPawn1();
                        gameModel.updatePapalPawn();
                        players.add(p.getNickname());
                        handler.handleClient(new UpdatePapalPawn(p.getNickname(), 1));
                        break;
                    }
                }
            case 1:
                for (Player p : gameModel.getPlayers()) {
                    if (p.getPlayerDashboard().getFaithPath().getPositionFaithPath() > 15 || p.getPlayerDashboard().getFaithPath().getPositionLorenzo()>15) {
                        p.getPlayerDashboard().getFaithPath().setPapalPawn2();
                        gameModel.updatePapalPawn();
                        players.add(p.getNickname());
                        handler.handleClient(new UpdatePapalPawn(p.getNickname(), 2));
                        break;
                    }
                }
            case 2:
                for (Player p : gameModel.getPlayers()) {
                    if (p.getPlayerDashboard().getFaithPath().getPositionFaithPath() > 23 || p.getPlayerDashboard().getFaithPath().getPositionLorenzo()>23) {
                        p.getPlayerDashboard().getFaithPath().setPapalPawn3();
                        gameModel.updatePapalPawn();
                        players.add(p.getNickname());
                        handler.handleClient(new UpdatePapalPawn(p.getNickname(), 3));
                        break;
                    }
                }
        }

        return players;
    }

    /**
     * This method handles the local game lose
     */
    public void localLose(){
        int victoryPoints = endgame.totalVictoryPoints(gameModel.getPlayers().get(0));
        handler.handleClient(new Lose("Sorry, you lose. You have "+ Constants.ANSI_BLUE + victoryPoints + Constants.ANSI_RESET +" victory points"));
    }

    /**
     * This method handles the local game win
     */
    public void localWin(){
        int victoryPoints = endgame.totalVictoryPoints(gameModel.getPlayers().get(0));
        handler.handleClient(new Win("You won! You have " + Constants.ANSI_BLUE + victoryPoints + Constants.ANSI_RESET + " victory points"));
    }


}