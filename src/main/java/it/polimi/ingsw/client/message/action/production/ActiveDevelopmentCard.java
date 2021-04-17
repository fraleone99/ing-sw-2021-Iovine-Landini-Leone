package it.polimi.ingsw.client.message.action.production;

import it.polimi.ingsw.client.message.action.UserAction;

public class ActiveDevelopmentCard implements UserAction {
    private final int position;

    public ActiveDevelopmentCard(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
