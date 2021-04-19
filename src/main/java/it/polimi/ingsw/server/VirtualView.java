package it.polimi.ingsw.server;

import it.polimi.ingsw.server.answer.Connection;
import it.polimi.ingsw.server.answer.PlayersNumber;
import it.polimi.ingsw.server.answer.RequestNickname;

import java.io.IOException;

public class VirtualView {
    private Object lock = new Object();
    private String nickname;

    public synchronized void requestPlayersNumber(ClientHandler client) throws IOException, InterruptedException {
        client.send(new PlayersNumber("Please insert the number of players:"));
    }


    public String requestNickname(ClientHandler client) throws IOException, InterruptedException {
        client.send(new RequestNickname("Please insert your nickname:"));
        synchronized (client) {
            while(!client.isReady()) client.wait();
            nickname = client.getNickname();
        }
        return nickname;
    }

    public void askHandShake(ClientHandler client) throws IOException, InterruptedException {
        client.send(new Connection("Welcome to this fantastic server!", true));
    }

    public Object getLock() {
        return lock;
    }
}
