package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class PlayingNick implements Answer {
    private final String message;

    public PlayingNick(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
