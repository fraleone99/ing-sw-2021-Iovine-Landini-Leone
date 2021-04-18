package it.polimi.ingsw.server;

import it.polimi.ingsw.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    private static ServerSocket Socket;
    private static ArrayList<ClientHandler> clients=new ArrayList<>();
    private static Lobby lobby;

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        boolean isFirst=true;

        int PORT_NUMBER;

        do {
            System.out.println("Insert port number:");
            PORT_NUMBER = scanner.nextInt();
        } while (PORT_NUMBER<1024);

        try {
            Socket=new ServerSocket(PORT_NUMBER);
        } catch (IOException e) {
            System.out.println("Can not open Server Socket");
        }

        while(true){
            try {
                System.out.println("Waiting for connection...");
                Socket newSocket = Socket.accept();
                System.out.println("Client connected");
                ClientHandler clientHandler=new ClientHandler(newSocket);
                Thread t = new Thread(clientHandler,"Server_" + newSocket.getInetAddress());
                t.start();
                clients.add(clientHandler);
                if(clients.size()==1)
                {
                    synchronized (clients){
                        lobby.newLobby(clients.get(0));
                    }
                }
            } catch (IOException e) {
                System.out.println("Connection dropped");
            }
        }
    }

}
