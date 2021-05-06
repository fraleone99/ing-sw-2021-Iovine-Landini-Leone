package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class AskLeaderCard implements Answer {
    private final String message;

    public AskLeaderCard(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
