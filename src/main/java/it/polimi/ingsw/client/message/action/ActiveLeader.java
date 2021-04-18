package it.polimi.ingsw.client.message.action;

import it.polimi.ingsw.client.message.Message;


public class ActiveLeader implements Message {
    private final int position;

    public ActiveLeader(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
