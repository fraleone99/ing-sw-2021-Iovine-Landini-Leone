package it.polimi.ingsw.server.answer.finalanswer;

import it.polimi.ingsw.server.answer.Answer;

public class Lose implements Answer {
    private String message;

    public Lose(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
