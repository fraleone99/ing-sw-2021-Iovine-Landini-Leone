package it.polimi.ingsw.server;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.client.Client;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VirtualView extends VirtualViewObservable {
    private String answer;
    private int number;
    private int number2;
    private final Map<String, ClientHandler> namesToClient = new HashMap<>();


    public String askHandShake(ClientHandler client) throws InterruptedException {
        client.send(new Connection("Welcome to this fantastic server!", true));
        synchronized (client.getLock()) {
            while (!client.isReady()) {
                client.getLock().wait();
            }
            answer = client.getAnswer();
        }
        client.setReady(false);
        return answer;
    }


    public String requestNickname(ClientHandler client){
        client.send(new RequestString("Please insert your nickname:"));
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


    public String InvalidNickname(ClientHandler client) throws InterruptedException{
        client.send(new RequestString("The chosen nickname is not valid. Try again:"));
        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            answer = client.getAnswer();
        }
        client.setReady(false);
        return answer;
    }


    public int requestPlayersNumber(ClientHandler client) throws InterruptedException {
        client.send(new RequestInt("NUMBER","Please insert the number of players:"));
        synchronized (client.getLock()) {
            while (!client.isReady()) client.getLock().wait();
            number = client.getNumber();
        }
        client.setReady(false);
        return number;
        //notify everyone the players number
    }


    public void waitingRoom(ClientHandler client){
        client.send(new SendMessage("You are now in the waiting room. The game will start soon!"));
    }


    public void prepareTheLobby() throws IOException {
        notifyPreparationOfLobby();
    }


    public void firstPlayer(String nickname) {
        namesToClient.get(nickname).send(new SendMessage("You are the first player."));
    }


    public int chooseResource(String nickname, String player, int amount) throws  InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        if(player.equals("fourth") && amount==2){
            client.send(new RequestInt("RESOURCE","Please choose another resource."));
        } else {
            client.send(new RequestInt("RESOURCE","You are the " + player + " player. Please choose a resource."));
        }

        synchronized (client.getLock()){
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public int discardFirstLeaders(String nickname, int number, ArrayList<Integer> IdLeader) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        if(number==1) {
            client.send(new SendMessage("Please choose the first leader card to discard."));
        } else {
            client.send(new SendMessage("Please choose the second leader card to discard."));
        }
        client.send(new PassLeaderCard(IdLeader));
        synchronized (client.getLock()) {
            while (!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public void startGame(String nickname) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SendMessage("The game start!"));
    }


    public void seeFaithPath(String nickname, String player, FaithPath path, boolean SinglePlayer) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new FaithPathInfo(("This is the Dashboard of "+player+" :"), path, SinglePlayer));
    }


    public void seeStorage(String nickname, Storage storage, Vault vault, String storageOwner) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new StorageInfo(storage, vault, storageOwner));
    }


    public void seeDevCardsSpace(String nickname, DevCardsSpace space) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new DevCardsSpaceInfo(space));
    }


    public void seeActionToken(String nickname, ActionToken actionToken){
        ClientHandler client=namesToClient.get(nickname);

        client.send(new ActionTokenInfo(actionToken));
    }


    public int seeGameBoard(boolean first, String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        if(first) notifyTurnChoice(nickname, " is watching the game board");

        client.send(new RequestInt("GAMEBOARD","What do you want to see about the Game Board?"));
        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public int seeLeaderCards(String nickname, ArrayList<Integer> leaderCards) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SeeLeaderCards(leaderCards));
        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public int seeMarket(String nickname, Market market) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SeeMarket(market));
        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public int chooseLine(String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("LINE","Please choose what you want to see from the Development Cards Grid"));
        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public int seeGrid(String nickname, ArrayList<Integer> id) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SeeGrid(id));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public int seeProduction(String nickname, ArrayList<Integer> productions) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SeeProductions(productions));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
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


    public int askChoice(String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("CHOICE", null));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public int chooseTurn(String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("TURN","Choose what you want to do in this turn:"));
        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public int activeLeader(String nickname, ArrayList<Integer> leaders) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is activating a leader");

        client.send(new ActiveLeader("Which leader do you want to activate?", leaders));
        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public int discardLeader(String nickname, ArrayList<Integer> leaders) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is discarding a leader");

        client.send(new DiscardLeader("Which leader do you want to discard?", leaders));
        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public int manageStorage(int number, String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        if(number==1){
            notifyTurnChoice(nickname, " is managing his storage");
            client.send(new RequestInt("STORAGE","Before using the market do you want to reorder your storage? You won't be able to do it later."));
        }
        else
            client.send(new RequestInt("STORAGE","Do you want to make other changes to the storage?"));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public ArrayList<Integer> moveShelves(String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);
        ArrayList<Integer> moves=new ArrayList<>();

        client.send(new RequestDoubleInt("SHELVES","Which shelves do you want to reverse?"));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
            number2=client.getNumber2();
            moves.add(number);
            moves.add(number2);
        }

        client.setReady(false);

        return moves;
    }


    public int useMarket(String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is using the market");

        client.send(new RequestInt("MARKET","Which market line do you want?"));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public ArrayList<Integer> seeBall(String nickname, ArrayList<Ball> balls) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        ArrayList<Integer> moves=new ArrayList<>();

        client.send(new SeeBall(balls));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
            moves.add(number);
        }

        client.setReady(false);

        client.send(new RequestInt("SHELF",""));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number2=client.getNumber();
            moves.add(number2);
        }

        client.setReady(false);

        return moves;
    }


    public int askWhiteBallLeader(String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("WHITE","You have 2 active WhiteBall leaders, which one do you want to use in this turn? (1 or 2)"));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public ArrayList<Integer> askCardToBuy(String nickname, ArrayList<Integer> cards, ArrayList<Integer> spaces) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is buying a card");
        ArrayList<Integer> card=new ArrayList<>();

        client.send(new RequestDoubleInt("DEVCARD", null, cards, spaces));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
            number2=client.getNumber2();
            card.add(number);
            card.add(number2);
        }

        client.setReady(false);

        return card;
    }


    public int askSpace(String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("SPACE","Choose the space where to insert the card"));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public int askType(String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);
        notifyTurnChoice(nickname, " is activating a production");

        client.send(new RequestInt("TYPE","What kind of production do you want to activate?\n1) Basic Production\n2) Development Card\n3) Leader Card\n4) It's okay, do productions"));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public Resource askInput(String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("INPUT","Choose the input:\n1) COIN\n2) SERVANT\n3) SHIELD\n4) STONE"));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        switch(number) {
            case 1 : return Resource.COIN;
            case 2 : return Resource.SERVANT;
            case 3 : return Resource.SHIELD;
            case 4 : return Resource.STONE;
            default : return null;
        }
    }


    public Resource askOutput(String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("OUTPUT","Choose the output:\n1) COIN\n2) SERVANT\n3) SHIELD\n4) STONE"));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        switch(number) {
            case 1 : return Resource.COIN;
            case 2 : return Resource.SERVANT;
            case 3 : return Resource.SHIELD;
            case 4 : return Resource.STONE;
            default : return null;
        }
    }


    public int askDevCard(String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("DEVCARD","Insert the number of the space containing the development card"));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public void initializeGameBoard(String nickname, Market market, ArrayList<Integer> idCards, ArrayList<Integer> leader) {
        ClientHandler client=namesToClient.get(nickname);
        client.send(new InitializeGameBoard(market, idCards, leader));

    }

    public void initialInfo(String nickname, int playerNumber, ArrayList<String> nicknames){
        PlayersInfo playersInfo = new PlayersInfo(playerNumber);
        for(String nick: nicknames){
            playersInfo.addNick(nick);
        }
        ClientHandler client = namesToClient.get(nickname);
        client.send(playersInfo);
    }


    public int askLeaderCard(String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("LEADCARD","Insert the number of the production leader that you want to use"));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public void papalPawn(ArrayList<String> nicknames) {
        for(ClientHandler client : namesToClient.values()) {
            client.send(new SendMessage("A Vatican report was activated. The following players will get the points of the Pope's Favor tile:"));
            for(String s : nicknames) {
                client.send(new SendMessage(Constants.ANSI_BLUE + s + Constants.ANSI_RESET));
            }
        }
        //namesToClient.get(nickname).send(new SendMessage("You have activated a Vatican report!"));
        //notify everyone
    }


    public int endTurn(String nickname) throws InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new RequestInt("END","What do you want to do?\n1) Active Leader\n2) Discard Leader\n3) End turn"));

        synchronized (client.getLock()) {
            while(!client.isReady()) client.getLock().wait();
            number=client.getNumber();
        }

        client.setReady(false);

        return number;
    }


    public void win(String nickname, int victoryPoints) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new Win("You had accumulated " + Constants.ANSI_BLUE + victoryPoints + Constants.ANSI_RESET + " victory points"));
    }


    public void lose(String nickname, int victoryPoints) {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new Lose("You had accumulated "+ Constants.ANSI_BLUE + victoryPoints + Constants.ANSI_RESET +" victory points"));
    }


    public void sendErrorMessage(String nickname){
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SendMessage("Invalid choice."));
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
            client.closeConnection();
        }
    }


    public void setNamesToClient(String nickname, ClientHandler client) {
        namesToClient.put(nickname,client);
    }


    public void removeNamesToClient(String nickname, ClientHandler client){
        namesToClient.remove(nickname, client);
    }


    public void sendTurnStatus(String message, String nickname){
        if(message.equals("START")){
            notifyPlayingNick(nickname);
        }

        ClientHandler client = namesToClient.get(nickname);

        client.send(new TurnStatus(message));
    }
}
