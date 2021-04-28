package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//this class is used by VirtualView

public class VirtualViewObservable {

    private List<VirtualViewObserver> observersList=new ArrayList<>();

    public void registerObserver(VirtualViewObserver observer){
        synchronized (observersList){
            observersList.add(observer);
        }
    }

    public void unregisterObserver(VirtualViewObserver observer){
        synchronized (observersList){
            observersList.remove(observer);
        }
    }

    public void notifyPreparationOfLobby() throws IOException {
        synchronized (observersList){
            for(VirtualViewObserver obs: observersList){
                obs.updatePreparationOfLobby();
            }
        }
    }
}
