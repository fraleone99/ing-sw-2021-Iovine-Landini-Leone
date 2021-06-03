package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.view.EndTurnType;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.ToSeeFromGameBoard;
import it.polimi.ingsw.client.view.TurnType;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.server.answer.infoanswer.StorageInfo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class GameSceneController {

    @FXML public Button toSee_nothing;
    @FXML public Button toSee_developmentGrid;
    @FXML public Button toSee_market;
    @FXML public RadioButton turn_market;
    @FXML public RadioButton turn_buyDevelopment;
    @FXML public RadioButton turn_activeProduction;
    @FXML public RadioButton turn_activeLeader;
    @FXML public ToggleGroup TurnType_group;
    @FXML public RadioButton turn_discardLeader;
    @FXML ImageView leader1;
    @FXML ImageView leader2;
    @FXML public Pane currentPlayerFirstShelf;
    @FXML public ImageView firstResourcesFirstShelf;
    public Pane currentPlayerSecondShelf;
    @FXML public ImageView firstResourcesSecondShelf;
    @FXML public ImageView secondResourcesSecondShelf;
    @FXML public ImageView firstResourcesThirdShelf;
    @FXML public ImageView secondResourcesThirdShelf;
    @FXML public ImageView thirdResourcesThirdShelf;
    @FXML Button ok_turnType;
    @FXML Label noActionSelectedLabel;
    @FXML private Label usernameLabel;
    @FXML Label turn;
    @FXML Label see;
    @FXML Label invalid;
    @FXML public RadioButton end_turn;

    private GUI gui;

    private Map<Resource, String> resourceToPathMap = new HashMap<>();


    public void setGui(GUI gui) {

        this.gui = gui;

        resourceToPathMap.put(Resource.COIN, "/graphics/COIN.PNG");
        resourceToPathMap.put(Resource.SERVANT, "/graphics/SERVANT.PNG");
        resourceToPathMap.put(Resource.SHIELD, "/graphics/SHIELD.PNG");
        resourceToPathMap.put(Resource.STONE, "/graphics/STONE.PNG");

    }


    public void seePhase(){
        end_turn.setOpacity(0);
        invalid.setOpacity(0);
        turn_market.setOpacity(0);
        turn_activeLeader.setOpacity(0);
        turn_activeProduction.setOpacity(0);
        turn_buyDevelopment.setOpacity(0);
        turn_discardLeader.setOpacity(0);
        toSee_developmentGrid.setOpacity(1);
        toSee_market.setOpacity(1);
        toSee_nothing.setOpacity(1);
        see.setOpacity(1);
        turn.setOpacity(0);
        ok_turnType.setDisable(true);
        ok_turnType.setOpacity(0);

        toSee_market.setOnAction(actionEvent -> gui.seeMarket(null));

        toSee_developmentGrid.setOnAction(actionEvent -> gui.seeGrid(null));

        toSee_nothing.setOnAction(actionEvent -> {
            gui.getHandler().send(new SendInt(ToSeeFromGameBoard.toInteger(ToSeeFromGameBoard.NOTHING)));
            ok_turnType.setDisable(false);
        });
    }

    public void setNicknameLabel(String nickname){
        usernameLabel.setText(nickname);
    }


    public void notMyTurn() {
        toSee_market.setDisable(false);
        toSee_nothing.setDisable(true);
        toSee_nothing.setOpacity(0);
        toSee_developmentGrid.setDisable(false);
        ok_turnType.setDisable(true);
        ok_turnType.setOpacity(0);
        turn_market.setOpacity(0);
        turn_activeProduction.setOpacity(0);
        end_turn.setOpacity(0);
        turn_buyDevelopment.setOpacity(0);
        turn_discardLeader.setOpacity(0);
        turn_activeLeader.setOpacity(0);
        turn.setOpacity(0);
        see.setOpacity(1);
        toSee_market.setOpacity(1);
        toSee_developmentGrid.setOpacity(1);
        invalid.setOpacity(0);
        usernameLabel.setTextFill(Color.color(0,0,0));
        toSee_market.setOnAction(actionEvent -> gui.seeMarket(null));
        toSee_developmentGrid.setOnAction(actionEvent -> gui.seeGrid(null));
    }

    public void isMyTurn() {
        toSee_nothing.setOpacity(1);
        ok_turnType.setDisable(true);
        ok_turnType.setOpacity(1);
        turn_market.setOpacity(1);
        turn_activeProduction.setOpacity(1);
        end_turn.setOpacity(1);
        turn_buyDevelopment.setOpacity(1);
        turn_discardLeader.setOpacity(1);
        turn_activeLeader.setOpacity(1);
        turn.setOpacity(1);
        see.setOpacity(1);
        invalid.setOpacity(1);
        toSee_market.setDisable(false);
        toSee_developmentGrid.setDisable(false);
        toSee_nothing.setDisable(false);
        usernameLabel.setTextFill(Color.color(0, 0, 1));
    }

    public void askTurn() {
        turn_market.setOpacity(1);
        turn_activeLeader.setOpacity(1);
        turn_activeProduction.setOpacity(1);
        turn_buyDevelopment.setOpacity(1);
        turn_discardLeader.setOpacity(1);
        ok_turnType.setOpacity(1);
        turn.setOpacity(1);
        see.setOpacity(0);
        toSee_developmentGrid.setOpacity(0);
        toSee_market.setOpacity(0);
        toSee_nothing.setOpacity(0);
        toSee_developmentGrid.setDisable(true);
        toSee_nothing.setDisable(true);
        toSee_market.setDisable(true);
        AtomicReference<TurnType> turnType = new AtomicReference<>();

        ok_turnType.setOnAction(actionEvent -> {
            if (turn_market.isSelected()) {
                turnType.set(TurnType.MARKET);
            } else if (turn_buyDevelopment.isSelected()) {
                turnType.set(TurnType.BUY_DEVELOPMENT);
            } else if (turn_activeProduction.isSelected()) {
                turnType.set(TurnType.ACTIVE_PRODUCTION);
            } else if (turn_activeLeader.isSelected()) {
                turnType.set(TurnType.ACTIVE_LEADER);
            } else if (turn_discardLeader.isSelected()) {
                turnType.set(TurnType.DISCARD_LEADER);
            }

            RadioButton selectedRadioButton = (RadioButton) TurnType_group.getSelectedToggle();

            if (selectedRadioButton != null) {
                gui.getHandler().send(new SendInt(TurnType.toInteger(turnType.get())));
                noActionSelectedLabel.setOpacity(0);
            } else {
                noActionSelectedLabel.setOpacity(1);
                askTurn();
            }
        });
    }

    public void updateStorage(StorageInfo storageInfo){

        //first shelf
        if(storageInfo.getShelf1Amount() == 0){
            firstResourcesFirstShelf.setImage(null);
        }
        else{
            firstResourcesFirstShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf1Type())));
        }


        //second shelf
        if(storageInfo.getShelf2Amount() == 0){
            firstResourcesSecondShelf.setImage(null);
            secondResourcesSecondShelf.setImage(null);
        }
        else{
            if(storageInfo.getShelf2Amount() > 1){
                firstResourcesFirstShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf2Type())));
                secondResourcesSecondShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf2Type())));
            }
            else{
                firstResourcesFirstShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf2Type())));
                secondResourcesSecondShelf.setImage(null);
            }
        }

        //third shelf
        if(storageInfo.getShelf3Amount() == 0){
            firstResourcesThirdShelf.setImage(null);
            secondResourcesThirdShelf.setImage(null);
            thirdResourcesThirdShelf.setImage(null);
        }
        else if(storageInfo.getShelf3Amount() == 1){
            firstResourcesThirdShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf3Type())));
            secondResourcesThirdShelf.setImage(null);
            thirdResourcesThirdShelf.setImage(null);
        }
        else if(storageInfo.getShelf3Amount() == 2){
            firstResourcesThirdShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf3Type())));
            secondResourcesThirdShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf3Type())));
            thirdResourcesThirdShelf.setImage(null);
        }
        else if(storageInfo.getShelf3Amount() == 3){
            firstResourcesThirdShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf3Type())));
            secondResourcesThirdShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf3Type())));
            thirdResourcesThirdShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf3Type())));
        }
    }

    public void updateLeaderCards(ArrayList<Integer> cards) {
        leader1.setImage(new Image("/graphics/" + cards.get(0) + ".png"));
        leader2.setImage(new Image("/graphics/" + cards.get(1) + ".png"));
    }

    public void endTurn() {
        end_turn.setOpacity(1);
        turn_buyDevelopment.setOpacity(0);
        turn_activeProduction.setOpacity(0);
        turn_market.setOpacity(0);
        AtomicReference<EndTurnType> turnType = new AtomicReference<>();

        ok_turnType.setOnAction(actionEvent -> {
            if (turn_activeLeader.isSelected()) {
                turnType.set(EndTurnType.ACTIVE_LEADER);
            } else if (turn_discardLeader.isSelected()) {
                turnType.set(EndTurnType.DISCARD_LEADER);
            } else if (end_turn.isSelected()) {
                turnType.set(EndTurnType.END_TURN);
            }

            RadioButton selectedRadioButton = (RadioButton) TurnType_group.getSelectedToggle();

            if (selectedRadioButton != null) {
                gui.getHandler().send(new SendInt(EndTurnType.toInteger(turnType.get())));
                noActionSelectedLabel.setOpacity(0);
            } else {
                noActionSelectedLabel.setOpacity(1);
                askTurn();
            }
        });
    }

    public void invalidChoice() {
        invalid.setOpacity(1);
    }
}
