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

    /**
     * Asks the player to choose a line from the market
     * @param message Server message
     */
    void chooseLine(String message);

    /**
     * Sends the player the Development Cards Grid
     * @param devCards contains the IDs of the Development Cards
     */
    void seeGrid(ArrayList<Integer> devCards);

    /**
     * Sends the player info about the production
     * @param productions message that contains info about the productions
     */
    void seeProductions(ArrayList<Integer> productions);

    /**
     * Sends the player info about the faith path
     * @param path message that contains info about the faith path
     */
    void printFaithPath(FaithPathInfo path);

    /**
     * Sends to the player the storage
     * @param storageInfo message that contains info about the storage
     */
    void printStorage(StorageInfo storageInfo);

    /**
     * Sends to the player storage and vault
     * @param storageInfo message that contains info about storage and vault
     */
    void printStorageAndVault(StorageInfo storageInfo);

    /**
     * Sends to the player the development cards
     * @param devCardsSpaceInfo contains the development cards
     */
    void printDevelopmentCardsSpace(DevCardsSpaceInfo devCardsSpaceInfo);

    /**
     * Sends to the player the action token used by LORENZO (singlePlayer)
     * @param actionToken the action token used
     */
    void printActionToken(ActionToken actionToken);

    /**
     * Asks the player if he wants to reorganize the storage
     * @param message server message
     */
    void  ManageStorage(String message);

    /**
     * Asks the player which shelves he wants to move
     * @param message server message
     */
    void MoveShelves(String message);

    /**
     * resets the card
     * @param pos the position to reset
     */
    void resetCard(int pos);

    /**
     * make the player use the market in his turn
     * @param message server message
     */
    void useMarket(String message);

    /**
     * Asks the player which white Ball leader he wants to use if he has two white bal leader activated
     * @param message server message
     */
    void chooseWhiteBallLeader(String message);

    /**
     * Sends the player the selected ball from the market
     * @param ball the selected ball
     */
    void seeBall(SeeBall ball);

    /**
     * Asks the player on which shelf he wants to place the resources
     */
    void chooseShelf();

    /**
     * Asks the player which card he wants to buy
     * @param cards is the selected card
     * @param spaces is the space in which he want to put the card
     */
    void askCardToBuy(ArrayList<Integer> cards, ArrayList<Integer> spaces);

    /**
     * Asks the player to select a space of the Development cards space
     * @param message server message
     */
    void askSpace(String message);

    /**
     * Asks the player the type of resources
     * @param message server message
     */
    void askType(String message);

    /**
     * Asks player input of the basic production
     * @param message server message
     */
    void askInput(String message);

    /**
     * Asks player output of the basic production
     * @param message server message
     */
    void askOutput(String message);

    /**
     * Ask the player which development card he wants to use
     * @param message server message
     */
    void askDevelopmentCard(String message);

    /**
     * Asks the player which leader he wants to activate
     * @param message server question
     */
    void askLeaderCard(String message);

    /**
     * Sends a message to the player at the end of the turn
     * @param message end turn message
     */
    void endTurn(String message);

    /**
     * Sends a message to the player when the game has ended and he has won
     * @param message win message
     */
    void win(String message);

    /**
     * Sends a message to the player when the game has ended and he has lost
     * @param message lose message
     */
    void lose(String message);

    /**
     * Sends to the client the active cards of the others player
     * @param leaderCards contains the IDs of the leader cards of the other players
     */
    void seeOtherCards(ArrayList<Integer> leaderCards);

    /**
     * Asks the player if he wants to see moro from the game board
     */
    void seeMoreFromTheGameBoard();

    /**
     * Sends a message whit info about the turn status
     * @param isMyTurn is a boolean that indicates if it is the turn of the player
     */
    void setIsMyTurn(boolean isMyTurn);

    /**
     * Sends a message to the client when is turn is ended
     */
    void waitForYourTurn();

    /**
     * Sends a message to the client to set the game board in the gui
     * @param message Server message
     */
    void initializeGameBoard(InitializeGameBoard message);

    /**
     * Sends a message to the client at the beginning of the game to inform them about the numbers of player
     * and the nicknames
     * @param playersInfo is the message sent that contains info about the players
     */
    void playersInfo(PlayersInfo playersInfo);

    /**
     * Updates the faith paths
     * @param updateFaithPath contains information about the faith path and the owner
     */
    void updateFaithPath(UpdateFaithPath updateFaithPath);

    /**
     * Updates the development space cards of the player
     * @param info contains the information about the development space cards of the player
     */
    void updateDevCardsSpace(CardsSpaceInfo info);

    /**
     * Sends to the client information when another player actives a leader
     * @param info contains information about the activated leader
     */
    void activeOtherLeaderCard(OtherLeaderCard info);

    /**
     * Sends to the client information when another player discards a leader
     * @param info contains information about the discarded leader
     */
    void discardOtherLeaderCard(OtherLeaderCard info);

    /**
     * Sends a error message to the client
     * @param errorType is the type of error
     */
    void errorHandling(String errorType);

    /**
     * Updates the grid of the development cards
     * @param idCards contains the IDs of the development cards
     */
    void updateGrid(ArrayList<Integer> idCards);

    /**
     * Sets the information for the chosen ba
     * @param info contains info about the basic production
     */
    void updateBasicProduction(BasicProductionInfo info);

    /**
     * Sends the player the new configuration of the market
     * @param market is the new configuration of the market
     */
    void updateMarket(Market market);

    void setDevCardsSpace(ArrayList<DevelopmentCardDeck> spaces, String owner);

    /**
     * Sends a message for updating the papal pawn
     * @param updatePapalPawn contains the information of the papal pawn
     */
    void updatePapalPawn(UpdatePapalPawn updatePapalPawn);
}
