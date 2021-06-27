package it.polimi.ingsw.model.gameboard.playerdashboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class is used to represent the faith path of each player
 *
 * @author Nicola Landini
 */

public class FaithPath {
    private final ArrayList<Cell> space=new ArrayList<>();
    private int positionFaithPath;
    private int positionLorenzo;
    private int papalPoints;
    private boolean papalPawn1;
    private boolean papalPawn2;
    private boolean papalPawn3;

    /**
     * FaithPath constructor: creates a new instance of the faith path
     */
    public FaithPath() {
        positionFaithPath=0;
        positionLorenzo=0;
        papalPoints=0;
        papalPawn1=false;
        papalPawn2=false;
        papalPawn3=false;
        initializeCells();
    }

    /**
     * This method initializes faith path cells
     */
    public void initializeCells(){
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new InputStreamReader(Cell.class.getResourceAsStream("/JSON/Cells.json")));
        ArrayList<Cell> data = gson.fromJson(jsonReader, new TypeToken<ArrayList<Cell>>(){}.getType());
        space.addAll(data);

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

    public void setPapalPawn1() {
        this.papalPawn1 = true;
        papalPoints=papalPoints+2;
    }

    public void setPapalPawn2() {
        this.papalPawn2 = true;
        papalPoints=papalPoints+3;
    }

    public void setPapalPawn3() {
        this.papalPawn3 = true;
        papalPoints=papalPoints+4;
    }

    public int getPositionFaithPath() { return positionFaithPath; }

    public int getPositionLorenzo() { return positionLorenzo; }

    /**
     * This method shifts player's position forward of move steps
     * @param move steps forward for the player
     */
    public void moveForward(int move){
        positionFaithPath = positionFaithPath+move;
    }

    /**
     * This method shifts Lorenzo il Magnifico's position forward of move steps
     * @param move steps forward for Lorenzo il Magnifico
     */
    public void moveForwardBCM(int move){
        positionLorenzo = positionLorenzo+move;
    }

    public void setPositionFaithPath(int positionFaithPath) {
        this.positionFaithPath = positionFaithPath;
    }

    /**
     * This method is called every times some player reaches papal favor position
     * and it establishes if the player has the right to activate the corresponding papal pawn
     * @param spaceIndicator indicates the papal space
     * @return a boolean that indicates if the pawn has to be activated
     */
    public boolean activatePapalPawn(int spaceIndicator) {
        switch(spaceIndicator){
            case 1: if(positionFaithPath>4){
                papalPawn1=true;
                papalPoints=papalPoints+1;
                return true;
            }
            case 2: if(positionFaithPath>11){
                papalPawn2=true;
                papalPoints=papalPoints+2;
                return true;
            }
            case 3: if(positionFaithPath>18){
                papalPawn3=true;
                papalPoints=papalPoints+3;
                return true;
            }
        }
        return false;
    }

    /**
     * This method calculate how much points the player accumulated from his position
     * and eventual papal pawns
     * @return the total number of point accumulated with faith path position and eventual papal pawns
     */
    public int getTotalPoint() {
        return space.get(positionFaithPath).getPoints()+papalPoints;
    }
}
