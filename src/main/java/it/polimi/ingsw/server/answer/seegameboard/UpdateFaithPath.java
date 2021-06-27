package it.polimi.ingsw.server.answer.seegameboard;

import it.polimi.ingsw.server.answer.Answer;


/**
 * Message containing faith path's updates.
 *
 * @author Nicola Landini
 */
public class UpdateFaithPath implements Answer {

    private final String nickname;
    private final int position;
    private final boolean isLorenzo;

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
