package it.polimi.ingsw.client.message;

import it.polimi.ingsw.client.message.Message;

public class SendInt implements Message {
    private final int choice;

    public SendInt(int choice) {
        this.choice = choice;
    }

    public int getChoice() {
        return choice;
    }
}