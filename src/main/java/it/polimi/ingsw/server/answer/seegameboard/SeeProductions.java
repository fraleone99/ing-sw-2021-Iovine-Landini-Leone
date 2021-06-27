package it.polimi.ingsw.server.answer.seegameboard;

import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;

/**
 * Message containing production card's id.
 *
 * @author Lorenzo Iovine
 */
public class SeeProductions implements Answer {
    private final ArrayList<Integer> IdProductions;

    public SeeProductions(ArrayList<Integer> id) {
        IdProductions = id;
    }

    public ArrayList<Integer> getMessage() {
        return IdProductions;
    }
}
