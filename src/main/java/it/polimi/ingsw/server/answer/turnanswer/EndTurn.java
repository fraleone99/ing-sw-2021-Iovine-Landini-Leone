package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class EndTurn implements Answer {
    private final String nickname;

    public EndTurn(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public Object getMessage() {
        return nickname;
    }
}
