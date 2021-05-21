package it.polimi.ingsw.client.view.CLI;


import it.polimi.ingsw.Constants;
import it.polimi.ingsw.client.Handler;
import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.message.SendString;

import java.util.ArrayList;
import java.util.Scanner;

public class CLILogin {
    private final Scanner in;
    private Handler handler = null;
    private  String ip;
    private int portNumber;
    private final CLIInitialize initialize;

    public CLILogin(CLIInitialize initialize) {
        this.initialize = initialize;
        this.in = new Scanner(System.in);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public int gameType() {
        return initialize.askInt(1, 2, "Choose game mode:\n1) Local \n2) Connected match");
    }

    public void startGame() {
        System.out.println(Constants.MAESTRI_RINASCIMENTO + Constants.AUTHORS);
    }

    public void setupConnection() {
        System.out.println("Default configuration? (localhost) [y/n]");
        String config = in.nextLine();

        if(config.equals("y")){
            ip = "127.0.0.1";
            portNumber = 3456;
        }
        else{
            System.out.println("ip:");
            ip = in.nextLine();
            portNumber = initialize.askInt(1024, 65535, "Insert Port number: ");
        }
    }

    public String getIp() {
        return ip;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void Handshake(String welcome) {
        Thread t=new Thread( () -> {
            startGame();
            System.out.println(welcome);
            handler.send(new SendString("Client connected!"));
        }
        );
        t.start();
    }

    public void askPlayerNumber(String message) {
        Thread t=new Thread( () -> {
            int number;

            number = initialize.askInt(1, 4, message);

            handler.send(new SendInt(number));
        }
        );
        t.start();
    }

    public void askNickname(String message) {
        Thread t=new Thread( () -> {
            String nickname;
            System.out.println(message);
            nickname = in.nextLine();
            handler.send(new SendString(nickname));
        }
        );
        t.start();
    }

    public void askResource(String message) {
        Thread t=new Thread( () -> {
            int resource;

            System.out.println(message);

            resource = initialize.askInt(1, 4, "Press the corresponding number:" +
                    "\n1) COIN\n2) STONE\n3) SHIELD\n4) SERVANT");

            handler.send(new SendInt(resource));
        }
        );
        t.start();
    }

    public void askLeaderToDiscard(ArrayList<Integer> IdLeaders) {
        Thread t=new Thread( () -> {
            int card;

            for (int i = 0; i < IdLeaders.size(); i++) {
                System.out.println(i + 1 + ")");
                System.out.println(initialize.printLeaderCard(initialize.getLeaderDeck().getFromID(IdLeaders.get(i))));
            }

            card = initialize.askInt(1, IdLeaders.size(), "Enter the corresponding number:");

            handler.send(new SendInt(card));
        }
        );
        t.start();
    }
}
