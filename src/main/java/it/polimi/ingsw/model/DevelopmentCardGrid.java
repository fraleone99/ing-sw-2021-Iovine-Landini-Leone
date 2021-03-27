package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * In this class we created the development cards instance
 * and we have inserted them in twelve ArrayList ordered by colors and levels.
 *
 * @author Lorenzo Iovine, Nicola Landini, Francesco Leone.
 */

public class DevelopmentCardGrid {
    private ArrayList<DevelopmentCard> PurpleOne= new ArrayList<>();
    private ArrayList<DevelopmentCard> PurpleTwo= new ArrayList<>();
    private ArrayList<DevelopmentCard> PurpleThree= new ArrayList<>();
    private ArrayList<DevelopmentCard> YellowOne= new ArrayList<>();
    private ArrayList<DevelopmentCard> YellowTwo= new ArrayList<>();
    private ArrayList<DevelopmentCard> YellowThree= new ArrayList<>();


    public DevelopmentCardGrid() {
        ArrayList<Goods> cost=new ArrayList<>();
        cost.add(new Goods(Resource.SERVANT, 3));
        ArrayList<Goods> input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 2));
        ArrayList<Goods> output=new ArrayList<>();
        output.add(new Goods(Resource.SERVANT,1));
        output.add(new Goods(Resource.SHIELD,1));
        output.add(new Goods(Resource.STONE,1));
        DevelopmentCard devCard=new DevelopmentCard(3, CardColor.PURPLE, 1, cost, input, output, 0);
        PurpleOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 1));
        cost.add(new Goods(Resource.SHIELD,1));
        cost.add(new Goods(Resource.SERVANT,1));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SHIELD,1));
        devCard=new DevelopmentCard(2, CardColor.PURPLE, 1, cost, input, output, 0);
        PurpleOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE, 2));
        cost.add(new Goods(Resource.SERVANT,2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        input.add(new Goods(Resource.SHIELD,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.STONE,2));
        devCard=new DevelopmentCard(4, CardColor.PURPLE, 1, cost, input, output, 1);
        PurpleOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SERVANT,2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        output=new ArrayList<>();
        devCard=new DevelopmentCard(1, CardColor.PURPLE, 1, cost, input, output, 1);
        PurpleOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SERVANT,4));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        output=new ArrayList<>();
        devCard=new DevelopmentCard(5, CardColor.PURPLE, 2, cost, input, output, 2);
        PurpleTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SERVANT,5));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 2));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,2));
        devCard=new DevelopmentCard(7, CardColor.PURPLE, 2, cost, input, output, 2);
        PurpleTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD,3));
        cost.add(new Goods(Resource.SERVANT,3));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SERVANT,2));
        devCard=new DevelopmentCard(8, CardColor.PURPLE, 2, cost, input, output, 1);
        PurpleTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 2));
        cost.add(new Goods(Resource.SERVANT,3));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        input.add(new Goods(Resource.SERVANT,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SHIELD,3));
        devCard=new DevelopmentCard(6, CardColor.PURPLE, 2, cost, input, output, 0);
        PurpleTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD,4));
        cost.add(new Goods(Resource.SERVANT,4));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.STONE,3));
        output.add(new Goods(Resource.SERVANT,1));
        devCard=new DevelopmentCard(12, CardColor.PURPLE, 3, cost, input, output, 0);
        PurpleThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SERVANT,7));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.STONE,1));
        devCard=new DevelopmentCard(11, CardColor.PURPLE, 3, cost, input, output, 3);
        PurpleThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SERVANT,5));
        cost.add(new Goods(Resource.COIN,2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        input.add(new Goods(Resource.SHIELD,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,2));
        output.add(new Goods(Resource.SERVANT,2));
        devCard=new DevelopmentCard(10, CardColor.PURPLE, 3, cost, input, output, 1);
        PurpleThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SERVANT,6));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 2));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,3));
        devCard=new DevelopmentCard(9, CardColor.PURPLE, 3, cost, input, output, 2);
        PurpleThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 1));
        output=new ArrayList<>();
        devCard=new DevelopmentCard(1, CardColor.YELLOW, 1, cost, input, output, 1);
        YellowOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.COIN,1));
        cost.add(new Goods(Resource.SHIELD,1));
        cost.add(new Goods(Resource.STONE,1));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,1));
        devCard=new DevelopmentCard(2, CardColor.YELLOW, 1, cost, input, output, 0);
        YellowOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,2));
        cost.add(new Goods(Resource.SHIELD,2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        input.add(new Goods(Resource.SERVANT,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SHIELD,2));
        devCard=new DevelopmentCard(4, CardColor.YELLOW, 1, cost, input, output, 1);
        YellowOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,3));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD, 2));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,1));
        output.add(new Goods(Resource.SERVANT,1));
        output.add(new Goods(Resource.STONE,1));
        devCard=new DevelopmentCard(3, CardColor.YELLOW, 1, cost, input, output, 0);
        YellowOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,3));
        cost.add(new Goods(Resource.SHIELD,2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        input.add(new Goods(Resource.SHIELD,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,3));
        devCard=new DevelopmentCard(6, CardColor.YELLOW, 2, cost, input, output, 0);
        YellowTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,5));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD,2));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SERVANT,2));
        devCard=new DevelopmentCard(7, CardColor.YELLOW, 2, cost, input, output, 2);
        YellowTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,3));
        cost.add(new Goods(Resource.SERVANT,3));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,2));
        devCard=new DevelopmentCard(8, CardColor.YELLOW, 2, cost, input, output, 1);
        YellowTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,4));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD,1));
        output=new ArrayList<>();
        devCard=new DevelopmentCard(5, CardColor.YELLOW, 2, cost, input, output, 2);
        YellowTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,5));
        cost.add(new Goods(Resource.SERVANT,2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        input.add(new Goods(Resource.SERVANT,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,2));
        output.add(new Goods(Resource.SHIELD,2));
        devCard=new DevelopmentCard(10, CardColor.YELLOW, 3, cost, input, output, 1);
        YellowThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,6));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD, 2));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SERVANT,3));
        devCard=new DevelopmentCard(9, CardColor.YELLOW, 3, cost, input, output, 2);
        YellowThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,7));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SERVANT,1));
        devCard=new DevelopmentCard(11, CardColor.YELLOW, 3, cost, input, output, 3);
        YellowThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,4));
        cost.add(new Goods(Resource.SERVANT,4));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.STONE,1));
        output.add(new Goods(Resource.SERVANT,3));
        devCard=new DevelopmentCard(12, CardColor.YELLOW, 3, cost, input, output, 0);
        YellowThree.add(devCard);
    }
}
