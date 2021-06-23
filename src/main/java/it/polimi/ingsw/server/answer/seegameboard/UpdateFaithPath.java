package it.polimi.ingsw.server.answer.seegameboard;

import it.polimi.ingsw.server.answer.Answer;

public class UpdateFaithPath implements Answer {

    String nickname;
    int position;
    boolean isLorenzo;

    public UpdateFaithPath(String nick, int pos, boolean isLorenzo) {
        this.nickname = nick;
        this.position = pos;
        this.isLorenzo = isLorenzo;
    }

    public String getNickname() {
        return nickname;
    }

    public int getPosition() {
        return position;
    }

    public boolean isLorenzo() {
        return isLorenzo;
    }

    public String getMessage(){
        return null;
    }
}
