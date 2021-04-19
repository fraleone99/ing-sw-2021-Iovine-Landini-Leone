package it.polimi.ingsw.server.answer;

public class RequestNickname implements Answer{
    private final String message;

    public RequestNickname(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
