package it.polimi.ingsw.server;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.observer.ConnectionObservable;
import it.polimi.ingsw.client.message.*;
import it.polimi.ingsw.server.answer.Pong;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * This class handles network connection on the Server side
 *
 * @author Lorenzo Iovine
 */
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

    private boolean isConnected = true;


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

    /**
     * Messages sender from server to client
     * @param message object sent to client
     */
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

    public void pong() {
        try {
            output.writeObject(new Pong());
            output.flush();
        } catch (IOException e) {
            closeConnection();
        }
    }

    /**
     * This method handles the connection with the client
     */
    public void handleClientConnection(){
        HeartbeatServer heartbeatServer = new HeartbeatServer(this);
        Thread heartbeatThread = new Thread(heartbeatServer);
        heartbeatThread.start();

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


    /**
     * Reads the client's answer
     * @return message received by client
     * @throws IOException if the reading goes wrong
     */
    public Message readFromClient() throws IOException {
        Message next = null;
        try {
            next = (Message) input.readObject();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return next;
    }


    /**
     * Manages client's answer
     * @param message object received by client
     */
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


    /**
     * Closes the connection with the client when the game is over or the client crashed
     */
    public void closeConnection(){
        if (!active.get()) return;

        active.set(false);

        synchronized (server.getLock()) {
            server.setPlayerReady(true);
            server.getLock().notifyAll();
        }

        synchronized (lock) {
            if (!isEnd) {
                server.removeClient(this, nickname);
                number = -1;
                number2 = -1;
                isReady = true;
            } else {
                server.clientDisconnected(nickname);
            }

            System.out.println(Constants.ANSI_RED + "[SERVER] client disconnected." + Constants.ANSI_RESET);
            notifyDisconnection(this);

            try {
                input.close();
                output.close();
                socketClient.close();
                lock.notifyAll();
            } catch (IOException e) {
                System.out.println("ERROR");
            }
        }
        isConnected = false;
    }

    public Object getLock() {
        return lock;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Initializes the connection with the client
     */
    @Override
    public void run() {
        handleClientConnection();
    }
}
