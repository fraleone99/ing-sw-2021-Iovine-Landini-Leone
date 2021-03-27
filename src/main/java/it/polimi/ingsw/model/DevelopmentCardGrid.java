package it.polimi.ingsw.model;

import java.util.ArrayList;

public class DevelopmentCardGrid {
    private ArrayList<DevelopmentCard> BlueOne = new ArrayList<>();
    private ArrayList<DevelopmentCard> BlueTwo = new ArrayList<>();
    private ArrayList<DevelopmentCard> BlueThree = new ArrayList<>();

    public DevelopmentCardGrid() {
        ArrayList<Goods> cost=new ArrayList<>();
        ArrayList<Goods> input=new ArrayList<>();
        ArrayList<Goods> output=new ArrayList<>();
        //DevelopmentCard devCard=new DevelopmentCard(1, CardColor.GREEN, 1, cost, input, output, 1);

        //level 1 BLUE
        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 3));
        input= new ArrayList<>();
        input.add(new Goods(Resource.STONE, 2));
        output= new ArrayList<>();
        output.add(new Goods(Resource.COIN, 1));
        DevelopmentCard devCard=new DevelopmentCard(3, CardColor.BLUE, 1, cost, input, output, 0);
        BlueOne.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 2));
        cost.add(new Goods(Resource.SERVANT, 2));
        input= new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        input.add(new Goods(Resource.SHIELD, 1));
        output= new ArrayList<>();
        output.add(new Goods(Resource.SERVANT, 2));
        devCard=new DevelopmentCard(4, CardColor.BLUE, 1, cost, input, output, 1);
        BlueOne.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 2));
        input= new ArrayList<>();
        input.add(new Goods(Resource.SHIELD, 1));
        output= new ArrayList<>();
        devCard=new DevelopmentCard(1, CardColor.BLUE, 1, cost, input, output, 1);
        BlueOne.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 1));
        cost.add(new Goods(Resource.STONE, 1));
        cost.add(new Goods(Resource.SERVANT, 1));
        input= new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 1));
        output= new ArrayList<>();
        output.add(new Goods(Resource.STONE, 1));
        devCard=new DevelopmentCard(2, CardColor.BLUE, 1, cost, input, output, 0);
        BlueOne.add(devCard);

        //Level 2 BLUE
        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 4));
        input= new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 1));
        output= new ArrayList<>();
        devCard=new DevelopmentCard(5, CardColor.BLUE, 2, cost, input, output, 2);
        BlueTwo.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 3));
        cost.add(new Goods(Resource.STONE, 2));
        input= new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        input.add(new Goods(Resource.STONE, 1));
        output= new ArrayList<>();
        output.add(new Goods(Resource.SERVANT, 3));
        devCard=new DevelopmentCard(6, CardColor.BLUE, 2, cost, input, output, 0);
        BlueTwo.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 5));
        input= new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 2));
        output= new ArrayList<>();
        output.add(new Goods(Resource.SHIELD, 2));
        devCard=new DevelopmentCard(7, CardColor.BLUE, 2, cost, input, output, 2);
        BlueTwo.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 3));
        cost.add(new Goods(Resource.STONE, 3));
        input= new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 1));
        output= new ArrayList<>();
        output.add(new Goods(Resource.STONE, 2));
        devCard=new DevelopmentCard(8, CardColor.BLUE, 2, cost, input, output, 1);
        BlueTwo.add(devCard);

        //level 3 BLUE
        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 6));
        input= new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 2));
        output= new ArrayList<>();
        output.add(new Goods(Resource.SHIELD, 3));
        devCard=new DevelopmentCard(9, CardColor.BLUE, 3, cost, input, output, 2);
        BlueThree.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 5));
        cost.add(new Goods(Resource.STONE, 2));
        input= new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        input.add(new Goods(Resource.SHIELD, 1));
        output= new ArrayList<>();
        output.add(new Goods(Resource.SERVANT, 2));
        output.add(new Goods(Resource.STONE, 2));
        devCard=new DevelopmentCard(10, CardColor.BLUE, 3, cost, input, output, 1);
        BlueThree.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 7));
        input= new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        output= new ArrayList<>();
        output.add(new Goods(Resource.SHIELD, 1));
        devCard=new DevelopmentCard(11, CardColor.BLUE, 3, cost, input, output, 3);
        BlueThree.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 4));
        cost.add(new Goods(Resource.STONE, 4));
        input= new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 1));
        output= new ArrayList<>();
        output.add(new Goods(Resource.COIN, 1));
        output.add(new Goods(Resource.SHIELD, 3));
        devCard=new DevelopmentCard(12, CardColor.BLUE, 3, cost, input, output, 0);
        BlueThree.add(devCard);
    }
}
