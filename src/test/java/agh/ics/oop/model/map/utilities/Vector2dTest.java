package agh.ics.oop.model.map.utilities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    @Test
    public void testEquals() {
        assert(new Vector2d(1, 2).equals(new Vector2d(1, 2)));
        assert(!new Vector2d(1, 2).equals(new Vector2d(1, 3)));
        assert(!new Vector2d(1, 2).equals(new Vector2d(2, 2)));
        assert(!new Vector2d(1, 2).equals(new Vector2d(2, 3)));
    }

    @Test
    public void testToString() {
        assertEquals(new Vector2d(1, 2).toString(), "(1,2)");
        assertEquals(new Vector2d(1, 3).toString(), "(1,3)");
        assertEquals(new Vector2d(2, 2).toString(), "(2,2)");
        assertNotEquals(new Vector2d(2, 3).toString(), "(2,2)");
        assertNotEquals(new Vector2d(2, 3).toString(), "(1,3)");
    }

    @Test
    public void testPrecedes() {
        assert(new Vector2d(1, 2).precedes(new Vector2d(1, 2)));
        assert(new Vector2d(1, 2).precedes(new Vector2d(1, 3)));
        assert(new Vector2d(1, 4).precedes(new Vector2d(2, 8)));
        assert(new Vector2d(1, 2).precedes(new Vector2d(2, 3)));
        assert(!new Vector2d(1, 2).precedes(new Vector2d(1, 1)));
        assert(new Vector2d(3, 2).precedes(new Vector2d(10, 2)));
        assert(!new Vector2d(1, 2).precedes(new Vector2d(0, 1)));
    }

    @Test
    public void testFollows() {
        assert(new Vector2d(1, 2).follows(new Vector2d(1, 2)));
        assert(new Vector2d(1, 3).follows(new Vector2d(1, 2)));
        assert(new Vector2d(2, 8).follows(new Vector2d(1, 4)));
        assert(new Vector2d(2, 3).follows(new Vector2d(1, 2)));
        assert(!new Vector2d(1, 1).follows(new Vector2d(1, 2)));
        assert(new Vector2d(10, 2).follows(new Vector2d(3, 2)));
        assert(!new Vector2d(0, 1).follows(new Vector2d(1, 2)));
    }

    @Test
    public void testUpperRight() {
        assertEquals(new Vector2d(1, 2).upperRight(new Vector2d(1, 2)), new Vector2d(1, 2));
        assertEquals(new Vector2d(1, 2).upperRight(new Vector2d(1, 3)), new Vector2d(1, 3));
        assertEquals(new Vector2d(1, 4).upperRight(new Vector2d(2, 8)), new Vector2d(2, 8));
        assertEquals(new Vector2d(1, 2).upperRight(new Vector2d(2, 3)), new Vector2d(2, 3));
        assertEquals(new Vector2d(1, 2).upperRight(new Vector2d(1, 1)), new Vector2d(1, 2));
        assertEquals(new Vector2d(3, 2).upperRight(new Vector2d(10, 2)), new Vector2d(10, 2));
        assertEquals(new Vector2d(1, 2).upperRight(new Vector2d(0, 1)), new Vector2d(1, 2));
    }

    @Test
    public void testLowerLeft() {
        assertEquals(new Vector2d(1, 2).lowerLeft(new Vector2d(1, 2)), new Vector2d(1, 2));
        assertEquals(new Vector2d(1, 2).lowerLeft(new Vector2d(1, 3)), new Vector2d(1, 2));
        assertEquals(new Vector2d(1, 4).lowerLeft(new Vector2d(2, 8)), new Vector2d(1, 4));
        assertEquals(new Vector2d(1, 2).lowerLeft(new Vector2d(2, 3)), new Vector2d(1, 2));
        assertEquals(new Vector2d(1, 2).lowerLeft(new Vector2d(1, 1)), new Vector2d(1, 1));
        assertEquals(new Vector2d(3, 2).lowerLeft(new Vector2d(10, 2)), new Vector2d(3, 2));
        assertEquals(new Vector2d(1, 2).lowerLeft(new Vector2d(0, 1)), new Vector2d(0, 1));
    }

    @Test
    public void testAdd() {
        assertEquals(new Vector2d(1, 2).add(new Vector2d(1, 2)), new Vector2d(2, 4));
        assertEquals(new Vector2d(1, 2).add(new Vector2d(1, 3)), new Vector2d(2, 5));
        assertEquals(new Vector2d(1, 4).add(new Vector2d(2, 8)), new Vector2d(3, 12));
        assertEquals(new Vector2d(1, 2).add(new Vector2d(2, 3)), new Vector2d(3, 5));
        assertEquals(new Vector2d(1, 2).add(new Vector2d(1, 1)), new Vector2d(2, 3));
        assertEquals(new Vector2d(3, 2).add(new Vector2d(10, 2)), new Vector2d(13, 4));
        assertEquals(new Vector2d(1, 2).add(new Vector2d(0, 1)), new Vector2d(1, 3));
    }

    @Test
    public void testSubtract() {
        assertEquals(new Vector2d(1, 2).subtract(new Vector2d(1, 2)), new Vector2d(0, 0));
        assertEquals(new Vector2d(1, 2).subtract(new Vector2d(1, 3)), new Vector2d(0, -1));
        assertEquals(new Vector2d(1, 4).subtract(new Vector2d(2, 8)), new Vector2d(-1, -4));
        assertEquals(new Vector2d(1, 2).subtract(new Vector2d(2, 3)), new Vector2d(-1, -1));
        assertEquals(new Vector2d(1, 2).subtract(new Vector2d(1, 1)), new Vector2d(0, 1));
        assertEquals(new Vector2d(3, 2).subtract(new Vector2d(10, 2)), new Vector2d(-7, 0));
        assertEquals(new Vector2d(1, 2).subtract(new Vector2d(0, 1)), new Vector2d(1, 1));
    }

    @Test
    public void testOpposite() {
        assertEquals(new Vector2d(1, 2).opposite(), new Vector2d(-1, -2));
        assertEquals(new Vector2d(1, 3).opposite(), new Vector2d(-1, -3));
        assertEquals(new Vector2d(2, 2).opposite(), new Vector2d(-2, -2));
        assertEquals(new Vector2d(2, 3).opposite(), new Vector2d(-2, -3));
        assertEquals(new Vector2d(1, 2).opposite(), new Vector2d(-1, -2));
        assertEquals(new Vector2d(3, 2).opposite(), new Vector2d(-3, -2));
        assertEquals(new Vector2d(1, 2).opposite(), new Vector2d(-1, -2));
    }
}
