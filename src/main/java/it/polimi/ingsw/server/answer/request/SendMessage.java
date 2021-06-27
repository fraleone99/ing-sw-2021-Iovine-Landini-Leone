package it.polimi.ingsw.server.answer.request;

import it.polimi.ingsw.server.answer.Answer;


/**
 * Message containing a notification for the client.
 *
 * @author Lorenzo Iovine
 */
public class SendMessage implements Answer {
    private final String message;

    public SendMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
