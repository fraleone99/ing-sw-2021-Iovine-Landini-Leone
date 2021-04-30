package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class ChooseTurn implements Answer {
    private final String message;

    public ChooseTurn(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

