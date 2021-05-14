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
import it.polimi.ingsw.model.gameboard.GameBoard;
import it.polimi.ingsw.model.gameboard.Ball;
import it.polimi.ingsw.server.VirtualView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TurnController {
    private Game game;
    private VirtualView view;
    private final ArrayList<String> players = new ArrayList<>();


    public TurnController(Game game, ArrayList<String> players, VirtualView view) {
        this.game = game;
        this.players.addAll(players);
        this.view=view;
    }


    public void seePlayerDashboards(int player) throws NotExistingPlayerException {
        for (String s : players) {
            view.seeFaithPath(players.get(player), s, game.getPlayer(s).getPlayerDashboard().getFaithPath(), players.size() == 1);
            view.seeStorage(players.get(player), game.getPlayer(s).getPlayerDashboard().getStorage(),
                    game.getPlayer(s).getPlayerDashboard().getVault());
            view.seeDevCardsSpace(players.get(player), game.getPlayer(s).getPlayerDashboard().getDevCardsSpace());
        }
    }


    public void seeGameBoard(boolean first, int player) throws  NotExistingPlayerException, InterruptedException {
        int answer=view.seeGameBoard(first, players.get(player));
        //1) Leader Cards, 2) Market, 3) Grid, 4) Possible Production, 5) Active Leader Cards of the other players
        //6) Development Cards of the other players, 7) Nothing
        int finish;

        switch(answer){
            case 1 : finish=view.seeLeaderCards(players.get(player), game.getPlayer(players.get(player)).getLeaders().IdDeck());
                if(finish==1) seeGameBoard(false, player);
                break;
            case 2 : finish=view.seeMarket(players.get(player), game.getGameBoard().getMarket());
                if(finish==1) seeGameBoard(false, player);
                break;
            case 3 : int choice=view.chooseLine(players.get(player));
                finish=view.seeGrid(players.get(player), game.getGameBoard().getDevelopmentCardGrid().getLine(choice).IdDeck());
                if(finish==1) seeGameBoard(false, player);
                break;
            case 4 : finish=view.seeProduction(players.get(player), game.getPlayer(players.get(player)).getProductions());
                if(finish==1) seeGameBoard(false, player);
                break;
            case 5 : finish=leaderCard(player);
                if(finish==1) seeGameBoard(false, player);
                break;
            case 6 : finish=devCard(player);
                if(finish==1) seeGameBoard(false, player);
                break;
            case 7 : break;
        }
    }


    public int leaderCard(int player) throws NotExistingPlayerException, InterruptedException {
        for(String s : players) {
            if(!s.equals(players.get(player))) {
                view.seeOtherLeader(players.get(player), s, game.getPlayer(s).getLeaders().idDeckActive());
            }
        }

        return view.askChoice(players.get(player));
    }


    public int devCard(int player) throws NotExistingPlayerException, InterruptedException {
        for(String s : players) {
            if(!s.equals(players.get(player))) {
                view.seeOtherDev(players.get(player), s, game.getPlayer(s).getDevCards());
            }
        }

        return view.askChoice(players.get(player));
    }


    public void chooseTurn(int player) throws InterruptedException, NotExistingPlayerException, InvalidChoiceException {
        int answer;
        //1) Active Leader, 2) Discard Leader, 3) Use Market, 4) Buy development card, 5) Do production

        int pos;
        int type;

        do {
            answer=view.chooseTurn(players.get(player));
            switch (answer) {
                case 1:
                    pos = view.activeLeader(players.get(player), game.getPlayer(players.get(player)).getLeaders().IdDeck());
                    try {
                        activeLeader(player, pos);
                    } catch (InvalidChoiceException e) {
                        view.sendErrorMessage(players.get(player));
                        view.resetCard(players.get(player), game.getPlayer(players.get(player)).getLeaders().get(pos - 1).getCardID());
                    }
                    break;

                case 2:
                    pos = view.discardLeader(players.get(player), game.getPlayer(players.get(player)).getLeaders().IdDeck());
                    try {
                        discardLeader(player, pos);
                        ArrayList<String> nick=new ArrayList<>(checkPapalPawn());
                        if(!nick.isEmpty()) {
                            view.papalPawn(nick);
                        }
                    } catch (InvalidChoiceException e) {
                        view.sendErrorMessage(players.get(player));
                        view.resetCard(players.get(player), game.getPlayer(players.get(player)).getLeaders().get(pos - 1).getCardID());
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
                pos = view.activeLeader(players.get(player), game.getPlayer(players.get(player)).getLeaders().IdDeck());
                try {
                    activeLeader(player, pos);
                } catch (InvalidChoiceException e) {
                    view.sendErrorMessage(players.get(player));
                    view.resetCard(players.get(player), game.getPlayer(players.get(player)).getLeaders().get(pos - 1).getCardID());
                }
            } else {
                pos = view.discardLeader(players.get(player), game.getPlayer(players.get(player)).getLeaders().IdDeck());
                try {
                    discardLeader(player, pos);
                } catch (InvalidChoiceException e) {
                    view.sendErrorMessage(players.get(player));
                    view.resetCard(players.get(player), game.getPlayer(players.get(player)).getLeaders().get(pos - 1).getCardID());
                }
            }
            answer=view.endTurn(players.get(player));
        }
    }


    public void activeProduction(int player, int type) throws NotExistingPlayerException, InterruptedException, InvalidChoiceException {
        //1) Basic Production, 2) Development Card, 3) Production Leader, 4) Do production

        switch(type) {
            case 1 : Resource input1=view.askInput(players.get(player));
                Resource input2=view.askInput(players.get(player));
                Resource output=view.askOutput(players.get(player));
                game.getPlayer(players.get(player)).getPlayerDashboard().getDevCardsSpace().setInputBasicProduction(input1, input2);
                game.getPlayer(players.get(player)).getPlayerDashboard().getDevCardsSpace().setOutputBasicProduction(output);
                try {
                    game.getPlayer(players.get(player)).ActiveProductionBase();
                } catch (NotEnoughResourceException e) {
                    view.sendErrorMessage(players.get(player));
                }
                break;

            case 2 : int space=view.askDevCard(players.get(player));
                try {
                    game.getPlayer(players.get(player)).ActiveProductionDevCard(space);
                } catch (InvalidChoiceException | NotEnoughResourceException e) {
                    view.sendErrorMessage(players.get(player));
                }
                break;

            case 3 : int index=view.askLeaderCard(players.get(player));
                try {
                    game.getPlayer(players.get(player)).ActiveProductionLeader(index);
                } catch (InvalidChoiceException | NotEnoughResourceException e) {
                    view.sendErrorMessage(players.get(player));
                }
                break;

            case 4 : try {
                if(game.getPlayer(players.get(player)).getActivatedProduction().isEmpty()) {
                    view.sendErrorMessage(players.get(player));
                    chooseTurn(player);
                } else {
                    game.getPlayer(players.get(player)).doProduction();
                    checkPapalPawn();
                }
            } catch (NotEnoughResourceException e) {
                view.sendErrorMessage(players.get(player));
                chooseTurn(player);
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

        DevelopmentCard card=game.getGameBoard().getDevelopmentCardGrid().getCard(cardColor,level);

        try {
            game.getPlayer(players.get(player)).buyCard(card, space);
            game.getGameBoard().getDevelopmentCardGrid().removeCard(cardColor,level);
        } catch(InvalidSpaceCardException e) {
            view.sendErrorMessage(players.get(player));
            chooseTurn(player);
        }
    }


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


    public void manageStorage(int player) throws InterruptedException, NotExistingPlayerException {
        int action;
        do{
            ArrayList<Integer> choice = view.moveShelves(players.get(player));

            try {
                game.getPlayer(players.get(player)).getPlayerDashboard().getStorage().InvertShelvesContent(choice.get(0),choice.get(1));
            } catch (NotEnoughSpaceException e) {
                view.sendErrorMessage(players.get(player));
            }


            action=view.manageStorage(2, players.get(player));
        } while(action==1);
    }


    public void activeLeader(int player, int pos) throws InvalidChoiceException, NotExistingPlayerException {
        game.getPlayer(players.get(player)).ActiveLeader(pos);
    }


    public void discardLeader(int player, int pos) throws InvalidChoiceException, NotExistingPlayerException {
        game.getPlayer(players.get(player)).DiscardLeader(pos);
    }


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
