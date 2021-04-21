package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Lobby {
    private int playersNumber;
    private final int lobbyID;
    private VirtualView view=new VirtualView();
    private Map<ClientHandler, String> clientNames = new HashMap<>();
    private ArrayList<String> nicknames = new ArrayList<>();

    public Lobby(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    public void newLobby(ClientHandler firstClient) throws IOException, InterruptedException {

        String s=view.askHandShake(firstClient);
        System.out.println(s);

        s = view.requestNickname(firstClient);
        clientNames.put(firstClient, s);
        nicknames.add(s);

        String str=  clientNames.get(firstClient);
        System.out.println(str + " has joined the lobby number " + lobbyID);

        playersNumber= Integer.parseInt( view.requestPlayersNumber(firstClient) );
        System.out.println("The lobby number "+ lobbyID +" will contain " + playersNumber + " players");
    }

    public void add(ClientHandler clientHandler) throws IOException, InterruptedException {
        String s=view.askHandShake(clientHandler);
        System.out.println(s);

        s = view.requestNickname(clientHandler);

        while(nicknames.contains(s) || s.equals("laulo") || s.equals("Laulo")){
            s=view.InvalidNickname(clientHandler);
        }

        clientNames.put(clientHandler,s);
        nicknames.add(s);

        String str= clientNames.get(clientHandler);
        System.out.println(str + " has joined the lobby number " + lobbyID);
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void prepareTheGame(){
        Thread t=new Thread( () -> {
            System.out.println("Prepare game of the lobby number " + lobbyID);
            Collections.shuffle(nicknames);

            Controller controller=new Controller(nicknames);

            controller.play();
        }
        );

        t.start();
    }
}
