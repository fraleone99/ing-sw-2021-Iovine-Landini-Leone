package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendDoubleInt;
import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.server.answer.infoanswer.CardsSpaceInfo;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * DevelopmentCardsGridController class is the controller for the development card scene in which the player can buy card
 * if it is his turn or he can see the available cards in every other moments.
 *
 * @author Lorenzo Iovine , Francesco Leone
 */

public class DevelopmentCardsGridController {
    @FXML private Group card_group;
    @FXML private Group button_group;
    @FXML private ImageView purple1;
    @FXML private ImageView purple2;
    @FXML private ImageView purple3;
    @FXML private ImageView yellow1;
    @FXML private ImageView yellow2;
    @FXML private ImageView yellow3;
    @FXML private ImageView blue1;
    @FXML private ImageView blue2;
    @FXML private ImageView blue3;
    @FXML private ImageView green1;
    @FXML private ImageView green2;
    @FXML private ImageView green3;
    @FXML private ImageView cardsSpace;
    @FXML private Button back_button;
    @FXML private Label space;
    @FXML private Label card;
    @FXML private Group spaceGroup;
    @FXML private Button buttonOne;
    @FXML private Button buttonTwo;
    @FXML private Button buttonThree;

    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * Method updateGrids updates the grid when the grid is modified and the server sends a message to the client.
     *
     * @param idCards is the arrayList with the new ids of the cards available to buy.
     */
    public void updateGrid(ArrayList<Integer> idCards) {
        space.setOpacity(0);
        spaceGroup.setOpacity(0);
        card.setOpacity(0);
        cardsSpace.setOpacity(0);
        button_group.setOpacity(0);
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

    /**
     * Method updateSpace updates the image of the development Cards already bought  from the player in his Development
     * cards space
     *
     * @param info contains the ids of the cards the the player has bought and the space they are in.
     */
    public void updateSpace(CardsSpaceInfo info) {
        Group group1 = (Group) spaceGroup.getChildren().get(info.getLevel()-1);
        switch(info.getSpace()) {
            case 1 : ImageView image1 = (ImageView) group1.getChildren().get(0);
                     image1.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                     break;
            case 2 : ImageView image2 = (ImageView) group1.getChildren().get(1);
                     image2.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                     break;
            case 3 : ImageView image3 = (ImageView) group1.getChildren().get(2);
                     image3.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                     break;
        }
    }

    /**
     * seePhase method is used for controlling the scene when the player hasn't decided to buy a card but he wants to see
     * the available cards. It is also possible that this method is called when the player is waiting for the other to
     * end their turn and wants to see the Development card grid.
     *
     */
    public void seePhase() {
        space.setOpacity(0);
        card.setOpacity(0);
        cardsSpace.setOpacity(0);

        spaceGroup.setOpacity(0);
        button_group.setOpacity(0);

        back_button.setOpacity(1);
        back_button.setDisable(false);
        back_button.setOnAction(actionEvent -> gui.changeGameBoard());
    }

    /**
     * Method buyCard lets the player choose the development card that he wants to buy
     */
    public void buyCard() {
        card_group.setDisable(false);

        back_button.setOpacity(0);
        back_button.setDisable(true);

        cardsSpace.setOpacity(1);
        spaceGroup.setOpacity(1);

        button_group.setDisable(true);
        button_group.setOpacity(1);

        card.setOpacity(1);

        int depth = 70;

        DropShadow selectedGlow = new DropShadow();
        selectedGlow.setOffsetY(0f);
        selectedGlow.setOffsetX(0f);
        selectedGlow.setColor(Color.GREEN);
        selectedGlow.setWidth(depth);
        selectedGlow.setHeight(depth);

        ImageView card;

        for(int i = 0; i < card_group.getChildren().size(); i++) {
            card_group.getChildren().forEach(x -> x.setEffect(null));
        }

        purple1.setOnMouseClicked(event -> {
            gui.getHandler().send(new SendDoubleInt(1, 1));
            purple1.setEffect(selectedGlow);
        });
        purple2.setOnMouseClicked(event -> {
            gui.getHandler().send(new SendDoubleInt(1, 2));
            purple2.setEffect(selectedGlow);
        });
        purple3.setOnMouseClicked(event -> {
            gui.getHandler().send(new SendDoubleInt(1, 3));
            purple3.setEffect(selectedGlow);
        });
        yellow1.setOnMouseClicked(event -> {
            gui.getHandler().send(new SendDoubleInt(2, 1));
            yellow1.setEffect(selectedGlow);
        });
        yellow2.setOnMouseClicked(event -> {
            gui.getHandler().send(new SendDoubleInt(2, 2));
            yellow2.setEffect(selectedGlow);
        });
        yellow3.setOnMouseClicked(event -> {
            gui.getHandler().send(new SendDoubleInt(2, 3));
            yellow3.setEffect(selectedGlow);
        });
        blue1.setOnMouseClicked(event -> {
            gui.getHandler().send(new SendDoubleInt(3, 1));
            blue1.setEffect(selectedGlow);
        });
        blue2.setOnMouseClicked(event -> {
            gui.getHandler().send(new SendDoubleInt(3, 2));
            blue2.setEffect(selectedGlow);
        });
        blue3.setOnMouseClicked(event -> {
            gui.getHandler().send(new SendDoubleInt(3, 1));
            blue3.setEffect(selectedGlow);
        });
        green1.setOnMouseClicked(event -> {
            gui.getHandler().send(new SendDoubleInt(4, 1));
            green1.setEffect(selectedGlow);
        });
        green2.setOnMouseClicked(event -> {
            gui.getHandler().send(new SendDoubleInt(4, 2));
            green2.setEffect(selectedGlow);
        });
        green3.setOnMouseClicked(event -> {
            gui.getHandler().send(new SendDoubleInt(4, 3));
            green3.setEffect(selectedGlow);
        });
    }

    /**
     * Method choose space let the player choose the space in which to place the development card
     */
    public void chooseSpace() {
        button_group.setDisable(false);
        card_group.setDisable(true);
        card.setOpacity(0);
        space.setOpacity(1);

        buttonOne.setOnMouseClicked(event -> gui.getHandler().send(new SendInt(1)));
        buttonTwo.setOnMouseClicked(event -> gui.getHandler().send(new SendInt(2)));
        buttonThree.setOnMouseClicked(event -> gui.getHandler().send(new SendInt(3)));
    }
}
