package it.polimi.ingsw.server.answer.infoanswer;

import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.server.answer.Answer;

public class BasicProductionInfo implements Answer {
    private Resource input1;
    private Resource input2;
    private Resource output;

    public BasicProductionInfo(Resource input1, Resource input2, Resource output) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    public Resource getInput1() {
        return input1;
    }

    public Resource getInput2() {
        return input2;
    }

    public Resource getOutput() {
        return output;
    }

    @Override
    public Object getMessage() {
        return null;
    }
}
