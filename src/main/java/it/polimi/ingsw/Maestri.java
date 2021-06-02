package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.server.Server;

import java.util.Scanner;

public class Maestri {
    public static void main(String[] args) {
        System.out.println("WELCOME! What do you want to launch?");
        System.out.println("0. SERVER\n1. CLIENT (CLI INTERFACE)\n2. CLIENT (GUI INTERFACE)");
        String[] mode = new String[1];
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("Insert your choice: ");
            input = scanner.nextLine();
        }while (!(input.equals("0") || input.equals("1") || input.equals("2")));


        switch (input){
            case "0":
                Server.main(null);
                break;
            case "1":
                mode[0] = "cli";
                Client.main(mode);
                break;
            case "2":
                mode[0] = "gui";
                Client.main(mode);
                break;
            default:
                System.out.println("invalid input!\n");


        }
    }
}
