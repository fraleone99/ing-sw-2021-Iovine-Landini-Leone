package it.polimi.ingsw.server.answer;

import java.io.Serializable;


/**
 * This is the interface of the messages that the server can send to the clients.
 *
 * @author Lorenzo Iovine
 */
public interface Answer extends Serializable {

    Object getMessage();
}
