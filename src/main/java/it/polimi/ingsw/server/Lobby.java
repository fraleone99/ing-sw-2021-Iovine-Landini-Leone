package it.polimi.ingsw.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Lobby {
    private int playersNumber;
    private int numberOfLobby;
    private VirtualView view=new VirtualView();
    private Map<ClientHandler, String> clientNames = new HashMap<>();
    private Server server=new Server();

    public void newLobby(ClientHandler firstClient) throws IOException, InterruptedException {
        setNumberOfLobby();

        String s=view.askHandShake(firstClient);
        System.out.println(s);

        s = view.requestNickname(firstClient);
        clientNames.put(firstClient, s);

        String str=(String) clientNames.get(firstClient);
        System.out.println(str + " has joined the lobby number " + numberOfLobby);

        playersNumber= Integer.parseInt( view.requestPlayersNumber(firstClient) );
        System.out.println("The lobby number "+ numberOfLobby +" will contain " + playersNumber + " players");
    }

    public void add(ClientHandler clientHandler) throws IOException, InterruptedException {
        String s=view.askHandShake(clientHandler);
        System.out.println(s);

        s = view.requestNickname(clientHandler);
        clientNames.put(clientHandler,s);

        String str=(String) clientNames.get(clientHandler);
        System.out.println(str + " has joined the lobby number " + numberOfLobby);
    }

    public void setNumberOfLobby() {
        this.numberOfLobby = (server.getNumberOfLobbies())+1;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }
}
