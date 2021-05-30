package it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.Constants;
import it.polimi.ingsw.client.Handler;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.server.answer.infoanswer.DevCardsSpaceInfo;
import it.polimi.ingsw.server.answer.infoanswer.FaithPathInfo;
import it.polimi.ingsw.server.answer.infoanswer.StorageInfo;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.server.answer.turnanswer.ActiveLeader;
import it.polimi.ingsw.server.answer.turnanswer.DiscardLeader;
import it.polimi.ingsw.server.answer.seegameboard.SeeBall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CLI implements View {
    private final CLIInitialize initialize;
    private final CLILogin login;
    private final CLIGameBoard gameBoard;
    private final CLITurn turn;
    private final CLIPrint print;


    private AtomicBoolean isMyTurn = new AtomicBoolean(false);


    public CLI() {
        initialize = new CLIInitialize();
        print = new CLIPrint(initialize);
        login = new CLILogin(initialize);
        gameBoard = new CLIGameBoard(initialize, print);
        turn = new CLITurn(initialize);
    }

    public void setHandler(Handler handler) {
        turn.setHandler(handler);
        login.setHandler(handler);
        gameBoard.setHandler(handler);
    }

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

    @Override
    public void setIsMyTurn(boolean isMyTurn) {
        this.isMyTurn.set(isMyTurn);
    }

    @Override
    public void setupConnection() {
        login.setupConnection();
    }

    @Override
    public String getIp() {
        return login.getIp();
    }

    @Override
    public int getPortNumber() {
        return login.getPortNumber();
    }

    @Override
    public int gameType() {
        return login.gameType();
    }

    @Override
    public void handShake(String welcome) {
        login.Handshake(welcome);
    }

    @Override
    public void askPlayerNumber(String message) {
        login.askPlayerNumber(message);
    }

    @Override
    public void askNickname(String message) {
        login.askNickname(message);
    }

    @Override
    public void askResource(String message) {
        login.askResource(message);
    }

    @Override
    public void readMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void askLeaderToDiscard(ArrayList<Integer> IdLeaders) {
        login.askLeaderToDiscard(IdLeaders);
    }

    @Override
    public void seeGameBoard(String message) {
        gameBoard.seeGameBoard(message);
    }

    @Override
    public void seeLeaderCards(ArrayList<Integer> leaderCards) {
        gameBoard.seeLeaderCards(leaderCards);
    }

    @Override
    public void seeMarket(Market market) {
        gameBoard.seeMarket(market);
    }

    @Override
    public void chooseLine(String message) {
        gameBoard.chooseLine(message);
    }

    @Override
    public void seeGrid(ArrayList<Integer> devCards) {
        gameBoard.seeGrid(devCards);
    }

    @Override
    public void seeProductions(ArrayList<Integer> productions) {
        gameBoard.seeProductions(productions);
    }

    @Override
    public void seeOtherCards(ArrayList<Integer> cards) {
        gameBoard.seeOtherCards(cards);
    }

    @Override
    public void seeMoreFromTheGameBoard() {
        gameBoard.choice();
    }

    @Override
    public void askTurnType(String message) {
        turn.askTurnType(message);
    }

    @Override
    public void activeLeader(ActiveLeader message) {
        turn.activeLeader(message);
    }

    @Override
    public void discardLeader(DiscardLeader message) {
        turn.discardLeader(message);
    }

    @Override
    public void resetCard(int pos) {
        turn.resetCard(pos);
    }

    @Override
    public void ManageStorage(String message) {
        turn.ManageStorage(message);
    }

    @Override
    public void MoveShelves(String message) {
        turn.MoveShelves(message);
    }

    @Override
    public void useMarket(String message) {
        turn.useMarket(message);
    }

    @Override
    public void chooseWhiteBallLeader(String message) {
        turn.chooseWhiteBallLeader(message);
    }

    @Override
    public void seeBall(SeeBall ball) {
        turn.seeBall(ball);
    }

    @Override
    public void chooseShelf() {
        turn.chooseShelf();
    }

    @Override
    public void askColor(String message) {
        turn.askColor(message);
    }

    @Override
    public void askLevel(String message) {
        turn.askLevel(message);
    }

    @Override
    public void askSpace(String message) {
        turn.askSpace(message);
    }

    @Override
    public void askType(String message) {
        turn.askType(message);
    }

    @Override
    public void askInput(String message) {
        turn.askInput(message);
    }

    @Override
    public void askOutput(String message) {
        turn.askOutput(message);
    }

    @Override
    public void askDevelopmentCard(String message) {
        turn.askDevelopmentCard(message);
    }

    @Override
    public void askLeaderCard(String message) {
        turn.askLeaderCard(message);
    }

    @Override
    public void endTurn(String message) {
        turn.endTurn(message);
    }

    @Override
    public void printStorage(StorageInfo storageInfo) {
        print.printStorage(storageInfo);
    }

    @Override
    public void printStorageAndVault(StorageInfo storageInfo) {
        print.printStorageAndVault(storageInfo);
    }

    @Override
    public void printDevelopmentCardsSpace(DevCardsSpaceInfo devCardsSpaceInfo) {
        print.printDevelopmentCardsSpace(devCardsSpaceInfo);
    }

    @Override
    public void printFaithPath(FaithPathInfo path) {
        print.printFaithPath(path);
    }

    @Override
    public void printActionToken(ActionToken actionToken) {
        print.printActionToken(actionToken);
    }

    @Override
    public void win(String message) {
        System.out.println(Constants.WIN + "\n" + Constants.ANSI_WHITE + message + Constants.ANSI_RESET + "\n" + Constants.THANKS_FOR_PLAYING);
    }

    @Override
    public void lose(String message) {
        System.out.println(Constants.LOSE + "\n" + Constants.ANSI_WHITE + message + Constants.ANSI_RESET + "\n" + Constants.THANKS_FOR_PLAYING);
    }

    public CLIInitialize getInitialize() {
        return initialize;
    }

    public CLIPrint getPrint() {
        return print;
    }
}
