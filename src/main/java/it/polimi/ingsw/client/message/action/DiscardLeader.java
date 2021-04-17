package it.polimi.ingsw.client.message.action;

public class DiscardLeader implements UserAction{
    private final int position;

    public DiscardLeader(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
