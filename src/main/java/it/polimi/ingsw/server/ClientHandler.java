package it.polimi.ingsw.server;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.observer.ConnectionObservable;
import it.polimi.ingsw.client.message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientHandler extends ConnectionObservable implements Runnable {
    private final Socket socketClient;
    private boolean isEnd = false;

    private  ObjectOutputStream output;
    private  ObjectInputStream input;
    private final Server server;

    private boolean isReady;
    private String answer;
    private int number;
    private int number2;

    private String nickname;


    private static final int CONNECTION_TIMEOUT = 10000;

    private final AtomicBoolean active = new AtomicBoolean(false);

    private final Object lock = new Object();

    public ClientHandler(Socket socketClient,Server server) {
        this.server=server;
        this.socketClient = socketClient;
        this.isReady=false;
    }

    public void isEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public String getNickname() {
        return nickname;
    }

    public void send(Object message) {
        //System.out.println("[SERVER] Sending message " + message.toString() + "to" + nickname);
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
        Message next = null;
        try {
            next = (Message) input.readObject();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*if(!(next instanceof Ping))
            System.out.println("[SERVER] Reading from client " + nickname + " " +  next.toString());*/
        return next;
    }


    public void processClientMessage(Message message){
        synchronized (lock) {
            if(message instanceof SendString) {
                answer=((SendString) message).getString();
            } else if(message instanceof SendInt) {
                number=(((SendInt) message).getChoice());
            } else if(message instanceof SendDoubleInt) {
                number=(((SendDoubleInt) message).getChoice1());
                number2=(((SendDoubleInt) message).getChoice2());
            }
            isReady=true;
            lock.notifyAll();
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

    public int getNumber() {
        return number;
    }

    public int getNumber2() {
        return number2;
    }

    public void closeConnection(){
        if (!active.get()) return;

        active.set(false);

        synchronized (server.getLock()) {
            server.setPlayerReady(true);
            server.getLock().notifyAll();
        }

        if(!isEnd) {
            synchronized (lock) {
                server.removeClient(this, nickname);
                number = -1;
                number2 = -1;
                isReady = true;
                lock.notifyAll();
            }
        } else {
            server.clientDisconnected(nickname);
        }

        System.out.println(Constants.ANSI_RED + "[SERVER] client disconnected." + Constants.ANSI_RESET);
        notifyDisconnection(this);

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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void run() {
        handleClientConnection();
    }
}
