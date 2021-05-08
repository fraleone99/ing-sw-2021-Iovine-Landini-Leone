package it.polimi.ingsw.server.answer;

public class TurnStatus implements Answer{
    public final String message;

    public TurnStatus(String message) {
        this.message = message;
    }


    @Override
    public Object getMessage() {
        return message;
    }
}
