package it.polimi.ingsw.model.gameboard.playerdashboard;

/**
 * Cell Class manages every cell of the FaithPath
 *
 * @author Nicola Landini
 */

public class Cell {

    private final int points;

    /**
     * Cell constructor: every cell indicates if it is a pope space and the points given by his position
     * @param points indicates the corresponding points of that cell
     */
    public Cell(int points) {
        this.points=points;
    }

    public int getPoints() { return points; }

}
