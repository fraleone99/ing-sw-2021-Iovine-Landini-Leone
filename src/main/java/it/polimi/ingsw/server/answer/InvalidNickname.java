package it.polimi.ingsw.server.answer;

public class InvalidNickname implements Answer{
    private final String message;

    public InvalidNickname(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
