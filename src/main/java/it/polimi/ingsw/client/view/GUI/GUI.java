package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.Handler;
import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.message.SendString;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;



public class GUI extends Application implements View {
    private Stage stage;
    private Scene currentScene;
    private Scene MenuScene;
    private Scene SetupScene;
    private Scene NicknameScene;
    private Scene ChooseNumberScene;
    private Scene LoadingScene;

    private final Map<String, Scene> sceneMap = new HashMap<>();
    private final String MENU = "MainMenu";
    private final String SETUP = "setup";
    private final String SINGLE_PLAYER = "SinglePlayer";
    private final String LOCAL_SP = "setupLocalSP";
    private final String WELCOME = "Welcome";

    //private guiController controller;

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
        Parent menu = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        MenuScene = new Scene(menu);

        Parent setup = FXMLLoader.load(getClass().getResource("/fxml/setup.fxml"));
        SetupScene = new Scene(setup);

        Parent nickname = FXMLLoader.load(getClass().getResource("/fxml/Nickname.fxml"));
        NicknameScene = new Scene(nickname);

        Parent ChooseNumber = FXMLLoader.load(getClass().getResource("/fxml/PlayerNumber.fxml"));
        ChooseNumberScene = new Scene(ChooseNumber);

        Parent Loading = FXMLLoader.load(getClass().getResource("/fxml/loading.fxml"));
        LoadingScene = new Scene(Loading);
    }

    @Override
    public void start(Stage primaryStage) {


        stage = primaryStage;
        stage.setTitle("I Maestri del Rinascimento");
        primaryStage.show();

        Client client = new Client(this);
        Thread clientThread = new Thread(client);
        clientThread.start();

    }

    public void changeStage(String scene){
    }

    public void initialize() {

    }

    @Override
    public int gameType() {
        notReady = true;
        AtomicInteger ris = new AtomicInteger();
        Platform.runLater(()->{
            stage.setScene(MenuScene);
            stage.show();

            Button multiplayer = (Button) MenuScene.lookup("#multiplayerButton");
            Button singlePlayer = (Button) MenuScene.lookup("#singlePlayerButton");


            multiplayer.setOnAction(actionEvent -> {
                synchronized (lock) {
                    System.out.println("pressed multiplayer");
                    ris.set(2);
                    notReady = false;
                    lock.notifyAll();
                }
            });

            singlePlayer.setOnAction(actionEvent -> {
                synchronized (lock) {
                    notReady = false;
                    ris.set(1);
                    lock.notifyAll();
                }
            });


        });

        synchronized (lock){
            while(notReady) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("returning int");
            return ris.get();
        }
    }

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void setupConnection() {
        notReady = true;

        System.out.println("Entering setup connection\n");
        Platform.runLater(()->{
            stage.setScene(SetupScene);
            stage.show();


            TextField ipBox = (TextField) SetupScene.lookup("#ip");
            TextField portNumberBox = (TextField) SetupScene.lookup("#port");

            ipBox.clear();
            portNumberBox.clear();

            Button playButton = (Button) SetupScene.lookup("#playButton");
            playButton.setDefaultButton(true);

            playButton.setOnAction( actionEvent ->{
                synchronized (lock) {
                    IP = ipBox.getText();
                    try {
                        portNumber = Integer.parseInt(portNumberBox.getText());
                    }
                    catch (NumberFormatException e){
                        portNumber = -1;
                    }
                    if(IP.equals("") || portNumber < 1024 || portNumber >65535){
                        errorHandling("setup");
                    }
                    //System.out.println("ip: " + IP + "\nport: " + portNumber);
                    if(!(IP.equals("") || portNumber < 1024 || portNumber >65535)) {
                        notReady = false;
                        lock.notifyAll();
                    }
                }



            });


        });
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
        System.out.println("getting ip");
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

    @Override
    public int getPortNumber() {
        System.out.println("getting port number");
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



    @Override
    public void handShake(String welcome) {
        System.out.println("Handshake");
        handler.send(new SendString("Client connected"));
    }

    @Override
    public void askPlayerNumber(String message) {
        System.out.println("asking player number");
        Platform.runLater(()->{

            AtomicInteger playersNumber = new AtomicInteger();

            stage.setScene(ChooseNumberScene);
            stage.show();

            ChoiceBox choiceBox = (ChoiceBox) ChooseNumberScene.lookup("#numberChoice");
            Button okButton = (Button) ChooseNumberScene.lookup("#okButton");

            choiceBox.getItems().add("1");
            choiceBox.getItems().add("2");
            choiceBox.getItems().add("3");
            choiceBox.getItems().add("4");

            okButton.setOnAction(actionEvent -> {
                playersNumber.set(Integer.parseInt((String) choiceBox.getValue()));
                handler.send(new SendInt(playersNumber.get()));
            });

        });
    }

    @Override
    public void askNickname(String message) {
        System.out.println("asking nickname");

       Platform.runLater(() -> {
           AtomicReference<String> nickname = new AtomicReference<>();
           stage.setScene(NicknameScene);
           stage.show();

           TextField nicknameBox = (TextField) NicknameScene.lookup("#nicknameBox");
           Button okButton = (Button) NicknameScene.lookup("#okButton");

           okButton.setOnAction(actionEvent ->
           {
               nickname.set(nicknameBox.getText());
               handler.send(new SendString(nickname.get()));
           });


       });

    }

    @Override
    public void readMessage(String message) {
        if(message.equals("You are now in the waiting room. The game will start soon!")){
            Platform.runLater(()->{
                stage.setScene(LoadingScene);
                stage.show();

                ProgressBar progressBar = (ProgressBar) LoadingScene.lookup("#progressBar");
                progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

            });
        }
    }

    @Override
    public void askResource(String message) {

    }

    @Override
    public void askLeaderToDiscard(ArrayList<Integer> IdLeaders) {

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
