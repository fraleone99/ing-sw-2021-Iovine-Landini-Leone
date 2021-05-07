package it.polimi.ingsw.server.answer;

import it.polimi.ingsw.model.singleplayer.ActionToken;

public class ActionTokenInfo implements Answer {
    private final ActionToken actionToken;

    public ActionTokenInfo(ActionToken actionToken) {
        this.actionToken = actionToken;
    }

    public ActionToken getMessage() {
        return actionToken;
    }
}
