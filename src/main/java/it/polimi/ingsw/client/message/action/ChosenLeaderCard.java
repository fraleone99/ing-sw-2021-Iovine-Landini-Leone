package it.polimi.ingsw.client.message.action;

import it.polimi.ingsw.client.message.Message;


public class ChosenLeaderCard implements Message {
    private final int position;

    public ChosenLeaderCard(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
