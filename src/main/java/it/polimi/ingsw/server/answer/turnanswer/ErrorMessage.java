package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;


/**
 * Message sent when somethings gone wrong.
 *
 * @author Lorenzo Iovine
 */
public class ErrorMessage implements Answer {
    private final String errorType;

    public ErrorMessage(String errorType) {
        this.errorType = errorType;
    }

    @Override
    public Object getMessage() {
        return errorType;
    }
}
