package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class AskSpace implements Answer {
    private final String message;

    public AskSpace(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
