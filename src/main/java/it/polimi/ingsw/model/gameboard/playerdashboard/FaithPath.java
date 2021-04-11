package it.polimi.ingsw.model.gameboard.playerdashboard;

import it.polimi.ingsw.exceptions.NotExistingSpaceException;

import java.util.ArrayList;

/**
 * FaithPath Class is used for handling the position changes of each player
 *
 * @author Nicola Landini
 */

public class FaithPath {
    private ArrayList<Cell> space=new ArrayList<>();
    private int positionFaithPath=0;
    private int positionLorenzo=0;
    private int papalPoints=0;
    private boolean papalPawn1=false;
    private boolean papalPawn2=false;
    private boolean papalPawn3=false;

    public FaithPath() {
        //0
        Cell cell=new Cell(false, false, false, 0);
        space.add(cell);

        //1
        cell=new Cell(false, false, false, 0);
        space.add(cell);

        //2
        cell=new Cell(false, false, false, 0);
        space.add(cell);

        //3
        cell=new Cell(false, false, false, 1);
        space.add(cell);

        //4
        cell=new Cell(false, false, false, 1);
        space.add(cell);

        //5
        cell=new Cell(false, false, false, 1);
        space.add(cell);

        //6
        cell=new Cell(false, false, false, 2);
        space.add(cell);

        //7
        cell=new Cell(false, false, false, 2);
        space.add(cell);

        //8
        cell=new Cell(true, false, false, 2);
        space.add(cell);

        //9
        cell=new Cell(false, false, false, 4);
        space.add(cell);

        //10
        cell=new Cell(false, false, false, 4);
        space.add(cell);

        //11
        cell=new Cell(false, false, false, 4);
        space.add(cell);

        //12
        cell=new Cell(false, false, false, 6);
        space.add(cell);

        //13
        cell=new Cell(false, false, false, 6);
        space.add(cell);

        //14
        cell=new Cell(false, false, false, 6);
        space.add(cell);

        //15
        cell=new Cell(false, false, false, 9);
        space.add(cell);

        //16
        cell=new Cell(false, true, false, 9);
        space.add(cell);

        //17
        cell=new Cell(false, false, false, 9);
        space.add(cell);

        //18
        cell=new Cell(false, false, false, 12);
        space.add(cell);

        //19
        cell=new Cell(false, false, false, 12);
        space.add(cell);

        //20
        cell=new Cell(false, false, false, 12);
        space.add(cell);

        //21
        cell=new Cell(false, false, false, 16);
        space.add(cell);

        //22
        cell=new Cell(false, false, false, 16);
        space.add(cell);

        //23
        cell=new Cell(false, false, false, 16);
        space.add(cell);

        //24
        cell=new Cell(false, false, true, 16);
        space.add(cell);
    }


    public boolean isPapalPawn1() {
        return papalPawn1;
    }

    public boolean isPapalPawn2() {
        return papalPawn2;
    }

    public boolean isPapalPawn3() {
        return papalPawn3;
    }

    public int getPositionFaithPath() { return positionFaithPath; }

    public void moveForward(int move){
        positionFaithPath = positionFaithPath+move;
    }

    public void moveForwardBCM(int move){
        positionLorenzo = positionLorenzo+move;
    }

    public void setPositionFaithPath(int positionFaithPath) {
        this.positionFaithPath = positionFaithPath;
    }

    //this method is called every times some player reaches papal favor position
    //spaceIndicator indicates which papal space
    public void activatePapalPawn(int spaceIndicator) throws NotExistingSpaceException {
        if (spaceIndicator != 1 && spaceIndicator != 2 && spaceIndicator != 3) {
            throw new NotExistingSpaceException();
        }

        switch(spaceIndicator){
            case 1: if(positionFaithPath>4){
                papalPawn1=true;
                papalPoints=papalPoints+1;
            }
            case 2: if(positionFaithPath>11){
                papalPawn2=true;
                papalPoints=papalPoints+2;
            }
            case 3: if(positionFaithPath>18){
                papalPawn3=true;
                papalPoints=papalPoints+3;
            }
        }
    }

    public int getTotalPoint() {
        return space.get(positionFaithPath).getPoints()+papalPoints;
    }
}
