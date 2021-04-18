package it.polimi.ingsw.server.answer;

public class Connection implements Answer{
    private final String message;
    private final boolean connection; //true connection confirmation, false connection termination.

    public Connection(String message, boolean connection) {
        this.message = message;
        this.connection = connection;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public boolean isConnection() {
        return connection;
    }
}
