package it.polimi.ingsw.server.answer.initialanswer;

import it.polimi.ingsw.server.answer.Answer;

public class RequestNickname implements Answer {
    private final String message;

    public RequestNickname(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
