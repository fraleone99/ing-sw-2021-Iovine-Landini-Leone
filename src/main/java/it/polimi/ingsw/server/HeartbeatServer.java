package it.polimi.ingsw.server;

/**
 * This is the heartbeat of the server.
 */
public class HeartbeatServer  implements Runnable{
    private final ClientHandler clientHandler;
    public HeartbeatServer(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void run() {
        while(clientHandler.isConnected()) {
            try {
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clientHandler.pong();
        }
    }
}
