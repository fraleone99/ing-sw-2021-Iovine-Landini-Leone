package it.polimi.ingsw.client.view;

public enum ProductionType {

    BASIC,
    DEVELOPMENT_CARD,
    PRODUCTION_LEADER,
    DO_PRODUCTION;


    public static ProductionType fromInteger(int x) {
        switch (x) {
            case 1:
                return BASIC;
            case 2:
                return DEVELOPMENT_CARD;
            case 3:
                return PRODUCTION_LEADER;
            case 4:
                return DO_PRODUCTION;
        }
        return null;
    }

}

