package it.polimi.ingsw.server.answer;

public class StartGame implements Answer{
    private final String message;

    public StartGame(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
