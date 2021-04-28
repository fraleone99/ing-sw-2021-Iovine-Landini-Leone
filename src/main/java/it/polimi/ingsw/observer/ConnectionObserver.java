package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.ClientHandler;

import java.io.IOException;

//this interface is implemented by Lobby

public interface ConnectionObserver {

    void updateDisconnection(ClientHandler clientHandler) throws IOException, InterruptedException;

}
