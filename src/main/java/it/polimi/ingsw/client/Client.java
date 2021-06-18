package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.CLI.CLI;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.View;
import javafx.application.Application;

import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {
    NetworkHandler networkHandler;
    private static View view;

    public Client(View view) {
        Client.view = view;
    }

    public static void main(String[] args) {
        String arg = args[0];
        View userView;

        if(arg.equals("cli")){
            userView = new CLI();
            Client  client = new Client(userView);
            client.run();
        }
        else if(arg.equals("gui")){
            GUI.main(args);
        }
    }


    @Override
    public void run() {
        int PORT_NUMBER;
        String ip ;
        int match;

        int LOCAL_GAME = 1;
        int CONNECTED_GAME = 2;

        match = view.gameType();

        if (match == LOCAL_GAME) {
            HandlerSP handlerSP = new HandlerSP(view);
            view.setHandler(handlerSP);
            handlerSP.run();
        }
        else if (match == CONNECTED_GAME) {
            view.setupConnection();
            ip = view.getIp();
            PORT_NUMBER = view.getPortNumber();


            Socket server;
            try {
                server = new Socket(ip, PORT_NUMBER);
                networkHandler = new NetworkHandler(server, this, view);
                view.setHandler(networkHandler);
                Thread networkHandlerThread = new Thread(networkHandler, "server" + server.getInetAddress().getHostAddress());
                networkHandlerThread.start();
            } catch (IOException e) {
                System.out.println("Unable to connect!");
                System.exit(0);
            }
        }


        }
    }
