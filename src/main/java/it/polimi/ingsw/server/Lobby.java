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

/**
 * This class manages the match creation.
 *
 * @author Lorenzo Iovine
 */
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


    /**
     * Creates a new lobby and registers the client
     * @param firstClient is the ClientHandler of the first client of the lobby
     * @param nickname is the nickname of the first client of the lobby
     * @param number is the player numbers of the lobby
     */
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


    /**
     * Adds a player to this lobby
     * @param clientHandler is the ClientHandler of the client who joined
     * @param nickname is the nickname of the client who joined
     */
    public void add(ClientHandler clientHandler, String nickname) {
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


    /**
     * Re-adds a player in the lobby after the crash
     * @param client is the ClientHandler of the client who re-joined the lobby
     * @param nickname is the nickname of the client who re-joined the lobby
     */
    public void reAdd(ClientHandler client, String nickname) {
        client.setNickname(nickname);

        for(String nick : nicknames) {
            namesToClient.get(nick).send(new SendMessage(nickname + " is back."));
        }

        clientToNames.put(client, nickname);
        namesToClient.put(nickname, client);
        nicknames.add(nickname);
        view.setNamesToClient(nickname, client, true);

        playersNumber++;

        client.registerObserver(this);

        System.out.println(nickname + " is back in the lobby number " + lobbyID);
    }


    /**
     * Removes client from the lobby when he crashed or the game is over
     * @param clientHandler is the ClientHandler of the client
     */
    public void removeConnection(ClientHandler clientHandler){
        String nick=clientToNames.get(clientHandler);
        System.out.println("Removing player "+nick+" from the lobby number " + lobbyID);
        namesToClient.remove(nick, clientHandler);
        nicknames.remove(nick);
        clientToNames.remove(clientHandler, nick);
        playersNumber--;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDisconnection(ClientHandler clientHandler) {
        //clientHandler.unregisterObserver(this);
        String nickname = clientToNames.get(clientHandler);
        removeConnection(clientHandler);
        for(String nick: nicknames){
            namesToClient.get(nick).send(new SendMessage("Player "+nickname+" left the game. Now there are "+playersNumber+" players in this Lobby."));
        }
        //removeConnection(clientHandler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePreparationOfLobby(){
        for(String nick: nicknames){
            namesToClient.get(nick).send(new SendMessage("All players joined the lobby. We are preparing the game!"));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePlayingNick(String nickname){
        for(String nick: nicknames){
            if(!nick.equals(nickname)){
                namesToClient.get(nick).send(new SendMessage(nickname+" is playing"));
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTurnChoice(String nickname, String message){
        for(String nick: nicknames){
            if(!nick.equals(nickname)){
                namesToClient.get(nick).send(new SendMessage(">"+nickname+message));
            }
        }
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public int getLobbyID() {
        return lobbyID;
    }


    /**
     * Prepares the game of this lobby and starts it
     */
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
