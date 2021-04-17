package it.polimi.ingsw.client.message.action;

public class ActiveLeader implements UserAction{
    private final int position;

    public ActiveLeader(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
