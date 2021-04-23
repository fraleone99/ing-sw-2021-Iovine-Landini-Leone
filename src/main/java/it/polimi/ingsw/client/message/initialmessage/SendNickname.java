package it.polimi.ingsw.client.message.initialmessage;

import it.polimi.ingsw.client.message.Message;

public class SendNickname implements Message {
    private final String nickname;

    public SendNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
