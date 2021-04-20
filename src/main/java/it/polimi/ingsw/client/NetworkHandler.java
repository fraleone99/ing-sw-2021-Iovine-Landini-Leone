package it.polimi.ingsw.client;

import it.polimi.ingsw.client.message.ClientConnection;
import it.polimi.ingsw.client.message.NumberOfPlayers;
import it.polimi.ingsw.client.message.SendNickname;
import it.polimi.ingsw.server.answer.Answer;
import it.polimi.ingsw.server.answer.Connection;
import it.polimi.ingsw.server.answer.PlayersNumber;
import it.polimi.ingsw.server.answer.RequestNickname;

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
                System.out.println(((Connection) inputObj).getMessage());
                output.writeObject(new ClientConnection("Client connected!"));
            }
        }
        else if(inputObj instanceof RequestNickname){
            String nickname;
            Scanner scanner = new Scanner(System.in);
            System.out.println(((RequestNickname) inputObj).getMessage());
            nickname = scanner.nextLine();
            Object msg = new SendNickname(nickname);
            output.writeObject(msg);
        }
        else if(inputObj instanceof PlayersNumber){
            String number;
            System.out.println(((PlayersNumber) inputObj).getMessage());
            do {
                Scanner scanner = new Scanner(System.in);
                number = scanner.nextLine();
                if(Integer.parseInt(number)<1 || Integer.parseInt(number)>4){
                    System.out.println("Incorrect number, please try again:");
                }
            } while (Integer.parseInt(number)<1 || Integer.parseInt(number)>4);
            output.writeObject(new NumberOfPlayers(number));
        }
    }

}
