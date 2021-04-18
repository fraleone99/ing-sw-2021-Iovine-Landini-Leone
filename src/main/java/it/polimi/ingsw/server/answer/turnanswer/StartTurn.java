package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class StartTurn implements Answer {
    private final String nickname;

    public StartTurn(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return nickname;
    }
}
