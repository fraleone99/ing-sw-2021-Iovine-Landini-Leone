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
    private Map<ClientHandler, String> clientToNames = new HashMap<>();
    private Map<String, ClientHandler> namesToClient = new HashMap<>();
    private ArrayList<String> nicknames = new ArrayList<>();

    public Lobby(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    public void newLobby(ClientHandler firstClient) throws IOException, InterruptedException {
        String s=view.askHandShake(firstClient);
        System.out.println(s);

        s = view.requestNickname(firstClient);
        clientToNames.put(firstClient, s);
        namesToClient.put(s, firstClient);
        nicknames.add(s);
        view.setNamesToClient(s,firstClient);

        String str = clientToNames.get(firstClient);
        System.out.println(str + " has joined the lobby number " + lobbyID);

        playersNumber= Integer.parseInt( view.requestPlayersNumber(firstClient) );
        System.out.println("The lobby number "+ lobbyID +" will contain " + playersNumber + " players");

        view.waitingRoom(firstClient);
    }

    public void add(ClientHandler clientHandler) throws IOException, InterruptedException {
        String s=view.askHandShake(clientHandler);
        System.out.println(s);

        s = view.requestNickname(clientHandler);

        while(nicknames.contains(s) || s.equals("laulo") || s.equals("Laulo")){
            s=view.InvalidNickname(clientHandler);
        }

        clientToNames.put(clientHandler,s);
        namesToClient.put(s, clientHandler);
        nicknames.add(s);
        view.setNamesToClient(s,clientHandler);

        if(nicknames.size()<playersNumber){
            view.waitingRoom(clientHandler);
        } else {
            view.waitingRoom(clientHandler);
            view.prepareTheLobby(clientHandler);
        }

        String str= clientToNames.get(clientHandler);
        System.out.println(str + " has joined the lobby number " + lobbyID);
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public Map<String, ClientHandler> getNamesToClient() {
        return namesToClient;
    }

    public void prepareTheGame(){
        Thread t=new Thread( () -> {
            System.out.println("Prepare game of the lobby number " + lobbyID);
            Collections.shuffle(nicknames);

            Controller controller = new Controller(nicknames, view);

            controller.play();
        }
        );

        t.start();
    }
}
