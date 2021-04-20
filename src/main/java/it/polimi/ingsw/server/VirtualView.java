package it.polimi.ingsw.server;

import it.polimi.ingsw.server.answer.Connection;
import it.polimi.ingsw.server.answer.PlayersNumber;
import it.polimi.ingsw.server.answer.RequestNickname;

import java.io.IOException;

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
