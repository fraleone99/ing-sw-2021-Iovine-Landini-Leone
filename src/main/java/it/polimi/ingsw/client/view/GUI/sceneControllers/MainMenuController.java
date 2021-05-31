package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.concurrent.atomic.AtomicInteger;

public class MainMenuController {
    @FXML private Button singlePlayerButton;
    @FXML private Button multiplayerButton;

    AtomicInteger ris = new AtomicInteger();

    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public int getRis() {
        return ris.get();
    }


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
