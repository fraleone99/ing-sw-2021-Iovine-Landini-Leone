package it.polimi.ingsw.server.answer.request;

import it.polimi.ingsw.server.answer.Answer;


/**
 * Message requesting one string to the client.
 *
 * @author Lorenzo Iovine
 */
public class RequestString implements Answer {
    private final String message;

    public RequestString( String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
