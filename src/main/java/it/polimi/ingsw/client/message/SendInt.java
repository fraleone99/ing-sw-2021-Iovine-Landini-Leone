package it.polimi.ingsw.client.message;

/**
 * This message sends one int to the server.
 */
public class SendInt implements Message {
    private final int choice;

    public SendInt(int choice) {
        this.choice = choice;
    }

    public int getChoice() {
        return choice;
    }
}