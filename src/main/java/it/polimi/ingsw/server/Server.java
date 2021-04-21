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
    private final ArrayList<ClientHandler> clients = new ArrayList<>();
    private final ArrayList<Lobby> lobbies = new ArrayList<>();
    private int numberOfLobbies;
    private int playersInLastLobby;
    private boolean lobbyFull;

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

                //if there is no lobby or the existing lobbies are full we need to create a new lobby
                if (clients.size() == 1 || lobbyFull) {
                    try {
                        numberOfLobbies++;
                        Lobby lobby = new Lobby(numberOfLobbies);
                        lobby.newLobby(clientHandler);
                        lobbies.add(lobby);
                        playersInLastLobby++;
                        if(lobbyFull){
                            lobbyFull=false;
                        }
                        if(playersInLastLobby == lobbies.get(numberOfLobbies-1).getPlayersNumber()) {
                            lobbyFull = true;
                            System.out.println("This lobby is now full. Next player will create a new lobby.");
                            lobbies.get(numberOfLobbies-1).prepareTheGame();
                            playersInLastLobby=0;
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    lobbies.get(numberOfLobbies-1).add(clientHandler);
                    playersInLastLobby++;
                    if(playersInLastLobby == lobbies.get(numberOfLobbies-1).getPlayersNumber()) {
                        lobbyFull = true;
                        System.out.println("This lobby is now full. Next player will create a new lobby.");
                        lobbies.get(numberOfLobbies-1).prepareTheGame();
                        playersInLastLobby=0;
                    }
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("Connection dropped");
            }
        }
    }

}
