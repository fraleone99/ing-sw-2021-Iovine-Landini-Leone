package it.polimi.ingsw.client.message.action.storage;

import it.polimi.ingsw.client.message.action.UserAction;

public class DiscardResource implements UserAction {
    private final int shelf;
    private final int amount;

    public DiscardResource(int shelf, int amount) {
        this.shelf = shelf;
        this.amount = amount;
    }

    public int getShelf() {
        return shelf;
    }

    public int getAmount() {
        return amount;
    }
}
