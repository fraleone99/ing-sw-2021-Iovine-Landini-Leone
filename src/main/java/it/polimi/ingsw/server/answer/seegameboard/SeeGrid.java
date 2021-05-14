package it.polimi.ingsw.server.answer.seegameboard;

import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;

public class SeeGrid implements Answer {
    private final ArrayList<Integer> IdDevCards;

    public SeeGrid(ArrayList<Integer> id) {
        IdDevCards = id;
    }

    public ArrayList<Integer> getMessage() {
        return IdDevCards;
    }
}
