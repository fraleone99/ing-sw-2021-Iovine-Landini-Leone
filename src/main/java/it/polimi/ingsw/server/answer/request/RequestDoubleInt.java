package it.polimi.ingsw.server.answer.request;

import it.polimi.ingsw.server.answer.Answer;

import java.util.ArrayList;


/**
 * Message requesting two int to the client.
 *
 * @author Lorenzo Iovine
 */
public class RequestDoubleInt implements Answer {
    private final String type;
    private final String message;
    private final ArrayList<Integer> cards = new ArrayList<>();
    private final ArrayList<Integer> spaces = new ArrayList<>();


    public RequestDoubleInt(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public RequestDoubleInt(String type, String message, ArrayList<Integer> idCards, ArrayList<Integer> spaces) {
        this.type = type;
        this.message = message;
        cards.addAll(idCards);
        this.spaces.addAll(spaces);
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Integer> getCards() {
        return cards;
    }

    public ArrayList<Integer> getSpaces() {
        return spaces;
    }
}