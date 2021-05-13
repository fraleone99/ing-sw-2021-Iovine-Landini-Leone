package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.server.answer.turnanswer.SeeBall;

import java.lang.reflect.Array;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;

public class SinglePlayerCLI extends CLI {

    public void writeMessage(String message) {
        System.out.println(message);
    }

    public void localHandShake(String nickname) {
        handShake("\nWelcome to the game "+ Constants.ANSI_GREEN+nickname+Constants.ANSI_RESET);
    }

    public int discardFirstLeader(int number, ArrayList<Integer> leaderID){
        int answer;

        if(number == 1){
            writeMessage("Please choose the first leader to discard:\n");
        } else {
            writeMessage("Please choose the second leader to discard:\n");
        }

        answer=askLeaderToDiscard(leaderID);

        return answer;
    }

    public int localManageStorage(int number) {
        if(number==1)
            return ManageStorage("Before using the market do you want to reorder your storage? You won't be able to do it later.");
        else
            return ManageStorage("Do you want to make other changes to the storage?");
    }


    public ArrayList<Integer> localSeeBall(SeeBall ball) {
        ArrayList<Integer> choices = new ArrayList<>();

        choices.add(seeBall(ball));
        choices.add(chooseShelf());

        return choices;
    }

    public Resource localAskInput(String message){
        int choice=askInput(message);
        return parser(choice);
    }

    public Resource localAskOutput(String message){
        int choice=askOutput(message);
        return parser(choice);
    }

    public Resource parser(int answer){
        switch(answer) {
            case 1 : return Resource.COIN;
            case 2 : return Resource.SERVANT;
            case 3 : return Resource.SHIELD;
            case 4 : return Resource.STONE;
            default : return null;
        }
    }


}
