package it.polimi.ingsw.client.view;

import java.util.ArrayList;

public interface View {

    void handShake(String welcome);

    //Method for Players' input
    String askPlayerNumber(String message);

    String askNickname(String message);

    void readMessage(String message);

    int askResource(String message);

    int askLeaderToDiscard(ArrayList<Integer> IdLeaders);
}
