package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.model.card.leadercard.LeaderCard;
import it.polimi.ingsw.model.gameboard.playerdashboard.FaithPath;
import it.polimi.ingsw.server.answer.FaithPathInfo;

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

    public void localPrintFaithPath(FaithPathInfo infoPlayer, int LorenzoPos){
        printFaithPath(infoPlayer);

        printLorenzoFaith(LorenzoPos);

        //TODO lorenzo's faith path
    }

}
