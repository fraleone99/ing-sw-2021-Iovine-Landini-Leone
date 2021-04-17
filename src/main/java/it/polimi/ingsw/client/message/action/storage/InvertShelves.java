package it.polimi.ingsw.client.message.action.storage;

import it.polimi.ingsw.client.message.action.UserAction;

public class InvertShelves implements UserAction {
    private final int s1;
    private final int s2;

    public InvertShelves(int s1, int s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public int getS1() {
        return s1;
    }

    public int getS2() {
        return s2;
    }
}
