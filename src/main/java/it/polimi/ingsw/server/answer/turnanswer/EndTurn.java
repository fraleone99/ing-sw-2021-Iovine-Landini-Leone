package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class EndTurn implements Answer {
    private String message;

    public EndTurn(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
