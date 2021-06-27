package it.polimi.ingsw.server.answer.infoanswer;

import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.gameboard.playerdashboard.DevCardsSpace;
import it.polimi.ingsw.server.answer.Answer;

import java.util.HashMap;


/**
 * Message containing information about a player's development cards space.
 *
 * @author Lorenzo Iovine
 */
public class DevCardsSpaceInfo implements Answer {
    private final int victoryPoints;

    private final HashMap<String, Integer> numberOfCardByColor = new HashMap<>();


    public DevCardsSpaceInfo(DevCardsSpace space) {
        victoryPoints = space.pointsByDevCards();


        numberOfCardByColor.put("Yellow level one", space.getNumberOfColor(CardColor.YELLOW, 1));
        numberOfCardByColor.put("Yellow level two", space.getNumberOfColor(CardColor.YELLOW, 2));
        numberOfCardByColor.put("Yellow level three", space.getNumberOfColor(CardColor.YELLOW, 3));

        numberOfCardByColor.put("Purple level one", space.getNumberOfColor(CardColor.PURPLE, 1));
        numberOfCardByColor.put("Purple level two", space.getNumberOfColor(CardColor.PURPLE, 2));
        numberOfCardByColor.put("Purple level three", space.getNumberOfColor(CardColor.PURPLE, 3));

        numberOfCardByColor.put("Green level one", space.getNumberOfColor(CardColor.GREEN, 1));
        numberOfCardByColor.put("Green level two", space.getNumberOfColor(CardColor.GREEN, 2));
        numberOfCardByColor.put("Green level three", space.getNumberOfColor(CardColor.GREEN, 3));

        numberOfCardByColor.put("Blue level one", space.getNumberOfColor(CardColor.BLUE, 1));
        numberOfCardByColor.put("Blue level two", space.getNumberOfColor(CardColor.BLUE, 2));
        numberOfCardByColor.put("Blue level three", space.getNumberOfColor(CardColor.BLUE, 3));

    }


    public int getVictoryPoints() {
        return victoryPoints;
    }

    public HashMap<String, Integer> getNumberOfCardByColor() {
        return numberOfCardByColor;
    }

    @Override
    public Object getMessage() {
        return null;
    }
}
