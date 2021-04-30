package it.polimi.ingsw.client.message.action.storage;

import it.polimi.ingsw.client.message.Message;


public class MoveResource implements Message {
    private final int source;
    private final int destination;

    public MoveResource(int source, int destination) {
        this.source = source;
        this.destination = destination;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }
}
