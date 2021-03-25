package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class StorageTest test Storage class.
 *
 * @author Francesco Leone
 */

public class StorageTest {

    @Test
    public void testAddResource() throws ShelfHasDifferentTypeException, AnotherShelfHasTheSameTypeException, NotEnoughSpaceException {
        Shelf shelf1 = new Shelf(1, 0, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 0, Resource.COIN);
        Shelf shelf3 = new Shelf(3, 0, Resource.STONE);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        storage.AddResource(1,Resource.COIN, 1);


        assertEquals(shelf1.getAmount(),1);
        assertEquals(shelf1.getResourceType(), Resource.COIN);

    }

    @Test(expected = AnotherShelfHasTheSameTypeException.class)
    public void testAddResource_TwoShelfWithTheSameResource() throws ShelfHasDifferentTypeException, AnotherShelfHasTheSameTypeException,
            NotEnoughSpaceException{

        Shelf shelf1 = new Shelf(1, 0, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 1, Resource.COIN);
        Shelf shelf3 = new Shelf(3, 0, Resource.STONE);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        storage.AddResource(3, Resource.COIN, 2);
    }

    @Test(expected = ShelfHasDifferentTypeException.class)
    public void testAddResource_ShelfHasDifferentType() throws ShelfHasDifferentTypeException, AnotherShelfHasTheSameTypeException,
            NotEnoughSpaceException{

        Shelf shelf1 = new Shelf(1, 1, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 1, Resource.SERVANT);
        Shelf shelf3 = new Shelf(3, 0, Resource.STONE);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        storage.AddResource(1, Resource.COIN, 1);
    }

    @Test(expected = NotEnoughSpaceException.class)
    public void testAddResource_NotEnoughSpace() throws ShelfHasDifferentTypeException, AnotherShelfHasTheSameTypeException,
            NotEnoughSpaceException{

        Shelf shelf1 = new Shelf(1, 1, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 1, Resource.SERVANT);
        Shelf shelf3 = new Shelf(3, 0, Resource.STONE);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        storage.AddResource(2, Resource.SERVANT, 2);
    }

    @Test
    public void testMoveResourceToEmpty() throws NotEnoughSpaceException, ShelfNotEmptyException {
        Shelf shelf1 = new Shelf(1, 1, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 2, Resource.SERVANT);
        Shelf shelf3 = new Shelf(3, 0, Resource.STONE);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        storage.MoveResourceToEmptyShelf(2,3);

        assertEquals(shelf3.getResourceType(), Resource.SERVANT);
        assertEquals(shelf3.getAmount(), 2);
        assertTrue(shelf2.isFree());
    }

    @Test
    public void testInvertShelvesContent() throws NotEnoughSpaceException {
        Shelf shelf1 = new Shelf(1, 1, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 1, Resource.SERVANT);
        Shelf shelf3 = new Shelf(3, 0, Resource.STONE);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        storage.InvertShelvesContent(1,2);

        assertEquals(shelf1.getResourceType(), Resource.SERVANT);
        assertEquals(shelf2.getResourceType(), Resource.STONE);
        assertEquals(shelf2.getAmount(), 1);
    }
}

