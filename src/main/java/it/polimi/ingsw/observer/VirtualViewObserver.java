package it.polimi.ingsw.observer;

//this interface is implemented by Lobby

/**
 * VirtualViewObserver is an interface used by Lobby class, to receive VirtualViewObservable notifications
 * @author Nicola Landini
 */
public interface VirtualViewObserver {

    void updatePreparationOfLobby();

    void updatePlayingNick(String nickname);

    void updateTurnChoice(String nickname, String message);

}
