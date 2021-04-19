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
            clientNames.put(view.requestNickname(firstClient),firstClient);

            //view.requestPlayersNumber(firstClient);

            //playersNumber = firstClient.getPlayersNumber();

            String s=(String) clientNames.keySet().toArray()[0];
            System.out.println(s);
    }

}
