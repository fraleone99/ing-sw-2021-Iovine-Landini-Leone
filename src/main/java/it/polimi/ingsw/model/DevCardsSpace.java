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
    ArrayList<DevelopmentCard> Space1 = new ArrayList<>();
    ArrayList<DevelopmentCard> Space2 = new ArrayList<>();
    ArrayList<DevelopmentCard> Space3 = new ArrayList<>();

    private final Production basicProduction;

    public DevCardsSpace() {
        basicProduction = new Production();

        Goods input = new Goods(Resource.UNKNOWN, 2);
        Goods output = new Goods(Resource.UNKNOWN, 1);

        basicProduction.setInputProduction(input);
        basicProduction.setOutputProduction(output);
    }

    public void AddCard(DevelopmentCard Card, int space) throws InvalidSpaceCardExeption{

        if(space == 1){
            if(Space1.isEmpty() && Card.getLevel() == 1)
                Space1.add(Card);
            else if(Space1.get(Space1.size()-1).getLevel() == Card.getLevel()  - 1 ){
                Space1.add(Card);
            }
            else
                throw new InvalidSpaceCardExeption();
        }
        else if(space == 2){
            if(Space2.isEmpty() && Card.getLevel() == 1)
                Space2.add(Card);
            else if(Space2.get(Space2.size()-1).getLevel() == Card.getLevel()  - 1 ){
                Space2.add(Card);
            }
            else
                throw new InvalidSpaceCardExeption();
        }
        else if(space == 3){
            if(Space3.isEmpty() && Card.getLevel() == 1)
                Space3.add(Card);
            else if(Space3.get(Space3.size()-1).getLevel() == Card.getLevel()  - 1 ){
                Space3.add(Card);
            }
            else
                throw new InvalidSpaceCardExeption();
        }
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
        if(space == 1 && !Space1.isEmpty())
            return Space1.get(Space1.size()-1);
        else if(space == 2 && !Space2.isEmpty())
            return Space2.get(Space2.size()-1);
        else if(space == 3 && !Space3.isEmpty())
            return Space3.get(Space3.size()-1);
        else
            throw new InvalidChoiceException();
    }
}
