package it.polimi.ingsw.client.view.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class guiController {

    GUI gui;

    @FXML
    Label title;

    public int startMultiplayerGame(ActionEvent actionEvent) {
        return 2;
    }

    public void startSinglePlayer(ActionEvent actionEvent) {
        gui.changeStage("SinglePlayer");
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void startLocalGame(ActionEvent actionEvent) {
        gui.changeStage("setupLocalSP");
    }

    public void startConnectedSP(ActionEvent actionEvent) {
        gui.changeStage("setup");
    }

    public void playLocalSP(ActionEvent actionEvent) {

    }
}
