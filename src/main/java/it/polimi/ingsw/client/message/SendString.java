package it.polimi.ingsw.client.message;

public class SendString implements Message {
    private final String string;

    public SendString (String string) {
        this.string=string;
    }

    public String getString() {
        return string;
    }
}
