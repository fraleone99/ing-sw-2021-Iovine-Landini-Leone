package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.Handler;
import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.server.answer.infoanswer.DevCardsSpaceInfo;
import it.polimi.ingsw.server.answer.infoanswer.FaithPathInfo;
import it.polimi.ingsw.server.answer.infoanswer.PlayersInfo;
import it.polimi.ingsw.server.answer.infoanswer.StorageInfo;
import it.polimi.ingsw.server.answer.turnanswer.ActiveLeader;
import it.polimi.ingsw.server.answer.turnanswer.DiscardLeader;
import it.polimi.ingsw.server.answer.seegameboard.SeeBall;

import java.util.ArrayList;

public interface View {

    void setHandler(Handler handler);

    void setupConnection();

    String getIp();

    int getPortNumber();

    int gameType();

    void handShake(String welcome);

    //Method for Players' input
    void askPlayerNumber(String message);

    void askNickname(String message);

    void readMessage(String message);

    void askResource(String message);

    void askLeaderToDiscard(ArrayList<Integer> IdLeaders);

    void askTurnType(String message);

    void activeLeader(ActiveLeader message);

    void discardLeader(DiscardLeader message);

    void seeGameBoard(String message);

    void seeLeaderCards(ArrayList<Integer> leaderCards);

    void seeMarket(Market market);

    void chooseLine(String message);

    void seeGrid(ArrayList<Integer> devCards);

    void seeProductions(ArrayList<Integer> productions);

    void printFaithPath(FaithPathInfo path);

    void printStorage(StorageInfo storageInfo);

    void printStorageAndVault(StorageInfo storageInfo);

    void printDevelopmentCardsSpace(DevCardsSpaceInfo devCardsSpaceInfo);

    void printActionToken(ActionToken actionToken);

    void ManageStorage(String message);

    void MoveShelves(String message);

    void resetCard(int pos);

    void useMarket(String message);

    void chooseWhiteBallLeader(String message);

    void seeBall(SeeBall ball);

    void chooseShelf();

    void askCardToBuy(ArrayList<Integer> cards, ArrayList<Integer> spaces);

    void askSpace(String message);

    void askType(String message);

    void askInput(String message);

    void askOutput(String message);

    void askDevelopmentCard(String message);

    void askLeaderCard(String message);

    void endTurn(String message);

    void win(String message);

    void lose(String message);

    void seeOtherCards(ArrayList<Integer> leaderCards);

    void seeMoreFromTheGameBoard();

    void setIsMyTurn(boolean isMyTurn);

    void waitForYourTurn();

    void initializeGameBoard(Market market, ArrayList<Integer> idCards, ArrayList<Integer> leaderCards);

    void playersInfo(PlayersInfo playersInfo);

    void errorHandling(String errorType);
}
