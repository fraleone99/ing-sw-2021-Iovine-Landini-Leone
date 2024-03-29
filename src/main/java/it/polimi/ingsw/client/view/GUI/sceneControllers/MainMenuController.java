package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * MainMenuController class controls the scene in which the player select the type of game
 */
public class MainMenuController {
    @FXML private Button singlePlayerButton;
    @FXML private Button multiplayerButton;

    private final AtomicInteger ris = new AtomicInteger();

    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public int getRis() {
        return ris.get();
    }


    /**
     * method starts allows the player to choose if he wants to play a game connected or in local
     */
    public void start() {

        gui.changeStage(GUI.MENU);
        multiplayerButton.setOnAction(actionEvent -> {
            synchronized (gui.getLock()) {
                ris.set(2);
                gui.setNotReady(false);
                gui.getLock().notifyAll();
            }
        });

        singlePlayerButton.setOnAction(actionEvent -> {
            synchronized (gui.getLock()) {
                ris.set(1);
                gui.setNotReady(false);
                gui.getLock().notifyAll();
            }
        });

    }
}
