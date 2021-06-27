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
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * MarketSceneController class controls the scene of the market in which the player can see the market, choose the
 * marble and where to put the selected resources.
 *
 * @author Francesco Leone
 */
public class MarketSceneController {
    @FXML private ImageView ramp;
    @FXML private  Button back_button;
    @FXML private  ImageView resource_firstShelf;
    @FXML private  ImageView first_resource_secondShelf;
    @FXML private  ImageView second_resource_secondShelf;
    @FXML private  ImageView first_resource_thirdShelf;
    @FXML private  ImageView second_resource_thirdShelf;
    @FXML private  ImageView third_resource_thirdShelf;
    @FXML private  Button yes_reorganizeStorage;
    @FXML private  Button no_reorganizeStorage;
    @FXML private  CheckBox manage_firstShelf;
    @FXML private  CheckBox manage_secondShelf;
    @FXML private  CheckBox manage_thirdShelf;
    @FXML private  Label error_label;
    @FXML private  Button ok_manageStorage;
    @FXML private  Group manageStorage_group;
    @FXML private  Group reorganizeQuestion_group;
    @FXML private  Group chosenBall_group;
    @FXML private  Group shelves_group;
    @FXML private  Label selectShelf_label;
    @FXML private  Group buttonMarket_group;
    @FXML private  ImageView chosenBall_1;
    @FXML private  ImageView chosenBall_2;
    @FXML private  ImageView chosenBall_3;
    @FXML private  ImageView chosenBall_4;
    @FXML private  Button storageLeader_button;
    @FXML private  ImageView storageLeader_1;
    @FXML private  ImageView storageLeader_2;
    @FXML private  Group leader_group;
    @FXML private  Label white_label;
    @FXML private ImageView im00;
    @FXML private ImageView im01;
    @FXML private ImageView im02;
    @FXML private ImageView im10;
    @FXML private ImageView im11;
    @FXML private ImageView im12;
    @FXML private ImageView im20;
    @FXML private ImageView im21;
    @FXML private ImageView im22;
    @FXML private ImageView im30;
    @FXML private ImageView im31;
    @FXML private ImageView im32;

    @FXML private Group choiceShelf_group;

    private final ImageView[][] imageViewMatrix = new ImageView[3][4];


    private GUI gui;
    private final Map<BallColor, String> ballColorPathMap = new HashMap<>();
    private final Map<Resource, String> resourceToPathMap = new HashMap<>();


    /**
     * Method setGui set the GUI for this scene and initialize the map of the resources and the matrix for the market
     *
     * @param gui is the gui to set
     */
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

        resourceToPathMap.put(Resource.COIN, "/graphics/COIN.png");
        resourceToPathMap.put(Resource.SERVANT, "/graphics/SERVANT.png");
        resourceToPathMap.put(Resource.SHIELD, "/graphics/SHIELD.png");
        resourceToPathMap.put(Resource.STONE, "/graphics/STONE.png");
    }

    /**
     * updateMarket method updates the matrix of the market
     *
     * @param market contains the new configuration of the market
     */
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

    /**
     * seePhase method allow the player only to see the configuration of the market and its storage. No action is permitted
     */
    public void seePhase() {
        manageStorage_group.setOpacity(0);
        reorganizeQuestion_group.setOpacity(0);
        selectShelf_label.setOpacity(0);
        back_button.setDisable(false);
        storageLeader_button.setOpacity(0);
        buttonMarket_group.setDisable(true);


        ImageView ballToSet;
        for(int i = 0; i < chosenBall_group.getChildren().size(); i++) {
            ballToSet = (ImageView) chosenBall_group.getChildren().get(i);
            ballToSet.setImage(null);
            ballToSet.setEffect(null);
        }

        back_button.setOnAction(actionEvent -> gui.changeGameBoard());
    }


    /**
     * Method usePhase enables the button for row and column of the market and sends a message to the server when one
     * of this button is pressed.
     */
    public void usePhase() {
        reorganizeQuestion_group.setOpacity(0);
        buttonMarket_group.setDisable(false);
        storageLeader_button.setOpacity(0);

        for(int i=0; i< buttonMarket_group.getChildren().size(); i++){
            int finalInti = i + 1;
            Button button = (Button) buttonMarket_group.getChildren().get(i);
            button.setOnAction(actionEvent-> gui.getHandler().send(new SendInt(finalInti)));
        }
    }

    /**
     * Method storage updates the storage of the player visible in the Market Scene.
     *
     * @param storageInfo contains the information of the market
     */
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


    /**
     * Method menageStorage ask the player if he wants to reorganize the storage before choosing the resources from the
     * market.
     */
    public void manageStorage() {
        reorganizeQuestion_group.setOpacity(1);
        manageStorage_group.setOpacity(0);
        back_button.setDisable(true);
        selectShelf_label.setOpacity(0);
        buttonMarket_group.setDisable(true);
        storageLeader_button.setOpacity(0);

        storageLeader_1.setImage(new Image("/graphics/" + gui.getLeaderCards().get(0) +".png"));
        storageLeader_2.setImage(new Image("/graphics/" + gui.getLeaderCards().get(1) +".png"));


        ImageView ballToSet;
        for(int i = 0; i < chosenBall_group.getChildren().size(); i++) {
            ballToSet = (ImageView) chosenBall_group.getChildren().get(i);
            ballToSet.setImage(null);
            ballToSet.setEffect(null);
        }

        yes_reorganizeStorage.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(1)));
        no_reorganizeStorage.setOnAction(actionEvent -> gui.getHandler().send(new SendInt(2)));
    }

    /**
     * Method moveShelves allows the player to select which shelves he wants to invert
     */
    public void moveShelves() {
        reorganizeQuestion_group.setOpacity(0);
        manageStorage_group.setOpacity(1);
        error_label.setOpacity(0);
        storageLeader_button.setOpacity(0);

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
                error_label.setOpacity(1);
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

    /**
     * Method seeBall sets the image of the selected marble and make the player choose which one he wants to place
     *
     * @param ball contains the selected balls
     */
    public void seeBall(SeeBall ball) {
        reorganizeQuestion_group.setOpacity(0);
        selectShelf_label.setOpacity(0);
        chosenBall_group.setDisable(false);
        buttonMarket_group.setDisable(true);
        storageLeader_button.setDisable(true);
        leader_group.setDisable(true);
        white_label.setOpacity(0);
        error_label.setOpacity(0);

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

        if(ball.getBalls().size() < 4){
            switch (ball.getBalls().size()){
                case 1:
                    chosenBall_1.setDisable(false);
                    chosenBall_2.setDisable(true);
                    chosenBall_3.setDisable(true);
                    chosenBall_4.setDisable(true);
                    break;
                case 2:
                    chosenBall_1.setDisable(false);
                    chosenBall_2.setDisable(false);
                    chosenBall_3.setDisable(true);
                    chosenBall_4.setDisable(true);
                    break;
                case 3:
                    chosenBall_1.setDisable(false);
                    chosenBall_2.setDisable(false);
                    chosenBall_3.setDisable(false);
                    chosenBall_4.setDisable(true);
                    break;
            }
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
                if (finalBallToSet.getEffect().equals(borderGlow))
                    finalBallToSet.setEffect(null);
            });
        }

            chosenBall_1.setOnMouseClicked(mouseEvent -> {
                chosenBall_1.setEffect(selectedGlow);
                gui.getHandler().send(new SendInt((1)));
                chosenBall_group.setDisable(true);
                selectShelf_label.setOpacity(0);
            });

            chosenBall_2.setOnMouseClicked(mouseEvent -> {
                chosenBall_2.setEffect(selectedGlow);
                gui.getHandler().send(new SendInt((2)));
                chosenBall_group.setDisable(true);
                selectShelf_label.setOpacity(0);
            });

            chosenBall_2.setOnMouseClicked(mouseEvent -> {
                chosenBall_2.setEffect(selectedGlow);
                gui.getHandler().send(new SendInt((2)));
                chosenBall_group.setDisable(true);
                selectShelf_label.setOpacity(0);
            });

            chosenBall_3.setOnMouseClicked(mouseEvent -> {
                chosenBall_3.setEffect(selectedGlow);
                gui.getHandler().send(new SendInt((3)));
                chosenBall_group.setDisable(true);
                selectShelf_label.setOpacity(0);
            });

            chosenBall_4.setOnMouseClicked(mouseEvent -> {
                chosenBall_4.setEffect(selectedGlow);
                gui.getHandler().send(new SendInt((4)));
                chosenBall_group.setDisable(true);
                selectShelf_label.setOpacity(0);
            });
    }

    /**
     * Method chooseShelf make the player choose in which shelf he wants to place the selected ball
     */
    public void chooseShelf() {
        shelves_group.setDisable(false);
        storageLeader_button.setOpacity(1);
        storageLeader_button.setDisable(false);


        for(int i = 0; i<3; i++){
            Node n = shelves_group.getChildren().get(i);
            n.setOnMouseEntered(mouseEvent -> gui.getSceneMap().get(GUI.MARKET).setCursor(Cursor.HAND));
            n.setOnMouseExited(mouseEvent -> gui.getSceneMap().get(GUI.MARKET).setCursor(Cursor.DEFAULT));
        }

        shelves_group.getChildren().get(0).setOnMouseClicked(mouseEvent -> {
                gui.getHandler().send(new SendInt(1));
                selectShelf_label.setOpacity(0);
                shelves_group.setDisable(true);
        });

        shelves_group.getChildren().get(1).setOnMouseClicked(mouseEvent -> {
            gui.getHandler().send(new SendInt(2));
            selectShelf_label.setOpacity(0);
            shelves_group.setDisable(true);
        });

        shelves_group.getChildren().get(2).setOnMouseClicked(mouseEvent -> {
            gui.getHandler().send(new SendInt(3));
            selectShelf_label.setOpacity(0);
            shelves_group.setDisable(true);
        });

        storageLeader_button.setOnAction(actionEvent -> {
            gui.getHandler().send(new SendInt(4));
            leader_group.setDisable(false);
        });


    }

    /**
     * method error manages the error message
     *
     * @param error is the type of error
     */
    public void error(String error) {
        switch (error) {
            case "MARKET_INVALID_SHELF" :
            case "MARKET_INVALID_STORAGE_LEADER" :
                error_label.setText("INVALID CHOICE");
                error_label.setOpacity(1);
                break;
            default:
                error_label.setText("ERROR");
                error_label.setOpacity(1);
        }
    }

    /**
     * method whiteBallLeader make the player choose which one of the white ball leader he wants to use if he has two
     * white ball leader both active
     */
    public void whiteBallLeader() {
        leader_group.setDisable(false);
        shelves_group.setDisable(true);
        chosenBall_group.setDisable(true);
        white_label.setOpacity(1);

        storageLeader_1.setOnMouseClicked(mouseEvent -> {
            gui.getHandler().send(new SendInt(1));
            white_label.setOpacity(0);
        });

        storageLeader_2.setOnMouseClicked(mouseEvent -> {
            gui.getHandler().send(new SendInt(2));
            white_label.setOpacity(0);
        });
    }
}
