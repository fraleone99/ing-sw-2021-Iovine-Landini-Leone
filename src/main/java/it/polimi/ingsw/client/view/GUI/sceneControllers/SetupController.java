package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.view.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * Setup controller class manage the scene from which the player insert the connection detail
 *
 * @author Francesco Leone
 */

public class SetupController {
    @FXML private TextField ipBox;
    @FXML private TextField portBox;
    @FXML private Button playButton;
    @FXML private Label invalidLabel;

    private String IP;
    private int portNumber;

    private GUI gui;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * Method setup connection get the input from the user of the ip and ort of the server
     */
    public void setupConnection(){
        gui.changeStage(GUI.SETUP);

        ipBox.clear();
        portBox.clear();

        playButton.setDefaultButton(true);

        playButton.setOnAction( actionEvent ->{
            synchronized (gui.getLock()) {
                IP = ipBox.getText();
                try {
                    portNumber = Integer.parseInt(portBox.getText());
                } catch (NumberFormatException e) {
                    portNumber = -1;
                }
                if (IP.equals("") || portNumber < 1024 || portNumber > 65535) {
                    invalidLabel.setOpacity(1);

                    invalidLabel.setTextFill(Color.color(1, 0, 0));
                    setupConnection();
                }
                //System.out.println("ip: " + IP + "\n port: " + portNumber);
                if (!(IP.equals("") || portNumber < 1024 || portNumber > 65535)) {
                    gui.setIP(IP);
                    gui.setPortNumber(portNumber);
                    gui.setNotReady(false);
                    gui.getLock().notifyAll();
                }
            }
        });
    }


}
