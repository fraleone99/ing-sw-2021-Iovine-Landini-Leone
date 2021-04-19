package it.polimi.ingsw.server;

import it.polimi.ingsw.client.message.ClientConnection;
import it.polimi.ingsw.client.message.Message;
import it.polimi.ingsw.client.message.NumberOfPlayers;
import it.polimi.ingsw.client.message.SendNickname;
import it.polimi.ingsw.server.answer.PlayersNumber;
import it.polimi.ingsw.server.answer.RequestNickname;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socketClient;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String nickname;
    private int playersNumber;
    private boolean isReady;


    public ClientHandler(Socket socketClient) {
        this.socketClient = socketClient;
        isReady=false;
    }

    public void send(Object message) throws IOException {
        if (message instanceof RequestNickname) {
            Object msg = new RequestNickname(((RequestNickname) message).getMessage());
            output.writeObject(msg);
            handleClientConnection();
        } else if (message instanceof PlayersNumber) {
            Object msg = new PlayersNumber(((PlayersNumber) message).getMessage());
            output.writeObject(msg);
            handleClientConnection();
        }
    }

    public void handleClientConnection() throws IOException{
        try{
            while(true){
                Object next = input.readObject();
                Message message=(Message)next;
                processClientMessage(message);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void processClientMessage(Message message){
        if(message instanceof ClientConnection){
            System.out.println(((ClientConnection) message).getMessage());
        }
        if(message instanceof SendNickname){
                nickname = ((SendNickname) message).getNickname();
                isReady = true;
                notifyAll();
        }
        else if(message instanceof NumberOfPlayers){
            playersNumber=((NumberOfPlayers) message).getNumber();
        }
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void run() {
        try{
            output=new ObjectOutputStream(socketClient.getOutputStream());
            input=new ObjectInputStream(socketClient.getInputStream());
        } catch (IOException e) {
            System.out.println("Not open connection to "+socketClient.getInetAddress());
            return;
        }

        System.out.println("Connected to "+socketClient.getInetAddress());

        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + socketClient.getInetAddress() + " connection dropped");
        }

        try{
            socketClient.close();
        } catch (IOException e) {}
    }
}
