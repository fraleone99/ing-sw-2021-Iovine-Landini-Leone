package it.polimi.ingsw.exceptions;

/**
 * Class AnotherShelfHasTheSameTypeException is thrown when there's another shelf
 * with the same type in the your storage.
 */
public class AnotherShelfHasTheSameTypeException extends Exception {
    public AnotherShelfHasTheSameTypeException(){
        super("There is another shelf with the same type of resource. The 3 shelves must contains different types of resources!");
    }
}
