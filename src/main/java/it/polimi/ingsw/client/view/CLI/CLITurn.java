package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.Handler;
import it.polimi.ingsw.client.message.SendDoubleInt;
import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.server.answer.seegameboard.SeeBall;
import it.polimi.ingsw.server.answer.turnanswer.ActiveLeader;
import it.polimi.ingsw.server.answer.turnanswer.DiscardLeader;

import java.util.ArrayList;

public class CLITurn {
    private final CLIInitialize initialize;
    private Handler handler = null;

    public CLITurn(CLIInitialize initialize) {
        this.initialize = initialize;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void askTurnType(String message) {
        Thread t=new Thread( () -> {
            int choose;

            System.out.println(message);

            choose = initialize.askInt(1, 5, "1) Active Leader Card.\n2) Discard Leader Card.\n3) Use Market.\n" +
                    "4) Buy Development Card.\n5) Active Productions");

            handler.send(new SendInt(choose));
        }
        );
        t.start();
    }

    public void activeLeader(ActiveLeader message){
        Thread t=new Thread( () -> {
            int leaderCard;

            leaderCard = initialize.askInt(1, 2, message.getMessage());

            int id = message.getLeaders().get(leaderCard - 1);

            initialize.getLeaderDeck().getFromID(id).setIsActive();

            handler.send(new SendInt(leaderCard));
        }
        );
        t.start();
    }

    public void discardLeader(DiscardLeader message) {
        Thread t=new Thread( () -> {
            int leaderCard;

            leaderCard = initialize.askInt(1, 2, message.getMessage());

            int id = message.getLeaders().get(leaderCard - 1);

            initialize.getLeaderDeck().getFromID(id).setIsDiscarded();

            handler.send(new SendInt(leaderCard));
        }
        );
        t.start();
    }

    public void resetCard(int pos) {
        initialize.getLeaderDeck().getFromID(pos).setIsNotActive();
        initialize.getLeaderDeck().getFromID(pos).setIsNotDiscarded();
    }

    public void ManageStorage(String message) {
        Thread t=new Thread( () -> {
            int choice;


            choice = initialize.askInt(1, 2, "Resources that cannot be placed, will be automatically discarded.\n" +
                    message + "\n1) Yes\n2) No");

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void MoveShelves(String message) {
        Thread t=new Thread( () -> {
            int firstShelf;
            int secondShelf;

            System.out.println(message);

            firstShelf = initialize.askInt(1, 3, "FirstShelf:");
            secondShelf = initialize.askInt(1, 3, "Second Shelf:");

            handler.send(new SendDoubleInt(firstShelf,secondShelf));
        }
        );
        t.start();
    }

    public void useMarket(String message) {
        Thread t=new Thread( () -> {
            int line;

            line = initialize.askInt(1, 7, message);

            handler.send(new SendInt(line));
        }
        );
        t.start();
    }

    public void chooseWhiteBallLeader(String message) {
        Thread t=new Thread( () -> {
            int choice;

            choice = initialize.askInt(1, 2, message);

            handler.send(new SendInt(choice));
        }
        );
        t.start();
    }

    public void seeBall(SeeBall ball) {
        Thread t = new Thread(()->{
            System.out.println("These are the resources taken from the market, which one do you want to place in the storage?");
            for(int i=0;i<ball.getBalls().size();i++) {
                System.out.println((i+1) + ")" + initialize.getBallToString().get(ball.getBalls().get(i).getType()));
            }
            int choice = initialize.askInt(1,ball.getBalls().size(),"");
            handler.send(new SendInt(choice));

        });
        t.start();
    }

    public void chooseShelf() {
        Thread t = new Thread(()->{
            int shelf = initialize.askInt(1,4,"Which shelf do you want to put this resource on? " +
                    "(Press 4 if you want to use the Storage leaders)");
            handler.send(new SendInt(shelf));

        });
        t.start();
    }

    public void askCardToBuy() {
        Thread t = new Thread( () -> {
            ArrayList<Integer> card = new ArrayList<>();

            int color = (initialize.askInt(1,4,"Choose the color of the card you want to buy.\n1) Purple\n2) Yellow\n3) Blue\n4) Green"));
            int level = (initialize.askInt(1,3,"Choose the level of the card you want to buy"));

            handler.send(new SendDoubleInt(color, level));
        });
        t.start();
    }

    public void askColor(String message) {
        Thread t = new Thread(()->{
            int color = initialize.askInt(1,4,message);
            handler.send(new SendInt(color));

        });
        t.start();
    }

    public void askLevel(String message) {
        Thread t = new Thread(()->{
            int level = initialize.askInt(1,3,message);
            handler.send(new SendInt(level));

        });
        t.start();
    }

    public void askSpace(String message) {
        Thread t = new Thread(()->{
            int space = initialize.askInt(1,3,message);
            handler.send(new SendInt(space));

        });
        t.start();
    }

    public void askType(String message) {
        Thread t = new Thread(() -> {
            int type = initialize.askInt(1, 4, message);
            handler.send(new SendInt(type));

        });
        t.start();
    }

    public void askInput(String message) {
        Thread t = new Thread(()->{
            int input = initialize.askInt(1,4,message);
            handler.send(new SendInt(input));

        });
        t.start();
    }

    public void askOutput(String message) {
        Thread t = new Thread(()->{
            int output = initialize.askInt(1,4,message);
            handler.send(new SendInt(output));

        });
        t.start();
    }

    public void askDevelopmentCard(String message) {
        Thread t = new Thread(()->{
            int space = initialize.askInt(1,3,message);
            handler.send(new SendInt(space));

        });
        t.start();
    }

    public void askLeaderCard(String message) {
        Thread t = new Thread(()->{
            int index = initialize.askInt(1,2,message);
            handler.send(new SendInt(index));
        });
        t.start();
    }

    public void endTurn(String message) {
        Thread t = new Thread(()->{
            int choice = initialize.askInt(1,3,message);
            handler.send(new SendInt(choice));
        });
        t.start();

    }
}
