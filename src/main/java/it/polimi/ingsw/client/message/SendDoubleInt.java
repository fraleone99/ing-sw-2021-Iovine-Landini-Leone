package it.polimi.ingsw.client.message;


/**
 * This message sends two int to the server.
 */
public class SendDoubleInt implements Message {
    private final int choice1;
    private final int choice2;

    public SendDoubleInt(int choice1, int choice2) {
        this.choice1=choice1;
        this.choice2=choice2;
    }

    public int getChoice1() {
        return choice1;
    }

    public int getChoice2() {
        return choice2;
    }
}
