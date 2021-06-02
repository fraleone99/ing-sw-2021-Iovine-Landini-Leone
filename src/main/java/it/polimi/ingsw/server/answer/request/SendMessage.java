package it.polimi.ingsw.server.answer.request;

import it.polimi.ingsw.server.answer.Answer;

public class SendMessage implements Answer {
    private String type;
    private final String message;

    public SendMessage(String message) {
        this.message = message;
    }

    public SendMessage(String message, String type) {
        this.message = message;
        this.type=type;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}
