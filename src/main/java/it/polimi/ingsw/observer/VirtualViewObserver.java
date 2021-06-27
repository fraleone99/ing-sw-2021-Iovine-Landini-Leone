package it.polimi.ingsw.observer;

import java.io.IOException;

//this interface is implemented by Lobby

public interface VirtualViewObserver {

    void updatePreparationOfLobby();

    void updatePlayingNick(String nickname);

    void updateTurnChoice(String nickname, String message);

}
