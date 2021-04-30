package it.polimi.ingsw.server.answer;

public class SeeGameBoard implements Answer{
    private final String message;

    public SeeGameBoard(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
