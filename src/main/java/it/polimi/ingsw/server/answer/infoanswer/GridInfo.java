package it.polimi.ingsw.server.answer.infoanswer;

import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;

public class GridInfo implements Answer {
    private final ArrayList<Integer> IdDevCards;

    public GridInfo(ArrayList<Integer> id) {
        IdDevCards = id;
    }

    public ArrayList<Integer> getMessage() {
        return IdDevCards;
    }
}
