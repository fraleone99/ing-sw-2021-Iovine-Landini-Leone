package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class DiscardLeader implements Answer {
    private final String message;

    public DiscardLeader(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
