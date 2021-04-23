package it.polimi.ingsw.server;

import it.polimi.ingsw.client.message.action.ChosenResource;
import it.polimi.ingsw.client.message.initialmessage.ClientConnection;
import it.polimi.ingsw.client.message.Message;
import it.polimi.ingsw.client.message.initialmessage.NumberOfPlayers;
import it.polimi.ingsw.client.message.initialmessage.SendNickname;
import it.polimi.ingsw.server.answer.ChooseResource;
import it.polimi.ingsw.client.message.*;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ClientHandler implements Runnable{
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

    public void handleClientConnection() throws IOException {
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

        heartbeat = new Heartbeat(this);
        Thread heartbeatThread = new Thread(heartbeat);
        heartbeatThread.start();

        try {
            //virtualView.askHandShake(this);
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
