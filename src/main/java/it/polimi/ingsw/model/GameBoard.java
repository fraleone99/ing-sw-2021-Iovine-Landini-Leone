package it.polimi.ingsw.model;

import java.util.ArrayList;

public class GameBoard {
    private int playersNumber;
    private Market market;
    private ArrayList<PlayerDashboard> playersDashboards = new ArrayList<>();
    private DevelopmentCardGrid developmentCardGrid;

    public GameBoard(int playersNumber, ArrayList<Player> players) {
        this.playersNumber = playersNumber;
        market = new Market();
        developmentCardGrid = new DevelopmentCardGrid();

        for(int i = 0; i<playersNumber; i++){
            playersDashboards.add(new PlayerDashboard(players.get(i).getNickname()));
        }
    }

    public DevelopmentCardGrid getDevelopmentCardGrid() {
        return developmentCardGrid;
    }

    public Market getMarket() {
        return market;
    }
}
