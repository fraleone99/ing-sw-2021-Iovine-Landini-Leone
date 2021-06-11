package it.polimi.ingsw.client.view.GUI.sceneControllers;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.client.message.SendDoubleInt;
import it.polimi.ingsw.client.message.SendInt;
import it.polimi.ingsw.client.view.EndTurnType;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.ToSeeFromGameBoard;
import it.polimi.ingsw.client.view.TurnType;
import it.polimi.ingsw.model.enumeration.CardColor;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.playerdashboard.FaithPath;
import it.polimi.ingsw.model.singleplayer.ActionToken;
import it.polimi.ingsw.model.singleplayer.BlackCrossMover;
import it.polimi.ingsw.model.singleplayer.DeleteCard;
import it.polimi.ingsw.server.answer.infoanswer.*;
import it.polimi.ingsw.server.answer.seegameboard.UpdateFaithPath;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.swing.text.Position;
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
    @FXML public ImageView player2Board;
    @FXML public ImageView player1Board;
    @FXML public ImageView player3Board;
    @FXML public Label username_1;
    @FXML public Label username_2;
    @FXML public Label username_3;
    @FXML public ImageView player1_1_1;
    @FXML public ImageView player1_2_1;
    @FXML public ImageView player1_2_2;
    @FXML public ImageView player1_3_1;
    @FXML public ImageView player1_3_2;
    @FXML public ImageView player1_3_3;
    @FXML public ImageView player2_1_1;
    @FXML public ImageView player2_2_1;
    @FXML public ImageView player2_2_2;
    @FXML public ImageView player2_3_1;
    @FXML public ImageView player2_3_2;
    @FXML public ImageView player2_3_3;
    @FXML public ImageView player3_1_1;
    @FXML public ImageView player3_2_1;
    @FXML public ImageView player3_2_2;
    @FXML public ImageView player3_3_1;
    @FXML public ImageView player3_3_2;
    @FXML public ImageView player3_3_3;
    @FXML public Label vault_coinAmount;
    @FXML public Label vault_shieldAmount;
    @FXML public Label vault_servantAmount;
    @FXML public Label vault_stoneAmount;
    @FXML public Label player1_coinAmount;
    @FXML public Label player1_shieldAmount;
    @FXML public Label player1_stoneAmount;
    @FXML public Label player1_servantAmount;
    @FXML public Pane player2_vault;
    @FXML public Pane player3_vault;
    public Label player3_coinAmount;
    public Label player3_shieldAmount;
    public Label player3_stoneAmount;
    public Label player3_servantAmount;
    public Label player2_coinAmount;
    public Label player2_shieldAmount;
    public Label player2_stoneAmount;
    public Label player2_servantAmount;
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
    @FXML private ImageView actionTokenPrint;
    @FXML Label message;
    @FXML Label active1;
    @FXML Label active2;
    @FXML Group current_faithPathGroup;
    @FXML Group player1_faithPathGroup;
    @FXML Group player2_faithPathGroup;
    @FXML Group player3_faithPathGroup;
    @FXML Group lorenzoFaithPathGroup;
    @FXML Group currentGroup;
    @FXML Group player1Group;
    @FXML Group player2Group;
    @FXML Group player3Group;
    @FXML Button developmentCardProduction;
    @FXML Button leaderCardProduction;
    @FXML Button basicProduction;
    @FXML Button endProduction;
    @FXML Group spaceGroup;
    @FXML Group chooseTurnGroup;
    @FXML Group basicProductionGroup;
    @FXML ImageView player1_leader1;
    @FXML ImageView player1_leader2;
    @FXML ImageView player2_leader1;
    @FXML ImageView player2_leader2;
    @FXML ImageView player3_leader1;
    @FXML ImageView player3_leader2;

    int oldCurrFPPos=0;
    int oldPlayer1FPPos=0;
    int oldPlayer2FPPos=0;
    int oldPlayer3FPPos=0;
    int oldLorenzoFPPos=0;

    private GUI gui;

    private final Map<Resource, String> resourceToPathMap = new HashMap<>();
    private final Map<CardColor, String> actionTokenDeleteCardToPathMap = new HashMap<>();

    private int playersNumber;
    private final ArrayList<String> othersPlayersNick = new ArrayList<>();
    private final HashMap<String, Integer> nicknameToPosition = new HashMap<>();
    private final HashMap<Integer, String> positionToNickname = new HashMap<>();

    private final HashMap<Integer, ImageView> currentFaithPathPosToImageView = new HashMap<>();
    private final HashMap<Integer, ImageView> player1FaithPathPosToImageView = new HashMap<>();
    private final HashMap<Integer, ImageView> player2FaithPathPosToImageView = new HashMap<>();
    private final HashMap<Integer, ImageView> player3FaithPathPosToImageView = new HashMap<>();
    private final HashMap<Integer, ImageView> lorenzoFaithPathPosToImageView = new HashMap<>();

    public void setGui(GUI gui) {
        this.gui = gui;

        resourceToPathMap.put(Resource.COIN, "/graphics/COIN.PNG");
        resourceToPathMap.put(Resource.SERVANT, "/graphics/SERVANT.PNG");
        resourceToPathMap.put(Resource.SHIELD, "/graphics/SHIELD.PNG");
        resourceToPathMap.put(Resource.STONE, "/graphics/STONE.PNG");

        actionTokenDeleteCardToPathMap.put(CardColor.YELLOW, "/graphics/mainScene/ActionToken/DeleteCardYellow.png");
        actionTokenDeleteCardToPathMap.put(CardColor.GREEN, "/graphics/mainScene/ActionToken/DeleteCardGreen.png");
        actionTokenDeleteCardToPathMap.put(CardColor.BLUE, "/graphics/mainScene/ActionToken/DeleteCardBlue.png");
        actionTokenDeleteCardToPathMap.put(CardColor.PURPLE, "/graphics/mainScene/ActionToken/DeleteCardPurple.png");

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
        see.setOpacity(1);
        toSee_market.setOpacity(1);
        toSee_developmentGrid.setOpacity(1);
        invalid.setOpacity(0);
        usernameLabel.setTextFill(Color.color(0,0,0));
        toSee_market.setOnAction(actionEvent -> gui.seeMarket(null));
        toSee_developmentGrid.setOnAction(actionEvent -> gui.seeGrid(null));
    }

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
        see.setOpacity(1);
        invalid.setOpacity(1);
        toSee_market.setDisable(false);
        toSee_developmentGrid.setDisable(false);
        toSee_nothing.setDisable(false);
        usernameLabel.setTextFill(Color.color(0, 0, 1));
    }

    public void askTurn() {
        chooseTurnGroup.setOpacity(1);
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

    public void storage(ImageView firstResourcesFirstShelf, ImageView firstResourcesSecondShelf, ImageView secondResourcesSecondShelf,
    ImageView firstResourcesThirdShelf, ImageView secondResourcesThirdShelf, ImageView thirdResourcesThirdShelf, Label vault_coin,
                        Label vault_stone, Label vault_servant, Label vault_shield, StorageInfo storageInfo){
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
    }

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

    public void updateBasicProduction(BasicProductionInfo info) {
        basicProductionGroup.setOpacity(1);

        ImageView image1 = (ImageView) basicProductionGroup.getChildren().get(0);
        ImageView image2 = (ImageView) basicProductionGroup.getChildren().get(1);
        ImageView image3 = (ImageView) basicProductionGroup.getChildren().get(2);

        image1.setImage(new Image(resourceToPathMap.get(info.getInput1())));
        image2.setImage(new Image(resourceToPathMap.get(info.getInput2())));
        image3.setImage(new Image(resourceToPathMap.get(info.getOutput())));
    }

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

    public void updateStorage(StorageInfo storageInfo){

        if(storageInfo.getNickname().equals(gui.getNickname())){
            //System.out.println("Updating my player dashboard...");
            storage(firstResourcesFirstShelf, firstResourcesSecondShelf, secondResourcesSecondShelf,firstResourcesThirdShelf,
                    secondResourcesThirdShelf,thirdResourcesThirdShelf,vault_coinAmount,vault_stoneAmount, vault_servantAmount,
                    vault_shieldAmount, storageInfo);
        }

        else{
            int player = nicknameToPosition.get(storageInfo.getNickname());
            //System.out.println("I have received storage of player " + player);
            if(player == 1){
                //System.out.println("Updating player Dashboard 1...");
                storage(player1_1_1, player1_2_1,player1_2_2, player1_3_1, player1_3_2, player1_3_3, player1_coinAmount, player1_stoneAmount,
                        player1_servantAmount, player1_shieldAmount, storageInfo);
            }
            else if(player == 2){
                //System.out.println("Updating player Dashboard 2...");
                storage(player2_1_1, player2_2_1,player2_2_2, player2_3_1, player2_3_2, player2_3_3, player2_coinAmount, player2_stoneAmount,
                        player2_servantAmount, player2_shieldAmount, storageInfo);
            }
            else if(player == 3){
                //System.out.println("Updating player Dashboard 3...");
                storage(player3_1_1, player3_2_1,player3_2_2, player3_3_1, player3_3_2, player3_3_3, player3_coinAmount, player3_stoneAmount,
                        player3_servantAmount, player3_shieldAmount, storageInfo);
            }
        }


    }

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

    public void updateLeaderCards(ArrayList<Integer> cards) {
        gui.getLeaderCards().addAll(cards);
        leader1.setImage(new Image("/graphics/" + cards.get(0) + ".png"));
        leader2.setImage(new Image("/graphics/" + cards.get(1) + ".png"));
    }

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



    public void setupGameBoard(PlayersInfo playersInfo) {
        this.playersNumber = playersInfo.getPlayersNumber();

        for(String nick: playersInfo.getNicknames()){
            if(!nick.equals(gui.getNickname())){
                othersPlayersNick.add(nick);
            }
        }

        if(playersNumber == 1) {
            for(int i=0; i<25; i++) {
                currentFaithPathPosToImageView.put(i, (ImageView) current_faithPathGroup.getChildren().get(i));
            }
        }
        else if(playersNumber == 2){
            player2Board.setOpacity(0.4);
            player2_vault.setOpacity(0.4);
            player3Board.setOpacity(0.4);
            player3_vault.setOpacity(0.4);
            username_1.setText(othersPlayersNick.get(0));
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

    public void setLorenzoFaithPathMap(){
        for(int i=0; i<25; i++) {
            currentFaithPathPosToImageView.put(i, (ImageView) current_faithPathGroup.getChildren().get(i));
            lorenzoFaithPathPosToImageView.put(i, (ImageView) lorenzoFaithPathGroup.getChildren().get(i));
        }
    }

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


    public void updateCardsSpace(CardsSpaceInfo info) {
        int level = info.getLevel();

        if(info.getNickname().equals(gui.getNickname())) {
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
        else {
            int player=nicknameToPosition.get(info.getNickname());

            if(player==1) {
                Group group2 = (Group) player1Group.getChildren().get(level-1);
                switch(info.getSpace()) {
                    case 1 : ImageView image1= (ImageView) group2.getChildren().get(0);
                             image1.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                             break;
                    case 2 : ImageView image2= (ImageView) group2.getChildren().get(1);
                             image2.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                             break;
                    case 3 : ImageView image3= (ImageView) group2.getChildren().get(2);
                             image3.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                             break;
                }
            } else if (player==2) {
                Group group3 = (Group) player2Group.getChildren().get(level-1);
                switch(info.getSpace()) {
                    case 1 : ImageView image1= (ImageView) group3.getChildren().get(0);
                        image1.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                        break;
                    case 2 : ImageView image2= (ImageView) group3.getChildren().get(1);
                        image2.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                        break;
                    case 3 : ImageView image3= (ImageView) group3.getChildren().get(2);
                        image3.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                        break;
                }
            } else {
                Group group4 = (Group) player3Group.getChildren().get(level-1);
                switch(info.getSpace()) {
                    case 1 : ImageView image1= (ImageView) group4.getChildren().get(0);
                             image1.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                             break;
                    case 2 : ImageView image2= (ImageView) group4.getChildren().get(1);
                             image2.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                             break;
                    case 3 : ImageView image3= (ImageView) group4.getChildren().get(2);
                             image3.setImage(new Image("/graphics/" + info.getIdCard() + ".png"));
                             break;
                }
            }
        }
    }
}
