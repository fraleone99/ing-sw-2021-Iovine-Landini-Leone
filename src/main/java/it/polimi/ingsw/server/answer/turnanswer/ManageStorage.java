package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class ManageStorage implements Answer {
    private final String message;

    public ManageStorage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
