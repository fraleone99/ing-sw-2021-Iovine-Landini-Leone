package it.polimi.ingsw.client.view;

public enum EndTurnType {
    ACTIVE_LEADER,
    DISCARD_LEADER,
    END_TURN;

    public static EndTurnType fromInteger(int x) {
        switch(x) {
            case 1:
                return ACTIVE_LEADER;
            case 2:
                return DISCARD_LEADER;
            case 3:
                return END_TURN;
        }
        return null;
    }

    public static int toInteger(EndTurnType endTurnType){
        switch (endTurnType){
            case ACTIVE_LEADER:
                return 1;
            case DISCARD_LEADER:
                return 2;
            case END_TURN:
                return 3;
        }
        return 0;
    }
}

