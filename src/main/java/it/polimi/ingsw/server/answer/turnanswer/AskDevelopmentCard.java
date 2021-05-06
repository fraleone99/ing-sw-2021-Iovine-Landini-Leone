package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class AskDevelopmentCard implements Answer {
    private final String message;

    public AskDevelopmentCard(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

