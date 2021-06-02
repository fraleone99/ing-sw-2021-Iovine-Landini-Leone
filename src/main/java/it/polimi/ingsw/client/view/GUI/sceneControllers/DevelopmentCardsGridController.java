package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendDoubleInt;
import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    @FXML ImageView space1;
    @FXML ImageView space2;
    @FXML ImageView space3;
    @FXML ImageView cardsSpace;
    @FXML public Button back_button;
    @FXML Label space;
    @FXML Label card;

    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void updateGrid(ArrayList<Integer> idCards) {
        space.setOpacity(0);
        card.setOpacity(0);
        cardsSpace.setOpacity(0);
        space1.setOpacity(0);
        space2.setOpacity(0);
        space3.setOpacity(0);
        back_button.setOpacity(1);
        back_button.setDisable(false);
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

    public void updateSpaces(ArrayList<Integer> spaces) {
        if(!(spaces.get(0) == -1)) {
            space1.setImage(new Image("/graphics/" + spaces.get(0) + ".png"));
        }
        if(!(spaces.get(1) == -1)) {
            space2.setImage(new Image("/graphics/" + spaces.get(1) + ".png"));
        }
        if(!(spaces.get(2) == -1)) {
            space3.setImage(new Image("/graphics/" + spaces.get(2) + ".png"));
        }
    }

    public void seePhase() {
        back_button.setOnAction(actionEvent -> {
            gui.getHandler().send(new SendInt(1)); // voglio vedere qualcos'altro
        });
    }

    public void buyCard() {
        space1.setDisable(true);
        space2.setDisable(true);
        space3.setDisable(true);
        purple1.setDisable(false);
        purple2.setDisable(false);
        purple3.setDisable(false);
        green1.setDisable(false);
        green2.setDisable(false);
        green3.setDisable(false);
        blue1.setDisable(false);
        blue2.setDisable(false);
        blue3.setDisable(false);
        yellow1.setDisable(false);
        yellow2.setDisable(false);
        yellow3.setDisable(false);
        back_button.setOpacity(0);
        back_button.setDisable(true);
        cardsSpace.setOpacity(1);
        space1.setOpacity(1);
        space2.setOpacity(1);
        space3.setOpacity(1);
        card.setOpacity(1);

        purple1.setOnMouseClicked(event -> gui.getHandler().send(new SendDoubleInt(1,1)));
        purple2.setOnMouseClicked(event -> gui.getHandler().send(new SendDoubleInt(1,2)));
        purple3.setOnMouseClicked(event -> gui.getHandler().send(new SendDoubleInt(1,3)));
        yellow1.setOnMouseClicked(event -> gui.getHandler().send(new SendDoubleInt(2,1)));
        yellow2.setOnMouseClicked(event -> gui.getHandler().send(new SendDoubleInt(2,2)));
        yellow3.setOnMouseClicked(event -> gui.getHandler().send(new SendDoubleInt(2,3)));
        blue1.setOnMouseClicked(event -> gui.getHandler().send(new SendDoubleInt(3,1)));
        blue2.setOnMouseClicked(event -> gui.getHandler().send(new SendDoubleInt(3,2)));
        blue3.setOnMouseClicked(event -> gui.getHandler().send(new SendDoubleInt(3,1)));
        green1.setOnMouseClicked(event -> gui.getHandler().send(new SendDoubleInt(4,1)));
        green2.setOnMouseClicked(event -> gui.getHandler().send(new SendDoubleInt(4,2)));
        green3.setOnMouseClicked(event -> gui.getHandler().send(new SendDoubleInt(4,3)));
    }

    public void chooseSpace() {
        space1.setDisable(false);
        space2.setDisable(false);
        space3.setDisable(false);
        purple1.setDisable(true);
        purple2.setDisable(true);
        purple3.setDisable(true);
        green1.setDisable(true);
        green2.setDisable(true);
        green3.setDisable(true);
        blue1.setDisable(true);
        blue2.setDisable(true);
        blue3.setDisable(true);
        yellow1.setDisable(true);
        yellow2.setDisable(true);
        yellow3.setDisable(true);
        card.setOpacity(0);
        space.setOpacity(1);

        space1.setOnMouseClicked(event -> gui.getHandler().send(new SendInt(1)));
        space2.setOnMouseClicked(event -> gui.getHandler().send(new SendInt(2)));
        space3.setOnMouseClicked(event -> gui.getHandler().send(new SendInt(3)));
    }
}
