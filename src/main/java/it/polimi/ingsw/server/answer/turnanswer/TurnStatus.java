package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;


/**
 * Message containing a player turn's status.
 *
 * @author Francesco Leone
 */
public class TurnStatus implements Answer {
    public final String message;

    public TurnStatus(String message) {
        this.message = message;
    }

    @Override
    public Object getMessage() {
        return message;
    }
}
