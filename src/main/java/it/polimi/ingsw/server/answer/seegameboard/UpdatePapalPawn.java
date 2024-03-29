package it.polimi.ingsw.server.answer.seegameboard;

import it.polimi.ingsw.server.answer.Answer;


/**
 * Message containing papal pawn's updates.
 *
 * @author Nicola Landini
 */
public class UpdatePapalPawn implements Answer {

    private final String nickname;
    private final int pawn;

    public UpdatePapalPawn(String nickname, int pawn) {
        this.nickname = nickname;
        this.pawn = pawn;
    }

    public String getNickname() {
        return nickname;
    }

    public int getPawn() {
        return pawn;
    }

    public String getMessage(){
        return null;
    }
}
