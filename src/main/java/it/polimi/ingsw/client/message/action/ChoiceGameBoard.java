package it.polimi.ingsw.client.message.action;

import it.polimi.ingsw.client.message.Message;

public class ChoiceGameBoard implements Message {
    private final int choice;

    public ChoiceGameBoard(int choice) {
        this.choice = choice;
    }

    public int getChoice() {
        return choice;
    }
}
