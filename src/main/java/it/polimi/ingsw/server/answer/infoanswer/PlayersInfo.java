package it.polimi.ingsw.server.answer.infoanswer;

import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;

public class PlayersInfo implements Answer {
    private final int playersNumber;
    private final ArrayList<String> nicknames = new ArrayList<>();

    public PlayersInfo(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    @Override
    public Object getMessage() {
        return null;
    }

    public void addNick(String nickname){
       nicknames.add(nickname);
    }

    public ArrayList<String> getNicknames() {
        return nicknames;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }
}
