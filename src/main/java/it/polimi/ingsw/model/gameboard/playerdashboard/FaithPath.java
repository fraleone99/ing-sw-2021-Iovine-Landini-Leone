package it.polimi.ingsw.model.gameboard.playerdashboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.exceptions.NotExistingSpaceException;
import it.polimi.ingsw.model.card.developmentcard.DevelopmentCard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * FaithPath Class is used for handling the position changes of each player
 *
 * @author Nicola Landini
 */

public class FaithPath {
    private ArrayList<Cell> space=new ArrayList<>();
    private int positionFaithPath;
    private int positionLorenzo;
    private int papalPoints;
    private boolean papalPawn1;
    private boolean papalPawn2;
    private boolean papalPawn3;

    public FaithPath() {
        positionFaithPath=0;
        positionLorenzo=0;
        papalPoints=0;
        papalPawn1=false;
        papalPawn2=false;
        papalPawn3=false;
        initializeCells();
    }

    public void initializeCells(){
        try{
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(new FileReader("src/main/java/it/polimi/ingsw/model/resources/Cells.json"));
            ArrayList<Cell> data = gson.fromJson(jsonReader, new TypeToken<ArrayList<Cell>>(){}.getType());

            for(Cell cell: data){
                space.add(cell);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
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

    public int getPositionLorenzo() { return positionLorenzo; }

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
            break;
            case 2: if(positionFaithPath>11){
                papalPawn2=true;
                papalPoints=papalPoints+2;
            }
            break;
            case 3: if(positionFaithPath>18){
                papalPawn3=true;
                papalPoints=papalPoints+3;
            }
            break;
        }
    }

    public int getTotalPoint() {
        return space.get(positionFaithPath).getPoints()+papalPoints;
    }
}
