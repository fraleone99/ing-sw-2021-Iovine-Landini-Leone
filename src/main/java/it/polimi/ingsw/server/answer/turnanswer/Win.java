package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class Win implements Answer {
    private String message;

    public Win(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

