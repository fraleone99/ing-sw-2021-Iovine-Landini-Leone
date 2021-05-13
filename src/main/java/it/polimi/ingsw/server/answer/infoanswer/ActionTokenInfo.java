package it.polimi.ingsw.server.answer.infoanswer;

import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.server.answer.Answer;

public class ActionTokenInfo implements Answer {
    private final ActionToken actionToken;

    public ActionTokenInfo(ActionToken actionToken) {
        this.actionToken = actionToken;
    }

    public ActionToken getMessage() {
        return actionToken;
    }
}
