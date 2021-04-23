package it.polimi.ingsw.client.message.action;

import it.polimi.ingsw.client.message.Message;

public class ChosenResource implements Message {
    private int resource;

    public ChosenResource(int resource) {
        this.resource = resource;
    }

    public int getResource() {
        return resource;
    }
}
