package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;

public class LorenzoMagnifico{

    private ArrayList<ActionToken> tokens;

    public LorenzoMagnifico(){
        ActionToken temp=new BlackCrossMover(2, false);
        tokens.add(temp);

        temp=new BlackCrossMover(2, false);
        tokens.add(temp);

        temp=new BlackCrossMover(1, true);
        tokens.add(temp);

        temp=new DeleteCard(CardColor.PURPLE);
        tokens.add(temp);

        temp=new DeleteCard(CardColor.YELLOW);
        tokens.add(temp);

        temp=new DeleteCard(CardColor.BLUE);
        tokens.add(temp);

        temp=new DeleteCard(CardColor.GREEN);
        tokens.add(temp);
    }

    public void shuffle(){
        Collections.shuffle(tokens);
    }

    //this method allow the player to draw a token and put it in the last position of the ArrayList
    public ActionToken draw() {
        ActionToken element;
        // remove last element, add it to front of the ArrayList
        element = tokens.remove(0);
        tokens.add(tokens.size()-1, element);

        return element;
    }
}
