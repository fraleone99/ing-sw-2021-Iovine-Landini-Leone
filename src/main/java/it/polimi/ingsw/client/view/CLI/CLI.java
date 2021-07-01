package it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.Constants;
import it.polimi.ingsw.client.Handler;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.server.answer.infoanswer.*;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.server.answer.seegameboard.InitializeGameBoard;
import it.polimi.ingsw.server.answer.seegameboard.UpdateFaithPath;
import it.polimi.ingsw.server.answer.seegameboard.UpdatePapalPawn;
import it.polimi.ingsw.server.answer.turnanswer.ActiveLeader;
import it.polimi.ingsw.server.answer.turnanswer.DiscardLeader;
import it.polimi.ingsw.server.answer.seegameboard.SeeBall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class CLI implements View {
    private final CLILogin login;
    private final CLIGameBoard gameBoard;
    private final CLITurn turn;
    private final CLIPrint print;


    private final AtomicBoolean isMyTurn = new AtomicBoolean(false);


    public CLI() {
        CLIInitialize initialize = new CLIInitialize();
        print = new CLIPrint(initialize);
        login = new CLILogin(initialize);
        gameBoard = new CLIGameBoard(initialize, print);
        turn = new CLITurn(initialize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHandler(Handler handler) {
        turn.setHandler(handler);
        login.setHandler(handler);
        gameBoard.setHandler(handler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void waitForYourTurn(){
        new Thread(()->{

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try
            {  while (!isMyTurn.get()) {
                if (br.ready()) {
                    System.out.print("It is not your turn please wait!\n");
                    br.readLine();
                } else {
                    Thread.sleep(200);
                }
            }
            }
            catch (IOException | InterruptedException ignored){
            }

        }).start();
    }

    /**
     * unused in CLI
     */
    @Override
    public void initializeGameBoard(InitializeGameBoard message) {

    }

    /**
     * unused in CLI
     */
    @Override
    public void playersInfo(PlayersInfo playersInfo) {

    }

    /**
     * unused in CLI
     */
    @Override
    public void updateDevCardsSpace(CardsSpaceInfo info) {

    }

    /**
     * unused in CLI
     */
    @Override
    public void activeOtherLeaderCard(OtherLeaderCard info) {

    }

    /**
     * unused in CLI
     */
    @Override
    public void discardOtherLeaderCard(OtherLeaderCard info) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void errorHandling(String errorType) {
        System.out.println("Invalid input! Please try again");
    }

    /**
     * unused in CLI
     */
    @Override
    public void updateGrid(ArrayList<Integer> idCards) {

    }

    /**
     * unused in CLI
     */
    @Override
    public void updateBasicProduction(BasicProductionInfo info) {

    }

    /**
     * unused in CLI
     */
    @Override
    public void updateMarket(Market market) {

    }

    /**
     * unused in CLI
     */
    @Override
    public void setDevCardsSpace(ArrayList<DevelopmentCardDeck> spaces, String owner) {

    }

    /**
     * unused in CLI
     */
    @Override
    public void updateFaithPath(UpdateFaithPath updateFaithPath){

    }

    /**
     * unused in CLI
     */
    @Override
    public void updatePapalPawn(UpdatePapalPawn updatePapalPawn){

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsMyTurn(boolean isMyTurn) {
        this.isMyTurn.set(isMyTurn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupConnection() {
        login.setupConnection();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIp() {
        return login.getIp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPortNumber() {
        return login.getPortNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int gameType() {
        return login.gameType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handShake(String welcome) {
        login.Handshake(welcome);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void askPlayerNumber(String message) {
        login.askPlayerNumber(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void askNickname(String message) {
        login.askNickname(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void askResource(String message) {
        login.askResource(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void readMessage(String message) {
        if(message.endsWith(" is playing") || message.endsWith("is back.")){
            System.out.println(Constants.PLAYING_EDGE+Constants.ANSI_GREEN+message+Constants.ANSI_RESET);
        }
        else if(message.endsWith("players in this Lobby.")){
            System.out.println(Constants.ANSI_RED+message+Constants.ANSI_RESET);
        }
        else {
            System.out.println(message);
        }

        if(message.equals("END_GAME")) {
            System.exit(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void askLeaderToDiscard(ArrayList<Integer> IdLeaders) {
        login.askLeaderToDiscard(IdLeaders);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void seeGameBoard(String message) {
        gameBoard.seeGameBoard(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void seeLeaderCards(ArrayList<Integer> leaderCards) {
        gameBoard.seeLeaderCards(leaderCards);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void seeMarket(Market market) {
        gameBoard.seeMarket(market);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chooseLine(String message) {
        gameBoard.chooseLine(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void seeGrid(ArrayList<Integer> devCards) {
        gameBoard.seeGrid(devCards);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void seeProductions(ArrayList<Integer> productions) {
        gameBoard.seeProductions(productions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void seeOtherCards(ArrayList<Integer> cards) {
        gameBoard.seeOtherCards(cards);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void seeMoreFromTheGameBoard() {
        gameBoard.choice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void askTurnType(String message) {
        turn.askTurnType(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activeLeader(ActiveLeader message) {
        turn.activeLeader(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void discardLeader(DiscardLeader message) {
        turn.discardLeader(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetCard(int pos) {
        turn.resetCard(pos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void ManageStorage(String message) {
        turn.ManageStorage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void MoveShelves(String message) {
        turn.MoveShelves(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void useMarket(String message) {
        turn.useMarket(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chooseWhiteBallLeader(String message) {
        turn.chooseWhiteBallLeader(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void seeBall(SeeBall ball) {
        turn.seeBall(ball);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chooseShelf() {
        turn.chooseShelf();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void askCardToBuy(ArrayList<Integer> cards, ArrayList<Integer> spaces) {
        turn.askCardToBuy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void askSpace(String message) {
        turn.askSpace(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void askType(String message) {
        turn.askType(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void askInput(String message) {
        turn.askInput(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void askOutput(String message) {
        turn.askOutput(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void askDevelopmentCard(String message) {
        turn.askDevelopmentCard(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void askLeaderCard(String message) {
        turn.askLeaderCard(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endTurn(String message) {
        turn.endTurn(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printStorage(StorageInfo storageInfo) {
        print.printStorage(storageInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printStorageAndVault(StorageInfo storageInfo) {
        print.printStorageAndVault(storageInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printDevelopmentCardsSpace(DevCardsSpaceInfo devCardsSpaceInfo) {
        print.printDevelopmentCardsSpace(devCardsSpaceInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printFaithPath(FaithPathInfo path) {
        print.printFaithPath(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printActionToken(ActionToken actionToken) {
        print.printActionToken(actionToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void win(String message) {
        System.out.println(Constants.WIN + "\n" + Constants.ANSI_WHITE + message + Constants.ANSI_RESET + "\n" + Constants.THANKS_FOR_PLAYING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lose(String message) {
        System.out.println(Constants.LOSE + "\n" + Constants.ANSI_WHITE + message + Constants.ANSI_RESET + "\n" + Constants.THANKS_FOR_PLAYING);
    }

}
