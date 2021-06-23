package it.polimi.ingsw.server;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.observer.ConnectionObserver;
import it.polimi.ingsw.observer.VirtualViewObserver;
import it.polimi.ingsw.server.answer.initialanswer.InitialSetup;
import it.polimi.ingsw.server.answer.request.SendMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Lobby implements ConnectionObserver, VirtualViewObserver {
    private int playersNumber;
    private final int lobbyID;
    private final VirtualView view;
    private final Map<ClientHandler, String> clientToNames = new HashMap<>();
    private final Map<String, ClientHandler> namesToClient = new HashMap<>();
    private final ArrayList<String> nicknames = new ArrayList<>();

    public Lobby(int lobbyID, VirtualView view) {
        this.lobbyID = lobbyID;
        this.view = view;
    }

    public void newLobby(ClientHandler firstClient, String nickname, int number) {
        clientToNames.put(firstClient, nickname);
        namesToClient.put(nickname, firstClient);
        nicknames.add(nickname);
        firstClient.setNickname(nickname);
        view.setNamesToClient(nickname, firstClient, false);

        firstClient.registerObserver(this);
        view.registerObserver(this);

        System.out.println(nickname + " has joined the lobby number " + lobbyID);

        playersNumber = number;

        System.out.println("The lobby number "+ lobbyID +" will contain " + playersNumber + " players");

        view.waitingRoom(firstClient);

        firstClient.send(new InitialSetup());
    }

    public void add(ClientHandler clientHandler, String nickname) throws IOException{
        clientHandler.setNickname(nickname);

        for(String nick: nicknames){
            namesToClient.get(nick).send(new SendMessage(Constants.ANSI_BLUE + nickname + " joins the game. "+(nicknames.size()+1)+" players have already joined this Lobby."+Constants.ANSI_RESET));
        }

        clientToNames.put(clientHandler, nickname);
        namesToClient.put(nickname, clientHandler);
        nicknames.add(nickname);
        view.setNamesToClient(nickname, clientHandler, false);

        clientHandler.registerObserver(this);

        if(nicknames.size()<playersNumber){
            view.waitingRoom(clientHandler);
        } else {
            view.waitingRoom(clientHandler);
            view.prepareTheLobby();
        }

        System.out.println(nickname + " has joined the lobby number " + lobbyID);

        clientHandler.send(new InitialSetup());
    }

    public void reAdd(ClientHandler client, String nickname) {
        client.setNickname(nickname);

        for(String nick : nicknames) {
            namesToClient.get(nick).send(new SendMessage(Constants.ANSI_BLUE + nickname + " is back."+Constants.ANSI_RESET));
        }

        clientToNames.put(client, nickname);
        namesToClient.put(nickname, client);
        nicknames.add(nickname);
        view.setNamesToClient(nickname, client, true);

        playersNumber++;

        client.registerObserver(this);

        System.out.println(nickname + " is back in the lobby number " + lobbyID);
    }

    public void removeConnection(ClientHandler clientHandler){
        String nick=clientToNames.get(clientHandler);
        System.out.println("Removing player "+nick+" from the lobby number " + lobbyID);
        namesToClient.remove(nick, clientHandler);
        nicknames.remove(nick);
        clientToNames.remove(clientHandler, nick);
        playersNumber--;
    }

    @Override
    public void updateDisconnection(ClientHandler clientHandler) {
        //clientHandler.unregisterObserver(this);
        String nickname = clientToNames.get(clientHandler);
        removeConnection(clientHandler);
        for(String nick: nicknames){
            namesToClient.get(nick).send(new SendMessage(Constants.ANSI_RED+"Player "+/*clientToNames.get(clientHandler)*/nickname+" left the game. Now there are "+playersNumber+" players in this Lobby."+Constants.ANSI_RESET));
        }
        //removeConnection(clientHandler);
    }

    @Override
    public void updatePreparationOfLobby(){
        for(String nick: nicknames){
            namesToClient.get(nick).send(new SendMessage("All players joined the lobby. We are preparing the game!"));
        }
    }

    @Override
    public void updatePlayingNick(String nickname){
        for(String nick: nicknames){
            if(!nick.equals(nickname)){
                namesToClient.get(nick).send(new SendMessage(nickname+" is playing"));
            }
        }
    }

    @Override
    public void updateTurnChoice(String nickname, String message){
        for(String nick: nicknames){
            if(!nick.equals(nickname)){
                namesToClient.get(nick).send(new SendMessage("->"+nickname+message));
            }
        }
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public Map<String, ClientHandler> getNamesToClient() {
        return namesToClient;
    }

    public void prepareTheGame(){
        Thread t=new Thread( () -> {
            System.out.println("Preparing the game of lobby number " + lobbyID);
            Collections.shuffle(nicknames);

            Controller controller = new Controller(nicknames, view);

            view.setController(controller);
            view.setClientConnectionToController();

            controller.play();
        }
        );

        t.start();
    }
}
