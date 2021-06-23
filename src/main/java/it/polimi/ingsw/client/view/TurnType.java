package it.polimi.ingsw.client.view;

public enum TurnType {
    ACTIVE_LEADER,
    DISCARD_LEADER,
    MARKET,
    BUY_DEVELOPMENT,
    ACTIVE_PRODUCTION,
    CRASHED;

    public static TurnType fromInteger(int x) {
        switch(x) {
            case 1:
                return ACTIVE_LEADER;
            case 2:
                return DISCARD_LEADER;
            case 3:
                return MARKET;
            case 4:
                return BUY_DEVELOPMENT;
            case 5:
                return ACTIVE_PRODUCTION;
            case -1:
                return CRASHED;
        }
        return null;
    }

    public static int toInteger(TurnType turnType){
        switch (turnType){
            case ACTIVE_LEADER:
                return 1;
            case DISCARD_LEADER:
                return 2;
            case MARKET:
                return 3;
            case BUY_DEVELOPMENT:
                return 4;
            case ACTIVE_PRODUCTION:
                return 5;
            case CRASHED:
                return -1;
        }
        return 0;
    }
}
