package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.Handler;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.server.answer.infoanswer.*;
import it.polimi.ingsw.server.answer.seegameboard.InitializeGameBoard;
import it.polimi.ingsw.server.answer.seegameboard.SeeBall;
import it.polimi.ingsw.server.answer.seegameboard.UpdateFaithPath;
import it.polimi.ingsw.server.answer.seegameboard.UpdatePapalPawn;
import it.polimi.ingsw.server.answer.turnanswer.ActiveLeader;
import it.polimi.ingsw.server.answer.turnanswer.DiscardLeader;

import java.util.ArrayList;

public interface View {

    /**
     * Sets the handler for single or multi player
     * @param handler handler that has to be set
     */
    void setHandler(Handler handler);

    /**
     * This method is used to set up the connection
     */
    void setupConnection();

    /**
     * Gets the IP
     * @return the IP
     */
    String getIp();

    /**
     * Gets the port number
     * @return the port
     */
    int getPortNumber();

    /**
     * Allows player to choose the game
     * @return player's choice
     */
    int gameType();

    /**
     * Welcomes the player
     * @param welcome welcome string
     */
    void handShake(String welcome);

    /**
     * This method asks the player number
     * @param message server question
     */
    void askPlayerNumber(String message);

    /**
     * This method asks the client his nickname
     * @param message server question
     */
    void askNickname(String message);

    /**
     * This method handles client notification messages
     * @param message notification
     */
    void readMessage(String message);

    /**
     * This method allows the player to choose his initial resource
     * @param message server question
     */
    void askResource(String message);

    /**
     * This method allows the player to discard two of the four initial leader cards
     * @param IdLeaders four initial leaders IDs
     */
    void askLeaderToDiscard(ArrayList<Integer> IdLeaders);

    /**
     * This method allows the player to choose his turn action
     * @param message server question
     */
    void askTurnType(String message);

    /**
     * This method allows the player to active a leader
     * @param message instance of ActiveLeader
     */
    void activeLeader(ActiveLeader message);

    /**
     * This method allows the player to discard a leader
     * @param message instance of DiscardLeader
     */
    void discardLeader(DiscardLeader message);

    /**
     * This method asks the player what he wants to see from the game board
     * @param message server question
     */
    void seeGameBoard(String message);

    /**
     * This method handles client request of watching his leader cards
     * @param leaderCards leader cards IDs
     */
    void seeLeaderCards(ArrayList<Integer> leaderCards);

    /**
     * This method handles client request of watching the market
     * @param market is the market
     */
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

    void initializeGameBoard(InitializeGameBoard message);

    void playersInfo(PlayersInfo playersInfo);

    void updateFaithPath(UpdateFaithPath updateFaithPath);

    void updateDevCardsSpace(CardsSpaceInfo info);

    void activeOtherLeaderCard(OtherLeaderCard info);

    void discardOtherLeaderCard(OtherLeaderCard info);

    void errorHandling(String errorType);

    void updateGrid(ArrayList<Integer> idCards);

    void updateBasicProduction(BasicProductionInfo info);

    void updateMarket(Market market);

    void setDevCardsSpace(ArrayList<DevelopmentCardDeck> spaces, String owner);

    void updatePapalPawn(UpdatePapalPawn updatePapalPawn);
}
