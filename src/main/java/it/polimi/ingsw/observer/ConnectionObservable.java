package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.ClientHandler;

import java.util.ArrayList;
import java.util.List;

//this class is used by ClientHandler

/**
 * ConnectionObservable is used to notify player in case of disconnection
 * @author Nicola Landini
 */
public class ConnectionObservable {

    private final List<ConnectionObserver> observersList=new ArrayList<>();

    /**
     * this method is used to add a new ConnectionObserver
     * @param obs is the observer that we want to register
     */
    public void registerObserver(ConnectionObserver obs){
        synchronized (observersList){
            observersList.add(obs);
        }
    }

    /**
     * this method is used to notify every object in observersList
     * @param clientHandler is the client that we want to notify
     */
    public void notifyDisconnection(ClientHandler clientHandler){
        synchronized (observersList){
            for(ConnectionObserver obs: observersList){
                obs.updateDisconnection(clientHandler);
            }
        }
    }
}
