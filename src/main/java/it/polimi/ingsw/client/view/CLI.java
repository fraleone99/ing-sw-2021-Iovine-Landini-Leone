package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.server.answer.PlayersNumber;
import it.polimi.ingsw.server.answer.RequestNickname;

import java.util.Scanner;

public class CLI implements View{
    private final Scanner in;

    public CLI() {
        this.in = new Scanner(System.in);
    }

    private static void startGame() {
        System.out.println("\n Welcome to" +
                "_________   _______  _______  _______  _______ _________ _______ _________   ______   _______  _            \n" +
                "\\__   __/  (       )(  ___  )(  ____ \\(  ____ \\\\__   __/(  ____ )\\__   __/  (  __  \\ (  ____ \\( \\           \n" +
                "   ) (     | () () || (   ) || (    \\/| (    \\/   ) (   | (    )|   ) (     | (  \\  )| (    \\/| (           \n" +
                "   | |     | || || || (___) || (__    | (_____    | |   | (____)|   | |     | |   ) || (__    | |           \n" +
                "   | |     | |(_)| ||  ___  ||  __)   (_____  )   | |   |     __)   | |     | |   | ||  __)   | |           \n" +
                "   | |     | |   | || (   ) || (            ) |   | |   | (\\ (      | |     | |   ) || (      | |           \n" +
                "___) (___  | )   ( || )   ( || (____/\\/\\____) |   | |   | ) \\ \\_____) (___  | (__/  )| (____/\\| (____/\\     \n" +
                "\\_______/  |/     \\||/     \\|(_______/\\_______)   )_(   |/   \\__/\\_______/  (______/ (_______/(_______/     \n" +
                "                                                                                                            \n" +
                " _______ _________ _        _______  _______  _______ _________ _______  _______  _       _________ _______ \n" +
                "(  ____ )\\__   __/( (    /|(  ___  )(  ____ \\(  ____ \\\\__   __/(       )(  ____ \\( (    /|\\__   __/(  ___  )\n" +
                "| (    )|   ) (   |  \\  ( || (   ) || (    \\/| (    \\/   ) (   | () () || (    \\/|  \\  ( |   ) (   | (   ) |\n" +
                "| (____)|   | |   |   \\ | || (___) || (_____ | |         | |   | || || || (__    |   \\ | |   | |   | |   | |\n" +
                "|     __)   | |   | (\\ \\) ||  ___  |(_____  )| |         | |   | |(_)| ||  __)   | (\\ \\) |   | |   | |   | |\n" +
                "| (\\ (      | |   | | \\   || (   ) |      ) || |         | |   | |   | || (      | | \\   |   | |   | |   | |\n" +
                "| ) \\ \\_____) (___| )  \\  || )   ( |/\\____) || (____/\\___) (___| )   ( || (____/\\| )  \\  |   | |   | (___) |\n" +
                "|/   \\__/\\_______/|/    )_)|/     \\|\\_______)(_______/\\_______/|/     \\|(_______/|/    )_)   )_(   (_______)\n" +
                "                                                                                                            ");
        }


    @Override
    public void handShake(String welcome) {
        System.out.println(welcome);
    }

    @Override
    public String askPlayerNumber(String message) {
        String number;
        System.out.println(message);
        do {
            Scanner scanner = new Scanner(System.in);
            number = scanner.nextLine();
            if(Integer.parseInt(number)<1 || Integer.parseInt(number)>4){
                System.out.println("Incorrect number, please try again:");
            }
        } while (Integer.parseInt(number)<1 || Integer.parseInt(number)>4);
        return number;
    }

    @Override
    public String askNickname(String message) {
        String nickname;
        System.out.println(message);
        nickname = in.nextLine();
        return  nickname;
    }
}
