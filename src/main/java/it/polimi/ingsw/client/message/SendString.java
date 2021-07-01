package it.polimi.ingsw.client.message;


/**
 * This message sends one string to the server.
 */
public class SendString implements Message {
    private final String string;

    public SendString (String string) {
        this.string=string;
    }

    public String getString() {
        return string;
    }
}
