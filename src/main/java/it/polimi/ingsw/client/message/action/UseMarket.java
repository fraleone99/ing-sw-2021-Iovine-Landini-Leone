package it.polimi.ingsw.client.message.action;

public class UseMarket implements UserAction{
    private final int choice;

    public UseMarket(int choice) {
        this.choice = choice;
    }

    public int getChoice() {
        return choice;
    }
}
