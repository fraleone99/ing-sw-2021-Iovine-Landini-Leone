package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;


/**
 * Message containing initial leader card's id.
 *
 * @author Lorenzo Iovine
 */
public class PassLeaderCard implements Answer {
    private final ArrayList<Integer> IdLeaders;

    public PassLeaderCard(ArrayList<Integer> idLeaders) {
        IdLeaders = idLeaders;
    }

    public ArrayList<Integer> getMessage() {
        return IdLeaders;
    }
}
