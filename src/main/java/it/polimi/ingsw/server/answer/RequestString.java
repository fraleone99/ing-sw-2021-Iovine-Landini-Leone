package it.polimi.ingsw.server.answer;

import it.polimi.ingsw.server.answer.Answer;

public class RequestString implements Answer {
    private final String message;

    public RequestString( String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
