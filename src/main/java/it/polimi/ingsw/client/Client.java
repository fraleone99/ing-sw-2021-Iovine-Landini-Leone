package it.polimi.ingsw.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    NetworkHandler networkHandler;

    private final String LOCALHOST = "127.0.0.1";
    private final int DEFAULT_PORT = 3456;

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        int PORT_NUMBER;
        String ip;

        System.out.println("Default configuration? (localhost) [y/n]");
        String config = scanner.nextLine();
        if(config.equalsIgnoreCase("y")){
            PORT_NUMBER = DEFAULT_PORT;
            ip = LOCALHOST;
        }
        else {
            do {
                System.out.println("Insert port number:");
                PORT_NUMBER = scanner.nextInt();
                System.out.println("ip:");
                ip = scanner.nextLine();
            } while (PORT_NUMBER < 1024);
        }

        Socket server;
        try {
            server = new Socket(ip, PORT_NUMBER);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }

        networkHandler = new NetworkHandler(server, this);
        Thread networkHandlerThread = new Thread(networkHandler, "server" +server.getInetAddress().getHostAddress());
        networkHandlerThread.start();
    }
}
