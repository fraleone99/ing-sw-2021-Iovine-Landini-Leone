package it.polimi.ingsw.client;


/**
 * This is the interface that manages the communication between the controller and the client
 */
public interface Handler {

    /**
     * Sends the message to the server
     * @param message is the message sent
     */
    void send(Object message);
}
