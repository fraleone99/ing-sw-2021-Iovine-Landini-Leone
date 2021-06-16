package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendString;
import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.concurrent.atomic.AtomicReference;

public class NicknameController {
    @FXML public TextField nicknameBox;
    @ FXML public Button okButton;
    public Label invalidNick_label;

    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void  setupNickname(String message){
        AtomicReference<String> nickname = new AtomicReference<>();
        gui.changeStage(GUI.NICKNAME);
        okButton.setDefaultButton(true);
        okButton.setOnAction(actionEvent ->
        {
            nickname.set(nicknameBox.getText());
            gui.getHandler().send(new SendString(nickname.get()));
            gui.setNickname(nickname.get());
            invalidNick_label.setOpacity(0);
        });

        if(message.equals("The chosen nickname is not valid. Try again:")){
            invalidNick_label.setOpacity(1);
        }

    }

}
