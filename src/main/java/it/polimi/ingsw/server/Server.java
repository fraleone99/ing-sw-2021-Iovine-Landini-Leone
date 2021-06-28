package it.polimi.ingsw.server;

import it.polimi.ingsw.server.answer.initialanswer.InitialSetup;
import it.polimi.ingsw.server.answer.request.SendMessage;
import it.polimi.ingsw.server.answer.turnanswer.TurnStatus;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Server class is the main one of the server side, it allows clients to connect
 *
 * @author Lorenzo Iovine
 */
public class Server {
    private ServerSocket Socket;
    private int numberOfLobbies;
    private int playersInLastLobby;
    private boolean lobbyFull;
    private boolean playerReady=true;
    private final Object lock=new Object();
    private final ArrayList<String> nicknames = new ArrayList<>();
    private final ArrayList<ClientHandler> clients = new ArrayList<>();
    private final ArrayList<Lobby> lobbies = new ArrayList<>();
    private final Map <String, Boolean> nicknameConnected = new HashMap<>();
    private final Map <Lobby, VirtualView> lobbyToView = new HashMap<>();
    private final Map <String, Lobby> nicknameToLobby = new HashMap<>();


    /**
     * The main class of the server. It create a new instance of Server and runs it.
     * @param args of type String[] - the main args, like any Java application.
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }


    /**
     * Manages the connection with the client and creates the corresponding ClientHandler
     */
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

                synchronized (lock) {
                    while(!playerReady) {
                        try {
                            System.out.println("hello");
                            clientHandler.send(new SendMessage("We are creating the lobby, please wait..."));
                            clientHandler.send(new InitialSetup());
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                clients.add(clientHandler);
                System.out.println("Client connected!");

                playerReady=false;

                manageLobby(clientHandler);

            } catch (IOException  e) {
                System.out.println("Connection dropped");
            }
        }
    }

    public void setPlayerReady(boolean playerReady) {
        this.playerReady = playerReady;
    }

    public Object getLock() {
        return lock;
    }


    /**
     * Manages the creation of a lobby (with the corresponding virtual view)
     * or the entry of a client into an existing one
     * @param clientHandler is the ClientHandler of the client who wants to join a lobby
     */
    public void manageLobby(ClientHandler clientHandler) {
        final ClientHandler clientHandler1=clientHandler;
        Thread t = new Thread(()-> {
            VirtualView view;
            if(clients.size() == 1 || lobbyFull) {
                view = new VirtualView(this);
            } else {
                view = lobbyToView.get(lobbies.get(numberOfLobbies-1));
            }
            handShake(clientHandler1, view);
            String nickname = requestNickname(clientHandler1, view);

            if(clientCrashed(nickname)) {
                nicknameToLobby.get(nickname).reAdd(clientHandler1, nickname);
                resetClient(nickname);
                clientHandler1.send(new SendMessage("You crashed. Please wait, you will play from the next turn."));
                clientHandler1.send(new TurnStatus("END"));
                synchronized (lock) {
                    playerReady = true;
                    lock.notifyAll();
                }
            }
            else {
                if (clients.size() == 1 || lobbyFull) {
                    setNickname(nickname);
                    Lobby lobby = new Lobby(numberOfLobbies + 1, view);
                    lobbyToView.put(lobby, view);
                    int number = requestPlayersNumber(clientHandler1, view);
                    nicknameToLobby.put(nickname, lobby);
                    lobby.newLobby(clientHandler1, nickname, number);
                    numberOfLobbies++;
                    lobbies.add(lobby);
                    playersInLastLobby++;
                    synchronized (lock) {
                        playerReady = true;
                        lock.notifyAll();
                    }
                    if (lobbyFull) {
                        lobbyFull = false;
                    }
                } else {
                    setNickname(nickname);
                    nicknameToLobby.put(nickname, lobbies.get(numberOfLobbies - 1));
                    lobbies.get(numberOfLobbies - 1).add(clientHandler1, nickname);
                    synchronized (lock) {
                        playerReady = true;
                        lock.notifyAll();
                    }
                    playersInLastLobby++;
                }
            }
            if (playersInLastLobby == lobbies.get(numberOfLobbies - 1).getPlayersNumber()) {
                lobbyFull = true;
                System.out.println("This lobby is now full. Next player will create a new lobby.");
                lobbies.get(numberOfLobbies - 1).prepareTheGame();
                playersInLastLobby = 0;
            }

        });
        t.start();
    }


    /**
     * Handshake with the client
     * @param client is the ClientHandler of the client who joined the game
     * @param view is the virtual view of the lobby of the ClientHandler
     */
    public void handShake(ClientHandler client, VirtualView view) {
        view.askHandShake(client);

    }


    /**
     * Requests the nickname to client who joined the game
     * @param client is the ClientHandler of the client who joined the game
     * @param view is the virtual view of the lobby of the ClientHandler
     * @return the chosen nickname
     */
    public String requestNickname(ClientHandler client, VirtualView view) {
        String nickname = view.requestNickname(client);

        while(nicknames.contains(nickname) && nicknameConnected.get(nickname)) {
            nickname = view.invalidNickname(client);
        }

        return nickname;
    }

    public void setNickname(String nickname) {
        nicknames.add(nickname);
        nicknameConnected.put(nickname, true);
    }


    /**
     * Requests the number of players to first client of a lobby
     * @param client is the ClientHandler of the client who joined the game
     * @param view is the virtual view of the lobby of the ClientHandler
     * @return the chosen players number
     */
    public int requestPlayersNumber(ClientHandler client, VirtualView view) {
        return view.requestPlayersNumber(client);
    }


    /**
     * Checks if the client is crashed
     * @param nickname is the check parameter
     * @return true if the client is crashed
     */
    public boolean clientCrashed(String nickname) {
        return nicknames.contains(nickname) && !nicknameConnected.get(nickname);
    }

    public void resetClient(String nickname) {
        nicknameConnected.replace(nickname, true);
    }


    /**
     * Removes client who crashed from the game
     * @param client is the ClientHandler of the client who crashed
     * @param nickname is the nickname of the client who crashed
     */
    public void removeClient(ClientHandler client, String nickname) {
        clients.remove(client);
        nicknameConnected.put(nickname, false);

        Lobby lobby = nicknameToLobby.get(nickname);
        VirtualView view = lobbyToView.get(lobby);

        view.clientCrashed(nickname);
    }


    /**
     * Removes client when the game is over
     * @param nickname is the nickname of the client
     */
    public void clientDisconnected(String nickname) {
        nicknames.remove(nickname);
        nicknameConnected.remove(nickname);
        nicknameToLobby.remove(nickname);
    }

    /**
     * Prints when the game of a lobby is over
     * @param nickname is the nickname of a client
     */
    public void closeGame(String nickname) {
        System.out.println("The game of the lobby number " + nicknameToLobby.get(nickname).getLobbyID() + " is over." );
    }
}
