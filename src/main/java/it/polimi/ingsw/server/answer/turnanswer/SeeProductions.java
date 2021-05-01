package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;

public class SeeProductions implements Answer {
    private final ArrayList<Integer> IdProductions;

    public SeeProductions(ArrayList<Integer> id) {
        IdProductions = id;
    }

    public ArrayList<Integer> getMessage() {
        return IdProductions;
    }
}
