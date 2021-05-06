package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class DiscardBall implements Answer {
    private final String message;

    public DiscardBall(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
