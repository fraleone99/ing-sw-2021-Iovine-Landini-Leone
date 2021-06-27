package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.ClientHandler;

//this interface is implemented by Lobby

public interface ConnectionObserver {

    void updateDisconnection(ClientHandler clientHandler);

}
