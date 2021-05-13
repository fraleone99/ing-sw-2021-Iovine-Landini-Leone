package it.polimi.ingsw.client;

import it.polimi.ingsw.client.message.Ping;

import java.io.IOException;

public class Heartbeat implements Runnable {

    NetworkHandler networkHandler;

    public Heartbeat(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    @Override
    public void run() {
        while(networkHandler.isConnected()) {
            try {
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            networkHandler.send(new Ping());
        }
    }
}
