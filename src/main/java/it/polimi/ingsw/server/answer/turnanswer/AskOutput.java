package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class AskOutput implements Answer {
    private final String message;

    public AskOutput(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
