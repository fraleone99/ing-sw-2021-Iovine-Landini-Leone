package it.polimi.ingsw.client;

import it.polimi.ingsw.client.message.SendDoubleInt;
import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.message.SendString;
import it.polimi.ingsw.client.view.CLI.CLI;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.answer.finalanswer.Lose;
import it.polimi.ingsw.server.answer.finalanswer.Win;
import it.polimi.ingsw.server.answer.initialanswer.InitialSetup;
import it.polimi.ingsw.server.answer.*;
import it.polimi.ingsw.server.answer.infoanswer.*;
import it.polimi.ingsw.server.answer.initialanswer.Connection;
import it.polimi.ingsw.server.answer.request.RequestDoubleInt;
import it.polimi.ingsw.server.answer.request.RequestInt;
import it.polimi.ingsw.server.answer.request.RequestString;
import it.polimi.ingsw.server.answer.request.SendMessage;
import it.polimi.ingsw.server.answer.seegameboard.*;
import it.polimi.ingsw.server.answer.turnanswer.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkHandler implements Runnable {
    private final Socket server;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    private Client owner;
    private final View view;

    private boolean isConnected;

    private AtomicBoolean isMyTurn = new AtomicBoolean(false);


    public NetworkHandler(Socket server, Client owner ,View view) {
        this.server = server;
        this.owner = owner;
        this.view = view;
        isConnected = true;
    }

    @Override
    public void run() {
        try {
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

    public void handleClientConnection() throws IOException{
        Heartbeat heartbeat = new Heartbeat(this);
        Thread heartbeatThread = new Thread(heartbeat);
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

    public void send(Object message) {
        /*if(!(message instanceof Ping))
            System.out.println("[DEBUG] sending message to server." + message.toString());
        else
            System.out.println(".\n");*/
        try {
            output.writeObject(message);
            output.flush();
        }
        catch (IOException e){
            closeConnection();
            System.exit(1);
        }
    }

    public void processServerAnswer(Object inputObj){
        String string;
        int choice=0;

        if(inputObj instanceof Connection){
            if(((Connection) inputObj).isConnection()){
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
                case "COLOR" : view.askColor(((RequestInt) inputObj).getMessage());
                               break;
                case "LEVEL" : view.askLevel(((RequestInt) inputObj).getMessage());
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
                case "CHOICE" : view.choice();
                                break;
                case "END" : view.endTurn(((RequestInt) inputObj).getMessage());
                             break;
            }
        }
        else if(inputObj instanceof SendMessage) {
            view.readMessage(((SendMessage) inputObj).getMessage());
        }
        else if(inputObj instanceof Win) {
            view.win(((Win) inputObj).getMessage());
        }
        else if(inputObj instanceof Lose) {
            view.lose(((Lose) inputObj).getMessage());
        }
        else if (inputObj instanceof FaithPathInfo) {
            view.printFaithPath((FaithPathInfo) inputObj);
        }
        else if (inputObj instanceof SeeOtherCards) {
            view.seeOtherCards(((SeeOtherCards) inputObj).getMessage());
        }
        else if (inputObj instanceof StorageInfo) {
            view.printStorage((StorageInfo) inputObj);
        }
        else if (inputObj instanceof DevCardsSpaceInfo) {
            view.printDevelopmentCardsSpace((DevCardsSpaceInfo) inputObj);
        }
        else if (inputObj instanceof ResetCard) {
            view.resetCard(((ResetCard) inputObj).getPos());
        }
        else if (inputObj instanceof RequestDoubleInt) {
            view.MoveShelves(((RequestDoubleInt) inputObj).getMessage());
        }
        else if (inputObj instanceof SeeBall) {
            view.seeBall((SeeBall) inputObj);
            view.chooseShelf();
        }
        else if (inputObj instanceof ActionTokenInfo) {
            view.printActionToken(((ActionTokenInfo) inputObj).getMessage());
        }
        else if (inputObj instanceof TurnStatus) {
            if (((TurnStatus) inputObj).getMessage().equals("END")) {
                isMyTurn.set(false);
                waitForYourTurn();
            } else if (((TurnStatus) inputObj).getMessage().equals("START")) {
                isMyTurn.set(true);
            }
        }
        else if (inputObj instanceof InitialSetup) {
            waitForYourTurn();
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
