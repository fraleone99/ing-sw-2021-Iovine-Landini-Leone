package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.ClientHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//this class is used by ClientHandler

public class ConnectionObservable {

    private List<ConnectionObserver> observersList=new ArrayList<>();

    /**
     * this method is used to add a new ConnectionObserver
     * @param obs
     */
    public void registerObserver(ConnectionObserver obs){
        synchronized (observersList){
            observersList.add(obs);
        }
    }

    /**
     * this method is used to remove a ConnectionObserver
     * @param obs
     */
    public void unregisterObserver(ConnectionObserver obs){
        synchronized (observersList){
            observersList.remove(obs);
        }
    }

    /**
     * this method is used to notify every object in observersList
     * @param clientHandler
     */
    //Every object in observersList is an instance of a class that implements ConnectionObserver interface
    public void notifyDisconnection(ClientHandler clientHandler) throws IOException, InterruptedException {
        synchronized (observersList){
            for(ConnectionObserver obs: observersList){
                obs.updateDisconnection(clientHandler);
            }
        }
    }
}
