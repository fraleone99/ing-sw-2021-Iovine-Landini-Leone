package it.polimi.ingsw.server.answer.initialanswer;

import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;

public class PassLeaderCard implements Answer {
    private final ArrayList<Integer> IdLeaders;

    public PassLeaderCard(ArrayList<Integer> idLeaders) {
        IdLeaders = idLeaders;
    }

    public ArrayList<Integer> getMessage() {
        return IdLeaders;
    }
}
