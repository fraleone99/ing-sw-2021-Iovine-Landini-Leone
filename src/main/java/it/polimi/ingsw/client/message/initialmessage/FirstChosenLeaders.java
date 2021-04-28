package it.polimi.ingsw.client.message.initialmessage;

import it.polimi.ingsw.client.message.Message;

public class FirstChosenLeaders implements Message {
    private int leader;

    public FirstChosenLeaders(int leader) {
        this.leader = leader;
    }

    public int getLeader() {
        return leader;
    }
}