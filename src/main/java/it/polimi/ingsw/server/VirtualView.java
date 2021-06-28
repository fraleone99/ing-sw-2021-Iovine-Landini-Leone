package it.polimi.ingsw.server;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;
import it.polimi.ingsw.model.card.leadercard.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.Ball;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.model.gameboard.playerdashboard.*;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.observer.VirtualViewObservable;
import it.polimi.ingsw.server.answer.finalanswer.Lose;
import it.polimi.ingsw.server.answer.finalanswer.Win;
import it.polimi.ingsw.server.answer.infoanswer.*;
import it.polimi.ingsw.server.answer.initialanswer.Connection;
import it.polimi.ingsw.server.answer.request.RequestDoubleInt;
import it.polimi.ingsw.server.answer.request.RequestInt;
import it.polimi.ingsw.server.answer.request.RequestString;
import it.polimi.ingsw.server.answer.request.SendMessage;
import it.polimi.ingsw.server.answer.seegameboard.*;
import it.polimi.ingsw.server.answer.turnanswer.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VirtualView extends VirtualViewObservable {
    private int number;
    private final Map <String, ClientHandler> namesToClient = new HashMap<>();
    private final Map <String, Boolean> clientConnected = new HashMap<>();
    private Controller controller;
    private final Server server;

    public VirtualView(Server server) {
        this.server = server;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setNamesToClient(String nickname, ClientHandler client, boolean crashed) {
        if(crashed) {
            clientConnected.replace(nickname, true);
            namesToClient.replace(nickname, client);
            controller.setClientConnection(nickname, true, true);
        } else {
            namesToClient.put(nickname, client);
            clientConnected.put(nickname, true);
        }
    }

    public void setClientConnectionToController() {
        for(String nickname : clientConnected.keySet()) {
            controller.setClientConnection(nickname, true, false);
        }
    }

    public void clientCrashed(String nickname) {
        clientConnected.replace(nickname, false);
        controller.setClientConnection(nickname, false, true);
    }

    public int waitForInt(ClientHandler client) {
        synchronized (client.getLock()) {
            while(!client.isReady()) {
                try {
                    client.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            number = client.getNumber();
        }
        client.setReady(false);

        return number;
    }

    public ArrayList<Integer> waitForDoubleInt(ClientHandler client) {
        ArrayList<Integer> numbers = new ArrayList<>();

        synchronized (client.getLock()) {
            while (!client.isReady()) {
                try {
                    client.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            numbers.add(client.getNumber());
            numbers.add(client.getNumber2());
        }
        client.setReady(false);

        return numbers;
    }

    public String waitForString(ClientHandler client) {
        String answer;

        synchronized (client.getLock()) {
            while(!client.isReady()) {
                try {
                    client.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            answer = client.getAnswer();
        }

        client.setReady(false);

        return answer;
    }

    public void askHandShake(ClientHandler client) {
        client.send(new Connection("Welcome to this fantastic server!", true));
    }


    public String requestNickname(ClientHandler client){
        client.send(new RequestString("Please insert your nickname:"));

        return waitForString(client);
    }


    public String invalidNickname(ClientHandler client){
        client.send(new RequestString("The chosen nickname is not valid. Try again:"));

        return waitForString(client);
    }


    public int requestPlayersNumber(ClientHandler client){
        client.send(new RequestInt("NUMBER","Please insert the number of players:"));

        return waitForInt(client);
        //notify everyone the players number
    }


    public void waitingRoom(ClientHandler client){
        client.send(new SendMessage("You are now in the waiting room. The game will start soon!"));
    }


    public void prepareTheLobby(){
        notifyPreparationOfLobby();
    }


    public void firstPlayer(String nickname) {
        namesToClient.get(nickname).send(new SendMessage("You are the first player."));
    }


    public int chooseResource(String nickname, String player, int amount) {
        ClientHandler client=namesToClient.get(nickname);

        if(player.equals("fourth") && amount==2){
            client.send(new RequestInt("RESOURCE","Please choose another resource."));
        } else {
            client.send(new RequestInt("RESOURCE","You are the " + player + " player. Please choose a resource."));
        }

        return waitForInt(client);
    }


    public int discardFirstLeaders(String nickname, int number, ArrayList<Integer> IdLeader) {
        ClientHandler client=namesToClient.get(nickname);

        if(number==1) {
            client.send(new SendMessage("Please choose the first leader card to discard."));
        } else {
            client.send(new SendMessage("Please choose the second leader card to discard."));
        }
        client.send(new PassLeaderCard(IdLeader));

        return waitForInt(client);
    }


    public void startGame(String nickname) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SendMessage("The game starts!"));
    }


    public void seeFaithPath(String nickname, String player, FaithPath path, boolean SinglePlayer) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new FaithPathInfo(("\n\n"+Constants.DIVIDING_LINE+"\nThis is the Dashboard of "+Constants.ANSI_GREEN+player+Constants.ANSI_RESET+" :\n"), path, SinglePlayer));
    }


    public void seeStorage(String nickname, PlayerDashboard playerDashboard ,String dashboardOwner, boolean toPrint, boolean vaultUpdate) {
        ClientHandler client=namesToClient.get(nickname);
        Storage storage = playerDashboard.getStorage();
        Vault vault;
        if(vaultUpdate) {
            vault = playerDashboard.getVault();
        }
        else {
            vault = null;
        }
        LeaderCard leader1 = playerDashboard.getLeaders().get(0);
        LeaderCard leader2 = playerDashboard.getLeaders().get(1);

        client.send(new StorageInfo(storage, vault, leader1, leader2, dashboardOwner, toPrint));
    }


    public void seeDevCardsSpace(String nickname, DevCardsSpace space) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new DevCardsSpaceInfo(space));
    }


    public void seeActionToken(String nickname, ActionToken actionToken){
        ClientHandler client=namesToClient.get(nickname);

        client.send(new ActionTokenInfo(actionToken));
    }


    public int seeGameBoard(boolean first, String nickname){
        ClientHandler client=namesToClient.get(nickname);

        if(first) notifyTurnChoice(nickname, " is watching the game board");

        client.send(new RequestInt("GAMEBOARD","What do you want to see about the Game Board?"));

        return waitForInt(client);
    }


    public int seeLeaderCards(String nickname, ArrayList<Integer> leaderCards){
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SeeLeaderCards(leaderCards));

        return waitForInt(client);
    }


    public int seeMarket(String nickname, Market market){
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SeeMarket(market));

        return waitForInt(client);
    }


    public int chooseLine(String nickname) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("LINE","Please choose what you want to see from the Development Cards Grid"));

        return waitForInt(client);
    }


    public int seeGrid(String nickname, ArrayList<Integer> id){
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SeeGrid(id));

        return waitForInt(client);
    }


    public int seeProduction(String nickname, ArrayList<Integer> productions) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SeeProductions(productions));

        return waitForInt(client);
    }


    public void seeOtherLeader(String nickname, String player, ArrayList<Integer> leaderCards) {
        ClientHandler client=namesToClient.get(nickname);

        if(leaderCards.isEmpty()) {
            client.send(new SendMessage(Constants.ANSI_BLUE + player + Constants.ANSI_RESET + " has no active leader cards"));
        } else {
            client.send(new SendMessage(Constants.ANSI_BLUE + player + Constants.ANSI_RESET + " has these active leader cards:"));
            client.send(new SeeOtherCards(leaderCards));
        }
    }


    public void seeOtherDev(String nickname, String player, ArrayList<Integer> devCards) {
        ClientHandler client=namesToClient.get(nickname);

        if(devCards.isEmpty()) {
            client.send(new SendMessage(Constants.ANSI_BLUE + player + Constants.ANSI_RESET + " has no development card that he can use"));
        } else {
            client.send(new SendMessage(Constants.ANSI_BLUE + player + Constants.ANSI_RESET + " has these development cards that he can use:"));
            client.send(new SeeOtherCards(devCards));
        }
    }


    public int askChoice(String nickname) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("CHOICE", null));

        return waitForInt(client);
    }


    public int chooseTurn(String nickname){
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("TURN","Choose what you want to do in this turn:"));

        return waitForInt(client);
    }


    public int activeLeader(String nickname, ArrayList<Integer> leaders){
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is activating a leader");

        client.send(new ActiveLeader("Which leader do you want to activate?", leaders));

        return waitForInt(client);
    }


    public int discardLeader(String nickname, ArrayList<Integer> leaders){
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is discarding a leader");

        client.send(new DiscardLeader("Which leader do you want to discard?", leaders));

        return waitForInt(client);
    }


    public int manageStorage(int number, String nickname){
        ClientHandler client=namesToClient.get(nickname);

        if(number==1){
            notifyTurnChoice(nickname, " is managing his storage");
            client.send(new RequestInt("STORAGE","Before using the market do you want to reorder your storage? You won't be able to do it later."));
        }
        else
            client.send(new RequestInt("STORAGE","Do you want to make other changes to the storage?"));

        return waitForInt(client);
    }


    public void activeOtherLeaderCard(String player, int idCard, String nickname, int pos) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new OtherLeaderCard(idCard, "ACTIVE", player, pos));
    }

    public void discardOtherLeaderCard(String player, int idCard, String nickname, int pos) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new OtherLeaderCard(idCard, "DISCARD", player, pos));
    }


    public ArrayList<Integer> moveShelves(String nickname) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestDoubleInt("SHELVES","Which shelves do you want to reverse?"));

        return waitForDoubleInt(client);
    }


    public int useMarket(String nickname){
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is using the market");

        client.send(new RequestInt("MARKET","Which market line do you want?"));

        return waitForInt(client);
    }


    public ArrayList<Integer> seeBall(String nickname, ArrayList<Ball> balls) {
        ClientHandler client=namesToClient.get(nickname);

        ArrayList<Integer> moves=new ArrayList<>();

        client.send(new SeeBall(balls));

        moves.add(waitForInt(client));

        if(moves.get(0) != -1) {
            client.send(new RequestInt("SHELF", ""));

            moves.add(waitForInt(client));
        }

        return moves;
    }


    public int askWhiteBallLeader(String nickname) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("WHITE","You have 2 active WhiteBall leaders, which one do you want to use in this turn? (1 or 2)"));

        return waitForInt(client);
    }


    public ArrayList<Integer> askCardToBuy(String nickname, ArrayList<Integer> cards, ArrayList<Integer> spaces) {
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is buying a card");

        client.send(new RequestDoubleInt("DEVCARD", null, cards, spaces));

        return waitForDoubleInt(client);
    }


    public int askSpace(String nickname) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("SPACE","Choose the space where to insert the card"));

        return waitForInt(client);
    }


    public int askType(String nickname) {
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is activating a production");

        client.send(new RequestInt("TYPE","Choose productions\n1) Basic Production\n2) Development Card\n3) Leader Card\n4) It's okay, do productions"));

        return waitForInt(client);
    }


    public Resource askInput(String nickname){
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("INPUT","Choose the input:\n1) COIN\n2) STONE\n3) SHIELD\n4) SERVANT"));

        number = waitForInt(client);

        switch(number) {
            case 1 : return Resource.COIN;
            case 2 : return Resource.STONE;
            case 3 : return Resource.SHIELD;
            case 4 : return Resource.SERVANT;
            default : return null;
        }
    }


    public Resource askOutput(String nickname) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("OUTPUT","Choose the output:\n1) COIN\n2) STONE\n3) SHIELD\n4) SERVANT"));

        number = waitForInt(client);

        switch(number) {
            case 1 : return Resource.COIN;
            case 2 : return Resource.STONE;
            case 3 : return Resource.SHIELD;
            case 4 : return Resource.SERVANT;
            default : return null;
        }
    }


    public void sendBasicProduction(String nickname, Resource input1, Resource input2, Resource output) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new BasicProductionInfo(input1, input2, output));
    }


    public int askDevCard(String nickname) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("DEVCARD","Insert the number of the space containing the development card"));

        return waitForInt(client);
    }


    public void initializeGameBoard(boolean crashed, String nickname, Market market, ArrayList<Integer> idCards, ArrayList<Integer> leader, boolean active1, boolean discarded1, boolean active2, boolean discarded2) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new InitializeGameBoard(crashed, market, idCards, leader, active1, discarded1, active2, discarded2));

    }

    public void updateFaithPath(String nickname, String nickToUpdate, int nickPos, boolean isLorenzo){
        ClientHandler client=namesToClient.get(nickname);
        client.send(new UpdateFaithPath(nickToUpdate, nickPos, isLorenzo));
    }

    public void updatePapalPawn(String nickname, String nickToUpdate, int pawn){
        ClientHandler client=namesToClient.get(nickname);
        client.send(new UpdatePapalPawn(nickToUpdate, pawn));
    }

    public void initialInfo(String nickname, int playerNumber, ArrayList<String> nicknames){
        PlayersInfo playersInfo = new PlayersInfo(playerNumber);
        for(String nick: nicknames){
            playersInfo.addNick(nick);
        }
        ClientHandler client = namesToClient.get(nickname);
        client.send(playersInfo);
    }


    public int askLeaderCard(String nickname) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("LEADCARD","Insert the number of the production leader that you want to use"));

        return waitForInt(client);
    }


    public void papalPawn(ArrayList<String> nicknames) {
        for(ClientHandler client : namesToClient.values()) {
            if(clientConnected.get(client.getNickname())) {
                client.send(new SendMessage("A Vatican report was activated. The following players will get the points of the Pope's Favor tile:"));
                for (String s : nicknames) {
                    client.send(new SendMessage(Constants.ANSI_BLUE + s + Constants.ANSI_RESET));
                }
            }
        }
    }


    public int endTurn(String nickname){
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("END","What do you want to do?\n1) Active Leader\n2) Discard Leader\n3) End turn"));

        return waitForInt(client);
    }


    public void win(String nickname, int victoryPoints) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new Win("You won! You have "  + victoryPoints  + " victory points\n"));
    }


    public void lose(String nickname, int victoryPoints) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new Lose("Sorry, you lose. You have " + victoryPoints + " victory points\n"));
    }


    public void updateDevCardSpace(String player, String nickname, int level, int space, int idCard) {
        ClientHandler client=namesToClient.get(player);
        client.send(new CardsSpaceInfo(nickname, level, space, idCard));
    }

    public void updateGrid(String player, ArrayList<Integer> idCards) {
        ClientHandler client=namesToClient.get(player);
        client.send(new GridInfo(idCards));
    }

    public void sendErrorMessage(String nickname, String errorType){
        ClientHandler client=namesToClient.get(nickname);
        client.send(new ErrorMessage(errorType));
    }

    public void sendInvalidInput(String nickname) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SendMessage("INVALID"));
    }


    public void resetCard(String nickname, int pos) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new ResetCard(pos));
    }


    public void closeConnection() {
        for(ClientHandler client : namesToClient.values()) {
            client.isEnd(true);
            client.send(new SendMessage("END_GAME"));
        }
    }

    public void allClientCrashed() {
        for(ClientHandler client : namesToClient.values()) {
            server.clientDisconnected(client.getNickname());
        }
    }

    public void closeGame(String nickname) {
        server.closeGame(nickname);
    }


    public void sendTurnStatus(String message, String nickname){
        if(message.equals("START")){
            notifyPlayingNick(nickname);
        }

        ClientHandler client = namesToClient.get(nickname);

        client.send(new TurnStatus(message));
    }

    public void sendUpdateMarket(String nickname, Market market){
        ClientHandler client = namesToClient.get(nickname);
        client.send(new MarketInfo(market));
    }

    public void setDevCardsSpaceForReconnection(String nickname, ArrayList<DevelopmentCardDeck> space, String owner) {
        ClientHandler client = namesToClient.get(nickname);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < space.get(i).size(); j++){
                client.send(new CardsSpaceInfo(owner,j+1,i+1,space.get(i).getDeck().get(j).getCardID()));
            }
        }
    }
}
