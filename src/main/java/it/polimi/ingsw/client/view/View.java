package it.polimi.ingsw.client.view;

import it.polimi.ingsw.model.gameboard.playerdashboard.Market;
import it.polimi.ingsw.server.answer.DevCardsSpaceInfo;
import it.polimi.ingsw.server.answer.FaithPathInfo;
import it.polimi.ingsw.server.answer.StorageInfo;

import java.util.ArrayList;

public interface View {

    void handShake(String welcome);

    //Method for Players' input
    String askPlayerNumber(String message);

    String askNickname(String message);

    void readMessage(String message);

    int askResource(String message);

    int askLeaderToDiscard(ArrayList<Integer> IdLeaders);

    int askTurnType(String message);

    int activeLeader(String message);

    int discardLeader(String message);

    int seeGameBoard(String message);

    int seeLeaderCards(ArrayList<Integer> leaderCards);

    int seeMarket(Market market);

    int chooseLine(String message);

    int seeGrid(ArrayList<Integer> devCards);

    int seeProductions(ArrayList<Integer> productions);

    void printFaithPath(FaithPathInfo path);

    void printStorage(StorageInfo storageInfo);

    void printDevelopmentCardsSpace(DevCardsSpaceInfo devCardsSpaceInfo);
}
