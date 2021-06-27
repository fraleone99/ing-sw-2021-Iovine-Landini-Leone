package it.polimi.ingsw.server.answer.seegameboard;

import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;


/**
 * Message containing another player's leader cards' id.
 *
 * @author Lorenzo Iovine
 */
public class SeeOtherCards implements Answer {
    private final ArrayList<Integer> IdLeaders;

    public SeeOtherCards(ArrayList<Integer> idLeaders) {
        IdLeaders = idLeaders;
    }

    public ArrayList<Integer> getMessage() {
        return IdLeaders;
    }
}