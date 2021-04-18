package it.polimi.ingsw.server;

import it.polimi.ingsw.client.message.Message;
import it.polimi.ingsw.client.message.SetupConnection;
import it.polimi.ingsw.server.answer.Connection;
import it.polimi.ingsw.server.answer.turnanswer.EndTurn;
import it.polimi.ingsw.server.answer.turnanswer.StartTurn;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socketClient;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private static StartTurn start;
    private static EndTurn end;
    private static boolean myTurn;
    private boolean isFirst=true;

    public ClientHandler(Socket socketClient) {
        this.socketClient = socketClient;
    }

    /*public static void send(Object message){
        try{
            if(message.equals(start.getMessage())){
                myTurn=true;
            }
            else if(message.equals(end.getMessage())){
                myTurn=false;
            }
        }
    }*/

    public void handleClientConnection() throws IOException{
        try{
            while(true){
                if(isFirst){
                    output.writeObject(new Connection("Welcome!",true));
                    isFirst=false;
                }
                Object next = input.readObject();
                Message message=(Message)next;
                processClientMessage(message);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void processClientMessage(Message message){
        if(message instanceof SetupConnection){
            System.out.println(((SetupConnection) message).getNickname());
        }
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
