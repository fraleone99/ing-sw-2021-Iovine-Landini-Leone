package it.polimi.ingsw.client;

import it.polimi.ingsw.client.message.action.ChoiceGameBoard;
import it.polimi.ingsw.client.message.action.ChosenLeaderCard;
import it.polimi.ingsw.client.message.action.TurnType;
import it.polimi.ingsw.client.message.initialmessage.FirstChosenLeaders;
import it.polimi.ingsw.client.view.CLI.CLI;
import it.polimi.ingsw.client.message.action.ChosenResource;
import it.polimi.ingsw.client.message.initialmessage.ClientConnection;
import it.polimi.ingsw.client.message.initialmessage.NumberOfPlayers;
import it.polimi.ingsw.client.message.initialmessage.SendNickname;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.answer.*;
import it.polimi.ingsw.server.answer.initialanswer.*;
import it.polimi.ingsw.server.answer.turnanswer.ActiveLeader;
import it.polimi.ingsw.server.answer.turnanswer.ChooseTurn;
import it.polimi.ingsw.server.answer.turnanswer.SeeLeaderCards;
import it.polimi.ingsw.server.answer.turnanswer.SeeMarket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class NetworkHandler implements Runnable {
    private Socket server;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Heartbeat heartbeat;
    private Client owner;
    private View view;

    public NetworkHandler(Socket server, Client owner) {
        this.server = server;
        this.owner = owner;
        view = new CLI();
    }

    @Override
    public void run() {
        try {
            input = new ObjectInputStream(server.getInputStream());
            output = new ObjectOutputStream(server.getOutputStream());
        } catch (IOException e) {
            System.out.println("Could not open connection to");
        }

        try {
            handleClientConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientConnection() throws IOException{
        heartbeat = new Heartbeat(this);
        Thread heartbeatThread = new Thread(heartbeat);
        heartbeatThread.start();
        while (true) {
            try {
                server.setSoTimeout(4000);
                Object next = input.readObject();
                Answer answer = (Answer) next;
                if(!(answer instanceof Pong))
                    processServerAnswer(answer);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            }
            catch (SocketTimeoutException e){
                System.out.println("Server unreachable!");
            }
        }
    }

    public void send(Object message) throws IOException{
        output.reset();
        output.writeObject(message);
        output.flush();
    }

    public void processServerAnswer(Object inputObj) throws IOException {
        if(inputObj instanceof Connection){
            if(((Connection) inputObj).isConnection()){
                view.handShake(((Connection) inputObj).getMessage());
                send(new ClientConnection("Client connected!"));
            }
        }
        else if(inputObj instanceof RequestNickname){
            String nickname = view.askNickname(((RequestNickname) inputObj).getMessage());
            send(new SendNickname(nickname));
        }
        else if(inputObj instanceof PlayersNumber){
            String number = view.askPlayerNumber(((PlayersNumber) inputObj).getMessage());
            output.writeObject(new NumberOfPlayers(number));
        }
        else if(inputObj instanceof InvalidNickname){
            String nickname = view.askNickname(((InvalidNickname)inputObj).getMessage());
            output.writeObject(new SendNickname(nickname));
        }
        else if(inputObj instanceof WaitingRoom){
            view.readMessage(((WaitingRoom) inputObj).getMessage());
        }
        else if(inputObj instanceof PrepareTheLobby){
            view.readMessage(((PrepareTheLobby) inputObj).getMessage());
        }
        else if(inputObj instanceof FirstPlayer){
            view.readMessage(((FirstPlayer) inputObj).getMessage());
        }
        else if(inputObj instanceof ChooseResource){
            int resource=view.askResource(((ChooseResource) inputObj).getMessage());
            output.writeObject(new ChosenResource(resource));
        }
        else if(inputObj instanceof DiscardFirstLeaders){
            view.readMessage(((DiscardFirstLeaders) inputObj).getMessage());
        }
        else if(inputObj instanceof PassLeaderCard) {
            int card=view.askLeaderToDiscard(((PassLeaderCard) inputObj).getMessage());
            output.writeObject(new FirstChosenLeaders(card));
        }
        else if(inputObj instanceof StartGame) {
            view.readMessage(((StartGame) inputObj).getMessage());
        }
        else if(inputObj instanceof JoiningPlayer){
            view.readMessage(((JoiningPlayer) inputObj).getMessage());
        }
        else if(inputObj instanceof ChooseTurn) {
            int turn=view.askTurnType(((ChooseTurn) inputObj).getMessage());
            output.writeObject(new TurnType(turn));
        }
        else if(inputObj instanceof ActiveLeader) {
            int leaderCard=view.activeLeader(((ActiveLeader) inputObj).getMessage());
            output.writeObject(new ChosenLeaderCard(leaderCard));
        }
        else if(inputObj instanceof GameError) {
            view.readMessage(((GameError) inputObj).getMessage());
        }
        else if(inputObj instanceof SeeGameBoard) {
            int choice=view.seeGameBoard(((SeeGameBoard) inputObj).getMessage());
            output.writeObject(new ChoiceGameBoard(choice));
        }
        else if(inputObj instanceof SeeLeaderCards) {
            int choice=view.seeLeaderCards(((SeeLeaderCards) inputObj).getMessage());
            output.writeObject(new ChoiceGameBoard(choice));
        }
        else if(inputObj instanceof SeeMarket) {
            int choice=view.seeMarket(((SeeMarket) inputObj).getMessage());
            output.writeObject(new ChoiceGameBoard(choice));
        }
    }

}
