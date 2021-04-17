package it.polimi.ingsw.client.message;

public class NumberOfPlayers implements Message {
    private final int number;

    public NumberOfPlayers(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
