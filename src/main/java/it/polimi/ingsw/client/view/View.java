package it.polimi.ingsw.client.view;

import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.server.answer.infoanswer.DevCardsSpaceInfo;
import it.polimi.ingsw.server.answer.infoanswer.FaithPathInfo;
import it.polimi.ingsw.server.answer.infoanswer.StorageInfo;
import it.polimi.ingsw.server.answer.turnanswer.ActiveLeader;
import it.polimi.ingsw.server.answer.turnanswer.DiscardLeader;
import it.polimi.ingsw.server.answer.turnanswer.SeeBall;

import java.util.ArrayList;

public interface View {

    void handShake(String welcome);

    //Method for Players' input
    int askPlayerNumber(String message);

    String askNickname(String message);

    void readMessage(String message);

    int askResource(String message);

    int askLeaderToDiscard(ArrayList<Integer> IdLeaders);

    int askTurnType(String message);

    int activeLeader(ActiveLeader message);

    int discardLeader(DiscardLeader message);

    int seeGameBoard(String message);

    int seeLeaderCards(ArrayList<Integer> leaderCards);

    int seeMarket(Market market);

    int chooseLine(String message);

    int seeGrid(ArrayList<Integer> devCards);

    int seeProductions(ArrayList<Integer> productions);

    void printFaithPath(FaithPathInfo path);

    void printStorage(StorageInfo storageInfo);

    void printDevelopmentCardsSpace(DevCardsSpaceInfo devCardsSpaceInfo);

    void printActionToken(ActionToken actionToken);

    int ManageStorage(String message);

    ArrayList<Integer> MoveShelves(String message);

    void resetCard(int pos);

    int useMarket(String message);

    int chooseWhiteBallLeader(String message);

    int seeBall(SeeBall ball);

    int chooseShelf();

    int askColor(String message);

    int askLevel(String message);

    int askSpace(String message);

    int askType(String message);

    int askInput(String message);

    int askOutput(String message);

    int askDevelopmentCard(String message);

    int askLeaderCard(String message);

    int endTurn(String message);
}
