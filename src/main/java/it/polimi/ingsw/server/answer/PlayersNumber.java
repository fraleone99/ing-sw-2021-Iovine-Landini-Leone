package it.polimi.ingsw.server.answer;

public class PlayersNumber implements Answer{
    private final String message;

    public PlayersNumber(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
