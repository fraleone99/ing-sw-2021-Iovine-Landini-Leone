package it.polimi.ingsw.client.message.action;

import it.polimi.ingsw.client.message.Message;

public class ChosenLine implements Message {
    private final int choice;

    public ChosenLine(int choice) {
        this.choice = choice;
    }

    public int getChoice() {
        return choice;
    }
}
