package it.polimi.ingsw.client.message;

import it.polimi.ingsw.client.message.Message;

public class SendString implements Message {
    private String string;

    public SendString (String string) {
        this.string=string;
    }

    public String getString() {
        return string;
    }
}
