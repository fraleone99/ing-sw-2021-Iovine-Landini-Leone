package it.polimi.ingsw.server;

import it.polimi.ingsw.server.answer.Pong;

import java.io.IOException;

public class Heartbeat implements Runnable{
    ClientHandler clientHandler;

    public Heartbeat(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

    }

    @Override
    public void run() {

    }
}
