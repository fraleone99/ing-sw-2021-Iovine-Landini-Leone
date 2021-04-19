package it.polimi.ingsw.client.message;

public class SendNickname implements Message {
    private final String nickname;

    public SendNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
