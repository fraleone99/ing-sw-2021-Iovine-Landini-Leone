package it.polimi.ingsw.model;

/**
 * Cell Class manages every cell of the FaithPath
 *
 * @author Nicola Landini
 */

public class Cell {

    private int position;
    private boolean isPopespace1;
    private boolean isPopespace2;
    private boolean isPopespace3;

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
