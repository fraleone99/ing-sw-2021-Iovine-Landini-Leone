package it.polimi.ingsw.server;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.gameboard.playerdashboard.Market;
import it.polimi.ingsw.observer.VirtualViewObservable;
import it.polimi.ingsw.server.answer.*;
import it.polimi.ingsw.server.answer.initialanswer.*;
import it.polimi.ingsw.server.answer.turnanswer.ActiveLeader;
import it.polimi.ingsw.server.answer.turnanswer.ChooseTurn;
import it.polimi.ingsw.server.answer.turnanswer.SeeLeaderCards;
import it.polimi.ingsw.server.answer.turnanswer.SeeMarket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VirtualView extends VirtualViewObservable {
    private String answer;
    private Map<String, ClientHandler> namesToClient = new HashMap<>();

    public String askHandShake(ClientHandler client) throws IOException, InterruptedException {
        client.send(new Connection("Welcome to this fantastic server!", true));
        synchronized (client) {
            while (!client.isReady()) client.wait();
            answer = client.getAnswer();
        }
        client.setReady(false);
        return answer;
    }

    public String requestNickname(ClientHandler client) throws IOException, InterruptedException {
        client.send(new RequestNickname("Please insert your nickname:"));
        synchronized (client) {
            while(!client.isReady()) client.wait();
            answer = client.getAnswer();
        }
        client.setReady(false);
        return answer;
        //notify everyone who joined the lobby
    }


    public String InvalidNickname(ClientHandler client) throws InterruptedException, IOException {
        client.send(new InvalidNickname("The chosen nickname is not valid. Try again:"));
        synchronized (client) {
            while(!client.isReady()) client.wait();
            answer = client.getAnswer();
        }
        client.setReady(false);
        return answer;
    }


    public String requestPlayersNumber(ClientHandler client) throws IOException, InterruptedException {
        client.send(new PlayersNumber("Please insert the number of players:"));
        synchronized (client) {
            while (!client.isReady()) client.wait();
            answer = client.getAnswer();
        }
        client.setReady(false);
        return answer;
        //notify everyone the players number
    }

    public void prepareTheLobby(ClientHandler client) throws IOException {
        //client.send(new PrepareTheLobby("All players joined the lobby. We are preparing the game!"));





        notifyPreparationOfLobby();
        //notify everyone
    }


    public void firstPlayer(String nickname) throws IOException {
        namesToClient.get(nickname).send(new FirstPlayer("You are the first player."));
    }


    public int chooseResource(String nickname, String player, int amount) throws IOException, InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        if(player.equals("fourth") && amount==2){
            client.send(new ChooseResource("Please choose another resource."));
        } else {
            client.send(new ChooseResource("You are the " + player + " player. Please choose a resource."));
        }

        synchronized (client){
            while(!client.isReady()) client.wait();
            answer=client.getAnswer();
        }

        client.setReady(false);

        return Integer.parseInt(answer);
    }


    public void waitingRoom(ClientHandler client) throws IOException {
        client.send(new WaitingRoom("You are now in the waiting room. The game will start soon!"));
    }


    public int discardFirstLeaders(String nickname, int number, ArrayList<Integer> IdLeader) throws IOException, InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        if(number==1) {
            client.send(new DiscardFirstLeaders("Please choose the first leader card to discard."));
        } else {
            client.send(new DiscardFirstLeaders("Please choose the second leader card to discard."));
        }
        client.send(new PassLeaderCard(IdLeader));
        synchronized (client) {
            while (!client.isReady()) client.wait();
            answer = client.getAnswer();
        }

        client.setReady(false);

        return Integer.parseInt(answer);
    }


    public void startGame(String nickname) throws IOException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new StartGame("The game start!"));
    }


    public int seeGameBoard(String nickname) throws IOException, InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SeeGameBoard("What do you want to see about the Game Board?"));
        synchronized (client) {
            while(!client.isReady()) client.wait();
            answer=client.getAnswer();
        }

        client.setReady(false);

        return Integer.parseInt(answer);
    }


    public int seeLeaderCards(String nickname, ArrayList<Integer> leaderCards) throws IOException, InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SeeLeaderCards(leaderCards));
        synchronized (client) {
            while(!client.isReady()) client.wait();
            answer=client.getAnswer();
        }

        client.setReady(false);

        return Integer.parseInt(answer);
    }

    public int seeMarket(String nickname, Market market) throws InterruptedException, IOException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new SeeMarket(market));
        synchronized (client) {
            while(!client.isReady()) client.wait();
            answer=client.getAnswer();
        }

        client.setReady(false);

        return Integer.parseInt(answer);
    }


    public int chooseTurn(String nickname) throws IOException, InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new ChooseTurn("Choose what you want to do in this turn:"));
        synchronized (client) {
            while(!client.isReady()) client.wait();
            answer=client.getAnswer();
        }

        client.setReady(false);

        return Integer.parseInt(answer);
    }

    public int activeLeader(String nickname) throws IOException, InterruptedException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new ActiveLeader("Which leader do you want to activate?"));
        synchronized (client) {
            while(!client.isReady()) client.wait();
            answer=client.getAnswer();
        }

        client.setReady(false);

        return Integer.parseInt(answer);
    }

    public void sendErrorMessage(String nickname) throws IOException {
        ClientHandler client=namesToClient.get(nickname);

        client.send(new GameError("Invalid choice."));
    }


    public void setNamesToClient(String nickname, ClientHandler client) {
        namesToClient.put(nickname,client);
    }

    public void removeNamesToClient(String nickname, ClientHandler client){
        namesToClient.remove(nickname, client);
    }
}
