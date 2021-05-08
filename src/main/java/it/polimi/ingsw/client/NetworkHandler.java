package it.polimi.ingsw.client;

import it.polimi.ingsw.client.message.action.*;
import it.polimi.ingsw.client.message.initialmessage.FirstChosenLeaders;
import it.polimi.ingsw.client.view.CLI.CLI;
import it.polimi.ingsw.client.message.initialmessage.ClientConnection;
import it.polimi.ingsw.client.message.initialmessage.NumberOfPlayers;
import it.polimi.ingsw.client.message.initialmessage.SendNickname;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.InitialSetup;
import it.polimi.ingsw.server.answer.*;
import it.polimi.ingsw.server.answer.initialanswer.*;
import it.polimi.ingsw.server.answer.turnanswer.*;
import it.polimi.ingsw.server.answer.turnanswer.UseMarket;

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
                isConnected = false;
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
            send(new NumberOfPlayers(number));
        }
        else if(inputObj instanceof InvalidNickname){
            String nickname = view.askNickname(((InvalidNickname)inputObj).getMessage());
            send(new SendNickname(nickname));
        }
        else if(inputObj instanceof WaitingRoom){
            view.readMessage(((WaitingRoom) inputObj).getMessage());
        }
        else if(inputObj instanceof PrepareTheLobby){
            view.readMessage(((PrepareTheLobby) inputObj).getMessage());
        }
        else if(inputObj instanceof PlayingNick){
            view.readMessage(((PlayingNick) inputObj).getMessage());
        }
        else if(inputObj instanceof FirstPlayer){
            view.readMessage(((FirstPlayer) inputObj).getMessage());
        }
        else if(inputObj instanceof ChooseResource){
            int resource=view.askResource(((ChooseResource) inputObj).getMessage());
            send(new ChosenResource(resource));
        }
        else if(inputObj instanceof DiscardFirstLeaders){
            view.readMessage(((DiscardFirstLeaders) inputObj).getMessage());
        }
        else if(inputObj instanceof PassLeaderCard) {
            int card=view.askLeaderToDiscard(((PassLeaderCard) inputObj).getMessage());
            send(new FirstChosenLeaders(card));
        }
        else if(inputObj instanceof StartGame) {
            view.readMessage(((StartGame) inputObj).getMessage());
        }
        else if(inputObj instanceof JoiningPlayer){
            view.readMessage(((JoiningPlayer) inputObj).getMessage());
        }
        else if(inputObj instanceof ChooseTurn) {
            int turn=view.askTurnType(((ChooseTurn) inputObj).getMessage());
            send(new TurnType(turn));
        }
        else if(inputObj instanceof GameError) {
            view.readMessage(((GameError) inputObj).getMessage());
        }
        else if(inputObj instanceof SeeGameBoard) {
            int choice=view.seeGameBoard(((SeeGameBoard) inputObj).getMessage());
            send(new ChoiceGameBoard(choice));
        }
        else if(inputObj instanceof SeeLeaderCards) {
            int choice=view.seeLeaderCards(((SeeLeaderCards) inputObj).getMessage());
            send(new ChoiceGameBoard(choice));
        }
        else if(inputObj instanceof SeeMarket) {
            int choice=view.seeMarket(((SeeMarket) inputObj).getMessage());
            send(new ChoiceGameBoard(choice));
        }
        else if(inputObj instanceof ActiveLeader) {
            int leaderCard=view.activeLeader(((ActiveLeader) inputObj));
            send(new ChosenLeaderCard(leaderCard));
        }
        else if(inputObj instanceof DiscardLeader) {
            int leaderCard=view.discardLeader(((DiscardLeader) inputObj));
            send(new ChosenLeaderCard(leaderCard));
        }
        else if(inputObj instanceof ChooseLine) {
            int choice=view.chooseLine(((ChooseLine) inputObj).getMessage());
            send(new ChosenLine(choice));
        }
        else if(inputObj instanceof SeeGrid) {
            int choice=view.seeGrid(((SeeGrid) inputObj).getMessage());
            send(new ChoiceGameBoard(choice));
        }
        else if(inputObj instanceof SeeProductions) {
            int choice=view.seeProductions(((SeeProductions) inputObj).getMessage());
            send(new ChoiceGameBoard(choice));
        }
        else if(inputObj instanceof Disconnection){
            view.readMessage(((Disconnection) inputObj).getMessage());
        }
        else if(inputObj instanceof FaithPathInfo){
            view.printFaithPath((FaithPathInfo) inputObj);
        }
        else if(inputObj instanceof StorageInfo){
            view.printStorage((StorageInfo) inputObj);
        }
        else if(inputObj instanceof DevCardsSpaceInfo){
            view.printDevelopmentCardsSpace((DevCardsSpaceInfo) inputObj);
        }
        else if(inputObj instanceof ManageStorage){
            int choice=view.ManageStorage(((ManageStorage) inputObj).getMessage());
            send(new ChoiceGameBoard(choice));
        }
        else if(inputObj instanceof MoveShelves) {
            ArrayList<Integer> moves=view.MoveShelves(((MoveShelves) inputObj).getMessage());
            send(new ChoiceGameBoard(moves.get(0)));
            send(new ChoiceGameBoard(moves.get(1)));
        }
        else if(inputObj instanceof ResetCard) {
            view.resetCard(((ResetCard) inputObj).getPos());
        }
        else if(inputObj instanceof UseMarket) {
            int line=view.useMarket(((UseMarket) inputObj).getMessage());
            send(new ChoiceGameBoard(line));
        }
        else if(inputObj instanceof ChooseTwoWhiteBallLeader) {
            int choice=view.chooseWhiteBallLeader(((ChooseTwoWhiteBallLeader) inputObj).getMessage());
            send(new ChoiceGameBoard(choice));
        }
        else if(inputObj instanceof DiscardBall) {
            view.readMessage(((DiscardBall) inputObj).getMessage());
        }
        else if(inputObj instanceof SeeBall) {
            int choice=view.seeBall((SeeBall)inputObj);
            int shelf=view.chooseShelf();
            send(new ChoiceGameBoard(choice));
            send(new ChoiceGameBoard(shelf));
        }
        else if(inputObj instanceof AskColor) {
            int choice=view.askColor(((AskColor) inputObj).getMessage());
            send(new ChoiceGameBoard(choice));
        }
        else if(inputObj instanceof AskLevel) {
            int choice=view.askLevel(((AskLevel) inputObj).getMessage());
            send(new ChoiceGameBoard(choice));
        }
        else if(inputObj instanceof AskSpace) {
            int choice=view.askSpace(((AskSpace) inputObj).getMessage());
            send(new ChoiceGameBoard(choice));
        }
        else if(inputObj instanceof AskType) {
            int type=view.askType(((AskType) inputObj).getMessage());
            send(new ChoiceGameBoard(type));
        }
        else if(inputObj instanceof AskInput) {
            int input=view.askInput(((AskInput) inputObj).getMessage());
            send(new ChoiceGameBoard(input));
        }
        else if(inputObj instanceof AskOutput) {
            int output=view.askOutput(((AskOutput) inputObj).getMessage());
            send(new ChoiceGameBoard(output));
        }
        else if(inputObj instanceof AskDevelopmentCard) {
            int space=view.askDevelopmentCard(((AskDevelopmentCard) inputObj).getMessage());
            send(new ChoiceGameBoard(space));
        }
        else if(inputObj instanceof AskLeaderCard) {
            int index=view.askLeaderCard(((AskLeaderCard) inputObj).getMessage());
            send(new ChoiceGameBoard(index));
        }
        else if(inputObj instanceof ActionTokenInfo){
            view.printActionToken(((ActionTokenInfo) inputObj).getMessage());
        }
        else if(inputObj instanceof EndTurn) {
            int choice=view.endTurn(((EndTurn) inputObj).getMessage());
            send(new ChoiceGameBoard(choice));
        }
        else if(inputObj instanceof TurnStatus){
            if(((TurnStatus) inputObj).getMessage().equals("END")){

                isMyTurn.set(false);
                waitForYourTurn();
            }
            else if(((TurnStatus) inputObj).getMessage().equals("START")){
               isMyTurn.set(true);
            }
        }
        else if(inputObj instanceof InitialSetup){
            waitForYourTurn();
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
