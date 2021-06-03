package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.gameboard.Ball;
import it.polimi.ingsw.model.gameboard.Market;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

public class MarketSceneController {
    @FXML public GridPane marketGrid;
    @FXML public ImageView ramp;
    @FXML public Button button_1;
    @FXML public Button button_2;
    @FXML public Button button_3;
    @FXML public Button button_4;
    @FXML public Button button_5;
    @FXML public Button button_6;
    @FXML public Button button_7;
    @FXML public Button back_button;
    @FXML public Button Use_button;
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

    public void updateMarket(Market market){

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

    public void seePhase() {
        back_button.setOnAction(actionEvent -> {
            gui.changeGameBoard();
        });

        /*Use_button.setOnAction(actionEvent -> {
            gui.getHandler().send(new SendInt(2));//non voglio vedere più niente
            gui.getHandler().send(new SendInt(3));//in questo turno uso il mercato
            usePhase();
        });*/
    }

    public void usePhase() {

        button_1.setDisable(false);
        button_2.setDisable(false);
        button_3.setDisable(false);
        button_4.setDisable(false);
        button_5.setDisable(false);
        button_6.setDisable(false);
        button_7.setDisable(false);

        button_1.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(1)));

        button_2.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(2)));

        button_3.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(3)));

        button_4.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(4)));

        button_5.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(5)));

        button_6.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(6)));

        button_7.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(7)));
    }
}
