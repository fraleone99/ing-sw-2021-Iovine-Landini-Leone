package it.polimi.ingsw.server;

import it.polimi.ingsw.server.answer.PlayersNumber;
import it.polimi.ingsw.server.answer.RequestNickname;

import java.io.IOException;

public class VirtualView {
    private Object lock;
    private String nickname;

    public synchronized void requestPlayersNumber(ClientHandler client) throws IOException {
        client.send(new PlayersNumber("Please insert the number of players:"));
    }


    public String requestNickname(ClientHandler client) throws IOException, InterruptedException {
        client.send(new RequestNickname("Please insert your nickname:"));
        synchronized (lock){
            while(!client.isReady())
                wait();
            nickname=client.getNickname();
        }
        return nickname;
    }
}
