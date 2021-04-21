package it.polimi.ingsw.server;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.server.answer.Connection;
import it.polimi.ingsw.server.answer.InvalidNickname;
import it.polimi.ingsw.server.answer.PlayersNumber;
import it.polimi.ingsw.server.answer.RequestNickname;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VirtualView {
    private String answer;

    public String requestPlayersNumber(ClientHandler client) throws IOException, InterruptedException {
        client.send(new PlayersNumber("Please insert the number of players:"));
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


    public String askHandShake(ClientHandler client) throws IOException, InterruptedException {
        client.send(new Connection("Welcome to this fantastic server!", true));
        synchronized (client) {
            while (!client.isReady()) client.wait();
            answer = client.getAnswer();
        }
        client.setReady(false);
        return answer;
    }

}
