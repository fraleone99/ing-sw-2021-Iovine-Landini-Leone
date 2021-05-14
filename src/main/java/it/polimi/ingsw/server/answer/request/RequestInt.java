package it.polimi.ingsw.server.answer.request;

import it.polimi.ingsw.server.answer.Answer;

public class RequestInt implements Answer {
    private final String type;
    private final String message;

    public RequestInt(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}