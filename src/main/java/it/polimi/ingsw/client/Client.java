package it.polimi.ingsw.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    NetworkHandler networkHandler;

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("IP address of server?");
        String ip = scanner.nextLine();

        System.out.println("Port?");
        int PORT = scanner.nextInt();

        Socket server;
        try {
            server = new Socket(ip, PORT);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }

        networkHandler = new NetworkHandler(server, this);
        Thread networkHandlerThread = new Thread(networkHandler, "server" +server.getInetAddress().getHostAddress());
        networkHandlerThread.start();
    }
}
