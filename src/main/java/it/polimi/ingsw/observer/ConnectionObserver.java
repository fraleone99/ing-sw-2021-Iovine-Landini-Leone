package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.ClientHandler;

//this interface is implemented by Lobby

/**
 * ConnectionObserver is an interface used by Lobby class, to receive ConnectionObservable notifications
 * @author Nicola Landini
 */
public interface ConnectionObserver {

    void updateDisconnection(ClientHandler clientHandler);

}
