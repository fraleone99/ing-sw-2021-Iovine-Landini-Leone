package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.server.answer.Answer;
import it.polimi.ingsw.server.answer.Pong;
import it.polimi.ingsw.server.answer.finalanswer.Lose;
import it.polimi.ingsw.server.answer.finalanswer.Win;
import it.polimi.ingsw.server.answer.infoanswer.*;
import it.polimi.ingsw.server.answer.initialanswer.Connection;
import it.polimi.ingsw.server.answer.initialanswer.InitialSetup;
import it.polimi.ingsw.server.answer.request.RequestDoubleInt;
import it.polimi.ingsw.server.answer.request.RequestInt;
import it.polimi.ingsw.server.answer.request.RequestString;
import it.polimi.ingsw.server.answer.request.SendMessage;
import it.polimi.ingsw.server.answer.seegameboard.*;
import it.polimi.ingsw.server.answer.turnanswer.*;

import java.io.*;
import java.net.Socket;

/**
 * Handles network connection on the Client side
 *
 * @author Francesco Leone
 */
public class NetworkHandler implements Runnable, Handler {
    private final Socket server;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    private final View view;

    private boolean isConnected;

    private static final int CONNECTION_TIMEOUT = 10000;


    public NetworkHandler(Socket server, Client owner ,View view) {
        this.server = server;
        this.view = view;
        isConnected = true;
    }

    /**
     * This method initializes the timeout, the input stream and the output stream.
     * It also calls the handle client connection method.
     */
    @Override
    public void run() {
        try {
            server.setSoTimeout(CONNECTION_TIMEOUT);

            input = new ObjectInputStream(server.getInputStream());
            output = new ObjectOutputStream(server.getOutputStream());

        } catch (IOException e) {
            System.out.println("Could not open connection to the server");
        }

        try {
            handleClientConnection();
        } catch (IOException e) {
            closeConnection();
        }
    }

    /**
     * This method runs the heartbeat thread and reads the server requests.
     * @throws IOException called by readObject a network error happens.
     */
    public void handleClientConnection() throws IOException{
        HeartbeatClient heartbeatClient = new HeartbeatClient(this);
        Thread heartbeatThread = new Thread(heartbeatClient);
        heartbeatThread.start();

        while (isConnected) {
            try {

                Answer next = (Answer) input.readObject();
                if(!(next instanceof Pong))
                    processServerAnswer(next);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void send(Object message) {
        try {
            output.writeObject(message);
            output.flush();
        }
        catch (IOException e){
            closeConnection();
            System.exit(1);
        }
    }


    /**
     * This method manages the server requests.
     * @param inputObj is the message sent by the server
     */
    public void processServerAnswer(Object inputObj){
        if(inputObj instanceof Connection){
            if(((Connection) inputObj).isConnected()){
                view.handShake(((Connection) inputObj).getMessage());
            }
        }
        else if(inputObj instanceof RequestString) {
            view.askNickname(((RequestString) inputObj).getMessage());
        }
        else if(inputObj instanceof RequestInt) {
            switch(((RequestInt) inputObj).getType()) {
                case "NUMBER" : view.askPlayerNumber(((RequestInt) inputObj).getMessage());
                                break;
                case "RESOURCE" : view.askResource(((RequestInt) inputObj).getMessage());
                                  break;
                case "GAMEBOARD" : view.seeGameBoard(((RequestInt) inputObj).getMessage());
                                      break;
                case "LINE" : view.chooseLine(((RequestInt) inputObj).getMessage());
                              break;
                case "TURN" : view.askTurnType(((RequestInt) inputObj).getMessage());
                              break;
                case "STORAGE" : view.ManageStorage(((RequestInt) inputObj).getMessage());
                                 break;
                case "MARKET" : view.useMarket(((RequestInt) inputObj).getMessage());
                                break;
                case "WHITE" : view.chooseWhiteBallLeader(((RequestInt) inputObj).getMessage());
                               break;
                case "SPACE" : view.askSpace(((RequestInt) inputObj).getMessage());
                               break;
                case "TYPE" : view.askType(((RequestInt) inputObj).getMessage());
                              break;
                case "INPUT" : view.askInput(((RequestInt) inputObj).getMessage());
                               break;
                case "OUTPUT" : view.askOutput(((RequestInt) inputObj).getMessage());
                                break;
                case "DEVCARD" : view.askDevelopmentCard(((RequestInt) inputObj).getMessage());
                                 break;
                case "LEADCARD" : view.askLeaderCard(((RequestInt) inputObj).getMessage());
                                  break;
                case "CHOICE" : view.seeMoreFromTheGameBoard();
                                break;
                case "SHELF" : view.chooseShelf();
                               break;
                case "END" : view.endTurn(((RequestInt) inputObj).getMessage());
                             break;
            }
        }
        else if(inputObj instanceof SendMessage) {
            view.readMessage(((SendMessage) inputObj).getMessage());
        }
        else if(inputObj instanceof OtherLeaderCard) {
            if(((OtherLeaderCard) inputObj).getAction().equals("ACTIVE")) view.activeOtherLeaderCard((OtherLeaderCard) inputObj);
            else view.discardOtherLeaderCard((OtherLeaderCard)inputObj);
        }
        else if (inputObj instanceof InitializeGameBoard) {
            view.initializeGameBoard((InitializeGameBoard)inputObj);
        }
        else if(inputObj instanceof Win) {
            view.win(((Win) inputObj).getMessage());
        }
        else if(inputObj instanceof Lose) {
            view.lose(((Lose) inputObj).getMessage());
        }
        else if(inputObj instanceof BasicProductionInfo) {
            view.updateBasicProduction((BasicProductionInfo)inputObj);
        }
        else if (inputObj instanceof FaithPathInfo) {
            view.printFaithPath((FaithPathInfo) inputObj);
        }
        else if (inputObj instanceof SeeOtherCards) {
            view.seeOtherCards(((SeeOtherCards) inputObj).getMessage());
        }
        else if (inputObj instanceof CardsSpaceInfo) {
            view.updateDevCardsSpace((CardsSpaceInfo) inputObj);
        }
        else if (inputObj instanceof GridInfo) {
            view.updateGrid(((GridInfo) inputObj).getMessage());
        }
        else if (inputObj instanceof StorageInfo) {
            if(((StorageInfo) inputObj).getCoinsAmount()==-1) {
                view.printStorage((StorageInfo) inputObj);
            } else {
                view.printStorageAndVault((StorageInfo) inputObj);
            }
        }
        else if (inputObj instanceof DevCardsSpaceInfo) {
            view.printDevelopmentCardsSpace((DevCardsSpaceInfo) inputObj);
        }
        else if (inputObj instanceof ResetCard) {
            view.resetCard(((ResetCard) inputObj).getPos());
        }
        else if (inputObj instanceof RequestDoubleInt) {
            if(((RequestDoubleInt) inputObj).getType().equals("DEVCARD")) {
                view.askCardToBuy(((RequestDoubleInt) inputObj).getCards(), ((RequestDoubleInt) inputObj).getSpaces());
            } else {
                view.MoveShelves(((RequestDoubleInt) inputObj).getMessage());
            }
        }
        else if (inputObj instanceof SeeBall) {
            view.seeBall((SeeBall) inputObj);
        }
        else if (inputObj instanceof ActionTokenInfo) {
            view.printActionToken(((ActionTokenInfo) inputObj).getMessage());
        }
        else if (inputObj instanceof TurnStatus) {
            if (((TurnStatus) inputObj).getMessage().equals("END")) {
                view.setIsMyTurn(false);
                view.waitForYourTurn();
            } else if (((TurnStatus) inputObj).getMessage().equals("START")) {
                view.setIsMyTurn(true);
            }
        }
        else if (inputObj instanceof InitialSetup) {
            view.waitForYourTurn();
        }
        else if (inputObj instanceof PlayersInfo){
            view.playersInfo((PlayersInfo) inputObj);
        }
        else if(inputObj instanceof ErrorMessage){
            view.errorHandling((String) ((ErrorMessage) inputObj).getMessage());
        }
        else if(inputObj instanceof MarketInfo){
            view.updateMarket((Market) ((MarketInfo) inputObj).getMessage());
        }
        else if (inputObj instanceof UpdateFaithPath){
            view.updateFaithPath((UpdateFaithPath) inputObj);
        }
        else if (inputObj instanceof UpdatePapalPawn){
            view.updatePapalPawn((UpdatePapalPawn) inputObj);
        }
        else {
            if (inputObj instanceof PassLeaderCard) {
                view.askLeaderToDiscard(((PassLeaderCard) inputObj).getMessage());
            } else if (inputObj instanceof SeeLeaderCards) {
                view.seeLeaderCards(((SeeLeaderCards) inputObj).getMessage());
            } else if (inputObj instanceof SeeMarket) {
                view.seeMarket(((SeeMarket) inputObj).getMessage());
            } else if (inputObj instanceof ActiveLeader) {
                view.activeLeader(((ActiveLeader) inputObj));
            } else if (inputObj instanceof DiscardLeader) {
                view.discardLeader(((DiscardLeader) inputObj));
            } else if (inputObj instanceof SeeGrid) {
                view.seeGrid(((SeeGrid) inputObj).getMessage());
            } else if (inputObj instanceof SeeProductions) {
                view.seeProductions(((SeeProductions) inputObj).getMessage());
            }
        }
    }

    public boolean isConnected() {
        return isConnected;
    }


    /**
     * This method closes client connection with the server
     */
    public synchronized void closeConnection(){
        System.out.println("Closing connection!");
        isConnected = false;
        try {

            input.close();
            output.close();
            server.close();
            System.exit(1);


        } catch (IOException ignored) {
        }
    }
}
