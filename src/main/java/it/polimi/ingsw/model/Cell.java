package it.polimi.ingsw.model;

/**
 * Cell Class manages every cell of the FaithPath
 *
 * @author Nicola Landini
 */

public class Cell {

    private final int position;
    private final boolean isPopespace1;
    private final boolean isPopespace2;
    private final boolean isPopespace3;

    public Cell(int position, boolean isPopespace1, boolean isPopespace2, boolean isPopespace3) {
        this.position = position;
        this.isPopespace1=isPopespace1;
        this.isPopespace2=isPopespace2;
        this.isPopespace3=isPopespace3;
    }

    public int getPosition(){
        return position;
    }

    public boolean isPopeSpace1(){
        return isPopespace1;
    }

    public boolean isPopeSpace2(){
        return isPopespace2;
    }

    public boolean isPopeSpace3(){
        return isPopespace3;
    }
}
