package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;

import java.util.ArrayList;

/**
 * In this class we create the instances of the development
 * cards before inserting them ordered by color and level.
 *
 * @author Lorenzo Iovine, Nicola Landini, Francesco Leone.
 */

//tested class
public class DevelopmentCardGrid {
    private ArrayList<DevelopmentCardDeck> devCardsDecks = new ArrayList<>();


    public DevelopmentCardGrid() {
        initializeDevCards();
    }

    public void ShufflesAllDecks(){
        for(DevelopmentCardDeck d: devCardsDecks){
            d.shuffle();
        }
    }

    public void removeCard(CardColor color, int level) throws InvalidChoiceException{
        if (color.equals(CardColor.PURPLE)) {
            devCardsDecks.get(level - 1).draw();
        }
        else if (color.equals(CardColor.YELLOW)) {
            devCardsDecks.get(level + 2).draw();
        }
        else if (color.equals(CardColor.BLUE)) {
            devCardsDecks.get(level + 5).draw();
        }
        else if (color.equals(CardColor.GREEN)) {
            devCardsDecks.get(level + 8).draw();
        }
        else throw new InvalidChoiceException();
    }

    public DevelopmentCard getCard(CardColor color, int level) throws InvalidChoiceException{
        if (color.equals(CardColor.PURPLE)) {
            return devCardsDecks.get(level - 1).get();
        }
        else if (color.equals(CardColor.YELLOW)) {
            return devCardsDecks.get(level + 2).get();
        }
        else if (color.equals(CardColor.BLUE)) {
            return devCardsDecks.get(level + 5).get();
        }
        else if (color.equals(CardColor.GREEN)) {
            return devCardsDecks.get(level + 8).get();
        }
        else throw new InvalidChoiceException();
    }

    public void initializeDevCards(){
        //PURPLE
        //Level 1
        DevelopmentCardDeck PurpleOne = new DevelopmentCardDeck();
        ArrayList<Goods> cost=new ArrayList<>();
        cost.add(new Goods(Resource.SERVANT, 3));
        ArrayList<Goods> input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 2));
        ArrayList<Goods> output=new ArrayList<>();
        output.add(new Goods(Resource.SERVANT,1));
        output.add(new Goods(Resource.SHIELD,1));
        output.add(new Goods(Resource.STONE,1));
        Production production=new Production(input, output);
        DevelopmentCard devCard=new DevelopmentCard(3, CardColor.PURPLE, 1, cost, production, 0);
        PurpleOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 1));
        cost.add(new Goods(Resource.SHIELD,1));
        cost.add(new Goods(Resource.SERVANT,1));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SHIELD,1));
        production=new Production(input, output);
        devCard=new DevelopmentCard(2, CardColor.PURPLE, 1, cost, production, 0);
        PurpleOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE, 2));
        cost.add(new Goods(Resource.SERVANT,2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        input.add(new Goods(Resource.SHIELD,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.STONE,2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(4, CardColor.PURPLE, 1, cost, production, 1);
        PurpleOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SERVANT,2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        output=new ArrayList<>();
        production=new Production(input, output);
        devCard=new DevelopmentCard(1, CardColor.PURPLE, 1, cost, production, 1);
        PurpleOne.add(devCard);
        devCardsDecks.add(PurpleOne);

        //level 2
        DevelopmentCardDeck PurpleTwo = new DevelopmentCardDeck();
        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SERVANT,4));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        output=new ArrayList<>();
        production=new Production(input, output);
        devCard=new DevelopmentCard(5, CardColor.PURPLE, 2, cost, production, 2);
        PurpleTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SERVANT,5));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 2));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(7, CardColor.PURPLE, 2, cost, production, 2);
        PurpleTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD,3));
        cost.add(new Goods(Resource.SERVANT,3));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SERVANT,2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(8, CardColor.PURPLE, 2, cost, production, 1);
        PurpleTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 2));
        cost.add(new Goods(Resource.SERVANT,3));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        input.add(new Goods(Resource.SERVANT,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SHIELD,3));
        production=new Production(input, output);
        devCard=new DevelopmentCard(6, CardColor.PURPLE, 2, cost, production, 0);
        PurpleTwo.add(devCard);
        devCardsDecks.add(PurpleTwo);

        //level3
        DevelopmentCardDeck PurpleThree = new DevelopmentCardDeck();
        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD,4));
        cost.add(new Goods(Resource.SERVANT,4));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.STONE,3));
        output.add(new Goods(Resource.SERVANT,1));
        production=new Production(input, output);
        devCard=new DevelopmentCard(12, CardColor.PURPLE, 3, cost, production, 0);
        PurpleThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SERVANT,7));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.STONE,1));
        production=new Production(input, output);
        devCard=new DevelopmentCard(11, CardColor.PURPLE, 3, cost, production, 3);
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
        production=new Production(input, output);
        devCard=new DevelopmentCard(10, CardColor.PURPLE, 3, cost, production, 1);
        PurpleThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SERVANT,6));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 2));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,3));
        production=new Production(input, output);
        devCard=new DevelopmentCard(9, CardColor.PURPLE, 3, cost, production, 2);
        PurpleThree.add(devCard);
        devCardsDecks.add(PurpleThree);


        //YELLOW
        //level1
        DevelopmentCardDeck YellowOne = new DevelopmentCardDeck();
        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 1));
        output=new ArrayList<>();
        production=new Production(input, output);
        devCard=new DevelopmentCard(1, CardColor.YELLOW, 1, cost, production, 1);
        YellowOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.COIN,1));
        cost.add(new Goods(Resource.SHIELD,1));
        cost.add(new Goods(Resource.STONE,1));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,1));
        production=new Production(input, output);
        devCard=new DevelopmentCard(2, CardColor.YELLOW, 1, cost, production, 0);
        YellowOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,2));
        cost.add(new Goods(Resource.SHIELD,2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        input.add(new Goods(Resource.SERVANT,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SHIELD,2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(4, CardColor.YELLOW, 1, cost, production, 1);
        YellowOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,3));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD, 2));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,1));
        output.add(new Goods(Resource.SERVANT,1));
        output.add(new Goods(Resource.STONE,1));
        production=new Production(input, output);
        devCard=new DevelopmentCard(3, CardColor.YELLOW, 1, cost, production, 0);
        YellowOne.add(devCard);
        devCardsDecks.add(YellowOne);

        //level 2
        DevelopmentCardDeck YellowTwo = new DevelopmentCardDeck();
        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,3));
        cost.add(new Goods(Resource.SHIELD,2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        input.add(new Goods(Resource.SHIELD,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,3));
        production=new Production(input, output);
        devCard=new DevelopmentCard(6, CardColor.YELLOW, 2, cost, production, 0);
        YellowTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,5));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD,2));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SERVANT,2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(7, CardColor.YELLOW, 2, cost, production, 2);
        YellowTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,3));
        cost.add(new Goods(Resource.SERVANT,3));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(8, CardColor.YELLOW, 2, cost, production, 1);
        YellowTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,4));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD,1));
        output=new ArrayList<>();
        production=new Production(input, output);
        devCard=new DevelopmentCard(5, CardColor.YELLOW, 2, cost, production, 2);
        YellowTwo.add(devCard);
        devCardsDecks.add(YellowOne);


        //level 3
        DevelopmentCardDeck YellowThree = new DevelopmentCardDeck();
        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,5));
        cost.add(new Goods(Resource.SERVANT,2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        input.add(new Goods(Resource.SERVANT,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN,2));
        output.add(new Goods(Resource.SHIELD,2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(10, CardColor.YELLOW, 3, cost, production, 1);
        YellowThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,6));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD, 2));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SERVANT,3));
        production=new Production(input, output);
        devCard=new DevelopmentCard(9, CardColor.YELLOW, 3, cost, production, 2);
        YellowThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,7));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SERVANT,1));
        production=new Production(input, output);
        devCard=new DevelopmentCard(11, CardColor.YELLOW, 3, cost, production, 3);
        YellowThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.STONE,4));
        cost.add(new Goods(Resource.SERVANT,4));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD,1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.STONE,1));
        output.add(new Goods(Resource.SERVANT,3));
        production=new Production(input, output);
        devCard=new DevelopmentCard(12, CardColor.YELLOW, 3, cost, production, 0);
        YellowThree.add(devCard);
        devCardsDecks.add(YellowOne);

        //level 1 BLUE
        DevelopmentCardDeck BlueOne = new DevelopmentCardDeck();
        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 3));
        input= new ArrayList<>();
        input.add(new Goods(Resource.STONE, 2));
        output= new ArrayList<>();
        output.add(new Goods(Resource.COIN, 1));
        production=new Production(input, output);
        devCard=new DevelopmentCard(3, CardColor.BLUE, 1, cost, production, 0);
        BlueOne.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 2));
        cost.add(new Goods(Resource.SERVANT, 2));
        input= new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        input.add(new Goods(Resource.SHIELD, 1));
        output= new ArrayList<>();
        output.add(new Goods(Resource.SERVANT, 2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(4, CardColor.BLUE, 1, cost, production, 1);
        BlueOne.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 2));
        input= new ArrayList<>();
        input.add(new Goods(Resource.SHIELD, 1));
        output= new ArrayList<>();
        production=new Production(input, output);
        devCard=new DevelopmentCard(1, CardColor.BLUE, 1, cost, production, 1);
        BlueOne.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 1));
        cost.add(new Goods(Resource.STONE, 1));
        cost.add(new Goods(Resource.SERVANT, 1));
        input= new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 1));
        output= new ArrayList<>();
        output.add(new Goods(Resource.STONE, 1));
        production=new Production(input, output);
        devCard=new DevelopmentCard(2, CardColor.BLUE, 1, cost, production, 0);
        BlueOne.add(devCard);
        devCardsDecks.add(BlueOne);

        //Level 2 BLUE
        DevelopmentCardDeck BlueTwo = new DevelopmentCardDeck();
        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 4));
        input= new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 1));
        output= new ArrayList<>();
        production=new Production(input, output);
        devCard=new DevelopmentCard(5, CardColor.BLUE, 2, cost, production, 2);
        BlueTwo.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 3));
        cost.add(new Goods(Resource.STONE, 2));
        input= new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        input.add(new Goods(Resource.STONE, 1));
        output= new ArrayList<>();
        output.add(new Goods(Resource.SERVANT, 3));
        production=new Production(input, output);
        devCard=new DevelopmentCard(6, CardColor.BLUE, 2, cost, production, 0);
        BlueTwo.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 5));
        input= new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 2));
        output= new ArrayList<>();
        output.add(new Goods(Resource.SHIELD, 2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(7, CardColor.BLUE, 2, cost, production, 2);
        BlueTwo.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 3));
        cost.add(new Goods(Resource.STONE, 3));
        input= new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 1));
        output= new ArrayList<>();
        output.add(new Goods(Resource.STONE, 2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(8, CardColor.BLUE, 2, cost, production, 1);
        BlueTwo.add(devCard);
        devCardsDecks.add(BlueTwo);

        //level 3 BLUE
        DevelopmentCardDeck BlueThree = new DevelopmentCardDeck();
        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 6));
        input= new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 2));
        output= new ArrayList<>();
        output.add(new Goods(Resource.SHIELD, 3));
        production=new Production(input, output);
        devCard=new DevelopmentCard(9, CardColor.BLUE, 3, cost, production, 2);
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
        production=new Production(input, output);
        devCard=new DevelopmentCard(10, CardColor.BLUE, 3, cost, production, 1);
        BlueThree.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 7));
        input= new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        output= new ArrayList<>();
        output.add(new Goods(Resource.SHIELD, 1));
        production=new Production(input, output);
        devCard=new DevelopmentCard(11, CardColor.BLUE, 3, cost, production, 3);
        BlueThree.add(devCard);

        cost = new ArrayList<>();
        cost.add(new Goods(Resource.COIN, 4));
        cost.add(new Goods(Resource.STONE, 4));
        input= new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 1));
        output= new ArrayList<>();
        output.add(new Goods(Resource.COIN, 1));
        output.add(new Goods(Resource.SHIELD, 3));
        production=new Production(input, output);
        devCard=new DevelopmentCard(12, CardColor.BLUE, 3, cost, production, 0);
        BlueThree.add(devCard);
        devCardsDecks.add(BlueThree);

        //GREEN
        //level1
        DevelopmentCardDeck GreenOne = new DevelopmentCardDeck();
        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        output=new ArrayList<>();
        production=new Production(input, output);
        devCard=new DevelopmentCard(1, CardColor.GREEN, 1, cost, production, 1);
        GreenOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 2));
        cost.add(new Goods(Resource.COIN, 2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        input.add(new Goods(Resource.SERVANT, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN, 2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(4, CardColor.GREEN, 1, cost, production, 1);
        GreenOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 1));
        cost.add(new Goods(Resource.SERVANT, 1));
        cost.add(new Goods(Resource.STONE, 1));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SERVANT, 2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(2, CardColor.GREEN, 1, cost, production, 0);
        GreenOne.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 3));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 2));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN, 1));
        output.add(new Goods(Resource.SHIELD, 1));
        output.add(new Goods(Resource.STONE, 1));
        production=new Production(input, output);
        devCard=new DevelopmentCard(3, CardColor.GREEN, 1, cost, production, 0);
        GreenOne.add(devCard);
        devCardsDecks.add(GreenOne);

        //level2
        DevelopmentCardDeck GreenTwo = new DevelopmentCardDeck();
        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 4));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        output=new ArrayList<>();
        production=new Production(input, output);
        devCard=new DevelopmentCard(5, CardColor.GREEN, 2, cost, production, 2);
        GreenTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 3));
        cost.add(new Goods(Resource.SERVANT, 2));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SHIELD, 1));
        input.add(new Goods(Resource.SERVANT, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.STONE, 3));
        production=new Production(input, output);
        devCard=new DevelopmentCard(6, CardColor.GREEN, 2, cost, production, 0);
        GreenTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 5));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 2));
        output=new ArrayList<>();
        output.add(new Goods(Resource.STONE, 2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(7, CardColor.GREEN, 2, cost, production, 2);
        GreenTwo.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 3));
        cost.add(new Goods(Resource.COIN, 3));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.SHIELD, 2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(8, CardColor.GREEN, 2, cost, production, 1);
        GreenTwo.add(devCard);
        devCardsDecks.add(GreenTwo);

        //level3
        DevelopmentCardDeck GreenThree = new DevelopmentCardDeck();
        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 6));
        input=new ArrayList<>();
        input.add(new Goods(Resource.COIN, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.STONE, 3));
        production=new Production(input, output);
        devCard=new DevelopmentCard(9, CardColor.GREEN, 3, cost, production, 2);
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
        production=new Production(input, output);
        devCard=new DevelopmentCard(10, CardColor.GREEN, 3, cost, production, 1);
        GreenThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 7));
        input=new ArrayList<>();
        input.add(new Goods(Resource.SERVANT, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN, 2));
        production=new Production(input, output);
        devCard=new DevelopmentCard(11, CardColor.GREEN, 3, cost, production, 3);
        GreenThree.add(devCard);

        cost=new ArrayList<>();
        cost.add(new Goods(Resource.SHIELD, 4));
        cost.add(new Goods(Resource.COIN, 4));
        input=new ArrayList<>();
        input.add(new Goods(Resource.STONE, 1));
        output=new ArrayList<>();
        output.add(new Goods(Resource.COIN, 3));
        output.add(new Goods(Resource.SHIELD, 1));
        production=new Production(input, output);
        devCard=new DevelopmentCard(12, CardColor.GREEN, 3, cost, production, 0);
        GreenThree.add(devCard);
        devCardsDecks.add(GreenThree);
    }
}
