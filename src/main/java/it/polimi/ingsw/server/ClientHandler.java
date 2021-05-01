package it.polimi.ingsw.server;

import it.polimi.ingsw.client.message.action.*;
import it.polimi.ingsw.client.message.initialmessage.ClientConnection;
import it.polimi.ingsw.client.message.Message;
import it.polimi.ingsw.client.message.initialmessage.FirstChosenLeaders;
import it.polimi.ingsw.client.message.initialmessage.NumberOfPlayers;
import it.polimi.ingsw.client.message.initialmessage.SendNickname;
import it.polimi.ingsw.observer.ConnectionObservable;
import it.polimi.ingsw.observer.VirtualViewObserver;
import it.polimi.ingsw.server.answer.ChooseResource;
import it.polimi.ingsw.client.message.*;
import it.polimi.ingsw.server.answer.turnanswer.ChooseLine;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ClientHandler extends ConnectionObservable implements Runnable {
    private Socket socketClient;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean isReady;
    private String answer;
    private Heartbeat heartbeat;


    public ClientHandler(Socket socketClient) throws IOException {
        this.socketClient = socketClient;
        isReady=false;

        output=new ObjectOutputStream(socketClient.getOutputStream());
        input=new ObjectInputStream(socketClient.getInputStream());

    }

    public void send(Object message) throws IOException{
        output.reset();
        output.writeObject(message);
        output.flush();
    }

    public void handleClientConnection() throws IOException{
        heartbeat = new Heartbeat(this);
        Thread heartbeatThread = new Thread(heartbeat);
        heartbeatThread.start();
        try{
            while(true){
                socketClient.setSoTimeout(4000);
                Object next = input.readObject();
                Message message=(Message)next;
                if(!(message instanceof Ping))
                    processClientMessage(message);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SocketTimeoutException e){
            System.out.println("Client unreachable!");


            try {
                notifyDisconnection(this);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    public synchronized void processClientMessage(Message message){
        if(message instanceof ClientConnection){
            answer = ((ClientConnection) message).getMessage();
            isReady = true;
            notifyAll();
        }
        else if(message instanceof SendNickname){
            answer = ((SendNickname) message).getNickname();
            isReady = true;
            notifyAll();
        }
        else if(message instanceof NumberOfPlayers){
            answer=((NumberOfPlayers) message).getNumber();
            isReady = true;
            notifyAll();
        }
        else if(message instanceof ChosenResource){
            answer=String.valueOf(((ChosenResource) message).getResource());
            isReady=true;
            notifyAll();
        }
        else if(message instanceof Ping){
            System.out.println("ping received!");
        }
        else if(message instanceof FirstChosenLeaders){
            answer=String.valueOf(((FirstChosenLeaders) message).getLeader());
            isReady=true;
            notifyAll();
        }
        else if(message instanceof ChoiceGameBoard) {
            answer=String.valueOf(((ChoiceGameBoard) message).getChoice());
            isReady=true;
            notifyAll();
        }
        else if(message instanceof TurnType) {
            answer=String.valueOf(((TurnType) message).getTurn());
            isReady=true;
            notifyAll();
        }
        else if(message instanceof ChosenLeaderCard) {
            answer=String.valueOf(((ChosenLeaderCard) message).getPosition());
            isReady=true;
            notifyAll();
        }
        else if(message instanceof ChosenLine) {
            answer=String.valueOf(((ChosenLine) message).getChoice());
            isReady=true;
            notifyAll();
        }
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String getAnswer() {
        return answer;
    }

    public void closeConnection() throws IOException {
        socketClient.close();
        System.out.println("Client disconnected");
    }


    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + socketClient.getInetAddress() + " connection dropped");
        }

        try{
            socketClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
