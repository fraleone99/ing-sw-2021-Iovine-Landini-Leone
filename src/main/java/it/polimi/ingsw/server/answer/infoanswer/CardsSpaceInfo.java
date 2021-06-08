package it.polimi.ingsw.server.answer.infoanswer;

import it.polimi.ingsw.server.answer.Answer;

public class CardsSpaceInfo implements Answer {
    private final String nickname;
    private final int level;
    private final int space;
    private final int idCard;

    public CardsSpaceInfo(String nickname, int level, int space, int idCard) {
        this.nickname=nickname;
        this.level=level;
        this.space=space;
        this.idCard=idCard;
    }

    public String getNickname() {
        return nickname;
    }

    public int getLevel() {
        return level;
    }

    public int getSpace() {
        return space;
    }

    public int getIdCard() {
        return idCard;
    }

    @Override
    public Object getMessage() {
        return null;
    }
}
