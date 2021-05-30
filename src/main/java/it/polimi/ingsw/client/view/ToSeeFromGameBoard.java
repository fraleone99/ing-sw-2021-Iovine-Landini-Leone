package it.polimi.ingsw.client.view;

public enum ToSeeFromGameBoard {
    LEADER_CARDS,
    MARKET,
    DEVELOPMENT_CARD_GRID,
    POSSIBLE_PRODUCTION,
    LEADER_CARDS_OTHER_PLAYER,
    DEVELOPMENT_CARDS_OTHER_PLAYER,
    NOTHING;

    public static ToSeeFromGameBoard fromInteger(int x) {
        switch (x) {
            case 1:
                return LEADER_CARDS;
            case 2:
                return MARKET;
            case 3:
                return DEVELOPMENT_CARD_GRID;
            case 4:
                return POSSIBLE_PRODUCTION;
            case 5:
                return LEADER_CARDS_OTHER_PLAYER;
            case 6:
                 return DEVELOPMENT_CARDS_OTHER_PLAYER;
            case 7:
                return NOTHING;
        }
        return null;
    }

    public static int toInteger(ToSeeFromGameBoard toSeeFromGameBoard){
        switch (toSeeFromGameBoard){
            case LEADER_CARDS:
                return 1;
            case MARKET:
                return 2;
            case DEVELOPMENT_CARD_GRID:
                return 3;
            case POSSIBLE_PRODUCTION:
                return 4;
            case LEADER_CARDS_OTHER_PLAYER:
                return 5;
            case DEVELOPMENT_CARDS_OTHER_PLAYER:
                return 6;
            case NOTHING:
                return 7;
        }
        return 0;
    }


}
