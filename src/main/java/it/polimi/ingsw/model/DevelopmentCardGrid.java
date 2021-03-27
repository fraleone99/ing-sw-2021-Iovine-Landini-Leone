package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * DevelopmentCardGrid Class manages the DevelopmentCard matrix
 *
 * @author Nicola Landini
 */

public class DevelopmentCardGrid {

    private final ArrayList<DevelopmentCard> GreenOne=new ArrayList<>();
    private final ArrayList<DevelopmentCard> GreenTwo=new ArrayList<>();
    private final ArrayList<DevelopmentCard> GreenThree=new ArrayList<>();

    public DevelopmentCardGrid() {
        //GREEN
        //level1
        ArrayList<Goods> cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 2));
        ArrayList<Goods> input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        ArrayList<Goods> output=new ArrayList<>();
        DevelopmentCard devCard=new DevelopmentCard(1, CardColor.GREEN, 1, cost, input, output, 1);
        GreenOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 2));
        cost.add(new Goods(Resource.COIN, 2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        input.add(new Goods(Resource.SERVANT, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN, 2));
        devCard=new DevelopmentCard(4, CardColor.GREEN, 1, cost, input, output, 1);
        GreenOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 1));
        cost.add(new Goods(Resource.SERVANT, 1));
        cost.add(new Goods(Resource.STONE, 1));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SERVANT, 2));
        devCard=new DevelopmentCard(2, CardColor.GREEN, 1, cost, input, output, 0);
        GreenOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 3));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 2));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN, 1));
        output.add(new Goods(Resource.SHIELD, 1));
        output.add(new Goods(Resource.STONE, 1));
        devCard=new DevelopmentCard(3, CardColor.GREEN, 1, cost, input, output, 0);
        GreenOne.add(devCard);


        //level2
        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 4));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        output=new ArrayList<>();
        devCard=new DevelopmentCard(5, CardColor.GREEN, 2, cost, input, output, 2);
        GreenTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 3));
        cost.add(new Goods(Resource.SERVANT, 2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD, 1));
        input.add(new Goods(Resource.SERVANT, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.STONE, 3));
        devCard=new DevelopmentCard(6, CardColor.GREEN, 2, cost, input, output, 0);
        GreenTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 5));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 2));
        output=new ArrayList<>();
        output.add(new Goods(Resource.STONE, 2));
        devCard=new DevelopmentCard(7, CardColor.GREEN, 2, cost, input, output, 2);
        GreenTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 3));
        cost.add(new Goods(Resource.COIN, 3));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SHIELD, 2));
        devCard=new DevelopmentCard(8, CardColor.GREEN, 2, cost, input, output, 1);
        GreenTwo.add(devCard);


        //level3
        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 6));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.STONE, 3));
        devCard=new DevelopmentCard(9, CardColor.GREEN, 3, cost, input, output, 2);
        GreenThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 5));
        cost.add(new Goods(Resource.SERVANT, 2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        input.add(new Goods(Resource.SERVANT, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SHIELD, 2));
        output.add(new Goods(Resource.STONE, 2));
        devCard=new DevelopmentCard(10, CardColor.GREEN, 3, cost, input, output, 1);
        GreenThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 7));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN, 2));
        devCard=new DevelopmentCard(11, CardColor.GREEN, 3, cost, input, output, 3);
        GreenThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 4));
        cost.add(new Goods(Resource.COIN, 4));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN, 3));
        output.add(new Goods(Resource.SHIELD, 1));
        devCard=new DevelopmentCard(12, CardColor.GREEN, 3, cost, input, output, 0);
        GreenThree.add(devCard);

    }

}
