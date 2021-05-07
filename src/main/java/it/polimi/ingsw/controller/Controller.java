package it.polimi.ingsw.controller;

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
import it.polimi.ingsw.model.gameboard.playerdashboard.Shelf;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.server.VirtualView;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private final Game gameModel;
    private final TurnController turncontroller;
    private final EndGameController endgame;
    private final ArrayList<String> players=new ArrayList<>();
    private final VirtualView view;
    private boolean isEnd=false;
    private boolean isOver=false;
    ActionToken actionToken;

    public Controller(ArrayList<String> players, VirtualView view) {
        this.players.addAll(players);
        gameModel=new Game(players.size(), players);
        turncontroller=new TurnController(gameModel, players);
        endgame=new EndGameController();
        this.view=view;
    }

    public void play() {
        try {
            for(int i=0; i<players.size(); i++) {
                turncontroller.setPlayers(i);
            }
            for (int i = 0; i < players.size(); i++) {
                setInitialBenefits(i);
                turncontroller.setInitialLeaderCards(i);
                discardFirstLeaders(i);
            }
            for(int i=0; i<players.size(); i++){
                startGame(i);
            }
            while(!isEnd){
                for(int i=0; i<players.size();i++){
                    seePlayerDashboards(i);
                    seeGameBoard(i);
                    chooseTurn(i);
                }

                //TODO
                if(players.size()==1){
                    try{
                        actionToken = gameModel.drawActionToken();
                        view.seeActionToken(players.get(0), actionToken);
                    } catch (EmptyDecksException e){
                        break;
                    } catch (InvalidChoiceException e){
                        e.printStackTrace();
                    }
                    isEnd = endgame.SinglePlayerIsEndGame(gameModel);
                } else {
                    isEnd = isEndGame();
                }
            }

            endGame();

        } catch (NotExistingPlayerException | InterruptedException | InvalidChoiceException e){
            e.printStackTrace();
        }
    }

    public void setInitialBenefits(int i) throws NotExistingPlayerException, InterruptedException {
        switch(i){
            case 0: view.firstPlayer(players.get(i));
                    break;
            case 1:
                int resource1 = view.chooseResource(players.get(i), "second", 1);
                    turncontroller.addInitialResource(i, resource1,1);
                    break;
            case 2: resource1 =view.chooseResource(players.get(i), "third",1);
                    turncontroller.addInitialResource(i, resource1,1);
                    gameModel.getPlayer(players.get(i)).getPlayerDashboard().getFaithPath().moveForward(1);
                    break;
            case 3: resource1 =view.chooseResource(players.get(i), "fourth",1);
                    turncontroller.addInitialResource(i, resource1,2);
                int resource2 = view.chooseResource(players.get(i), "fourth", 2);
                    if(resource1 == resource2) {
                        turncontroller.addInitialResource(i, resource2, 2);
                    } else {
                        turncontroller.addInitialResource(i, resource2,1);
                    }
                    gameModel.getPlayer(players.get(i)).getPlayerDashboard().getFaithPath().moveForward(1);
        }
    }

    public void discardFirstLeaders(int player) throws  InterruptedException, NotExistingPlayerException {
        int card;
        card=view.discardFirstLeaders(players.get(player), 1, gameModel.getPlayer(players.get(player)).getLeaders().IdDeck());
        gameModel.getPlayer(players.get(player)).getPlayerDashboard().getLeaders().remove(card-1);
        card=view.discardFirstLeaders(players.get(player), 2, gameModel.getPlayer(players.get(player)).getLeaders().IdDeck());
        gameModel.getPlayer(players.get(player)).getPlayerDashboard().getLeaders().remove(card-1);
    }

    public void startGame(int player) {
        view.startGame(players.get(player));
    }

    public void seePlayerDashboards(int player) throws NotExistingPlayerException {
        for (String s : players) {
            if(players.size()==1){
                view.seeFaithPath(players.get(player), s, gameModel.getPlayer(s).getPlayerDashboard().getFaithPath(), true);
            } else {
                view.seeFaithPath(players.get(player), s, gameModel.getPlayer(s).getPlayerDashboard().getFaithPath(), false);
            }
            view.seeStorage(players.get(player), gameModel.getPlayer(s).getPlayerDashboard().getStorage(),
                    gameModel.getPlayer(players.get(player)).getPlayerDashboard().getVault());
            view.seeDevCardsSpace(players.get(player), gameModel.getPlayer(s).getPlayerDashboard().getDevCardsSpace());
        }
    }

    public void seeGameBoard(int player) throws  NotExistingPlayerException, InterruptedException {
        int answer=view.seeGameBoard(players.get(player));
        //1) Leader Cards, 2) Market, 3) Grid, 4) Possible Production, 5) Nothing
        int finish;

        switch(answer){
            case 1 : finish=view.seeLeaderCards(players.get(player), gameModel.getPlayer(players.get(player)).getLeaders().IdDeck());
                     if(finish==1) seeGameBoard(player);
                     break;
            case 2 : finish=view.seeMarket(players.get(player), gameModel.getGameBoard().getMarket());
                     if(finish==1) seeGameBoard(player);
                     break;
            case 3 : int choice=view.chooseLine(players.get(player));
                     finish=view.seeGrid(players.get(player), gameModel.getGameBoard().getDevelopmentCardGrid().getLine(choice).IdDeck());
                     if(finish==1) seeGameBoard(player);
                     break;
            case 4 : finish=view.seeProduction(players.get(player), gameModel.getPlayer(players.get(player)).getProductions());
                     if(finish==1) seeGameBoard(player);
                     break;
            case 5 : break;
        }
    }


    public void chooseTurn(int player) throws InterruptedException, NotExistingPlayerException, InvalidChoiceException {
        int answer;
        //1) Active Leader, 2) Discard Leader, 3) Use Market, 4) Buy development card, 5) Do production

        int pos;
        int type=0;

        do {
            answer=view.chooseTurn(players.get(player));
            switch (answer) {
                case 1:
                    pos = view.activeLeader(players.get(player), gameModel.getPlayer(players.get(player)).getLeaders().IdDeck());
                    try {
                        turncontroller.activeLeader(player, pos);
                    } catch (InvalidChoiceException e) {
                        view.sendErrorMessage(players.get(player));
                        view.resetCard(players.get(player), gameModel.getPlayer(players.get(player)).getLeaders().get(pos - 1).getCardID());
                        chooseTurn(player);
                    }
                    break;

                case 2:
                    pos = view.discardLeader(players.get(player), gameModel.getPlayer(players.get(player)).getLeaders().IdDeck());
                    try {
                        turncontroller.discardLeader(player, pos);
                    } catch (InvalidChoiceException e) {
                        view.sendErrorMessage(players.get(player));
                        view.resetCard(players.get(player), gameModel.getPlayer(players.get(player)).getLeaders().get(pos - 1).getCardID());
                        chooseTurn(player);
                    }
                    break;

                case 3:
                    int choice = view.manageStorage(1, players.get(player));
                    if (choice == 1) manageStorage(player);
                    int line = view.useMarket(players.get(player));
                    useMarket(player, line);
                    break;

                case 4:
                    int color = view.askColor(players.get(player));
                    int level = view.askLevel(players.get(player));
                    int space = view.askSpace(players.get(player));
                    buyCard(player, color, level, space);
                    break;

                case 5:
                    do {
                        type = view.askType(players.get(player));
                        activeProduction(player, type);
                    } while (type != 4);
                    break;

            }
        } while(answer==1 || answer==2);

            answer=view.endTurn(players.get(player));

            while(answer==1 || answer==2) {
                if (answer == 1) {
                    pos = view.activeLeader(players.get(player), gameModel.getPlayer(players.get(player)).getLeaders().IdDeck());
                    try {
                        turncontroller.activeLeader(player, pos);
                    } catch (InvalidChoiceException e) {
                        view.sendErrorMessage(players.get(player));
                        view.resetCard(players.get(player), gameModel.getPlayer(players.get(player)).getLeaders().get(pos - 1).getCardID());
                        chooseTurn(player);
                    }
                } else {
                    pos = view.discardLeader(players.get(player), gameModel.getPlayer(players.get(player)).getLeaders().IdDeck());
                    try {
                        turncontroller.discardLeader(player, pos);
                    } catch (InvalidChoiceException e) {
                        view.sendErrorMessage(players.get(player));
                        view.resetCard(players.get(player), gameModel.getPlayer(players.get(player)).getLeaders().get(pos - 1).getCardID());
                        chooseTurn(player);
                    }
                }
                answer=view.endTurn(players.get(player));
            }
        }


    public void activeProduction(int player, int type) throws NotExistingPlayerException, InterruptedException {
        //1) Basic Production, 2) Development Card, 3) Production Leader, 4) Do production

        switch(type) {
            case 1 : Resource input1=view.askInput(players.get(player));
                     Resource input2=view.askInput(players.get(player));
                     Resource output=view.askOutput(players.get(player));
                     gameModel.getPlayer(players.get(player)).getPlayerDashboard().getDevCardsSpace().setInputBasicProduction(input1, input2);
                     gameModel.getPlayer(players.get(player)).getPlayerDashboard().getDevCardsSpace().setOutputBasicProduction(output);
                     try {
                        gameModel.getPlayer(players.get(player)).ActiveProductionBase();
                     } catch (NotEnoughResourceException e) {
                        view.sendErrorMessage(players.get(player));
                     }
                     break;

            case 2 : int space=view.askDevCard(players.get(player));
                     try {
                        gameModel.getPlayer(players.get(player)).ActiveProductionDevCard(space);
                     } catch (InvalidChoiceException | NotEnoughResourceException e) {
                         view.sendErrorMessage(players.get(player));
                     }
                     break;

            case 3 : int index=view.askLeaderCard(players.get(player));
                     try {
                        gameModel.getPlayer(players.get(player)).ActiveProductionLeader(index);
                     } catch (InvalidChoiceException | NotEnoughResourceException e) {
                        view.sendErrorMessage(players.get(player));
                     }
                     break;

            case 4 : try {
                        gameModel.getPlayer(players.get(player)).doProduction();
                     } catch (NotEnoughResourceException e) {
                        view.sendErrorMessage(players.get(player));
                     }
                     break;
        }
    }


    public void buyCard(int player, int color, int level, int space) throws InvalidChoiceException, NotExistingPlayerException, InterruptedException {
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

        DevelopmentCard card=gameModel.getGameBoard().getDevelopmentCardGrid().getCard(cardColor,level);

        try {
            gameModel.getPlayer(players.get(player)).buyCard(card, space);
            gameModel.getGameBoard().getDevelopmentCardGrid().removeCard(cardColor,level);
        } catch(InvalidSpaceCardException e) {
            view.sendErrorMessage(players.get(player));
            chooseTurn(player);
        }
    }

    public void useMarket(int player, int line) throws InvalidChoiceException, NotExistingPlayerException, InterruptedException {
        ArrayList<Integer> choice=new ArrayList<>();
        Resource resource = null;

        ArrayList<Ball> market = new ArrayList<>(gameModel.getGameBoard().getMarket().getChosenColor(line));
        ArrayList<Ball> balls = new ArrayList<>();
        ArrayList<Ball> toPlace = new ArrayList<>();

        if (gameModel.getPlayer(players.get(player)).WhiteBallLeader() == 2) {
            int ball = view.askWhiteBallLeader(players.get(player));
            resource = ((WhiteBallLeader) gameModel.getPlayer(players.get(player)).getLeaders().get(ball - 1)).getConversionType();
        } else if (gameModel.getPlayer(players.get(player)).WhiteBallLeader() == 1) {
            if (gameModel.getPlayer(players.get(player)).getLeaders().get(0) instanceof WhiteBallLeader)
                resource = ((WhiteBallLeader) gameModel.getPlayer(players.get(player)).getLeaders().get(0)).getConversionType();
            else
                resource = ((WhiteBallLeader) gameModel.getPlayer(players.get(player)).getLeaders().get(1)).getConversionType();
        }

        for(Ball b : market) {
            if (b.getType().equals(BallColor.RED)) {
                gameModel.getPlayer(players.get(player)).getPlayerDashboard().getFaithPath().moveForward(1);
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

        for(Ball b : balls) {
            int i = gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().typePresent(b.getCorrespondingResource());

            if (gameModel.getPlayer(players.get(player)).StorageLeader(b.getCorrespondingResource())) {
                toPlace.add(new Ball(b.getType()));
            } else {
                if (i != 0) {
                    if (gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().getShelves().get(i - 1).getAvailableSpace() == 0) {
                        for (int j = 0; j < players.size(); j++) {
                            if (j != player) gameModel.getPlayer(players.get(j)).move(1);
                        }
                    } else {
                        toPlace.add(new Ball(b.getType()));
                        //gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().getShelves().get(i-1).setResourceAmount(1);
                    }
                } else {
                    if (gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().emptyShelves() == 0) {
                        for (int j = 0; j < players.size(); j++) {
                            if (j != player) gameModel.getPlayer(players.get(j)).move(1);
                        }
                    } else {
                        toPlace.add(new Ball(b.getType()));
                    }
                }
            }
        }

        if(!toPlace.isEmpty()) {
            do {
                choice.addAll(view.seeBall(players.get(player), toPlace));

                try {
                    if(choice.get(1)==4) {
                        int card=gameModel.getPlayer(players.get(player)).indexOfStorageLeader(toPlace.get((choice.get(0)) - 1).getCorrespondingResource());
                        ((StorageLeader) gameModel.getPlayer(players.get(player)).getLeaders().get(card - 1)).AddResources(toPlace.get((choice.get(0)) - 1).getCorrespondingResource(), 1);
                    } else {
                        gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().AddResource(choice.get(1), toPlace.get((choice.get(0)) - 1).getCorrespondingResource(), 1);
                    }
                    toPlace.remove(choice.get(0) - 1);
                    ArrayList<Ball> temp=new ArrayList<>(checkEmptyShelves(player,toPlace));
                    toPlace.clear();
                    toPlace.addAll(temp);
                } catch (NotEnoughSpaceException | ShelfHasDifferentTypeException | AnotherShelfHasTheSameTypeException | InvalidChoiceException e) {
                    view.sendErrorMessage(players.get(player));
                }

                choice.clear();
            } while (toPlace.size() > 0);
        }
    }

    public ArrayList<Ball> checkEmptyShelves(int player, ArrayList<Ball> balls) throws NotExistingPlayerException {
        ArrayList<Ball> toPlace=new ArrayList<>();

        for(Ball b : balls) {
            int i = gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().typePresent(b.getCorrespondingResource());

            if (gameModel.getPlayer(players.get(player)).StorageLeader(b.getCorrespondingResource())) {
                toPlace.add(new Ball(b.getType()));
            } else {
                if (i != 0) {
                    if (gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().getShelves().get(i - 1).getAvailableSpace() == 0) {
                        for (int j = 0; j < players.size(); j++) {
                            if (j != player) gameModel.getPlayer(players.get(j)).move(1);
                        }
                    } else {
                        toPlace.add(new Ball(b.getType()));
                        //gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().getShelves().get(i-1).setResourceAmount(1);
                    }
                } else {
                    if (gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().emptyShelves() == 0) {
                        for (int j = 0; j < players.size(); j++) {
                            if (j != player) gameModel.getPlayer(players.get(j)).move(1);
                        }
                    } else {
                        toPlace.add(new Ball(b.getType()));
                    }
                }
            }
        }

        return toPlace;
    }

    public void manageStorage(int player) throws InterruptedException, NotExistingPlayerException {
        int action;
        do{
            ArrayList<Integer> choice = view.moveShelves(players.get(player));

            try {
                gameModel.getPlayer(players.get(player)).getPlayerDashboard().getStorage().InvertShelvesContent(choice.get(0),choice.get(1));
            } catch (NotEnoughSpaceException e) {
                view.sendErrorMessage(players.get(player));
            }


            action=view.manageStorage(2, players.get(player));
        } while(action==1);
    }


    public EndGameController getEndgame() {
        return endgame;
    }

    public void endGame(){

        if(players.size()==1){
            try {
                if(endgame.SinglePlayerLose(gameModel)){
                    //TODO send to the server that Lorenzo won
                } else {
                    //TODO send to the server that player won
                }
            } catch (InvalidChoiceException e) {
                e.printStackTrace();
            }
        }
        else {
            Player winner = endgame.getWinner(gameModel);
            //TODO send the winner to the server
        }

        /*if(isOver){
            if(gameModel.getPlayers().indexOf(gameModel.getCurrentPlayer())==gameModel.getPlayers().size()-1) {
                Player winner = getEndgame().getWinner(gameModel);
                /*send the winner to the server*/
            /*}
        }
        else{
            for(Player p : gameModel.getPlayers()) {
                if (p.getPlayerDashboard().getDevCardsSpace().getAmountCards() == 7 || p.getPlayerDashboard().getFaithPath().getPositionFaithPath() == 24) {
                    isOver = true;
                }
            }
        }*/
    }

    public boolean isEndGame(){
        for(Player p : gameModel.getPlayers()) {
            if (p.getPlayerDashboard().getDevCardsSpace().getAmountCards() == 7 || p.getPlayerDashboard().getFaithPath().getPositionFaithPath() == 24) {
                return true;
            }
        }

        return false;
    }

    //SINGLE PLAYER

    public void singlePlayerLose(){
        int victoryPoints = endgame.totalVictoryPoints(gameModel.getCurrentPlayer());
        //TODO send that Lorenzo is the winner to the server
    }

    public void singlePlayerWins(){
        Player winner = gameModel.getCurrentPlayer();
        int victoryPoints = endgame.totalVictoryPoints(gameModel.getCurrentPlayer());
        //TODO send the winner to the server
    }

}
