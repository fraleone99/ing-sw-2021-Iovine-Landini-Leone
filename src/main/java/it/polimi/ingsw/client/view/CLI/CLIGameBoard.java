package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.Handler;
import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.model.gameboard.Market;

import java.util.ArrayList;

/**
 * This class allows the player to see the game board.
 * See CLI.
 */
public class CLIGameBoard {
    private Handler handler = null;
    private final CLIInitialize initialize;
    private final CLIPrint print;

    public CLIGameBoard(CLIInitialize initialize, CLIPrint print) {
        this.initialize = initialize;
        this.print = print;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void seeGameBoard(String message) {
        Thread t=new Thread( () -> {
            int choice;
            System.out.println(message);

            choice = initialize.askInt(1, 7, "1) Leader Cards.\n2) Market.\n3) Development cards grid.\n" +
                    "4) Cards for production.\n5) Active Leader Cards of the other players.\n6) Development Cards" +
                    " that can be used by the other players.\n7) Nothing");

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void seeLeaderCards(ArrayList<Integer> leaderCards) {
        Thread t=new Thread( () -> {
            int choice;

            for (Integer leaderCard : leaderCards) {
                System.out.println(initialize.printLeaderCard(initialize.getLeaderDeck().getFromID(leaderCard)));
            }

            choice = initialize.askInt(1, 2, "Do you want to see more from the Game Board?\n1) Yes\n2) No");

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void seeMarket(Market market) {
        Thread t=new Thread( () -> {
            int choice;

            System.out.println(print.printMarket(market));

            choice = initialize.askInt(1, 2, "Do you want to see more from the Game Board?\n1) Yes\n2) No");

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void chooseLine(String message) {
        Thread t=new Thread( () -> {
            int choice;

            System.out.println(message);

            choice = initialize.askInt(1, 7, "1) Level one cards\n2) Level two cards\n3) Level three cards\n" +
                    "4) Purple cards\n5) Yellow Cards\n6) Blue Cards\n7) Green Cards");

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void seeGrid(ArrayList<Integer> devCards) {
        Thread t=new Thread( () -> {
            int choice;

            for (Integer devCard : devCards) {
                System.out.println(initialize.printDevelopmentCard(initialize.getDevelopmentCardDeck().getCardByID(devCard)));
            }

            choice = initialize.askInt(1, 2, "Do you want to see more from the Game Board?\n1) Yes\n2) No");

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void seeProductions(ArrayList<Integer> productions) {
        Thread t=new Thread( () -> {
            int choice;

            if (productions.size() == 0) {
                System.out.println("You haven't cards to make productions.");
            } else {
                for (Integer production : productions) {
                    if (production < 17)
                        System.out.println(initialize.printLeaderCard(initialize.getLeaderDeck().getFromID(production)));
                    else
                        System.out.println(initialize.printDevelopmentCard(initialize.getDevelopmentCardDeck().getCardByID(production)));
                }
            }

            choice = initialize.askInt(1, 2, "Do you want to see more from the Game Board?\n1) Yes\n2) No");

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void seeOtherCards(ArrayList<Integer> cards) {
        for(Integer card : cards) {
            if (card < 17)
                System.out.println(initialize.printLeaderCard(initialize.getLeaderDeck().getFromID(card)));
            else
                System.out.println(initialize.printDevelopmentCard(initialize.getDevelopmentCardDeck().getCardByID(card)));
        }
    }

    public void choice() {
        Thread t = new Thread(() -> {
            int choice = initialize.askInt(1, 2, "Do you want to see more from the Game Board?\n1) Yes\n2) No");
            handler.send(new SendInt(choice));
        });
        t.start();
    }
}
