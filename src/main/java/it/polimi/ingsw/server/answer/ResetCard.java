package it.polimi.ingsw.server.answer;

public class ResetCard implements Answer{
    private final int pos;

    public ResetCard(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
