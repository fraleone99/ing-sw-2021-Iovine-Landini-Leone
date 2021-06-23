package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * DiscardLeaderController controls the scene in which at the beginning of the game the player has to choose between
 * 4 leaders
 *
 * @author Francesco Leone
 */
public class DiscardLeaderController {
    @FXML private ImageView leader1;
    @FXML private ImageView leader2;
    @FXML private ImageView leader3;
    @FXML private ImageView leader4;

    GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * discardLeader let the player choose the leader to discard between 4 leader
     * @param idLeaders contains the 4 IDs of the leader among which the player has to choose
     */
    public void discardLeader(ArrayList<Integer> idLeaders) {
        leader1.setImage(new Image("/graphics/" + idLeaders.get(0) + ".png"));
        leader2.setImage(new Image("/graphics/" + idLeaders.get(1) + ".png"));
        leader3.setImage(new Image("/graphics/" + idLeaders.get(2) + ".png"));
        if(idLeaders.size() > 3)
            leader4.setImage(new Image("/graphics/" + idLeaders.get(3) + ".png"));
        else
            leader4.setImage(null);

        leader1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gui.getHandler().send(new SendInt(1));
            gui.getSecondaryStage().close();
        });
        leader2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gui.getHandler().send(new SendInt(2));
            gui.getSecondaryStage().close();
        });
        leader3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gui.getHandler().send(new SendInt(3));
            gui.getSecondaryStage().close();
        });
        if(leader4.getImage() != null) {
            leader4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                gui.getHandler().send(new SendInt(4));
                gui.getSecondaryStage().close();
            });
        }

    }
}
