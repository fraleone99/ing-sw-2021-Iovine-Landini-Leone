package it.polimi.ingsw.server.answer;

import java.io.Serializable;

public class SerializedAnswer implements Serializable {
    private Answer answer;

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Answer getAnswer() {
        return answer;
    }
}
