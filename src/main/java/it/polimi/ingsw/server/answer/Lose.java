package it.polimi.ingsw.server.answer;

public class Lose implements Answer{
    private final String message;

    public Lose(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
