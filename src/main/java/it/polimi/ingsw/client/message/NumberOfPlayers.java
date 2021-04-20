package it.polimi.ingsw.client.message;

public class NumberOfPlayers implements Message {
    private final String number;

    public NumberOfPlayers(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
