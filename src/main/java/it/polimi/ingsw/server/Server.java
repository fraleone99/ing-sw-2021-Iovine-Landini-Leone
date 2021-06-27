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

    public void createLobby(ClientHandler clienthandler) {
        final ClientHandler clientHandler1=clienthandler;
        Thread t = new Thread(()-> {
            VirtualView view;
            if(clients.size() == 1 || lobbyFull) {
                view = new VirtualView();
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
                    try {
                        lobbies.get(numberOfLobbies - 1).add(clientHandler1, nickname);
                        synchronized (lock) {
                            playerReady = true;
                            lock.notifyAll();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
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

    public void handShake(ClientHandler client, VirtualView view) {
        String welcome = view.askHandShake(client);
        System.out.println(welcome);
    }

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

    public int requestPlayersNumber(ClientHandler client, VirtualView view) {
        return view.requestPlayersNumber(client);
    }

    public boolean clientCrashed(String nickname) {
        return nicknames.contains(nickname) && !nicknameConnected.get(nickname);
    }

    public void resetClient(String nickname) {
        nicknameConnected.replace(nickname, true);
    }

    public void removeClient(ClientHandler client, String nickname) {
        clients.remove(client);
        nicknameConnected.put(nickname, false);

        Lobby lobby = nicknameToLobby.get(nickname);
        VirtualView view = lobbyToView.get(lobby);

        view.clientCrashed(nickname);
    }

    public void clientDisconnected(String nickname) {
        nicknames.remove(nickname);
        nicknameConnected.remove(nickname);
        nicknameToLobby.remove(nickname);
    }
}
