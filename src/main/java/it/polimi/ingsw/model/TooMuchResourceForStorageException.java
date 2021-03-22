package it.polimi.ingsw.model;

public class TooMuchResourceForStorageException extends Exception {
    TooMuchResourceForStorageException(){
        super("Maximum amount of resources for storage is 3");
    }
}
