package it.polimi.ingsw.server.answer;

public class ChooseResource implements Answer {
    private final String message;

    public ChooseResource(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
