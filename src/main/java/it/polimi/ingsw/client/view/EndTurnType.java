package it.polimi.ingsw.client.view;

public enum EndTurnType {
    ACTIVE_LEADER,
    DISCARD_LEADER,
    END_TURN;

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

