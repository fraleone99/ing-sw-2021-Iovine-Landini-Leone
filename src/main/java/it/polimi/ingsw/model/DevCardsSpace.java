package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * Class for The The Development Cards of the player. Each player has 3 spaces where he can place these Cards and
 * a Basic Production
 *
 *
 * @author Francesco Leone
 */

public class DevCardsSpace {
    private ArrayList<DevelopmentCardDeck> Space = new ArrayList<>(3);
    private final Production basicProduction;

    public DevCardsSpace() {
        Goods Input = new Goods(Resource.UNKNOWN, 2);
        ArrayList<Goods> basicInput = new ArrayList<>();
        basicInput.add(Input);

        Goods Output = new Goods(Resource.UNKNOWN, 1);
        ArrayList<Goods> basicOutput = new ArrayList<>();
        basicInput.add(Output);

        basicProduction = new Production(basicInput, basicOutput);

        Goods input = new Goods(Resource.UNKNOWN, 2);
        Goods output = new Goods(Resource.UNKNOWN, 1);

        basicProduction.setInputProduction(input);
        basicProduction.setOutputProduction(output);
    }

    public void AddCard(DevelopmentCard Card, int space) throws InvalidSpaceCardExeption{
        if(space < 1 || space > 3) throw new InvalidSpaceCardExeption();
        if(Space.get(space-1).isEmpty() && Card.getLevel() == 1)
            Space.get(space-1).add(Card);

        else if (!Space.get(space-1).isEmpty() && Space.get(space-1).get().getLevel() == Card.getLevel() - 1)
            Space.get(space-1).add(Card);
        else
            throw new InvalidSpaceCardExeption();
    }

    public void setInputBasicProduction(Resource type1, Resource type2){
        Goods input1 = new Goods(type1, 1);
        basicProduction.setInputProduction(input1);

        Goods input2 = new Goods(type2, 1);
        basicProduction.setInputProduction(input2);
    }

    public void setOutputBasicProduction(Resource type){
        Goods output = new Goods(type, 1);
        basicProduction.setOutputProduction(output);
    }

    public Production getBasicProduction() {
        return basicProduction;
    }

    public DevelopmentCard getCard(int space) throws InvalidChoiceException{
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
}
