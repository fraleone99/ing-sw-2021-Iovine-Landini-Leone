package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.Handler;
import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.message.SendString;
import it.polimi.ingsw.client.view.GUI.sceneControllers.MainMenuController;
import it.polimi.ingsw.client.view.GUI.sceneControllers.NicknameController;
import it.polimi.ingsw.client.view.GUI.sceneControllers.PlayerNumberController;
import it.polimi.ingsw.client.view.GUI.sceneControllers.SetupController;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.server.answer.infoanswer.DevCardsSpaceInfo;
import it.polimi.ingsw.server.answer.infoanswer.FaithPathInfo;
import it.polimi.ingsw.server.answer.infoanswer.StorageInfo;
import it.polimi.ingsw.server.answer.seegameboard.SeeBall;
import it.polimi.ingsw.server.answer.turnanswer.ActiveLeader;
import it.polimi.ingsw.server.answer.turnanswer.DiscardLeader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.*;


public class GUI extends Application implements View {
    private Stage stage;
    private Scene currentScene;
    private Scene MenuScene;
    private Scene SetupScene;
    private Scene NicknameScene;
    private Scene ChooseNumberScene;
    private Scene LoadingScene;

    DialogPane DiscardLeader;
    DialogPane InitialResources;

    private MainMenuController mainMenuController;
    private SetupController setupController;
    private NicknameController nicknameController;
    private PlayerNumberController playerNumberController;

    private final Map<String, Scene> sceneMap = new HashMap<>();
    public static final String MENU = "MainMenu";
    public static final String SETUP = "setup";
    public static final String NICKNAME = "Nickname";
    public static final String NUMBER = "Number";
    public static final String LOADING = "Loading";
    public static final String SINGLE_PLAYER = "SinglePlayer";
    public static final String LOCAL_SP = "setupLocalSP";
    public static final String WELCOME = "Welcome";


    Handler handler;

    private final Object lock = new Object();
    private boolean notReady = true;

    private String IP;
    private int portNumber;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        FXMLLoader menu = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        MenuScene = new Scene(menu.load());
        mainMenuController = menu.getController();
        mainMenuController.setGui(this);

        FXMLLoader setup = new FXMLLoader(getClass().getResource("/fxml/setup.fxml"));
        SetupScene = new Scene(setup.load());
        setupController = setup.getController();
        setupController.setGui(this);

        FXMLLoader nickname = new FXMLLoader(getClass().getResource(("/fxml/Nickname.fxml")));
        NicknameScene = new Scene(nickname.load());
        nicknameController = nickname.getController();
        nicknameController.setGui(this);


        FXMLLoader number = new FXMLLoader(getClass().getResource("/fxml/PlaYerNumber.fxml"));
        ChooseNumberScene = new Scene(number.load());
        playerNumberController = number.getController();
        playerNumberController.setGui(this);

        FXMLLoader loading = new FXMLLoader(getClass().getResource(("/fxml/loading.fxml")));
        LoadingScene = new Scene(loading.load());

        DiscardLeader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/DiscardLeader.fxml")));
        InitialResources = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/InitialResource.fxml")));

        sceneMap.put(MENU, MenuScene);
        sceneMap.put(SETUP, SetupScene);
        sceneMap.put(NICKNAME, NicknameScene);
        sceneMap.put(NUMBER, ChooseNumberScene);
        sceneMap.put(LOADING, LoadingScene);
    }

    @Override
    public void start(Stage primaryStage) {


        stage = primaryStage;
        stage.setTitle("I Maestri del Rinascimento");
        stage.centerOnScreen();
        stage.setHeight(810);
        stage.setWidth(1440);
        stage.setFullScreen(true);
        stage.setMaximized(true);
        stage.show();

        Client client = new Client(this);
        Thread clientThread = new Thread(client);
        clientThread.start();

    }

    public void changeStage(String scene){
        currentScene = sceneMap.get(scene);
        stage.setScene(currentScene);
        stage.show();
    }

    public Object getLock() {
        return lock;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setNotReady(boolean notReady) {
        this.notReady = notReady;
    }

    @Override
    public int gameType() {
        Platform.runLater(()-> mainMenuController.start());

        synchronized (lock){
            while(notReady) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return mainMenuController.getRis();
        }
    }

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void setupConnection() {
        notReady = true;
        Platform.runLater(()-> setupController.setupConnection());
    }

    public void errorHandling(String error) {
        switch (error) {
            case "setup" : Platform.runLater(() -> {
                errorDialog("Wrong Setup configuration!");
                setupConnection();
            });
                break;
            default : Platform.runLater(() -> errorDialog("Generic Error!"));
        }
    }


    private void errorDialog(String error) {
        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle("Game Error");
        errorDialog.setHeaderText("Error!");
        errorDialog.setContentText(error);
        errorDialog.showAndWait();
    }


    @Override
    public String getIp() {
        synchronized (lock) {
            while (notReady) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return IP;
        }
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    @Override
    public int getPortNumber() {
        synchronized (lock) {
            while (notReady) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return portNumber;
        }
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public void handShake(String welcome) {
        handler.send(new SendString("Client connected"));
    }

    @Override
    public void askPlayerNumber(String message) {
        Platform.runLater(()-> playerNumberController.setupPlayersNumber());
    }

    @Override
    public void askNickname(String message) {
       Platform.runLater(() -> nicknameController.setupNickname());

    }

    @Override
    public void readMessage(String message) {
        if(message.equals("You are now in the waiting room. The game will start soon!")){
            Platform.runLater(()->{
                changeStage(LOADING);
                ProgressBar progressBar = (ProgressBar) LoadingScene.lookup("#progressBar");
                progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            });
        }
    }

    @Override
    public void askResource(String message) {
        //1) COIN 2) STONE 3) SHIELD 4) SERVANT
        Platform.runLater(()->{
            ImageView coin = (ImageView) InitialResources.lookup("#coin");
            ImageView stone = (ImageView) InitialResources.lookup("#stone");
            ImageView servant = (ImageView) InitialResources.lookup("#servant");
            ImageView shield = (ImageView) InitialResources.lookup("#shield");

            final Dialog dlg = new Dialog();
            // Add grid inside dialog.
            dlg.getDialogPane().setContent(InitialResources);
            dlg.show();

            coin.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                handler.send(new SendInt(1));
                dlg.setResult(Boolean.TRUE);
                dlg.close();
            });

            stone.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                handler.send(new SendInt(2));
                dlg.setResult(Boolean.TRUE);
                dlg.close();
            });

            shield.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                handler.send(new SendInt(3));
                dlg.setResult(Boolean.TRUE);
                dlg.close();
            });

            servant.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                handler.send(new SendInt(4));
                dlg.setResult(Boolean.TRUE);
                dlg.close();
            });
        });
    }

    @Override
    public void askLeaderToDiscard(ArrayList<Integer> IdLeaders) {
        if(IdLeaders.size() == 4) {
            Platform.runLater(() -> {
                ImageView leader1 = (ImageView) DiscardLeader.lookup("#leader1");
                leader1.setImage(new Image("/graphics/" + IdLeaders.get(0) + ".png"));

                ImageView leader2 = (ImageView) DiscardLeader.lookup("#leader2");
                leader2.setImage(new Image("/graphics/" + IdLeaders.get(1) + ".png"));

                ImageView leader3 = (ImageView) DiscardLeader.lookup("#leader3");
                leader3.setImage(new Image("/graphics/" + IdLeaders.get(2) + ".png"));

                ImageView leader4 = (ImageView) DiscardLeader.lookup("#leader4");
                leader4.setImage(new Image("/graphics/" + IdLeaders.get(3) + ".png"));

                // make a dialog
                final Dialog dlg = new Dialog();
                // Add grid inside dialog.
                dlg.getDialogPane().setContent(DiscardLeader);
                dlg.show();

                leader1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    handler.send(new SendInt(1));
                    dlg.setResult(Boolean.TRUE);
                    dlg.close();
                });
                leader2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    handler.send(new SendInt(2));
                    dlg.setResult(Boolean.TRUE);
                    dlg.close();
                });
                leader3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    handler.send(new SendInt(3));
                    dlg.setResult(Boolean.TRUE);
                    dlg.close();
                });
                leader4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    handler.send(new SendInt(4));
                    dlg.setResult(Boolean.TRUE);
                    dlg.close();
                });

            });

        } else {
            Platform.runLater(()->{
                ImageView leader1 = (ImageView) DiscardLeader.lookup("#leader1");
                leader1.setImage(new Image("/graphics/" + IdLeaders.get(0) + ".png"));

                ImageView leader2 = (ImageView) DiscardLeader.lookup("#leader2");
                leader2.setImage(new Image("/graphics/" + IdLeaders.get(1) + ".png"));

                ImageView leader3 = (ImageView) DiscardLeader.lookup("#leader3");
                leader3.setImage(new Image("/graphics/" + IdLeaders.get(2) + ".png"));

                ImageView leader4 = (ImageView) DiscardLeader.lookup("#leader4");
                leader4.setImage(null);

                // make a dialog
                final Dialog dlg = new Dialog();
                // Add grid inside dialog.
                dlg.getDialogPane().setContent(DiscardLeader);
                dlg.show();

                leader1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    handler.send(new SendInt(1));
                    dlg.setResult(Boolean.TRUE);
                    dlg.close();
                });
                leader2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    handler.send(new SendInt(2));
                    dlg.setResult(Boolean.TRUE);
                    dlg.close();
                });
                leader3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    handler.send(new SendInt(3));
                    dlg.setResult(Boolean.TRUE);
                    dlg.close();
                });
            });
        }
    }

    @Override
    public void askTurnType(String message) {

    }

    @Override
    public void activeLeader(ActiveLeader message) {

    }

    @Override
    public void discardLeader(DiscardLeader message) {

    }

    @Override
    public void seeGameBoard(String message) {

    }

    @Override
    public void seeLeaderCards(ArrayList<Integer> leaderCards) {

    }

    @Override
    public void seeMarket(Market market) {

    }

    @Override
    public void chooseLine(String message) {

    }

    @Override
    public void seeGrid(ArrayList<Integer> devCards) {

    }

    @Override
    public void seeProductions(ArrayList<Integer> productions) {

    }

    @Override
    public void printFaithPath(FaithPathInfo path) {

    }

    @Override
    public void printStorage(StorageInfo storageInfo) {

    }

    @Override
    public void printStorageAndVault(StorageInfo storageInfo) {

    }

    @Override
    public void printDevelopmentCardsSpace(DevCardsSpaceInfo devCardsSpaceInfo) {

    }

    @Override
    public void printActionToken(ActionToken actionToken) {

    }

    @Override
    public void ManageStorage(String message) {

    }

    @Override
    public void MoveShelves(String message) {

    }

    @Override
    public void resetCard(int pos) {

    }

    @Override
    public void useMarket(String message) {

    }

    @Override
    public void chooseWhiteBallLeader(String message) {

    }

    @Override
    public void seeBall(SeeBall ball) {

    }

    @Override
    public void chooseShelf() {

    }

    @Override
    public void askColor(String message) {

    }

    @Override
    public void askLevel(String message) {

    }

    @Override
    public void askSpace(String message) {

    }

    @Override
    public void askType(String message) {

    }

    @Override
    public void askInput(String message) {

    }

    @Override
    public void askOutput(String message) {

    }

    @Override
    public void askDevelopmentCard(String message) {

    }

    @Override
    public void askLeaderCard(String message) {

    }

    @Override
    public void endTurn(String message) {

    }

    @Override
    public void win(String message) {

    }

    @Override
    public void lose(String message) {

    }

    @Override
    public void seeOtherCards(ArrayList<Integer> leaderCards) {

    }

    @Override
    public void choice() {

    }


}
