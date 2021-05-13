package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class ResetCard implements Answer {
    private final int pos;

    public ResetCard(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
