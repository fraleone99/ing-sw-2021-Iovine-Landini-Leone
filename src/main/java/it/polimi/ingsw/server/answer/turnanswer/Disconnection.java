package it.polimi.ingsw.server.answer.turnanswer;

import it.polimi.ingsw.server.answer.Answer;

public class Disconnection implements Answer {
    private final String message;

    public Disconnection(String message){
        this.message=message;
    }

    @Override
    public String getMessage(){
        return message;
    }

}
