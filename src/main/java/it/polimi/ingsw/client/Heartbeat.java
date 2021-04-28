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
        while(true) {
            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                networkHandler.send(new Ping());
                //System.out.println("Sending ping...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
