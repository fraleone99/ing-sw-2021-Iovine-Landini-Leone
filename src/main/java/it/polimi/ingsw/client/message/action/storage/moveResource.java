package it.polimi.ingsw.client.message.action.storage;

import it.polimi.ingsw.client.message.action.UserAction;

public class moveResource implements UserAction {
    private final int source;
    private final int destination;

    public moveResource(int source, int destination) {
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
