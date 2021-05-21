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

import java.util.ArrayList;

public class CLI implements View {
    private final CLIInitialize initialize;
    private final CLILogin login;
    private final CLIGameBoard gameBoard;
    private final CLITurn turn;
    private final CLIPrint print;
    private Handler handler;

    public CLI() {
        initialize = new CLIInitialize();
        print = new CLIPrint(initialize);
        login = new CLILogin(initialize);
        gameBoard = new CLIGameBoard(initialize, print);
        turn = new CLITurn(initialize);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
        turn.setHandler(handler);
        login.setHandler(handler);
        gameBoard.setHandler(handler);
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

    public void askResource(String message) {
        login.askResource(message);
    }

    public void readMessage(String message) {
        System.out.println(message);
    }

    public void askLeaderToDiscard(ArrayList<Integer> IdLeaders) {
        login.askLeaderToDiscard(IdLeaders);
    }


    public void seeGameBoard(String message) {
        gameBoard.seeGameBoard(message);
    }

    public void seeLeaderCards(ArrayList<Integer> leaderCards) {
        gameBoard.seeLeaderCards(leaderCards);
    }

    public void seeMarket(Market market) {
        gameBoard.seeMarket(market);
    }

    public void chooseLine(String message) {
        gameBoard.chooseLine(message);
    }

    public void seeGrid(ArrayList<Integer> devCards) {
        gameBoard.seeGrid(devCards);
    }

    public void seeProductions(ArrayList<Integer> productions) {
        gameBoard.seeProductions(productions);
    }

    public void seeOtherCards(ArrayList<Integer> cards) {
        gameBoard.seeOtherCards(cards);
    }

    public void choice() {
        gameBoard.choice();
    }

    public void askTurnType(String message) {
        turn.askTurnType(message);
    }

    public void activeLeader(ActiveLeader message) {
        turn.activeLeader(message);
    }

    public void discardLeader(DiscardLeader message) {
        turn.discardLeader(message);
    }

    public void resetCard(int pos) {
        turn.resetCard(pos);
    }

    public void ManageStorage(String message) {
        turn.ManageStorage(message);
    }

    public void MoveShelves(String message) {
        turn.MoveShelves(message);
    }

    public void useMarket(String message) {
        turn.useMarket(message);
    }

    public void chooseWhiteBallLeader(String message) {
        turn.chooseWhiteBallLeader(message);
    }

    public void seeBall(SeeBall ball) {
        turn.seeBall(ball);
    }

    public void chooseShelf() {
        turn.chooseShelf();
    }

    public void askColor(String message) {
        turn.askColor(message);
    }

    public void askLevel(String message) {
        turn.askLevel(message);
    }

    public void askSpace(String message) {
        turn.askSpace(message);
    }

    public void askType(String message) {
        turn.askType(message);
    }

    public void askInput(String message) {
        turn.askInput(message);
    }

    public void askOutput(String message) {
        turn.askOutput(message);
    }

    public void askDevelopmentCard(String message) {
        turn.askDevelopmentCard(message);
    }

    public void askLeaderCard(String message) {
        turn.askLeaderCard(message);
    }

    public void endTurn(String message) {
        turn.endTurn(message);

    }

    public void printStorage(StorageInfo storageInfo) {
        print.printStorage(storageInfo);
    }

    public void printStorageAndVault(StorageInfo storageInfo) {
        print.printStorageAndVault(storageInfo);
    }

    public void printDevelopmentCardsSpace(DevCardsSpaceInfo devCardsSpaceInfo) {
        print.printDevelopmentCardsSpace(devCardsSpaceInfo);
    }

    public void printFaithPath(FaithPathInfo path) {
        print.printFaithPath(path);
    }

    public void printActionToken(ActionToken actionToken) {
        print.printActionToken(actionToken);
    }

    public void win(String message) {
        System.out.println(Constants.WIN + "\n" + Constants.ANSI_WHITE + message + Constants.ANSI_RESET + "\n" + Constants.THANKS_FOR_PLAYING);
    }

    public void lose(String message) {
        System.out.println(Constants.LOSE + "\n" + Constants.ANSI_WHITE + message + Constants.ANSI_RESET + "\n" + Constants.THANKS_FOR_PLAYING);
    }
}
