package it.polimi.ingsw.server.answer.infoanswer;

import it.polimi.ingsw.server.answer.Answer;

/**
 * Message containing information regarding a card bought by another player.
 *
 * @author Lorenzo Iovine
 */
public class OtherLeaderCard implements Answer {
    private final int idCard;
    private final String action;
    private final String owner;
    private final int pos;

    public OtherLeaderCard(int idCard, String action, String owner, int pos) {
        this.idCard = idCard;
        this.action = action;
        this.owner = owner;
        this.pos = pos;
    }

    public String getAction() {
        return action;
    }

    public int getIdCard() {
        return idCard;
    }

    public String getOwner() {
        return owner;
    }

    public int getPos() {
        return pos;
    }

    @Override
    public Object getMessage() {
        return null;
    }
}
