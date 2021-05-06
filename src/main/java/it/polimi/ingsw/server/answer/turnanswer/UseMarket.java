package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class UseMarket implements Answer {
    private final String message;

    public UseMarket(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
