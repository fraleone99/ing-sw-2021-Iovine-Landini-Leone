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

    public Cell(boolean isPopeSpace1, boolean isPopeSpace2, boolean isPopeSpace3, int points) {
        this.isPopeSpace1=isPopeSpace1;
        this.isPopeSpace2=isPopeSpace2;
        this.isPopeSpace3=isPopeSpace3;
        this.points=points;
    }

    public int getPoints() { return points; }

    public boolean isPopeSpace1(){
        return isPopeSpace1;
    }

    public boolean isPopeSpace2(){
        return isPopeSpace2;
    }

    public boolean isPopeSpace3(){
        return isPopeSpace3;
    }

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
