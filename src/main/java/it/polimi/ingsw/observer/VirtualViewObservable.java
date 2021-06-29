package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;

//this class is used by VirtualView

/**
 * VirtualViewObservable is used to notify player from VirtualView
 * @author Nicola Landini
 */
public class VirtualViewObservable {

    private final List<VirtualViewObserver> observersList=new ArrayList<>();

    /**
     * This method is used to create another VirtualViewObserver
     * @param observer is the observer that we want to register
     */
    public void registerObserver(VirtualViewObserver observer){
        synchronized (observersList){
            observersList.add(observer);
        }
    }

    /**
     * This method notifies every registered observers the preparation of lobby
     */
    public void notifyPreparationOfLobby() {
        synchronized (observersList){
            for(VirtualViewObserver obs: observersList){
                obs.updatePreparationOfLobby();
            }
        }
    }

    /**
     * This method notifies every registered observers, which player is now playing
     */
    public void notifyPlayingNick(String nick) {
        synchronized (observersList){
            for(VirtualViewObserver obs: observersList){
                obs.updatePlayingNick(nick);
            }
        }
    }

    /**
     * This method notifies every registered observers, the current player action
     */
    public void notifyTurnChoice(String nick, String message){
        synchronized (observersList){
            for(VirtualViewObserver obs: observersList){
                obs.updateTurnChoice(nick, message);
            }
        }
    }
}
