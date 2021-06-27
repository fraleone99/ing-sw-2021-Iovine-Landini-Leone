package it.polimi.ingsw.server.answer.initialanswer;

import it.polimi.ingsw.server.answer.Answer;


/**
 * Message containing information about the player's connection status.
 *
 * @author Lorenzo Iovine
 */
public class Connection implements Answer {
    private final String message;
    private final boolean connected; //true connection confirmation, false connection termination.

    public Connection(String message, boolean connection) {
        this.message = message;
        this.connected = connection;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public boolean isConnected() {
        return connected;
    }
}
