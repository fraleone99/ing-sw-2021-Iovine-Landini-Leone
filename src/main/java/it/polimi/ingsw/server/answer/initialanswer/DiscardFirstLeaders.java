package it.polimi.ingsw.server.answer.initialanswer;

import it.polimi.ingsw.server.answer.Answer;

public class DiscardFirstLeaders implements Answer {
    private final String message;

    public DiscardFirstLeaders(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
