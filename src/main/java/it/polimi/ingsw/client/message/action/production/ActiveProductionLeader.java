package it.polimi.ingsw.client.message.action.production;

import it.polimi.ingsw.client.message.Message;
import it.polimi.ingsw.model.enumeration.Resource;

public class ActiveProductionLeader implements Message {
    private final int position;
    private final Resource output;

    public ActiveProductionLeader(int position, Resource output) {
        this.position = position;
        this.output = output;
    }

    public int getPosition() {
        return position;
    }

    public Resource getOutput() {
        return output;
    }
}
