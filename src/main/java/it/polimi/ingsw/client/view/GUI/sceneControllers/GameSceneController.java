package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.ToSeeFromGameBoard;
import it.polimi.ingsw.client.view.TurnType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicReference;

public class GameSceneController {

    @FXML public Button toSee_nothing;
    @FXML public Button toSee_developmentGrid;
    @FXML public Button toSee_market;
    @FXML public RadioButton turn_market;
    @FXML public RadioButton turn_buyDevelopment;
    @FXML public RadioButton turn_activeProduction;
    @FXML public RadioButton turn_activeLeader;
    @FXML public ToggleGroup TurnType_group;
    @FXML public RadioButton turn_discardLeader;
    @FXML Button ok_turnType;
    @FXML Label noActionSelectedLabel;
    @FXML private Label usernameLabel;

    private GUI gui;


    public void setGui(GUI gui) {
        this.gui = gui;
    }


    public void seePhase(){
        ok_turnType.setDisable(true);

        toSee_market.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(ToSeeFromGameBoard.toInteger(ToSeeFromGameBoard.MARKET))));

        toSee_developmentGrid.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(ToSeeFromGameBoard.toInteger(ToSeeFromGameBoard.DEVELOPMENT_CARD_GRID))));

        toSee_nothing.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(ToSeeFromGameBoard.toInteger(ToSeeFromGameBoard.NOTHING))));
    }

    public void setNicknameLabel(String nickname){
        usernameLabel.setText(nickname);
    }


    public void notMyTurn() {
        toSee_market.setDisable(true);
        toSee_nothing.setDisable(true);
        toSee_developmentGrid.setDisable(true);
        ok_turnType.setDisable(true);
        usernameLabel.setTextFill(Color.color(0,0,0));
    }

    public void isMyTurn() {
        //gui.changeStage(GUI.GAME);
        toSee_market.setDisable(false);
        toSee_developmentGrid.setDisable(false);
        toSee_nothing.setDisable(false);
        usernameLabel.setTextFill(Color.color(0, 0, 1));
    }

    public void askTurn() {
        AtomicReference<TurnType> turnType = new AtomicReference<>();
        toSee_market.setDisable(true);
        toSee_developmentGrid.setDisable(true);
        toSee_market.setDisable(true);

        RadioButton selectedRadioButton = (RadioButton) TurnType_group.getSelectedToggle();

        ok_turnType.setOnAction(actionEvent -> {
            if (selectedRadioButton == null) {
                noActionSelectedLabel.setOpacity(1);
            } else if (turn_market.isSelected()) {
                turnType.set(TurnType.MARKET);
            } else if (turn_buyDevelopment.isSelected()) {
                turnType.set(TurnType.BUY_DEVELOPMENT);
            } else if (turn_activeProduction.isSelected()) {
                turnType.set(TurnType.ACTIVE_PRODUCTION);
            } else if (turn_activeLeader.isSelected()) {
                turnType.set(TurnType.ACTIVE_LEADER);
            } else if (turn_discardLeader.isSelected()) {
                turnType.set(TurnType.DISCARD_LEADER);
            }

            if (selectedRadioButton != null)
                gui.getHandler().send(new SendInt(TurnType.toInteger(turnType.get())));
            else
                askTurn();
        });
    }
}
