package it.polimi.ingsw.model.gameboard.playerdashboard;

/**
 * Cell Class manages every cell of the FaithPath
 *
 * @author Nicola Landini
 */

public class Cell {

    private final boolean isPopeSpace1;
    private final boolean isPopeSpace2;
    private final boolean isPopeSpace3;
    private final int points;

    /**
     * Cell constructor: every cell indicates if it is a pope space and the points given by his position
     * @param isPopeSpace1 is a boolean that is set to true if only the cells matches with the first pope space
     * @param isPopeSpace2 is a boolean that is set to true if only the cells matches with the second pope space
     * @param isPopeSpace3 is a boolean that is set to true if only the cells matches with the third pope space
     * @param points indicates the corresponding points of that cell
     */
    public Cell(boolean isPopeSpace1, boolean isPopeSpace2, boolean isPopeSpace3, int points) {
        this.isPopeSpace1=isPopeSpace1;
        this.isPopeSpace2=isPopeSpace2;
        this.isPopeSpace3=isPopeSpace3;
        this.points=points;
    }

    public int getPoints() { return points; }

    @Override
    public String toString(){
        return "Cell{" +
                "isPopeSpace1=" + isPopeSpace1 +
                ",isPopeSpace2=" + isPopeSpace2 +
                ",isPopeSpace3=" + isPopeSpace3 +
                ",points=" + points +
                '}';
    }
}
