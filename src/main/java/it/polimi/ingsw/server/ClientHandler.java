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
    private boolean handshake;
    VirtualView virtualView;


    public ClientHandler(Socket socketClient) throws IOException {
        this.socketClient = socketClient;
        isReady=false;
        handshake = false;
        virtualView = new VirtualView();

        output=new ObjectOutputStream(socketClient.getOutputStream());
        input=new ObjectInputStream(socketClient.getInputStream());

    }

    public void send(Object message) throws IOException, InterruptedException {
            output.writeObject(message);
            //handleClientConnection();
    }

    public void handleClientConnection() throws IOException, InterruptedException {
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

    public synchronized void processClientMessage(Message message){
        if(message instanceof ClientConnection){
            System.out.println(((ClientConnection) message).getMessage());
            handshake = true;
            notifyAll();
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
        System.out.println("Connected to "+socketClient.getInetAddress());
        try {
            //virtualView.askHandShake(this);
            handleClientConnection();
        } catch (IOException | InterruptedException e) {
            System.out.println("client " + socketClient.getInetAddress() + " connection dropped");
        }

        try{
            socketClient.close();
        } catch (IOException e) {}
    }
}
