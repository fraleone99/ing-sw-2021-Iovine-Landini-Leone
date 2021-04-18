package it.polimi.ingsw.client;

import it.polimi.ingsw.client.message.Message;
import it.polimi.ingsw.client.message.NumberOfPlayers;
import it.polimi.ingsw.client.message.SetupConnection;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.answer.Answer;
import it.polimi.ingsw.server.answer.Connection;
import it.polimi.ingsw.server.answer.PlayersNumber;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class NetworkHandler implements Runnable {
    private Socket server;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Client owner;

    public NetworkHandler(Socket server, Client owner) {
        this.server = server;
        this.owner = owner;
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
        while (true) {
            try {
                Object next = input.readObject();
                Answer answer = (Answer) next;
                processServerAnswer(answer);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void processServerAnswer(Object inputObj) throws IOException {
        if(inputObj instanceof Connection){
            if(((Connection) inputObj).isConnection()){
                System.out.println("You are now connected to the server!");
                String nickname;
                Scanner scanner = new Scanner(System.in);
                System.out.println("Insert nickname:");
                nickname = scanner.nextLine();
                Object msg = new SetupConnection(nickname);
                output.writeObject(msg);
            }
        }
        else if(inputObj instanceof PlayersNumber){
            System.out.println("Insert the number of players:");
            Scanner scanner = new Scanner(System.in);
            int number = scanner.nextInt();
            output.writeObject(new NumberOfPlayers(number));
        }
    }

}
