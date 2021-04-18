package it.polimi.ingsw.client.message.action;

import it.polimi.ingsw.client.message.Message;


public class DiscardLeader implements Message {
    private final int position;

    public DiscardLeader(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
