package it.polimi.ingsw.server.answer.initialanswer;

import it.polimi.ingsw.server.answer.Answer;

public class WaitingRoom implements Answer {
    private final String message;

    public WaitingRoom(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
