package it.polimi.ingsw.client.message.initialmessage;

import it.polimi.ingsw.client.message.Message;

public class NumberOfPlayers implements Message {
    private final String number;

    public NumberOfPlayers(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
