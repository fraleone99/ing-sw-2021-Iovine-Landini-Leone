package it.polimi.ingsw.server.answer.infoanswer;

import it.polimi.ingsw.model.gameboard.playerdashboard.FaithPath;
import it.polimi.ingsw.server.answer.Answer;


/**
 * Message containing information about a player's faith path.
 *
 * @author Lorenzo Iovine
 */
public class FaithPathInfo implements Answer {
    private final int position;
    private final int LorenzoPos;
    private final boolean Papal1;
    private final boolean Papal2;
    private final boolean Papal3;
    private final boolean singlePlayer;
    private final String message;

    public FaithPathInfo(String message, FaithPath path, boolean SinglePlayer) {
        this.message= message;
        position= path.getPositionFaithPath();
        Papal1= path.isPapalPawn1();
        Papal2= path.isPapalPawn2();
        Papal3= path.isPapalPawn3();
        singlePlayer = SinglePlayer;
        LorenzoPos= path.getPositionLorenzo();
    }

    public int getPosition() {
        return position;
    }

    public int getLorenzoPos() { return LorenzoPos; }

    public boolean isPapal1() {
        return Papal1;
    }

    public boolean isPapal2() {
        return Papal2;
    }

    public boolean isPapal3() {
        return Papal3;
    }

    public boolean isSinglePlayer() { return singlePlayer; }

    @Override
    public String getMessage() {
        return message;
    }
}
