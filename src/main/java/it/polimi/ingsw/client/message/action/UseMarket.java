package it.polimi.ingsw.client.message.action;

import it.polimi.ingsw.client.message.Message;


public class UseMarket implements Message {
    private final int choice;

    public UseMarket(int choice) {
        this.choice = choice;
    }

    public int getChoice() {
        return choice;
    }
}
