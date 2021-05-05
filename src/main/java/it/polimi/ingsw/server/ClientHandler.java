package it.polimi.ingsw.server;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.client.message.action.ChoiceGameBoard;
import it.polimi.ingsw.client.message.action.ChosenLeaderCard;
import it.polimi.ingsw.client.message.action.ChosenResource;
import it.polimi.ingsw.client.message.action.TurnType;
import it.polimi.ingsw.client.message.action.*;
import it.polimi.ingsw.client.message.initialmessage.ClientConnection;
import it.polimi.ingsw.client.message.Message;
import it.polimi.ingsw.client.message.initialmessage.FirstChosenLeaders;
import it.polimi.ingsw.client.message.initialmessage.NumberOfPlayers;
import it.polimi.ingsw.client.message.initialmessage.SendNickname;
import it.polimi.ingsw.observer.ConnectionObservable;
import it.polimi.ingsw.observer.VirtualViewObserver;
import it.polimi.ingsw.server.answer.ChooseResource;
import it.polimi.ingsw.client.message.*;
import it.polimi.ingsw.server.answer.Pong;
import it.polimi.ingsw.server.answer.turnanswer.ChooseLine;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientHandler extends ConnectionObservable implements Runnable {
    private final Socket socketClient;

    private  ObjectOutputStream output;
    private  ObjectInputStream input;

    private boolean isReady;
    private String answer;

    private boolean isConnected;


    private static final int CONNECTION_TIMEOUT = 4000;

    private AtomicBoolean active = new AtomicBoolean(false);

    private final Thread heartbeat;

    ConnectionObservable connectionObservable = new ConnectionObservable();

    private final Object lock = new Object();


    public ClientHandler(Socket socketClient) {
        this.socketClient = socketClient;
        this.isReady=false;

        heartbeat = new Thread(()->{
            while(active.get()){
                try {
                    Thread.sleep(CONNECTION_TIMEOUT/2);
                    send(new Pong());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void send(Object message) {
        synchronized (lock) {
            while (!active.get()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                output.reset();
                output.writeObject(message);
                output.flush();
            } catch (IOException e) {
                closeConnection();
            }

        }
    }

    public void handleClientConnection(){
        try {
            this.socketClient.setSoTimeout(CONNECTION_TIMEOUT);

            output = new ObjectOutputStream(socketClient.getOutputStream());
            input  = new ObjectInputStream(socketClient.getInputStream());

            synchronized (lock) {
                active.set(true);
                lock.notifyAll();
            }

            heartbeat.start();

            while(active.get()) {
                Message message = readFromClient();
                if(!(message instanceof Ping))
                    processClientMessage(message);
            }

        } catch (IOException e) {
            closeConnection();
        }


    }

    public Message readFromClient() throws IOException {
        Object next = null;
        try {
            next = input.readObject();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (Message) next;
    }


    public void processClientMessage(Message message){

        synchronized (lock) {
            if (message instanceof ClientConnection) {
                answer = ((ClientConnection) message).getMessage();
                isReady = true;
                lock.notifyAll();
            } else if (message instanceof SendNickname) {
                answer = ((SendNickname) message).getNickname();
                isReady = true;
                lock.notifyAll();
            } else if (message instanceof NumberOfPlayers) {
                answer = ((NumberOfPlayers) message).getNumber();
                isReady = true;
                lock.notifyAll();
            } else if (message instanceof ChosenResource) {
                answer = String.valueOf(((ChosenResource) message).getResource());
                isReady = true;
                lock.notifyAll();
            } else if (message instanceof Ping) {
                System.out.println("ping received!");
            } else if (message instanceof FirstChosenLeaders) {
                answer = String.valueOf(((FirstChosenLeaders) message).getLeader());
                isReady = true;
                lock.notifyAll();
            } else if (message instanceof ChoiceGameBoard) {
                answer = String.valueOf(((ChoiceGameBoard) message).getChoice());
                isReady = true;
                lock.notifyAll();
            } else if (message instanceof TurnType) {
                answer = String.valueOf(((TurnType) message).getTurn());
                isReady = true;
                lock.notifyAll();
            } else if (message instanceof ChosenLeaderCard) {
                answer = String.valueOf(((ChosenLeaderCard) message).getPosition());
                isReady = true;
                lock.notifyAll();
            } else if (message instanceof ChosenLine) {
                answer = String.valueOf(((ChosenLine) message).getChoice());
                isReady = true;
                lock.notifyAll();
            }
        }
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String getAnswer() {
        return answer;
    }


    public boolean isConnected() {
        return isConnected;
    }

    public boolean isActive() {
        return active.get();
    }

    public synchronized void closeConnection(){
        if(!active.get()) return;

        active.set(false);

        System.out.println(Constants.ANSI_RED +  "[SERVER] client disconnected." + Constants.ANSI_RESET);
        notifyDisconnection(this);

        isConnected = false;

        try {

            input.close();
            output.close();
            socketClient.close();


        } catch (IOException ignored) {
        }
    }

    public Object getLock() {
        return lock;
    }

    @Override
    public void run() {
        handleClientConnection();
    }
}
