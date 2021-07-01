package it.polimi.ingsw.client;

import it.polimi.ingsw.client.message.Ping;

public class HeartbeatClient implements Runnable {

    private final NetworkHandler networkHandler;

    public HeartbeatClient(NetworkHandler networkHandler) {
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
