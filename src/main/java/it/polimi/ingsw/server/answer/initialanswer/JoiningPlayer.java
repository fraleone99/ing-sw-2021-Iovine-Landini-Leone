package it.polimi.ingsw.server.answer.initialanswer;

import it.polimi.ingsw.server.answer.Answer;

public class JoiningPlayer implements Answer {
    private final String message;

    public JoiningPlayer(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
