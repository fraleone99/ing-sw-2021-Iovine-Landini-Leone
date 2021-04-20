package it.polimi.ingsw.server;

import it.polimi.ingsw.client.message.ClientConnection;
import it.polimi.ingsw.client.message.Message;
import it.polimi.ingsw.client.message.NumberOfPlayers;
import it.polimi.ingsw.client.message.SendNickname;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socketClient;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean isReady;
    VirtualView virtualView;
    private String answer;


    public ClientHandler(Socket socketClient) throws IOException {
        this.socketClient = socketClient;
        isReady=false;
        virtualView = new VirtualView();

        output=new ObjectOutputStream(socketClient.getOutputStream());
        input=new ObjectInputStream(socketClient.getInputStream());

    }

    public void send(Object message) throws IOException{
            output.writeObject(message);
    }

    public void handleClientConnection() throws IOException {
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


    @Override
    public void run() {
        //System.out.println("Connected to "+socketClient.getInetAddress());
        try {
            //virtualView.askHandShake(this);
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + socketClient.getInetAddress() + " connection dropped");
        }

        try{
            socketClient.close();
        } catch (IOException e) {}
    }
}
