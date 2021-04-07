package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;

import java.util.ArrayList;
import java.util.Collections;

public class GameBoard {
    private int playersNumber;
    private Market market;
    private ArrayList<PlayerDashboard> playersDashboards = new ArrayList<>();
    private DevelopmentCardGrid developmentCardGrid;
    private LorenzoMagnifico lorenzoMagnifico;

    public GameBoard(int playersNumber, ArrayList<Player> players) {
        this.playersNumber = playersNumber;
        market = new Market();
        developmentCardGrid = new DevelopmentCardGrid();

        for(int i = 0; i<playersNumber; i++){
            playersDashboards.add(new PlayerDashboard(players.get(i).getNickname()));
        }
    }

    public void drawActionToken() throws InvalidChoiceException {
        ActionToken element=lorenzoMagnifico.draw();

        if(element instanceof BlackCrossMover){
            playersDashboards.get(0).getFaithPath().moveForwardBCM(((BlackCrossMover) element).getSteps());
            if(((BlackCrossMover) element).haveToBeShuffled()){
                lorenzoMagnifico.shuffle();
            }
        }
        else{
            ((DeleteCard) element).draw(((DeleteCard) element).getColorType(), developmentCardGrid);
            ((DeleteCard) element).draw(((DeleteCard) element).getColorType(), developmentCardGrid);
        }
    }

    public DevelopmentCardGrid getDevelopmentCardGrid() {
        return developmentCardGrid;
    }

    public Market getMarket() {
        return market;
    }
}
