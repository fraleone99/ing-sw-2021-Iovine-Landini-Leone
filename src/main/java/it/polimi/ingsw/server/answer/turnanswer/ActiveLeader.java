package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;


/**
 * Message sent when a player wants to activate a leader card.
 *
 * @author Lorenzo Iovine
 */
public class ActiveLeader implements Answer {
    private final String message;
    private final ArrayList<Integer> leaders = new ArrayList<>();

    public ActiveLeader(String message, ArrayList<Integer> id) {
        this.message = message;
        leaders.addAll(id);
    }

    public ArrayList<Integer> getLeaders() {
        return leaders;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
