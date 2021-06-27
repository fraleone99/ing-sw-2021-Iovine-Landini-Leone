package it.polimi.ingsw.server.answer.finalanswer;

import it.polimi.ingsw.server.answer.Answer;


/**
 * Message sent to the winner's client.
 *
 * @author Lorenzo Iovine
 */
public class Win implements Answer {
    private final String message;

    public Win(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

