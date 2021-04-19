package it.polimi.ingsw.client.message;

public class ClientConnection implements Message{
    private String message;

    public ClientConnection(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
