package it.polimi.ingsw.model.gameboard.playerdashboard;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.Goods;
import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;

import java.util.ArrayList;

/**
 * Class for player's development cards. Each player has 3 spaces where he can place these Cards and
 * a Basic Production
 *
 *
 * @author Francesco Leone
 */

public class DevCardsSpace {
    private final ArrayList<DevelopmentCardDeck> Space = new ArrayList<>(3);
    private final Production basicProduction;

    /**
     * DevCardsSpace constructor: creates a new instance of DevCardsSpace
     */
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

    /**
     * This method calculates the total amount of cards in DevCardsSpace
     * @return the total amount of cards
     */
    public int getAmountCards() {
        int sum=0;
        for(DevelopmentCardDeck d : Space){
            sum+=d.size();
        }
        return sum;
    }

    /**
     * This method checks if the given development card could be placed in the selected space
     * @param card card to place
     * @param space where the card has to be placed
     * @return true if the card can be placed, else it returns false
     */
    public boolean checkSpace(DevelopmentCard card, int space) {
        if(Space.get(space-1).isEmpty() && card.getLevel() == 1)
            return true;
        else return !Space.get(space - 1).isEmpty() && Space.get(space - 1).get().getLevel() == card.getLevel() - 1;
    }

    /**
     * This method place the card in the selected space
     * @param card card to place
     * @param space where the card has to be placed
     */
    public void addCard(DevelopmentCard card, int space) {
        Space.get(space-1).add(card);
    }

    /**
     * This method sets the input of the basic production
     * @param type1 first input resource
     * @param type2 second input resource
     */
    public void setInputBasicProduction(Resource type1, Resource type2){

        if(type1.equals(type2)){
            Goods input1 = new Goods(type1, 2);
            basicProduction.getInputProduction().set(0, input1);

            Goods input2 = new Goods(type2, 0);
            basicProduction.getInputProduction().set(1, input2);
        }
        else {

            Goods input1 = new Goods(type1, 1);
            basicProduction.getInputProduction().set(0, input1);

            Goods input2 = new Goods(type2, 1);
            basicProduction.getInputProduction().set(1, input2);
        }
    }

    /**
     * This method sets the output of the basic production
     * @param type output resource
     */
    public void setOutputBasicProduction(Resource type){
        Goods output = new Goods(type, 1);
        basicProduction.getOutputProduction().set(0,output);
    }

    public Production getBasicProduction() {
        return basicProduction;
    }

    /**
     * This method return the card on the top of the selected space
     * @param space where card has to be drawn
     * @return the card on the top of the space
     * @throws InvalidChoiceException if space is invalid
     */
    public DevelopmentCard getCard(int space) throws InvalidChoiceException {
        if(!Space.get(space - 1).isEmpty())
            return Space.get(space - 1).get();
        else
            throw new InvalidChoiceException();

    }

    /**
     * This method calculate the total amount of card of a certain color and level
     * @param color is the color of the cards we are looking for
     * @param level is the level of the cards we are looking for
     * @return total amount of cards of a certain color and level
     */
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

    /**
     * This method calculate the number of colors in the spaces
     * @param color is the color of the cards we are looking for
     * @param level is the level of the cards we are looking for
     * @return total amount of colors in development cards spaces
     */
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

    /**
     * This method calculate the total amount of points given by the development
     * cards in development cards spaces
     * @return total amount of points by the cards in development cards spaces
     */
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
