package it.polimi.ingsw.server;

//import it.polimi.ingsw.observer.LobbyObserver;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.server.answer.initialanswer.InitialSetup;
import it.polimi.ingsw.server.answer.request.SendMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private ServerSocket Socket;
    private final ArrayList<ClientHandler> clients = new ArrayList<>();
    private final ArrayList<Lobby> lobbies = new ArrayList<>();
    private int numberOfLobbies;
    private int playersInLastLobby;
    private boolean lobbyFull;
    private boolean playerReady=true;
    private final Object lock=new Object();


    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        int PORT_NUMBER;

        System.out.println("Default configuration?[y/n]");
        String config = scanner.nextLine();
        if(config.equalsIgnoreCase("y")){
            PORT_NUMBER = 3456;
        }
        else {
            do {
                System.out.println("Insert port number:");
                PORT_NUMBER = scanner.nextInt();
            } while (PORT_NUMBER < 1024);
        }
        try {
            Socket = new ServerSocket(PORT_NUMBER);
        } catch (IOException e) {
            System.out.println("Can not open Server Socket");
        }

        while (true) {
            try {
                System.out.println("Waiting for connection...");
                Socket newSocket = Socket.accept();
                ClientHandler clientHandler = new ClientHandler(newSocket,this);
                Thread t = new Thread(clientHandler, "Server_" + newSocket.getInetAddress());
                t.start();

                clients.add(clientHandler);

                synchronized (lock) {
                    while(!playerReady) {
                        try {
                            clientHandler.send(new SendMessage("We are creating the lobby, please wait..."));
                            clientHandler.send(new InitialSetup());
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                playerReady=false;

                createLobby(clientHandler);

                //if there is no lobby or the existing lobbies are full we need to create a new lobby
            } catch (IOException  e) {
                System.out.println("Connection dropped1");
            }
        }
    }

    public void setPlayerReady(boolean playerReady) {
        this.playerReady = playerReady;
    }

    public Object getLock() {
        return lock;
    }

    public ArrayList<ClientHandler> getClients() {
        return clients;
    }

    public void createLobby(ClientHandler clienthandler) {
        final ClientHandler clientHandler1=clienthandler;
        Thread t = new Thread(()-> {
            if (clients.size() == 1 || lobbyFull) {
                    try {
                        Lobby lobby = new Lobby(numberOfLobbies+1);
                        lobby.newLobby(clientHandler1);
                        numberOfLobbies++;
                        lobbies.add(lobby);
                        playersInLastLobby++;
                        synchronized (lock) {
                            playerReady=true;
                            lock.notifyAll();
                        }
                        if (lobbyFull) {
                            lobbyFull = false;
                        }
                        if (playersInLastLobby == lobbies.get(numberOfLobbies - 1).getPlayersNumber()) {
                            lobbyFull = true;
                            System.out.println("This lobby is now full. Next player will create a new lobby.");
                            lobbies.get(numberOfLobbies - 1).prepareTheGame();
                            playersInLastLobby = 0;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        lobbies.get(numberOfLobbies - 1).add(clientHandler1);
                        synchronized (lock) {
                            playerReady=true;
                            lock.notifyAll();
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    playersInLastLobby++;
                    if (playersInLastLobby == lobbies.get(numberOfLobbies - 1).getPlayersNumber()) {
                        lobbyFull = true;
                        System.out.println("This lobby is now full. Next player will create a new lobby.");
                        lobbies.get(numberOfLobbies - 1).prepareTheGame();
                        playersInLastLobby = 0;
                    }
                }

        });
        t.start();
    }
}
