package it.polimi.ingsw.server;

import it.polimi.ingsw.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    private ServerSocket Socket;
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private ArrayList<Lobby> lobbies = new ArrayList<>();
    private int numberOfLobbies=0;
    private int playerRegistered=0;

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        int PORT_NUMBER;

        do {
            System.out.println("Insert port number:");
            PORT_NUMBER = scanner.nextInt();
        } while (PORT_NUMBER < 1024);

        try {
            Socket = new ServerSocket(PORT_NUMBER);
        } catch (IOException e) {
            System.out.println("Can not open Server Socket");
        }

        while (true) {
            try {
                System.out.println("Waiting for connection...");
                Socket newSocket = Socket.accept();
                ClientHandler clientHandler = new ClientHandler(newSocket);
                Thread t = new Thread(clientHandler, "Server_" + newSocket.getInetAddress());
                t.start();

                clients.add(clientHandler);

                if (clients.size() == 1 + playerRegistered) {
                    try {
                        Lobby lobby = new Lobby();
                        lobby.newLobby(clients.get(clients.size()-1));
                        lobbies.add(lobby);
                        playerRegistered+=lobbies.get(numberOfLobbies).getPlayersNumber();
                        numberOfLobbies++;
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    lobbies.get(numberOfLobbies-1).add(clientHandler);
                } /*else if(clients.size() == 1+lobbies.get(numberOfLobbies-1).getPlayersNumber()){
                    Lobby lobby=new Lobby();
                    lobby.newLobby(clients.get(lobbies.get(numberOfLobbies-1).getPlayersNumber()));
                    lobbies.add(lobby);
                    numberOfLobbies++;
                } else if(clients.size()<= 1+lobbies.get(numberOfLobbies-1).getPlayersNumber()){
                    lobbies.get(numberOfLobbies-1).add(clientHandler);
                }*/
            } catch (IOException | InterruptedException e) {
                System.out.println("Connection dropped");
            }
        }
    }

    public int getNumberOfLobbies() {
        return numberOfLobbies;
    }
}
