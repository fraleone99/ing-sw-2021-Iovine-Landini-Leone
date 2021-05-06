package it.polimi.ingsw.model.gameboard.playerdashboard;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.exceptions.InvalidSpaceCardException;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.Goods;
import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;

import java.util.ArrayList;

/**
 * Class for The The Development Cards of the player. Each player has 3 spaces where he can place these Cards and
 * a Basic Production
 *
 *
 * @author Francesco Leone
 */

public class DevCardsSpace {
    private final ArrayList<DevelopmentCardDeck> Space = new ArrayList<>(3);
    private final Production basicProduction;

    public DevCardsSpace() {
        Goods Input1 = new Goods(Resource.UNKNOWN, 1);
        Goods Input2 = new Goods(Resource.UNKNOWN, 1);
        ArrayList<Goods> basicInput = new ArrayList<>();
        basicInput.add(Input1);
        basicInput.add(Input2);

        Goods Output = new Goods(Resource.UNKNOWN, 1);
        ArrayList<Goods> basicOutput = new ArrayList<>();
        basicOutput.add(Output);

        basicProduction = new Production(basicInput, basicOutput,0);

        DevelopmentCardDeck deck1 = new DevelopmentCardDeck();
        DevelopmentCardDeck deck2 = new DevelopmentCardDeck();
        DevelopmentCardDeck deck3 = new DevelopmentCardDeck();

        Space.add(deck1);
        Space.add(deck2);
        Space.add(deck3);
    }

    public int getAmountCards() {
        int sum=0;
        for(DevelopmentCardDeck d : Space){
            sum+=d.size();
        }
        return sum;
    }

    public boolean checkSpace(DevelopmentCard card, int space) {
        if(Space.get(space-1).isEmpty() && card.getLevel() == 1)
            return true;
        else if (!Space.get(space-1).isEmpty() && Space.get(space-1).get().getLevel() == card.getLevel() - 1)
            return true;
        else
            return false;
    }

    public void addCard(DevelopmentCard card, int space) {
        Space.get(space-1).add(card);
    }

    public void AddCard(DevelopmentCard Card, int space) throws InvalidSpaceCardException {
        if(Space.get(space-1).isEmpty() && Card.getLevel() == 1)
            Space.get(space-1).add(Card);
        else if (!Space.get(space-1).isEmpty() && Space.get(space-1).get().getLevel() == Card.getLevel() - 1)
            Space.get(space-1).add(Card);
        else
            throw new InvalidSpaceCardException();
    }

    public void setInputBasicProduction(Resource type1, Resource type2){
        Goods input1 = new Goods(type1, 1);
        basicProduction.getInputProduction().set(0, input1);

        Goods input2 = new Goods(type2, 1);
        basicProduction.getInputProduction().set(1,input2);
    }

    public void setOutputBasicProduction(Resource type){
        Goods output = new Goods(type, 1);
        basicProduction.getOutputProduction().set(0,output);
    }

    public Production getBasicProduction() {
        return basicProduction;
    }

    public DevelopmentCard getCard(int space) throws InvalidChoiceException {
        if(!Space.get(space - 1).isEmpty())
            return Space.get(space - 1).get();
        else
            throw new InvalidChoiceException();

    }

    public int checkSpace(CardColor color, int level){
        int result = 0;
        for(DevelopmentCardDeck d: Space){
            result += d.checkDeck(color, level);
        }
        return  result;
    }

    public ArrayList<DevelopmentCardDeck> getSpace() {
        return Space;
    }

    public int getNumberOfColor(CardColor color, int level){
        int result = 0;

        for(DevelopmentCardDeck deck : Space){
            for(DevelopmentCard card : deck.getDeck()){
                if (card.getLevel() == level && card.getColor().equals(color))
                    result++;
            }
        }

        return  result;
    }

    public int pointsByDevCards(){
        int points = 0;
        for(DevelopmentCardDeck deck : Space) {
            for (DevelopmentCard card : deck.getDeck()) {
                points += card.getVictoryPoints();
            }
        }
        return points;

    }
}
