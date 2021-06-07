package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendDoubleInt;
import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.model.enumeration.BallColor;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.Ball;
import it.polimi.ingsw.model.gameboard.Market;
import it.polimi.ingsw.server.answer.infoanswer.StorageInfo;
import it.polimi.ingsw.server.answer.seegameboard.SeeBall;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class MarketSceneController {
    @FXML public GridPane marketGrid;
    @FXML public ImageView ramp;
    @FXML public Button back_button;
    @FXML public Button Use_button;
    public ImageView resource_firstShelf;
    public ImageView first_resource_secondShelf;
    public ImageView second_resource_secondShelf;
    public ImageView first_resource_thirdShelf;
    public ImageView second_resource_thirdShelf;
    public ImageView third_resource_thirdShelf;
    public Button yes_reorganizeStorage;
    public Button no_reorganizeStorage;
    public CheckBox manage_firstShelf;
    public CheckBox manage_secondShelf;
    public CheckBox manage_thirdShelf;
    public Label invertShelfError_label;
    public Button ok_manageStorage;
    public Label invertShelf_label;
    public Group manageStorage_group;
    public Group reorganizeQuestion_group;
    @FXML public Group chosenBall_group;
    public Pane firstShelf_pane;
    public Pane secondShelf_pane;
    public Pane thirdShelf_pane;
    public Group shelves_group;
    public Label selectShelf_label;
    public Group buttonMarket_group;
    @FXML ImageView im00;
    @FXML ImageView im01;
    @FXML ImageView im02;
    @FXML ImageView im10;
    @FXML ImageView im11;
    @FXML ImageView im12;
    @FXML ImageView im20;
    @FXML ImageView im21;
    @FXML ImageView im22;
    @FXML ImageView im30;
    @FXML ImageView im31;
    @FXML ImageView im32;

    @FXML Group choiceShelf_group;

    ImageView[][] imageViewMatrix = new ImageView[3][4];


    private GUI gui;
    private Map<BallColor, String> ballColorPathMap = new HashMap<>();
    private Map<Resource, String> resourceToPathMap = new HashMap<>();


    public void setGui(GUI gui) {
        this.gui = gui;

        ballColorPathMap.put(BallColor.BLUE, "/graphics/BLUE_BALL.png");
        ballColorPathMap.put(BallColor.WHITE, "/graphics/WHITE_BALL.png");
        ballColorPathMap.put(BallColor.RED, "/graphics/RED_BALL.png");
        ballColorPathMap.put(BallColor.PURPLE, "/graphics/PURPLE_BALL.png");
        ballColorPathMap.put(BallColor.GREY, "/graphics/GREY_BALL.png");
        ballColorPathMap.put(BallColor.YELLOW, "/graphics/YELLOW_BALL.png");

        imageViewMatrix[0][0] = im00;
        imageViewMatrix[0][1] = im10;
        imageViewMatrix[0][2] = im20;
        imageViewMatrix[0][3] = im30;

        imageViewMatrix[1][0] = im01;
        imageViewMatrix[1][1] = im11;
        imageViewMatrix[1][2] = im21;
        imageViewMatrix[1][3] = im31;

        imageViewMatrix[2][0] = im02;
        imageViewMatrix[2][1] = im12;
        imageViewMatrix[2][2] = im22;
        imageViewMatrix[2][3] = im32;

        resourceToPathMap.put(Resource.COIN, "/graphics/COIN.PNG");
        resourceToPathMap.put(Resource.SERVANT, "/graphics/SERVANT.PNG");
        resourceToPathMap.put(Resource.SHIELD, "/graphics/SHIELD.PNG");
        resourceToPathMap.put(Resource.STONE, "/graphics/STONE.PNG");
    }

    public void updateMarket(Market market){

        Ball[][] matrix = market.getMatrix();

        for (int i = 0; i< 3; i++){
            for(int j = 0; j<4; j++){
                Image ball = new Image(ballColorPathMap.get(matrix[i][j].getType()));
                imageViewMatrix[i][j].setImage(ball);
            }
        }
        Image rampIm = new Image(ballColorPathMap.get(market.getRamp().getType()));
        ramp.setImage(rampIm);

    }

    public void seePhase() {
        manageStorage_group.setOpacity(0);
        reorganizeQuestion_group.setOpacity(0);
        selectShelf_label.setOpacity(0);
        back_button.setDisable(false);

        ImageView ballToSet;
        for(int i = 0; i < chosenBall_group.getChildren().size(); i++) {
            ballToSet = (ImageView) chosenBall_group.getChildren().get(i);
            ballToSet.setImage(null);
            ballToSet.setEffect(null);
        }

        back_button.setOnAction(actionEvent -> gui.changeGameBoard());

        /*Use_button.setOnAction(actionEvent -> {
            gui.getHandler().send(new SendInt(2));//non voglio vedere più niente
            gui.getHandler().send(new SendInt(3));//in questo turno uso il mercato
            usePhase();
        });*/
    }

    public void usePhase() {
        reorganizeQuestion_group.setOpacity(0);
        buttonMarket_group.setDisable(false);

        for(int i=0; i< buttonMarket_group.getChildren().size(); i++){
            int finalInti = i + 1;
            Button button = (Button) buttonMarket_group.getChildren().get(i);
            button.setOnAction(actionEvent-> gui.getHandler().send(new SendInt(finalInti)));
        }
    }

    public void storage( StorageInfo storageInfo){
        //first shelf
        if(storageInfo.getShelf1Amount() == 0){
            resource_firstShelf.setImage(null);
        }
        else{
            resource_firstShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf1Type())));
        }


        //second shelf
        if(storageInfo.getShelf2Amount() == 0){
            first_resource_secondShelf.setImage(null);
            second_resource_secondShelf.setImage(null);
        }
        else{
            if(storageInfo.getShelf2Amount() > 1){
                first_resource_secondShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf2Type())));
                second_resource_secondShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf2Type())));
            }
            else{
                first_resource_secondShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf2Type())));
                second_resource_secondShelf.setImage(null);
            }
        }

        //third shelf
        if(storageInfo.getShelf3Amount() == 0){
            first_resource_thirdShelf.setImage(null);
            second_resource_thirdShelf.setImage(null);
            third_resource_thirdShelf.setImage(null);
        }
        else if(storageInfo.getShelf3Amount() == 1){
            first_resource_thirdShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf3Type())));
            second_resource_thirdShelf.setImage(null);
            third_resource_thirdShelf.setImage(null);
        }
        else if(storageInfo.getShelf3Amount() == 2){
            first_resource_thirdShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf3Type())));
            second_resource_thirdShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf3Type())));
            third_resource_thirdShelf.setImage(null);
        }
        else if(storageInfo.getShelf3Amount() == 3){
            first_resource_thirdShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf3Type())));
            second_resource_thirdShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf3Type())));
            third_resource_thirdShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf3Type())));
        }

    }

    public void manageStorage() {
        reorganizeQuestion_group.setOpacity(1);
        manageStorage_group.setOpacity(0);
        back_button.setDisable(true);
        selectShelf_label.setOpacity(0);
        buttonMarket_group.setDisable(true);

        ImageView ballToSet;
        for(int i = 0; i < chosenBall_group.getChildren().size(); i++) {
            ballToSet = (ImageView) chosenBall_group.getChildren().get(i);
            ballToSet.setImage(null);
            ballToSet.setEffect(null);
        }

        yes_reorganizeStorage.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(1)));
        no_reorganizeStorage.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(2)));
    }

    public void moveShelves() {
        reorganizeQuestion_group.setOpacity(0);
        manageStorage_group.setOpacity(1);
        invertShelfError_label.setOpacity(0);
        for(Node node : choiceShelf_group.getChildren()){
            ((CheckBox) node).setSelected(false);
        }

        ok_manageStorage.setOnAction(actionEvent -> {
            int selected = 0;
            for(Node node : choiceShelf_group.getChildren()){
                CheckBox checkBox = (CheckBox) node;
                if(checkBox.isSelected())
                    selected++;
            }
            if(selected!=2) {
                invertShelfError_label.setOpacity(1);
            }
            else {
                if(manage_firstShelf.isSelected() && manage_secondShelf.isSelected()) {
                    System.out.println("1,2");
                    gui.getHandler().send(new SendDoubleInt(1, 2));
                }
                else if(manage_firstShelf.isSelected() && manage_thirdShelf.isSelected()) {
                    System.out.println("1,3");
                    gui.getHandler().send((new SendDoubleInt(1, 3)));
                }
                else if(manage_secondShelf.isSelected() && manage_thirdShelf.isSelected()) {
                    System.out.println("2,3");
                    gui.getHandler().send(new SendDoubleInt(2, 3));
                }
            }
        });
    }

    public void seeBall(SeeBall ball) {
        reorganizeQuestion_group.setOpacity(0);
        selectShelf_label.setOpacity(0);
        chosenBall_group.setDisable(false);
        buttonMarket_group.setDisable(true);

        ImageView ballToSet;

        int depth = 70;

        DropShadow borderGlow= new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.RED);
        borderGlow.setWidth(depth);
        borderGlow.setHeight(depth);

        DropShadow selectedGlow = new DropShadow();
        selectedGlow.setOffsetY(0f);
        selectedGlow.setOffsetX(0f);
        selectedGlow.setColor(Color.GREEN);
        selectedGlow.setWidth(depth);
        selectedGlow.setHeight(depth);

        for(int i = 0; i < chosenBall_group.getChildren().size(); i++) {
            ballToSet = (ImageView) chosenBall_group.getChildren().get(i);
            ballToSet.setImage(null);
            ballToSet.setEffect(null);
        }


        for(int i = 0; i < ball.getBalls().size(); i++) {
            ballToSet = (ImageView) chosenBall_group.getChildren().get(i);
            ballToSet.setImage(new Image(ballColorPathMap.get(ball.getBalls().get(i).getType())));
            ImageView finalBallToSet = ballToSet;

            ballToSet.setOnMouseEntered(mouseEvent -> {
                gui.getSceneMap().get(GUI.MARKET).setCursor(Cursor.HAND);
                finalBallToSet.setEffect(borderGlow);
            });

            ballToSet.setOnMouseExited(mouseEvent -> {
                gui.getSceneMap().get(GUI.MARKET).setCursor(Cursor.DEFAULT);
                if(finalBallToSet.getEffect().equals(borderGlow))
                    finalBallToSet.setEffect(null);
            });


            int finalI = i + 1;
            finalBallToSet.setOnMouseClicked(mouseEvent -> {
                finalBallToSet.setEffect(selectedGlow);
                gui.getHandler().send(new SendInt(finalI));
                chosenBall_group.setDisable(true);
                selectShelf_label.setOpacity(0);
            });
        }
    }

    public void chooseShelf() {
        shelves_group.setDisable(false);

        for(int i = 0; i<3; i++){
            Node n = shelves_group.getChildren().get(i);
            n.setOnMouseEntered(mouseEvent -> gui.getSceneMap().get(GUI.MARKET).setCursor(Cursor.HAND));
            n.setOnMouseExited(mouseEvent -> gui.getSceneMap().get(GUI.MARKET).setCursor(Cursor.DEFAULT));

            int finalI = i +1;
            n.setOnMouseClicked(mouseEvent -> {
                gui.getHandler().send(new SendInt(finalI));
                selectShelf_label.setOpacity(0);
                shelves_group.setDisable(true);
            });
        }

    }

    public void error(String error) {
        invertShelfError_label.setOpacity(1);
    }
}
