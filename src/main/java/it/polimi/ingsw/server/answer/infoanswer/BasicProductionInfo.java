package it.polimi.ingsw.server.answer.infoanswer;

import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.server.answer.Answer;


/**
 * Message containing information about basic production.
 *
 * @author Lorenzo Iovine
 */
public class BasicProductionInfo implements Answer {
    private final Resource input1;
    private final Resource input2;
    private final Resource output;

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
