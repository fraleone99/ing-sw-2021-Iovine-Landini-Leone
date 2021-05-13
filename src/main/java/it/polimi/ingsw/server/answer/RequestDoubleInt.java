package it.polimi.ingsw.server.answer;

import it.polimi.ingsw.server.answer.Answer;

public class RequestDoubleInt implements Answer {
    private final String type;
    private final String message;


    public RequestDoubleInt(String type, String message) {
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