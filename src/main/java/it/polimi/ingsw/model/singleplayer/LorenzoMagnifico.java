package it.polimi.ingsw.model.singleplayer;

import it.polimi.ingsw.model.enumeration.CardColor;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class initializes action tokens and allows to do every possible action of Lorenzo
 *
 * @author Nicola Landini
 */

public class LorenzoMagnifico{

    private ArrayList<ActionToken> tokens=new ArrayList<>();

    /**
     * LorenzoMagnifico constructor: it initializes the action tokens
     */
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

        shuffle();
    }

    public ArrayList<ActionToken> getTokens(){
        return tokens;
    }

    /**
     * It shuffles action tokens
     */
    public void shuffle(){
        Collections.shuffle(tokens);
    }

    /**
     * This method allows the player to draw a token and put it in the last position of the ArrayList
     * @return drawn action token
     */
    public ActionToken draw() {
        ActionToken element;
        element = tokens.remove(0);
        tokens.add(element);

        return element;
    }
}
