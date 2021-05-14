package it.polimi.ingsw.server.answer.seegameboard;

import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;

public class SeeLeaderCards implements Answer {
    private final ArrayList<Integer> IdLeaders;

    public SeeLeaderCards(ArrayList<Integer> idLeaders) {
        IdLeaders = idLeaders;
    }

    public ArrayList<Integer> getMessage() {
        return IdLeaders;
    }
}

