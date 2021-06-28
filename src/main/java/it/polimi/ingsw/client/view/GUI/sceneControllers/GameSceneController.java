package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.view.EndTurnType;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.ToSeeFromGameBoard;
import it.polimi.ingsw.client.view.TurnType;
import it.polimi.ingsw.model.card.deck.DevelopmentCardDeck;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.model.singleplayer.BlackCrossMover;
import it.polimi.ingsw.model.singleplayer.DeleteCard;
import it.polimi.ingsw.server.answer.infoanswer.*;
import it.polimi.ingsw.server.answer.seegameboard.InitializeGameBoard;
import it.polimi.ingsw.server.answer.seegameboard.UpdateFaithPath;
import it.polimi.ingsw.server.answer.seegameboard.UpdatePapalPawn;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.management.Notification;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


/**
 * GameSceneController class controls the main scene of the game form which the player see the dashboards and chooses what
 * to do in his turn.
 *
 * @author Francesco Leone, Lorenzo Iovine, Nicola Landini
 */
public class GameSceneController {

    @FXML private Button toSee_nothing;
    @FXML private Button toSee_developmentGrid;
    @FXML private Button toSee_market;
    @FXML private RadioButton turn_market;
    @FXML private RadioButton turn_buyDevelopment;
    @FXML private RadioButton turn_activeProduction;
    @FXML private RadioButton turn_activeLeader;
    @FXML private ToggleGroup TurnType_group;
    @FXML private RadioButton turn_discardLeader;
    @FXML private ImageView player2Board;
    @FXML private ImageView player1Board;
    @FXML private ImageView player3Board;
    @FXML private Label username_1;
    @FXML private Label username_2;
    @FXML private Label username_3;
    @FXML private ImageView player1_1_1;
    @FXML private ImageView player1_2_1;
    @FXML private ImageView player1_2_2;
    @FXML private ImageView player1_3_1;
    @FXML private ImageView player1_3_2;
    @FXML private ImageView player1_3_3;
    @FXML private ImageView player2_1_1;
    @FXML private ImageView player2_2_1;
    @FXML private ImageView player2_2_2;
    @FXML private ImageView player2_3_1;
    @FXML private ImageView player2_3_2;
    @FXML private ImageView player2_3_3;
    @FXML private ImageView player3_1_1;
    @FXML private ImageView player3_2_1;
    @FXML private ImageView player3_2_2;
    @FXML private ImageView player3_3_1;
    @FXML private ImageView player3_3_2;
    @FXML private ImageView player3_3_3;
    @FXML private Label vault_coinAmount;
    @FXML private Label vault_shieldAmount;
    @FXML private Label vault_servantAmount;
    @FXML private Label vault_stoneAmount;
    @FXML private Label player1_coinAmount;
    @FXML private Label player1_shieldAmount;
    @FXML private Label player1_stoneAmount;
    @FXML private Label player1_servantAmount;
    @FXML private Pane player2_vault;
    @FXML private Pane player3_vault;
    @FXML private Label player3_coinAmount;
    @FXML private Label player3_shieldAmount;
    @FXML private Label player3_stoneAmount;
    @FXML private Label player3_servantAmount;
    @FXML private Label player2_coinAmount;
    @FXML private Label player2_shieldAmount;
    @FXML private Label player2_stoneAmount;
    @FXML private Label player2_servantAmount;
    @FXML private Group player_leader1Storage_group;
    @FXML private Group player_leader2Storage_group;
    @FXML private Group player1_leader1storage_group;
    @FXML private Group player1_leader2Storage_group;
    @FXML private Group player2_leader1storage_group;
    @FXML private Group player2_leader2storage_group;
    @FXML private Group player3_leader1storage_group;
    @FXML private Group player3_leader2storage_group;
    @FXML private Button quit_button;
    @FXML private ImageView leader1;
    @FXML private ImageView leader2;
    @FXML private ImageView firstResourcesFirstShelf;
    @FXML private ImageView firstResourcesSecondShelf;
    @FXML private ImageView secondResourcesSecondShelf;
    @FXML private ImageView firstResourcesThirdShelf;
    @FXML private ImageView secondResourcesThirdShelf;
    @FXML private ImageView thirdResourcesThirdShelf;
    @FXML private Button ok_turnType;
    @FXML private Label noActionSelectedLabel;
    @FXML private Label usernameLabel;
    @FXML private Label turn;
    @FXML private Label invalid;
    @FXML private RadioButton end_turn;
    @FXML private ImageView actionTokenPrint;
    @FXML private Label message;
    @FXML private Label active1;
    @FXML private Label active2;
    @FXML private Group current_faithPathGroup;
    @FXML private Group player1_faithPathGroup;
    @FXML private Group player2_faithPathGroup;
    @FXML private Group player3_faithPathGroup;
    @FXML private Group lorenzoFaithPathGroup;
    @FXML private Group currentGroup;
    @FXML private Group player1Group;
    @FXML private Group player2Group;
    @FXML private Group player3Group;
    @FXML private Button developmentCardProduction;
    @FXML private Button leaderCardProduction;
    @FXML private Button basicProduction;
    @FXML private Button endProduction;
    @FXML private Group spaceGroup;
    @FXML private Group chooseTurnGroup;
    @FXML private Group basicProductionGroup;
    @FXML private ImageView player1_leader1;
    @FXML private ImageView player1_leader2;
    @FXML private ImageView player2_leader1;
    @FXML private ImageView player2_leader2;
    @FXML private ImageView player3_leader1;
    @FXML private ImageView player3_leader2;
    @FXML private Group currentPlayerPapalPawn;
    @FXML private Group player1_PapalPawn;
    @FXML private Group player2_PapalPawn;
    @FXML private Group player3_PapalPawn;
    @FXML private ImageView currentPlayer_inkwell;
    @FXML private ImageView player1_inkwell;
    @FXML private ImageView player2_inkwell;
    @FXML private ImageView player3_inkwell;


    private int oldCurrFPPos=0;
    private int oldPlayer1FPPos=0;
    private int oldPlayer2FPPos=0;
    private int oldPlayer3FPPos=0;
    private int oldLorenzoFPPos=0;

    private String firstPlayer;

    private GUI gui;

    private final Map<Resource, String> resourceToPathMap = new HashMap<>();
    private final Map<CardColor, String> actionTokenDeleteCardToPathMap = new HashMap<>();

    private final ArrayList<String> othersPlayersNick = new ArrayList<>();
    private final HashMap<String, Integer> nicknameToPosition = new HashMap<>();
    private final HashMap<Integer, String> positionToNickname = new HashMap<>();

    private final HashMap<Integer, ImageView> currentFaithPathPosToImageView = new HashMap<>();
    private final HashMap<Integer, ImageView> player1FaithPathPosToImageView = new HashMap<>();
    private final HashMap<Integer, ImageView> player2FaithPathPosToImageView = new HashMap<>();
    private final HashMap<Integer, ImageView> player3FaithPathPosToImageView = new HashMap<>();
    private final HashMap<Integer, ImageView> lorenzoFaithPathPosToImageView = new HashMap<>();

    private final HashMap<Integer, String> papalPawnToImageView = new HashMap<>();

    /**
     * Method set gui sets the gui and initialize the maps
     *
     */
    public void setGui(GUI gui) {
        this.gui = gui;

        resourceToPathMap.put(Resource.COIN, "/graphics/COIN.png");
        resourceToPathMap.put(Resource.SERVANT, "/graphics/SERVANT.png");
        resourceToPathMap.put(Resource.SHIELD, "/graphics/SHIELD.png");
        resourceToPathMap.put(Resource.STONE, "/graphics/STONE.png");

        actionTokenDeleteCardToPathMap.put(CardColor.YELLOW, "/graphics/mainScene/ActionToken/DeleteCardYellow.png");
        actionTokenDeleteCardToPathMap.put(CardColor.GREEN, "/graphics/mainScene/ActionToken/DeleteCardGreen.png");
        actionTokenDeleteCardToPathMap.put(CardColor.BLUE, "/graphics/mainScene/ActionToken/DeleteCardBlue.png");
        actionTokenDeleteCardToPathMap.put(CardColor.PURPLE, "/graphics/mainScene/ActionToken/DeleteCardPurple.png");

        papalPawnToImageView.put(1, "/graphics/PAPAL_PAWN_2.png");
        papalPawnToImageView.put(2, "/graphics/PAPAL_PAWN_3.png");
        papalPawnToImageView.put(3, "/graphics/PAPAL_PAWN_4.png");

        message.setBackground(new Background(new BackgroundFill(Color.rgb(249, 228, 183, 0.9), new CornerRadii(5.0), new Insets(-5.0))));
        message.setTextFill(Color.BLACK);

    }

    /**
     * seePhase method handles the phase of the game in which the player wants to see the market or the development card
     * grid in his turn or during the turn of the others players
     */
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
        usernameLabel.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.9), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * notMyTurn method handles the gameScene when it is not the turn of the player. He can only see the market and the
     * grid.
     */
    public void notMyTurn() {
        message.setText("IT'S NOT YOUR TURN!");
        message.setOpacity(1);
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
        toSee_market.setOpacity(1);
        toSee_developmentGrid.setOpacity(1);
        invalid.setOpacity(0);
        usernameLabel.setTextFill(Color.color(0,0,0));
        toSee_market.setOnAction(actionEvent -> gui.seeMarket(null));
        toSee_developmentGrid.setOnAction(actionEvent -> gui.seeGrid(null));
    }

    /**
     * isMyTurn method handles the start of the turn of the player he can see the grid or/and the market and then he
     * starts the turn
     */
    public void isMyTurn() {
        message.setText("IT'S YOUR TURN!");
        message.setOpacity(1);
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
        invalid.setOpacity(1);
        toSee_market.setDisable(false);
        toSee_developmentGrid.setDisable(false);
        toSee_nothing.setDisable(false);
        usernameLabel.setTextFill(Color.color(0, 0, 1));
    }

    /**
     * askTurn method let the player choose what he wants to do in that turn
     */
    public void askTurn() {
        chooseTurnGroup.setOpacity(1);
        turn_market.setOpacity(1);
        turn_activeLeader.setOpacity(1);
        turn_activeProduction.setOpacity(1);
        turn_buyDevelopment.setOpacity(1);
        turn_discardLeader.setOpacity(1);
        ok_turnType.setOpacity(1);
        turn.setOpacity(1);
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
                gui.changeStage(GUI.MARKET);
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
                invalid.setOpacity(0);
            } else {
                noActionSelectedLabel.setOpacity(1);
                askTurn();
            }
        });
    }

    /**
     * Storage method updates a storage when the server sends a StorageInfo message.
     *
     * @param firstResourcesFirstShelf is the first resources of the first shelf to update
     * @param firstResourcesSecondShelf is the first resources of the second shelf to update
     * @param secondResourcesSecondShelf is the second resources of the second shelf to update
     * @param firstResourcesThirdShelf is the first resources of the third shelf to update
     * @param secondResourcesThirdShelf is the second resources of the second shelf to update
     * @param thirdResourcesThirdShelf is the third resources of the third shelf to update
     * @param vault_coin is the amount of coin in the vault
     * @param vault_stone is the amount of stone in the vault
     * @param vault_servant is the amount of servant in the vault
     * @param vault_shield is the amount of shield in the vault
     * @param leader1 is the group containing the resources of the first leader in case he is a storage leader
     * @param leader2 is the group containing the resources of the second leader in case he is a storage leader
     * @param storageInfo is the message received from the server containing the information to update the scene
     */
    public void storage(ImageView firstResourcesFirstShelf, ImageView firstResourcesSecondShelf, ImageView secondResourcesSecondShelf,
    ImageView firstResourcesThirdShelf, ImageView secondResourcesThirdShelf, ImageView thirdResourcesThirdShelf, Label vault_coin,
                        Label vault_stone, Label vault_servant, Label vault_shield, Group leader1, Group leader2, StorageInfo storageInfo){
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
                firstResourcesSecondShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf2Type())));
                secondResourcesSecondShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf2Type())));
            }
            else{
                firstResourcesSecondShelf.setImage(new Image(resourceToPathMap.get(storageInfo.getShelf2Type())));
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

        if(storageInfo.isVaultUpdate()) {
            vault_coin.setText(String.valueOf(storageInfo.getCoinsAmount()));
            vault_stone.setText(String.valueOf(storageInfo.getStoneAmount()));
            vault_servant.setText(String.valueOf(storageInfo.getServantsAmount()));
            vault_shield.setText(String.valueOf(storageInfo.getShieldsAmount()));
        }

        if(storageInfo.getStorageLeader1() !=  -1){
            int amount = storageInfo.getStorageLeader1();
            ImageView imageView;
            if(amount == 0){
                imageView = (ImageView) leader1.getChildren().get(0);
                imageView.setImage(null);
                imageView = (ImageView) leader1.getChildren().get(1);
                imageView.setImage(null);
            }

            else if(amount == 1){
                imageView = (ImageView) leader1.getChildren().get(0);
                imageView.setImage(new Image(resourceToPathMap.get(storageInfo.getLeader1Type())));
                imageView = (ImageView) leader1.getChildren().get(1);
                imageView.setImage(null);
            }
            else {
                imageView = (ImageView) leader1.getChildren().get(0);
                imageView.setImage(new Image(resourceToPathMap.get(storageInfo.getLeader1Type())));
                imageView = (ImageView) leader1.getChildren().get(1);
                imageView.setImage(new Image(resourceToPathMap.get(storageInfo.getLeader1Type())));
            }
        }

        if(storageInfo.getStorageLeader2() !=  -1){
            int amount = storageInfo.getStorageLeader2();
            ImageView imageView;
            if(amount == 0){
                imageView = (ImageView) leader2.getChildren().get(0);
                imageView.setImage(null);
                imageView = (ImageView) leader2.getChildren().get(1);
                imageView.setImage(null);
            }

            else if(amount == 1){
                imageView = (ImageView) leader2.getChildren().get(0);
                imageView.setImage(new Image(resourceToPathMap.get(storageInfo.getLeader2Type())));
                imageView = (ImageView) leader2.getChildren().get(1);
                imageView.setImage(null);
            }
            else {
                imageView = (ImageView) leader2.getChildren().get(0);
                imageView.setImage(new Image(resourceToPathMap.get(storageInfo.getLeader2Type())));
                imageView = (ImageView) leader2.getChildren().get(1);
                imageView.setImage(new Image(resourceToPathMap.get(storageInfo.getLeader2Type())));
            }
        }


    }

    /**
     * discardLeader method let the player discard a leader at the beginning or at the end of the turn
     */
    public void discardLeader() {
        message.setText("Press on the leader card that you want to discard.");
        message.setOpacity(1);
        leader1.setDisable(false);
        leader2.setDisable(false);
        leader1.setOnMouseClicked(event -> {
            if(leader1.getOpacity()==1) {
                gui.readMessage("INVALID");
                gui.getHandler().send(new SendInt(3));
            } else {
                gui.getHandler().send(new SendInt(1));
                leader1.setOpacity(0);
            }
            message.setOpacity(0);
            leader1.setDisable(true);
            leader2.setDisable(true);
        });
        leader2.setOnMouseClicked(event -> {
            if(leader2.getOpacity()==1) {
                gui.readMessage("INVALID");
                gui.getHandler().send(new SendInt(3));
            } else {
                gui.getHandler().send(new SendInt(2));
                leader2.setOpacity(0);
            }
            message.setOpacity(0);
            leader1.setDisable(true);
            leader2.setDisable(true);
        });
    }

    /**
     * activeLeader method let the player active a leader at the beginning or at the end of the turn
     */
    public void activeLeader() {
        message.setText("Press on the leader card that you want to activate.");
        message.setOpacity(1);
        leader1.setDisable(false);
        leader2.setDisable(false);
        leader1.setOnMouseClicked(event -> {
            if(leader1.getOpacity()==0) {
                gui.readMessage("INVALID");
                gui.getHandler().send(new SendInt(3));
            } else {
                gui.getHandler().send(new SendInt(1));
                active1.setOpacity(1);
                leader1.setOpacity(1);
            }
            message.setOpacity(0);
            leader1.setDisable(true);
            leader2.setDisable(true);
        });
        leader2.setOnMouseClicked(event -> {
            if(leader2.getOpacity()==0) {
                gui.readMessage("INVALID");
                gui.getHandler().send(new SendInt(3));
            } else {
                gui.getHandler().send(new SendInt(2));
                active2.setOpacity(1);
                leader2.setOpacity(1);
            }
            message.setOpacity(0);
            leader1.setDisable(true);
            leader2.setDisable(true);
        });
    }

    /**
     * resetCard reset the selected card
     * @param pos is the leader card to reset
     */
    public void resetCard(int pos) {
        if(gui.getLeaderCards().get(0)==pos && leader1.getOpacity()==1) {
            leader1.setOpacity(0.5);
            active1.setOpacity(0);
        }
        if(gui.getLeaderCards().get(1)==pos && leader2.getOpacity()==1) {
            leader2.setOpacity(0.5);
            active2.setOpacity(0);
        }
    }


    /**
     *method chooseProduction make the player choose which production he wants to activate
     */
    public void chooseProduction() {
        chooseTurnGroup.setOpacity(0);
        turn.setOpacity(0);
        leader1.setDisable(true);
        leader2.setDisable(true);
        ok_turnType.setOpacity(0);
        spaceGroup.setOpacity(0);
        basicProduction.setOpacity(1);
        developmentCardProduction.setOpacity(1);
        endProduction.setOpacity(1);
        leaderCardProduction.setOpacity(1);
        message.setText("Choose what type of production do you want to activate");

        basicProduction.setOnMouseClicked(event -> gui.getHandler().send(new SendInt(1)));
        developmentCardProduction.setOnMouseClicked(event -> gui.getHandler().send(new SendInt(2)));
        leaderCardProduction.setOnMouseClicked(event -> gui.getHandler().send(new SendInt(3)));
        endProduction.setOnMouseClicked(event -> {
            gui.getHandler().send(new SendInt(4));
            basicProduction.setOpacity(0);
            developmentCardProduction.setOpacity(0);
            leaderCardProduction.setOpacity(0);
            endProduction.setOpacity(0);
            basicProductionGroup.setOpacity(0);
        });
    }

    /**
     * method updateBasicProduction updates the image of the basicProduction
     * @param info is the message received from the server containing the info of the basic production
     */
    public void updateBasicProduction(BasicProductionInfo info) {
        basicProductionGroup.setOpacity(1);

        ImageView image1 = (ImageView) basicProductionGroup.getChildren().get(0);
        ImageView image2 = (ImageView) basicProductionGroup.getChildren().get(1);
        ImageView image3 = (ImageView) basicProductionGroup.getChildren().get(2);

        image1.setImage(new Image(resourceToPathMap.get(info.getInput1())));
        image2.setImage(new Image(resourceToPathMap.get(info.getInput2())));
        image3.setImage(new Image(resourceToPathMap.get(info.getOutput())));
    }

    /**
     * leaderCardProduction let the player use a production Leader
     */
    public void leaderCardProduction() {
        basicProduction.setOpacity(0);
        developmentCardProduction.setOpacity(0);
        endProduction.setOpacity(0);
        leaderCardProduction.setOpacity(0);
        message.setText("Press on the leader that you want to use");
        message.setOpacity(1);
        leader1.setDisable(false);
        leader2.setDisable(false);

        leader1.setOnMouseClicked(event -> gui.getHandler().send(new SendInt(1)));

        leader2.setOnMouseClicked(event -> gui.getHandler().send(new SendInt(2)));
    }

    /**
     * leaderCardProduction let the player use a DevelopmentCard for the production
     */
    public void developmentCardProduction() {
        basicProduction.setOpacity(0);
        developmentCardProduction.setOpacity(0);
        endProduction.setOpacity(0);
        leaderCardProduction.setOpacity(0);
        spaceGroup.setOpacity(1);
        message.setText("Choose which space you want to activate");

        for(int i=0; i<3; i++) {
            Button button = (Button) spaceGroup.getChildren().get(i);
            final int finalI = i;
            button.setOnMouseClicked(event -> gui.getHandler().send(new SendInt((finalI+1))));
        }
    }

    /**
     * method updateStorage manages the message of Storage Info received from the server reading whose is the storage to
     * update and selecting the right node to modify
     * @param storageInfo is the message received from the server that contains the info of the storage to update
     */
    public void updateStorage(StorageInfo storageInfo){

        if(storageInfo.getNickname().equals(gui.getNickname())){
            storage(firstResourcesFirstShelf, firstResourcesSecondShelf, secondResourcesSecondShelf,firstResourcesThirdShelf,
                    secondResourcesThirdShelf,thirdResourcesThirdShelf,vault_coinAmount,vault_stoneAmount, vault_servantAmount,
                    vault_shieldAmount,player_leader1Storage_group, player_leader2Storage_group,  storageInfo);
        }

        else{
            int player = nicknameToPosition.get(storageInfo.getNickname());
            //System.out.println("I have received storage of player " + player);
            if(player == 1){
                //System.out.println("Updating player Dashboard 1..." + positionToNickname.get(1));
                storage(player1_1_1, player1_2_1,player1_2_2, player1_3_1, player1_3_2, player1_3_3, player1_coinAmount, player1_stoneAmount,
                        player1_servantAmount, player1_shieldAmount, player1_leader1storage_group, player1_leader2Storage_group, storageInfo);
            }
            else if(player == 2){
                //System.out.println("Updating player Dashboard 2..." + positionToNickname.get(2));
                storage(player2_1_1, player2_2_1,player2_2_2, player2_3_1, player2_3_2, player2_3_3, player2_coinAmount, player2_stoneAmount,
                        player2_servantAmount, player2_shieldAmount, player2_leader1storage_group ,player2_leader2storage_group, storageInfo);
            }
            else if(player == 3){
                //System.out.println("Updating player Dashboard 3..." + positionToNickname.get(3));
                storage(player3_1_1, player3_2_1,player3_2_2, player3_3_1, player3_3_2, player3_3_3, player3_coinAmount, player3_stoneAmount,
                        player3_servantAmount, player3_shieldAmount, player3_leader1storage_group, player3_leader2storage_group, storageInfo);
            }
        }


    }

    /**
     * winLabel method set a label with the winning message
     * @param win is the winning message that contains the points accumulated by the player
     */
    public void winLabel(String win) {
        message.setText(win);
        message.setTextFill(Color.LIMEGREEN);
        message.setOpacity(1);
    }

    /**
     * lose label method set a label with a lose message
     * @param lose is the losing message that contains the points accumulated by the player
     */
    public void loseLabel(String lose) {
        message.setText(lose);
        message.setTextFill(Color.RED);
        message.setOpacity(1);
    }

    /**
     * method activeOtherLeaderCard updates the scene when a player actives a Leader card that card becomes visible on
     * all the players Dashboard
     * @param info contains the leader card activated, the player who has activated the card and the position
     */
    public void activeOtherLeaderCard(OtherLeaderCard info) {
        int player = nicknameToPosition.get(info.getOwner());

        if(player==1) {
            if(info.getPos()==1) {
                player1_leader1.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
            } else {
                player1_leader2.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
            }
        } else if (player==2) {
            if(info.getPos()==1) {
                player2_leader1.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
            } else {
                player2_leader2.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
            }
        } else if (player==3) {
            if(info.getPos()==1) {
                player3_leader1.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
            } else {
                player3_leader2.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
            }
        }
    }

    /**
     * method discardOtherLeaderCard updates the scene when a player discard a Leader card that card is deleted from all
     * the players Dashboard
     * @param info contains the leader card discarded, the player who has discarded the card and the position
     */
    public void discardOtherLeaderCard(OtherLeaderCard info) {
        int player = nicknameToPosition.get(info.getOwner());

        if(player==1) {
            if(info.getPos()==1) player1_leader1.setOpacity(0);
            else player1_leader2.setOpacity(0);
        } else if(player==2) {
            if(info.getPos()==1) player2_leader1.setOpacity(0);
            else player2_leader2.setOpacity(0);
        } else if(player==3) {
            if(info.getPos()==1) player3_leader1.setOpacity(0);
            else player3_leader2.setOpacity(0);
        }
    }

    /**
     * updateLeaderCards updates the leader card of the player
     */
    public void updateLeaderCards(InitializeGameBoard message) {
        gui.getLeaderCards().addAll(message.getLeaderCards());
        leader1.setImage(new Image("/graphics/" + message.getLeaderCards().get(0) + ".png"));
        leader2.setImage(new Image("/graphics/" + message.getLeaderCards().get(1) + ".png"));

        if(message.isCrashed()){
            if(message.isActive1()) {
                active1.setOpacity(1);
                leader1.setOpacity(1);
            } else if(message.isDiscarded1()) {
                leader1.setOpacity(0);
            }

            if(message.isActive2()) {
                active2.setOpacity(1);
                leader2.setOpacity(1);
            } else if(message.isDiscarded2()) {
                leader2.setOpacity(0);
            }
        }
    }

    /**
     * setDevCardsSpaceForReconnection sets the development cards when a player reconnects to the game after a disconnection
     *
     * @param spaces contains the development Cards of the owner
     * @param owner is the nickname of the owner of the cards
     */
    public void setDevCardsSpaceForReconnection(ArrayList<DevelopmentCardDeck> spaces, String owner) {
        if(owner.equals(gui.getNickname())) {
            for(int i=0; i<3; i++) {
                Group group = (Group) currentGroup.getChildren().get(i);
                for(int j=0; j<spaces.get(i).size() ; j++) {
                    ImageView image = (ImageView) group.getChildren().get(j);
                    image.setImage(new Image("/graphics/"+ spaces.get(i).getDeck().get(j).getCardID() +".png"));
                }
            }
        } else {
            int player = nicknameToPosition.get(owner);

            if(player == 1) {
                for(int i=0; i<3; i++) {
                    Group group = (Group) player1Group.getChildren().get(i);
                    for(int j=0; j<spaces.get(i).size() ; j++) {
                        ImageView image = (ImageView) group.getChildren().get(j);
                        image.setImage(new Image("/graphics/"+ spaces.get(i).getDeck().get(j).getCardID() +".png"));
                    }
                }
            } else if(player == 2) {
                for(int i=0; i<3; i++) {
                    Group group = (Group) player2Group.getChildren().get(i);
                    for(int j=0; j<spaces.get(i).size() ; j++) {
                        ImageView image = (ImageView) group.getChildren().get(j);
                        image.setImage(new Image("/graphics/"+ spaces.get(i).getDeck().get(j).getCardID() +".png"));
                    }
                }
            } else if(player == 3) {
                for(int i=0; i<3; i++) {
                    Group group = (Group) player3Group.getChildren().get(i);
                    for(int j=0; j<spaces.get(i).size() ; j++) {
                        ImageView image = (ImageView) group.getChildren().get(j);
                        image.setImage(new Image("/graphics/"+ spaces.get(i).getDeck().get(j).getCardID() +".png"));
                    }
                }
            }
        }
    }


    /**
     * endTurn method handles the end of the turn of the player. After the end of the turn until it is his turn again the
     * player can only view the market and the grid
     */
    public void endTurn() {
        chooseTurnGroup.setOpacity(1);
        end_turn.setOpacity(1);
        turn_buyDevelopment.setOpacity(0);
        turn_activeProduction.setOpacity(0);
        turn_market.setOpacity(0);
        turn_activeLeader.setOpacity(1);
        turn_discardLeader.setOpacity(1);
        ok_turnType.setOpacity(1);
        message.setText("IT'S YOUR TURN!");
        AtomicReference<EndTurnType> turnType = new AtomicReference<>();

        ok_turnType.setOnAction(actionEvent -> {
            if (turn_activeLeader.isSelected()) {
                turnType.set(EndTurnType.ACTIVE_LEADER);
            } else if (turn_discardLeader.isSelected()) {
                turnType.set(EndTurnType.DISCARD_LEADER);
            } else {
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

    /**
     * Sets an invalid choice label
     */
    public void invalidChoice() {
        invalid.setOpacity(1);
    }

    public void updateActionToken(ActionToken actionToken){
        if(actionToken instanceof BlackCrossMover) {
            if(((BlackCrossMover) actionToken).haveToBeShuffled()){
                actionTokenPrint.setImage(new Image("/graphics/mainScene/ActionToken/BlackCrossMover1.png"));
            } else {
                actionTokenPrint.setImage(new Image("/graphics/mainScene/ActionToken/BlackCrossMover2.png"));
            }
        } else {
            actionTokenPrint.setImage(new Image(actionTokenDeleteCardToPathMap.get(((DeleteCard) actionToken).getColorType())));
        }
    }


    /**
     * setupGameBoard method sets the Game scene at the beginning of the game. The player dashboards are set based
     * on the number of the player and the nickname are associated to the player dashboard
     * @param playersInfo contains the info on the number of player and their nicknames
     */
    public void setupGameBoard(PlayersInfo playersInfo) {
        int playersNumber = playersInfo.getPlayersNumber();
        this.firstPlayer = playersInfo.getNicknames().get(0);

        for(String nick: playersInfo.getNicknames()){
            if(!nick.equals(gui.getNickname())){
                othersPlayersNick.add(nick);
            }
        }

        if(playersNumber == 1) {
            for(int i=0; i<25; i++) {
                currentFaithPathPosToImageView.put(i, (ImageView) current_faithPathGroup.getChildren().get(i));
            }
            usernameLabel.setText(playersInfo.getNicknames().get(0));
            usernameLabel.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.9), CornerRadii.EMPTY, Insets.EMPTY)));
        }
        else if(playersNumber == 2){
            player2Board.setOpacity(0.4);
            player2_vault.setOpacity(0.4);
            player3Board.setOpacity(0.4);
            player3_vault.setOpacity(0.4);
            username_1.setText(othersPlayersNick.get(0));
            username_1.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.9), CornerRadii.EMPTY, Insets.EMPTY)));

            username_2.setText("");
            username_3.setText("");
            player1_leader1.setImage(new Image("/graphics/65.png"));
            player1_leader2.setImage(new Image("/graphics/65.png"));

            nicknameToPosition.put(othersPlayersNick.get(0) , 1);
            positionToNickname.put(1, othersPlayersNick.get(0));

            for(int i=0; i<25; i++) {
                currentFaithPathPosToImageView.put(i, (ImageView) current_faithPathGroup.getChildren().get(i));
                player1FaithPathPosToImageView.put(i, (ImageView) player1_faithPathGroup.getChildren().get(i));
            }
        }
        else if(playersNumber == 3){
            player3Board.setOpacity(0.4);
            player3_vault.setOpacity(0.4);
            username_1.setText(othersPlayersNick.get(0));
            username_2.setText(othersPlayersNick.get(1));
            username_1.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.9), CornerRadii.EMPTY, Insets.EMPTY)));
            username_2.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.9), CornerRadii.EMPTY, Insets.EMPTY)));

            username_3.setText("");
            player1_leader1.setImage(new Image("/graphics/65.png"));
            player1_leader2.setImage(new Image("/graphics/65.png"));
            player2_leader1.setImage(new Image("/graphics/65.png"));
            player2_leader2.setImage(new Image("/graphics/65.png"));

            nicknameToPosition.put(othersPlayersNick.get(0) , 1);
            nicknameToPosition.put(othersPlayersNick.get(1) , 2);
            positionToNickname.put(1, othersPlayersNick.get(0));
            positionToNickname.put(2, othersPlayersNick.get(1));

            for(int i=0; i<25; i++) {
                currentFaithPathPosToImageView.put(i, (ImageView) current_faithPathGroup.getChildren().get(i));
                player1FaithPathPosToImageView.put(i, (ImageView) player1_faithPathGroup.getChildren().get(i));
                player2FaithPathPosToImageView.put(i, (ImageView) player2_faithPathGroup.getChildren().get(i));
            }

        }
        else{
            username_1.setText(othersPlayersNick.get(0));
            username_2.setText(othersPlayersNick.get(1));
            username_3.setText(othersPlayersNick.get(2));
            username_1.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.9), CornerRadii.EMPTY, Insets.EMPTY)));
            username_2.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.9), CornerRadii.EMPTY, Insets.EMPTY)));
            username_3.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.9), CornerRadii.EMPTY, Insets.EMPTY)));

            player1_leader1.setImage(new Image("/graphics/65.png"));
            player1_leader2.setImage(new Image("/graphics/65.png"));
            player2_leader1.setImage(new Image("/graphics/65.png"));
            player2_leader2.setImage(new Image("/graphics/65.png"));
            player3_leader1.setImage(new Image("/graphics/65.png"));
            player3_leader2.setImage(new Image("/graphics/65.png"));

            nicknameToPosition.put(othersPlayersNick.get(0) , 1);
            nicknameToPosition.put(othersPlayersNick.get(1) , 2);
            nicknameToPosition.put(othersPlayersNick.get(2) , 3);

            positionToNickname.put(1, othersPlayersNick.get(0));
            positionToNickname.put(2, othersPlayersNick.get(1));
            positionToNickname.put(3, othersPlayersNick.get(2));

            for(int i=0; i<25; i++) {
                currentFaithPathPosToImageView.put(i, (ImageView) current_faithPathGroup.getChildren().get(i));
                player1FaithPathPosToImageView.put(i, (ImageView) player1_faithPathGroup.getChildren().get(i));
                player2FaithPathPosToImageView.put(i, (ImageView) player2_faithPathGroup.getChildren().get(i));
                player3FaithPathPosToImageView.put(i, (ImageView) player3_faithPathGroup.getChildren().get(i));
            }
        }
    }

    /**
     * sets the FaithPath of Lorenzo in the single player game
     */
    public void setLorenzoFaithPathMap(){
        for(int i=0; i<25; i++) {
            currentFaithPathPosToImageView.put(i, (ImageView) current_faithPathGroup.getChildren().get(i));
            lorenzoFaithPathPosToImageView.put(i, (ImageView) lorenzoFaithPathGroup.getChildren().get(i));
        }
    }

    /**
     * Updates the faithPath of a player
     * @param updateFaithPath contains the information of the faith path to update
     */
    public void updateFaithPath(UpdateFaithPath updateFaithPath) {
        int player;

        if(updateFaithPath.isLorenzo()){
            lorenzoFaithPathPosToImageView.get(oldLorenzoFPPos).setImage(null);
            oldLorenzoFPPos=updateFaithPath.getPosition();
            lorenzoFaithPathPosToImageView.get(updateFaithPath.getPosition()).setImage(new Image("/graphics/BLACK_CROSS.png"));
        } else {
            if(updateFaithPath.getNickname().equals(gui.getNickname())){
                currentFaithPathPosToImageView.get(oldCurrFPPos).setImage(null);
                oldCurrFPPos=updateFaithPath.getPosition();
                currentFaithPathPosToImageView.get(updateFaithPath.getPosition()).setImage(new Image("/graphics/RED_CROSS.png"));
            } else {
                player = nicknameToPosition.get(updateFaithPath.getNickname());

                if(player==1){
                    player1FaithPathPosToImageView.get(oldPlayer1FPPos).setImage(null);
                    oldPlayer1FPPos=updateFaithPath.getPosition();
                    player1FaithPathPosToImageView.get(updateFaithPath.getPosition()).setImage(new Image("/graphics/RED_CROSS.png"));
                } else if (player==2) {
                    player2FaithPathPosToImageView.get(oldPlayer2FPPos).setImage(null);
                    oldPlayer2FPPos=updateFaithPath.getPosition();
                    player2FaithPathPosToImageView.get(updateFaithPath.getPosition()).setImage(new Image("/graphics/RED_CROSS.png"));
                } else if(player==3) {
                    player3FaithPathPosToImageView.get(oldPlayer3FPPos).setImage(null);
                    oldPlayer3FPPos=updateFaithPath.getPosition();
                    player3FaithPathPosToImageView.get(updateFaithPath.getPosition()).setImage(new Image("/graphics/RED_CROSS.png"));
                }
            }
        }
    }

    /**
     * This method updates the papal pawn of a player of the lobby
     * @param updatePapalPawn is a message that contains the nickname of the player that
     *                        activated the papal pawn and the level of the pawn
     */
    public void updatePapalPawn(UpdatePapalPawn updatePapalPawn) {
        int player;
        ImageView image;

        if(updatePapalPawn.getNickname().equals(gui.getNickname())) {
            image=(ImageView) currentPlayerPapalPawn.getChildren().get(updatePapalPawn.getPawn()-1);
            image.setImage(new Image(papalPawnToImageView.get(updatePapalPawn.getPawn())));
        } else {
            player = nicknameToPosition.get(updatePapalPawn.getNickname());

            if(player==1){
                image=(ImageView) player1_PapalPawn.getChildren().get(updatePapalPawn.getPawn()-1);
                image.setImage(new Image(papalPawnToImageView.get(updatePapalPawn.getPawn())));
            } else if (player==2) {
                image=(ImageView) player2_PapalPawn.getChildren().get(updatePapalPawn.getPawn()-1);
                image.setImage(new Image(papalPawnToImageView.get(updatePapalPawn.getPawn())));
            } else if (player==3) {
                image=(ImageView) player3_PapalPawn.getChildren().get(updatePapalPawn.getPawn()-1);
                image.setImage(new Image(papalPawnToImageView.get(updatePapalPawn.getPawn())));
            }
        }
    }

    public void setInkwell() {
        int player;

        if(firstPlayer.equals(gui.getNickname())){
            currentPlayer_inkwell.setImage(new Image("/graphics/inkwell.png"));
        } else {
            player = nicknameToPosition.get(firstPlayer);

            if(player==1){
                player1_inkwell.setImage(new Image("/graphics/inkwell.png"));
            } else if (player==2) {
                player2_inkwell.setImage(new Image("/graphics/inkwell.png"));
            } else if (player==3) {
                player3_inkwell.setImage(new Image("/graphics/inkwell.png"));
            }
        }
    }

    /**
     * updateCardsSpace method updates the devCardsSpace of a player
     * @param info contains the information  of the devCardsSpace to update
     */
    public void updateCardsSpace(CardsSpaceInfo info) {
        int level = info.getLevel();

        if(info.getNickname().equals(gui.getNickname())) {
            getSpaceGroup(info, level, currentGroup);
        }
        else {
            int player=nicknameToPosition.get(info.getNickname());

            if(player==1) {
                getSpaceGroup(info, level, player1Group);
            } else if (player==2) {
                getSpaceGroup(info, level, player2Group);
            } else {
                getSpaceGroup(info, level, player3Group);
            }
        }
    }

    /**
     * getSpaceGroup modifies a specific devCardsSPace
     * @param info contains the information  of the devCardsSpace to update
     * @param level is the level of the card to update
     * @param currentGroup is the group that contains the cards to modify
     */
    private void getSpaceGroup(CardsSpaceInfo info, int level, Group currentGroup) {
        Group group1 = (Group) currentGroup.getChildren().get(level-1);
        switch(info.getSpace()) {
            case 1 : ImageView image1 = (ImageView) group1.getChildren().get(0);
                     image1.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                     break;
            case 2 : ImageView image2 = (ImageView) group1.getChildren().get(1);
                     image2.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                     break;
            case 3 : ImageView image3 = (ImageView) group1.getChildren().get(2);
                     image3.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                     break;
        }
    }

    /**
     * Enables a button to quit the game after the game is ended
     */
    public void endGame() {
        quit_button.setOpacity(1);
        quit_button.setDisable(false);

        quit_button.setOnAction(actionEvent -> Platform.exit());
    }

    /**
     * This method allows to update the notifications message
     * @param notification is the message that the player have to know
     */
    public void updateMessage(String notification){
        if(!notification.endsWith(" is watching the game board")){
            message.setText(notification);
        }
    }

}
