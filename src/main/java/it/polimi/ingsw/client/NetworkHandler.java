package it.polimi.ingsw.client;

import it.polimi.ingsw.client.message.Ping;
import it.polimi.ingsw.client.message.SendDoubleInt;
import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.message.SendString;
import it.polimi.ingsw.client.view.CLI.CLI;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.answer.InitialSetup;
import it.polimi.ingsw.server.answer.*;
import it.polimi.ingsw.server.answer.infoanswer.*;
import it.polimi.ingsw.server.answer.turnanswer.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkHandler implements Runnable {
    private final Socket server;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    private Heartbeat heartbeat;
    private Client owner;
    private final View view;

    private boolean isConnected;

    private AtomicBoolean isMyTurn = new AtomicBoolean(false);


    public NetworkHandler(Socket server, Client owner) {
        this.server = server;
        this.owner = owner;
        view = new CLI();
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
            e.printStackTrace();
        }
    }

    public void handleClientConnection() throws IOException{
        heartbeat = new Heartbeat(this);
        Thread heartbeatThread = new Thread(heartbeat);
        heartbeatThread.start();

        while (isConnected) {
            try {
                Object next = input.readObject();
                Answer answer = (Answer) next;
                if(!(answer instanceof Pong))
                    processServerAnswer(answer);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            }
            catch (SocketTimeoutException e){
                System.out.println("Server unreachable!");
                isConnected = false;
            }
        }
    }

    public void send(Object message) throws IOException{
        /*if(!(message instanceof Ping))
            System.out.println("[DEBUG] sending message to server." + message.toString());
        else
            System.out.println(".\n");*/
        output.writeObject(message);
        output.flush();
    }

    public void processServerAnswer(Object inputObj) throws IOException {
        String string;
        int choice=0;

        if(inputObj instanceof Connection){
            if(((Connection) inputObj).isConnection()){
                view.handShake(((Connection) inputObj).getMessage());
                send(new SendString("Client connected!"));
            }
        }
        else if(inputObj instanceof RequestString) {
            string=view.askNickname(((RequestString) inputObj).getMessage());
            send(new SendString(string));
        }
        else if(inputObj instanceof RequestInt) {
            switch(((RequestInt) inputObj).getType()) {
                case "NUMBER" : choice=view.askPlayerNumber(((RequestInt) inputObj).getMessage());
                                break;
                case "RESOURCE" : choice=view.askResource(((RequestInt) inputObj).getMessage());
                                  break;
                case "GAMEBOARD" : choice=view.seeGameBoard(((RequestInt) inputObj).getMessage());
                                      break;
                case "LINE" : choice=view.chooseLine(((RequestInt) inputObj).getMessage());
                              break;
                case "TURN" : choice= view.askTurnType(((RequestInt) inputObj).getMessage());
                              break;
                case "STORAGE" : choice=view.ManageStorage(((RequestInt) inputObj).getMessage());
                                 break;
                case "MARKET" : choice=view.useMarket(((RequestInt) inputObj).getMessage());
                                break;
                case "WHITE" : choice=view.chooseWhiteBallLeader(((RequestInt) inputObj).getMessage());
                               break;
                case "COLOR" : choice=view.askColor(((RequestInt) inputObj).getMessage());
                               break;
                case "LEVEL" : choice=view.askLevel(((RequestInt) inputObj).getMessage());
                               break;
                case "SPACE" : choice=view.askSpace(((RequestInt) inputObj).getMessage());
                               break;
                case "TYPE" : choice=view.askType(((RequestInt) inputObj).getMessage());
                              break;
                case "INPUT" : choice=view.askInput(((RequestInt) inputObj).getMessage());
                               break;
                case "OUTPUT" : choice=view.askOutput(((RequestInt) inputObj).getMessage());
                                break;
                case "DEVCARD" : choice=view.askDevelopmentCard(((RequestInt) inputObj).getMessage());
                                 break;
                case "LEADCARD" : choice=view.askLeaderCard(((RequestInt) inputObj).getMessage());
                                  break;
                case "END" : choice=view.endTurn(((RequestInt) inputObj).getMessage());
                             break;
            }
            send(new SendInt(choice));
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
            ArrayList<Integer> moves = view.MoveShelves(((RequestDoubleInt) inputObj).getMessage());
            send(new SendDoubleInt(moves.get(0), moves.get(1)));
        }
        else if (inputObj instanceof SeeBall) {
            choice = view.seeBall((SeeBall) inputObj);
            int shelf = view.chooseShelf();
            send(new SendDoubleInt(choice, shelf));
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
                choice = view.askLeaderToDiscard(((PassLeaderCard) inputObj).getMessage());
            } else if (inputObj instanceof SeeLeaderCards) {
                choice = view.seeLeaderCards(((SeeLeaderCards) inputObj).getMessage());
            } else if (inputObj instanceof SeeMarket) {
                choice = view.seeMarket(((SeeMarket) inputObj).getMessage());
            } else if (inputObj instanceof ActiveLeader) {
                choice = view.activeLeader(((ActiveLeader) inputObj));
            } else if (inputObj instanceof DiscardLeader) {
                choice = view.discardLeader(((DiscardLeader) inputObj));
            } else if (inputObj instanceof SeeGrid) {
                choice = view.seeGrid(((SeeGrid) inputObj).getMessage());
            } else if (inputObj instanceof SeeProductions) {
                choice = view.seeProductions(((SeeProductions) inputObj).getMessage());
            }
            send(new SendInt(choice));
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
}
