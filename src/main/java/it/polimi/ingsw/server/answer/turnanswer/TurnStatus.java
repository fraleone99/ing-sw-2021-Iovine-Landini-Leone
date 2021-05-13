package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class TurnStatus implements Answer {
    public final String message;

    public TurnStatus(String message) {
        this.message = message;
    }


    @Override
    public Object getMessage() {
        return message;
    }
}
