package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * method InitialResourcesController controls the scene in which the player choose the initial benefits
 */
public class InitialResourcesController {
    @FXML private ImageView coin;
    @FXML private ImageView stone;
    @FXML private ImageView servant;
    @FXML private ImageView shield;
    @FXML private Label message;
    GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void setLabel(String label) {
        message.setText(label);
    }

    /**
     * method askResources let the player choose which resource he wants and send the answer to the server
     */
    public void askResource() {
        coin.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            gui.getHandler().send(new SendInt(1));
            gui.getSecondaryStage().close();
        });

        stone.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            gui.getHandler().send(new SendInt(4));
            gui.getSecondaryStage().close();
        });

        shield.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            gui.getHandler().send(new SendInt(3));
            gui.getSecondaryStage().close();
        });

        servant.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            gui.getHandler().send(new SendInt(2));
            gui.getSecondaryStage().close();
        });
    }
}
