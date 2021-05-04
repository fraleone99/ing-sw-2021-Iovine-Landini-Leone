package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.LocalSPController;

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
        int match;
        String nick;
        String config;

        do {
            System.out.println("Choose game mode:\n1) Local \n2) Connected match");
            match = Integer.parseInt(scanner.nextLine());
        } while (match != 1 && match != 2);

        if (match == 1) {
            System.out.println("Please insert your nickname: ");
            nick = scanner.nextLine();
            LocalSPController localController = new LocalSPController(nick);
            localController.localGame();
        } else {
            System.out.println("Default configuration? (localhost) [y/n]");
            config = scanner.nextLine();
            if (config.equalsIgnoreCase("y")) {
                PORT_NUMBER = DEFAULT_PORT;
                ip = LOCALHOST;
            } else {
                do {
                    System.out.println("ip:");
                    ip = scanner.nextLine();
                    System.out.println("Insert port number:");
                    PORT_NUMBER = scanner.nextInt();
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
            Thread networkHandlerThread = new Thread(networkHandler, "server" + server.getInetAddress().getHostAddress());
            networkHandlerThread.start();
        }
    }
}
