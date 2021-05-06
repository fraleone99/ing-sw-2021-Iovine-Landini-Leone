package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class AskColor implements Answer {
    private final String message;

    public AskColor(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
