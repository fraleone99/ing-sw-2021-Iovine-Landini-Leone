package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.util.concurrent.atomic.AtomicInteger;

public class PlayerNumberController {
    @FXML public ChoiceBox numberChoice;
    @FXML public Button okButton;
    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void setupPlayersNumber(){
        AtomicInteger playersNumber = new AtomicInteger();
        gui.changeStage(GUI.NUMBER);

        okButton.setDefaultButton(true);

        numberChoice.getItems().add("1");
        numberChoice.getItems().add("2");
        numberChoice.getItems().add("3");
        numberChoice.getItems().add("4");

        numberChoice.setValue("2");

        okButton.setOnAction(actionEvent -> {
            playersNumber.set(Integer.parseInt((String) numberChoice.getValue()));
            gui.getHandler().send(new SendInt(playersNumber.get()));
        });
    }
}
