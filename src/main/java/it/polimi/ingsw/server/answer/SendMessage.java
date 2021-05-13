package it.polimi.ingsw.server.answer;

import it.polimi.ingsw.server.answer.Answer;

public class SendMessage implements Answer {
    private final String message;

    public SendMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
