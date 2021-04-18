package it.polimi.ingsw.server.answer;

public class GameError implements Answer {
    private final String message;
    private final Errors error;

    public GameError(String message, Errors error) {
        this.message = message;
        this.error = error;
    }

    public GameError(Errors error) {
        this.message=null;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public Errors getError() {
        return error;
    }
}
