package it.polimi.ingsw.server;

import it.polimi.ingsw.server.answer.PlayersNumber;
import it.polimi.ingsw.server.answer.turnanswer.StartTurn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Lobby {
    private int playersNumber;
    private StartTurn start;
    private VirtualView view=new VirtualView();
    private Map<String, ClientHandler> clientNames = new HashMap<>();

    public void newLobby(ClientHandler firstClient) throws IOException, InterruptedException {
        String s = view.requestNickname(firstClient);
        clientNames.put(s,firstClient);

         String str=(String) clientNames.keySet().toArray()[0];
         System.out.println(str + " has joined the server.");

          playersNumber = view.requestPlayersNumber(firstClient);
        System.out.println("This lobby will contain " + playersNumber + " players");
    }

    public void add(ClientHandler clientHandler) throws IOException, InterruptedException {
        String s = view.requestNickname(clientHandler);
        clientNames.put(s,clientHandler);
    }

}
