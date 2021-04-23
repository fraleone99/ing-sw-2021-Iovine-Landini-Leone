package it.polimi.ingsw.server.answer;

public class FirstPlayer implements Answer{
    private final String message;

    public FirstPlayer(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
