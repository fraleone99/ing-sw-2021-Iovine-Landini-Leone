package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.NetworkHandler;

public interface View {

    void handShake(String welcome);

    //Method for Players' input
    String askPlayerNumber(String message);

    String askNickname(String message);


}
