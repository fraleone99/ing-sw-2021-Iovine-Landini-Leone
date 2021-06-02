package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class DevelopmentCardsGridController {
    @FXML ImageView purple1;
    @FXML ImageView purple2;
    @FXML ImageView purple3;
    @FXML ImageView yellow1;
    @FXML ImageView yellow2;
    @FXML ImageView yellow3;
    @FXML ImageView blue1;
    @FXML ImageView blue2;
    @FXML ImageView blue3;
    @FXML ImageView green1;
    @FXML ImageView green2;
    @FXML ImageView green3;
    @FXML public Button back_button;

    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void updateGrid(ArrayList<Integer> idCards) {
        purple1.setImage(new Image("/graphics/" + idCards.get(0) + ".png"));
        yellow1.setImage(new Image("/graphics/" + idCards.get(1) + ".png"));
        blue1.setImage(new Image("/graphics/" + idCards.get(2) + ".png"));
        green1.setImage(new Image("/graphics/" + idCards.get(3) + ".png"));
        purple2.setImage(new Image("/graphics/" + idCards.get(4) + ".png"));
        yellow2.setImage(new Image("/graphics/" + idCards.get(5) + ".png"));
        blue2.setImage(new Image("/graphics/" + idCards.get(6) + ".png"));
        green2.setImage(new Image("/graphics/" + idCards.get(7) + ".png"));
        purple3.setImage(new Image("/graphics/" + idCards.get(8) + ".png"));
        yellow3.setImage(new Image("/graphics/" + idCards.get(9) + ".png"));
        blue3.setImage(new Image("/graphics/" + idCards.get(10) + ".png"));
        green3.setImage(new Image("/graphics/" + idCards.get(11) + ".png"));
    }

    public void seePhase() {
        back_button.setOnAction(actionEvent -> {
            gui.getHandler().send(new SendInt(1)); // voglio vedere qualcos'altro
        });
    }
}
