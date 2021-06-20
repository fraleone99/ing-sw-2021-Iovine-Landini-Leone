package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendString;
import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.concurrent.atomic.AtomicReference;

/**
 * NicknameController class controls the scene in which the players choose the username
 *
 * @author Francesco Leone
 */
public class NicknameController {
    @FXML public TextField nicknameBox;
    @ FXML public Button okButton;
    public Label invalidNick_label;

    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * setupNickname method let the user insert his nickname
     *
     * @param message informs the user that the chosen nickname is already taken
     */
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
