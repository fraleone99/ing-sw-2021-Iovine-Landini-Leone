package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.message.Message;
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
import javafx.stage.Stage;

import java.io.IOException;
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

    private guiController controller;

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
    public void setupConnection() {
        notReady = true;
        System.out.println("Entering setup connection\n");
        Platform.runLater(()->{
            stage.setScene(SetupScene);
            stage.show();

            TextField ipBox = (TextField) SetupScene.lookup("#ip");
            TextField portNumberBox = (TextField) SetupScene.lookup("#port");

            Button playButton = (Button) SetupScene.lookup("#playButton");

            playButton.setOnAction( actionEvent ->{
                synchronized (lock) {
                    IP = ipBox.getText();
                    portNumber = Integer.parseInt(portNumberBox.getText());
                    System.out.println("ip: " + IP + "\nport: " + portNumber);
                    notReady = false;
                    lock.notifyAll();
                }
            });



        });
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



    @Override
    public void handShake(String welcome) {

    }

    @Override
    public int askPlayerNumber(String message) {
        notReady = true;
        AtomicInteger number = new AtomicInteger();

        Platform.runLater(()->{


            stage.setScene(ChooseNumberScene);
            stage.show();

            ChoiceBox choiceBox = (ChoiceBox) ChooseNumberScene.lookup("#numberChoice");
            Button okButton = (Button) ChooseNumberScene.lookup("#okButton");

            choiceBox.getItems().add("1");
            choiceBox.getItems().add("2");
            choiceBox.getItems().add("3");
            choiceBox.getItems().add("4");

            okButton.setOnAction(actionEvent -> {
                synchronized (lock){
                    number.set(Integer.parseInt((String) choiceBox.getValue()));
                    notReady = false;
                    lock.notifyAll();
                }
            });



        System.out.println("player num:" + number.get());
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
            return number.get();
        }
    }

    @Override
    public String askNickname(String message) {
       AtomicReference<String> nickname = new AtomicReference<>();
        notReady = true;

       Platform.runLater(() -> {

           stage.setScene(NicknameScene);
           stage.show();

           TextField nicknameBox = (TextField) NicknameScene.lookup("#nicknameBox");
           Button okButton = (Button) NicknameScene.lookup("#okButton");

           okButton.setOnAction(actionEvent ->
           {
               synchronized (lock) {
                   nickname.set(nicknameBox.getText());
                   notReady = false;
                   lock.notifyAll();
               }
           });


       });


        synchronized (lock) {
            while (notReady) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return nickname.get();
        }

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
    public int askResource(String message) {
        return 0;
    }

    @Override
    public int askLeaderToDiscard(ArrayList<Integer> IdLeaders) {
        return 0;
    }

    @Override
    public int askTurnType(String message) {
        return 0;
    }

    @Override
    public int activeLeader(ActiveLeader message) {
        return 0;
    }

    @Override
    public int discardLeader(DiscardLeader message) {
        return 0;
    }

    @Override
    public int seeGameBoard(String message) {
        return 0;
    }

    @Override
    public int seeLeaderCards(ArrayList<Integer> leaderCards) {
        return 0;
    }

    @Override
    public int seeMarket(Market market) {
        return 0;
    }

    @Override
    public int chooseLine(String message) {
        return 0;
    }

    @Override
    public int seeGrid(ArrayList<Integer> devCards) {
        return 0;
    }

    @Override
    public int seeProductions(ArrayList<Integer> productions) {
        return 0;
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
    public int ManageStorage(String message) {
        return 0;
    }

    @Override
    public ArrayList<Integer> MoveShelves(String message) {
        return null;
    }

    @Override
    public void resetCard(int pos) {

    }

    @Override
    public int useMarket(String message) {
        return 0;
    }

    @Override
    public int chooseWhiteBallLeader(String message) {
        return 0;
    }

    @Override
    public int seeBall(SeeBall ball) {
        return 0;
    }

    @Override
    public int chooseShelf() {
        return 0;
    }

    @Override
    public int askColor(String message) {
        return 0;
    }

    @Override
    public int askLevel(String message) {
        return 0;
    }

    @Override
    public int askSpace(String message) {
        return 0;
    }

    @Override
    public int askType(String message) {
        return 0;
    }

    @Override
    public int askInput(String message) {
        return 0;
    }

    @Override
    public int askOutput(String message) {
        return 0;
    }

    @Override
    public int askDevelopmentCard(String message) {
        return 0;
    }

    @Override
    public int askLeaderCard(String message) {
        return 0;
    }

    @Override
    public int endTurn(String message) {
        return 0;
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
    public int choice() {
        return 0;
    }
}
