package it.polimi.ingsw.server.answer.finalanswer;

import it.polimi.ingsw.server.answer.Answer;

/**
 * Message sent to the clients that lose the game.
 *
 * @author Lorenzo Iovine
 */
public class Lose implements Answer {
    private final String message;

    public Lose(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
