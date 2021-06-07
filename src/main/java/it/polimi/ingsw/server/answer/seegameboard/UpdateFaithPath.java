package it.polimi.ingsw.server.answer.seegameboard;

import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateFaithPath implements Answer {

    String nickname = new String();
    int position;

    public UpdateFaithPath(String nick, int pos) {
        this.nickname = nick;
        this.position = pos;
    }

    public String getNickname() {
        return nickname;
    }

    public int getPosition() {
        return position;
    }

    public String getMessage(){
        return null;
    }
}
