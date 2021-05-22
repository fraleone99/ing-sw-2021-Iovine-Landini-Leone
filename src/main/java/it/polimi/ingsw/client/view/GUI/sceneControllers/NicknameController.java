package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendString;
import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.concurrent.atomic.AtomicReference;

public class NicknameController {
    @FXML public TextField nicknameBox;
    @ FXML public Button okButton;

    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void  setupNickname(){
        AtomicReference<String> nickname = new AtomicReference<>();
        gui.changeStage(GUI.NICKNAME);
        okButton.setDefaultButton(true);
        okButton.setOnAction(actionEvent ->
        {
            nickname.set(nicknameBox.getText());
            gui.getHandler().send(new SendString(nickname.get()));

        });

    }

}
