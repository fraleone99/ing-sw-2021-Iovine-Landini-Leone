package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.AnotherShelfHasTheSameTypeException;
import it.polimi.ingsw.exceptions.NotEnoughSpaceException;
import it.polimi.ingsw.exceptions.ShelfHasDifferentTypeException;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.gameboard.playerdashboard.Shelf;
import it.polimi.ingsw.model.gameboard.playerdashboard.Storage;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class StorageTest test Storage class.
 *
 * @author Francesco Leone
 */

public class StorageTest {

    @Test
    public void testGetTotalResources(){
        Shelf shelf1 = new Shelf(1, 1, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 2, Resource.COIN);
        Shelf shelf3 = new Shelf(3, 2, Resource.SHIELD);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        assertEquals(5, storage.getTotalResources());
    }

    @Test
    public void testTypePresent(){
        Shelf shelf1 = new Shelf(1, 1, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 2, Resource.COIN);
        Shelf shelf3 = new Shelf(3, 2, Resource.SHIELD);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        assertEquals(3, storage.typePresent(Resource.SHIELD));
        assertEquals(0, storage.typePresent(Resource.SERVANT));
    }

    @Test
    public void testIsThisTypePresent(){
        Shelf shelf1 = new Shelf(1, 1, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 2, Resource.COIN);
        Shelf shelf3 = new Shelf(3, 2, Resource.SHIELD);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        assertEquals(shelf2, storage.isThisTypePresent(Resource.COIN));
    }

    @Test
    public void testDiscardResources(){
        Shelf shelf1 = new Shelf(1, 1, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 2, Resource.COIN);
        Shelf shelf3 = new Shelf(3, 2, Resource.SHIELD);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        assertEquals(1, storage.DiscardResources(Resource.SHIELD, 3));
        assertEquals(0, storage.DiscardResources(Resource.COIN, 1));
        assertEquals(3, storage.DiscardResources(Resource.SERVANT, 3));
    }

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

    @Test(expected = NotEnoughSpaceException.class)
    public void testInvertShelvesContent_NotEnoughSpace() throws NotEnoughSpaceException {
        Shelf shelf1 = new Shelf(1, 1, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 2, Resource.SERVANT);
        Shelf shelf3 = new Shelf(3, 3, Resource.SHIELD);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        storage.InvertShelvesContent(3, 1);
    }

    @Test
    public void testCheckInput(){
        Shelf shelf1 = new Shelf(1, 1, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 2, Resource.SERVANT);
        Shelf shelf3 = new Shelf(3, 3, Resource.SHIELD);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        Goods needed = new Goods(Resource.COIN, 2);
        assertEquals(2, storage.checkInput(needed));

        needed = new Goods(Resource.SHIELD, 2);
        assertEquals(0, storage.checkInput(needed));

        needed = new Goods(Resource.STONE, 3);
        assertEquals(2, storage.checkInput(needed));
    }

    @Test
    public void testGetAmountShelf(){
        Shelf shelf1 = new Shelf(1, 1, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 2, Resource.SERVANT);
        Shelf shelf3 = new Shelf(3, 2, Resource.SHIELD);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        assertEquals(2, storage.getAmountShelf(3));
    }

    @Test
    public void testEmptyShelves(){
        Shelf shelf1 = new Shelf(1, 1, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 0, Resource.SERVANT);
        Shelf shelf3 = new Shelf(3, 2, Resource.SHIELD);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        assertEquals(1, storage.emptyShelves());
    }

    @Test
    public void testGetTypeShelf(){
        Shelf shelf1 = new Shelf(1, 1, Resource.STONE);
        Shelf shelf2 = new Shelf(2, 2, Resource.SERVANT);
        Shelf shelf3 = new Shelf(3, 2, Resource.SHIELD);
        Storage storage = new Storage(shelf1,shelf2,shelf3);

        assertEquals(Resource.STONE, storage.getTypeShelf(1));
        assertEquals(Resource.SERVANT, storage.getTypeShelf(2));
        assertEquals(Resource.SHIELD, storage.getTypeShelf(3));
    }
}

