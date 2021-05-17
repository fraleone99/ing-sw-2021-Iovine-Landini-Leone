package it.polimi.ingsw.model.gameboard;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.model.enumeration.BallColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Market Class handles the shop
 *
 * @author Nicola Landini
 */

public class Market implements Serializable {
    private Ball[][] matrix = new Ball[3][4];
    private Ball ramp;
    private ArrayList<Ball>randomBall=new ArrayList<>();

    /**
     * Market constructor: creates a new instance of market
     */
    public Market() {
        Ball ball=new Ball(BallColor.WHITE);
        randomBall.add(ball);

        ball=new Ball(BallColor.WHITE);
        randomBall.add(ball);

        ball=new Ball(BallColor.WHITE);
        randomBall.add(ball);

        ball=new Ball(BallColor.WHITE);
        randomBall.add(ball);

        ball=new Ball(BallColor.BLUE);
        randomBall.add(ball);

        ball=new Ball(BallColor.BLUE);
        randomBall.add(ball);

        ball=new Ball(BallColor.GREY);
        randomBall.add(ball);

        ball=new Ball(BallColor.GREY);
        randomBall.add(ball);

        ball=new Ball(BallColor.YELLOW);
        randomBall.add(ball);

        ball=new Ball(BallColor.YELLOW);
        randomBall.add(ball);

        ball=new Ball(BallColor.PURPLE);
        randomBall.add(ball);

        ball=new Ball(BallColor.PURPLE);
        randomBall.add(ball);

        ball=new Ball(BallColor.RED);
        randomBall.add(ball);

        fillMatrix();
    }


    public Ball[][] getMatrix() {
        return matrix;
    }

    public Ball getRamp() {
        return ramp;
    }

    /**
     * This method randomly fills the market matrix
     */
    public void fillMatrix(){
        int cont=13;

        for(int i=0; i<3; i++) {
            for(int j=0; j<4; j++) {
                int num=(int)(Math.random()*cont); //casual number from 1 to cont
                matrix[i][j]=new Ball(randomBall.get(num));
                cont--;
                randomBall.remove(num);
            }
        }
        ramp=new Ball(randomBall.get(0));
        randomBall.clear();
    }

    /**
     * This method returns drawn marbles by the player choice
     * @param choice indicates which row or column has to be shifted:
     *               1-first column, 4-last column
     *               5-upper row, 7-lower row
     * @return drawn marbles
     * @throws InvalidChoiceException if the choice is invalid
     */
    public ArrayList<Ball> getChosenColor(int choice) throws InvalidChoiceException {
       if(choice<1 || choice>7){
           throw new InvalidChoiceException();
       }

       ArrayList<Ball> chosenBalls = new ArrayList<>();

       if(choice<5){
           for(int i=0; i<3; i++) {
               chosenBalls.add(matrix[i][choice-1]);
           }

           Ball temp;
           temp=matrix[2][choice-1];
           matrix[2][choice-1]=ramp;
           ramp=matrix[0][choice-1];
           matrix[0][choice-1]=matrix[1][choice-1];
           matrix[1][choice-1]=temp;
       }
       else {
           chosenBalls.addAll(Arrays.asList(matrix[choice - 5]).subList(0, 4));

           Ball temp;
           temp=matrix[choice-5][3];
           matrix[choice-5][3]=ramp;
           ramp=matrix[choice-5][0];
           System.arraycopy(matrix[choice - 5], 1, matrix[choice - 5], 0, 2);
           matrix[choice-5][2]=temp;
       }

       return chosenBalls;
    }
}
