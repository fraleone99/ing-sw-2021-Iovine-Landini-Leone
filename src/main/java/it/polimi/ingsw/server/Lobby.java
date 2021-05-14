package it.polimi.ingsw.server;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.observer.ConnectionObserver;
import it.polimi.ingsw.observer.VirtualViewObserver;
import it.polimi.ingsw.server.answer.InitialSetup;
import it.polimi.ingsw.server.answer.SendMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Lobby implements ConnectionObserver, VirtualViewObserver {
    private int playersNumber;
    private final int lobbyID;
    private final VirtualView view=new VirtualView();
    private final Map<ClientHandler, String> clientToNames = new HashMap<>();
    private final Map<String, ClientHandler> namesToClient = new HashMap<>();
    private final ArrayList<String> nicknames = new ArrayList<>();

    public Lobby(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    public void newLobby(ClientHandler firstClient) throws InterruptedException {
        String s = view.askHandShake(firstClient);
        System.out.println(s);

        s = view.requestNickname(firstClient);
        clientToNames.put(firstClient, s);
        namesToClient.put(s, firstClient);
        nicknames.add(s);
        firstClient.setNickname(s);
        view.setNamesToClient(s,firstClient);


        firstClient.registerObserver(this);
        view.registerObserver(this);


        String str = clientToNames.get(firstClient);
        System.out.println(str + " has joined the lobby number " + lobbyID);

        playersNumber = view.requestPlayersNumber(firstClient);

        System.out.println("The lobby number "+ lobbyID +" will contain " + playersNumber + " players");

        view.waitingRoom(firstClient);

        firstClient.send(new InitialSetup());
    }

    public void add(ClientHandler clientHandler) throws IOException, InterruptedException {
        String s=view.askHandShake(clientHandler);
        System.out.println(s);

        s = view.requestNickname(clientHandler);
        clientHandler.setNickname(s);

        while(nicknames.contains(s) || s.equals("laulo") || s.equals("Laulo")){
            s=view.InvalidNickname(clientHandler);
        }


        for(String nick: nicknames){
            namesToClient.get(nick).send(new SendMessage(Constants.ANSI_BLUE +  s + " joins the game. "+(nicknames.size()+1)+" players have already joined this Lobby."+Constants.ANSI_RESET));
        }


        clientToNames.put(clientHandler,s);
        namesToClient.put(s, clientHandler);
        nicknames.add(s);
        view.setNamesToClient(s,clientHandler);


        clientHandler.registerObserver(this);


        if(nicknames.size()<playersNumber){
            view.waitingRoom(clientHandler);
        } else {
            view.waitingRoom(clientHandler);
            view.prepareTheLobby();
        }

        String str= clientToNames.get(clientHandler);
        System.out.println(str + " has joined the lobby number " + lobbyID);

        clientHandler.send(new InitialSetup());
    }

    public String removeConnection(ClientHandler clientHandler){
        String nick=clientToNames.get(clientHandler);
        System.out.println("Removing player "+nick);
        namesToClient.remove(nick);
        nicknames.remove(nick);
        view.removeNamesToClient(nick, clientHandler);
        clientToNames.remove(clientHandler);
        playersNumber=playersNumber-1;

        return nick;
    }

    @Override
    public void updateDisconnection(ClientHandler clientHandler) {
        //clientHandler.unregisterObserver(this);
        String nickname=removeConnection(clientHandler);
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
                namesToClient.get(nick).send(new SendMessage("++++++++++++++++++++++++++++++++++++++++++++++\n"
                        +Constants.ANSI_GREEN+nickname+Constants.ANSI_RESET+" is playing"));
            }
        }
    }

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
