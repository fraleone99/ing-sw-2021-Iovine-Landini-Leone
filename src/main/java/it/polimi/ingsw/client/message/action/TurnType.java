package it.polimi.ingsw.client.message.action;

import it.polimi.ingsw.client.message.Message;

public class TurnType implements Message {
    private final int turn;

    public TurnType(int choice) {
        this.turn = choice;
    }

    public int getTurn() {
        return turn;
    }
}
