package it.polimi.ingsw.client.message.action.production;

import it.polimi.ingsw.client.message.Message;


public class ActiveDevelopmentCard implements Message {
    private final int position;

    public ActiveDevelopmentCard(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
