package it.polimi.ingsw.server.answer;

public class DiscardResource implements Answer{
    private final String message;
    private final int amount;

    public DiscardResource(String message, int amount) {
        this.message = message;
        this.amount=amount;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getAmount() {
        return amount;
    }
}
