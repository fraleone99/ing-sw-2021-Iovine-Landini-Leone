package it.polimi.ingsw.client.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class GUI extends Application {
    private Stage stage;
    private Scene currentScene;

    private final Map<String, Scene> sceneMap = new HashMap<>();
    private final String MENU = "MainMenu";
    private final String SETUP = "setup";
    private final String SINGLE_PLAYER = "SinglePlayer";
    private final String LOCAL_SP = "setupLocalSP";
    private guiController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        initialize();

        //
        this.stage = primaryStage;
        stage.setTitle("Maestri del Rinascimento");
        stage.setScene(sceneMap.get(MENU));
        stage.show();
    }

    public void changeStage(String scene){
        currentScene = sceneMap.get(scene);
        stage.setScene(currentScene);
        stage.show();
    }

    public void initialize() throws IOException {
        List<String> scenes = new ArrayList<>(Arrays.asList(MENU, SETUP, SINGLE_PLAYER, LOCAL_SP));


        for(String scene: scenes){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+ scene + ".fxml"));
            sceneMap.put(scene, new Scene(loader.load()));
            guiController controller = loader.getController();
            controller.setGui(this);

        }
    }
}
