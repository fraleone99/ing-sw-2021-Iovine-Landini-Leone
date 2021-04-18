package it.polimi.ingsw.server.answer;

public class ChooseTwoWhiteBallLeader implements Answer{
    private final String message;

    public ChooseTwoWhiteBallLeader(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
