package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//this class is used by VirtualView

public class VirtualViewObservable {

    private final List<VirtualViewObserver> observersList=new ArrayList<>();

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

    public void notifyPlayingNick(String nick) {
        synchronized (observersList){
            for(VirtualViewObserver obs: observersList){
                obs.updatePlayingNick(nick);
            }
        }
    }

    public void notifyTurnChoice(String nick, String message){
        synchronized (observersList){
            for(VirtualViewObserver obs: observersList){
                obs.updateTurnChoice(nick, message);
            }
        }
    }
}
