package it.polimi.ingsw.client.message.action.production;

import it.polimi.ingsw.client.message.Message;
import it.polimi.ingsw.model.enumeration.Resource;


public class ActiveBasicProduction implements Message {
    private final Resource input1;
    private final Resource input2;
    private final Resource output;

    public ActiveBasicProduction(Resource input1, Resource input2, Resource output) {
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
}
