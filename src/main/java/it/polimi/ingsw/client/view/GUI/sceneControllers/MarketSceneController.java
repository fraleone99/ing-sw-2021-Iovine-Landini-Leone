package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.gameboard.Ball;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

public class MarketSceneController {
    @FXML public GridPane marketGrid;
    @FXML public ImageView ramp;
    @FXML ImageView im00;
    @FXML ImageView im01;
    @FXML ImageView im02;
    @FXML ImageView im10;
    @FXML ImageView im11;
    @FXML ImageView im12;
    @FXML ImageView im20;
    @FXML ImageView im21;
    @FXML ImageView im22;
    @FXML ImageView im30;
    @FXML ImageView im31;
    @FXML ImageView im32;

    ImageView[][] imageViewMatrix = new ImageView[3][4];


    private GUI gui;
    private Map<BallColor, String> ballColorPathMap = new HashMap<>();


    public void setGui(GUI gui) {
        this.gui = gui;

        ballColorPathMap.put(BallColor.BLUE, "/graphics/BLUE_BALL.png");
        ballColorPathMap.put(BallColor.WHITE, "/graphics/WHITE_BALL.png");
        ballColorPathMap.put(BallColor.RED, "/graphics/RED_BALL.png");
        ballColorPathMap.put(BallColor.PURPLE, "/graphics/PURPLE_BALL.png");
        ballColorPathMap.put(BallColor.GREY, "/graphics/GREY_BALL.png");
        ballColorPathMap.put(BallColor.YELLOW, "/graphics/YELLOW_BALL.png");

        imageViewMatrix[0][0] = im00;
        imageViewMatrix[0][1] = im10;
        imageViewMatrix[0][2] = im20;
        imageViewMatrix[0][3] = im30;

        imageViewMatrix[1][0] = im01;
        imageViewMatrix[1][1] = im11;
        imageViewMatrix[1][2] = im21;
        imageViewMatrix[1][3] = im31;

        imageViewMatrix[2][0] = im02;
        imageViewMatrix[2][1] = im12;
        imageViewMatrix[2][2] = im22;
        imageViewMatrix[2][3] = im32;



    }

    public void updateMarket(it.polimi.ingsw.model.gameboard.Market market){

        Ball[][] matrix = market.getMatrix();

        for (int i = 0; i< 3; i++){
            for(int j = 0; j<4; j++){
                Image ball = new Image(ballColorPathMap.get(matrix[i][j].getType()));
                imageViewMatrix[i][j].setImage(ball);
            }
        }
        Image rampIm = new Image(ballColorPathMap.get(market.getRamp().getType()));
        ramp.setImage(rampIm);

    }
}
