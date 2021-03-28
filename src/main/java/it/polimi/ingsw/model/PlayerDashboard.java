package it.polimi.ingsw.model;


public class PlayerDashboard {

    private String playerName;
    private FaithPath faithPath;
    private Storage storage;
    private Vault vault;
    private DevCardsSpace devCardsSpace;


    public PlayerDashboard(String playerName) {
        this.playerName = playerName;
        faithPath = new FaithPath();
        vault = new Vault();
        devCardsSpace = new DevCardsSpace();

        Shelf s1 = new Shelf(1,0,Resource.STONE);
        Shelf s2 = new Shelf(2,0,Resource.STONE);
        Shelf s3 = new Shelf(3,0,Resource.STONE);

        storage = new Storage(s1,s2,s3);
    }
}
