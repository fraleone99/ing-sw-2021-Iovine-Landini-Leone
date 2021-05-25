package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.gameboard.Ball;
import it.polimi.ingsw.model.gameboard.Market;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

public class GameSceneController {

    @FXML public Button marketButton;

    private GUI gui;


    public void setGui(GUI gui) {
        this.gui = gui;
    }


    public void seePhase(){
        marketButton.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(2)));
    }


}
