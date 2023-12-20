package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.jupiter.api.Assertions.*;

public class MapDirectionTest {
    @Test
    public void testNext() {
        assert(MapDirection.NORTH.next(1) == MapDirection.NORTHEAST);
        assert(MapDirection.NORTHEAST.next(1) == MapDirection.EAST);
        assert(MapDirection.EAST.next(1) == MapDirection.SOUTHEAST);
        assert(MapDirection.SOUTHEAST.next(1) == MapDirection.SOUTH);
        assert(MapDirection.SOUTH.next(1) == MapDirection.SOUTHWEST);
        assert(MapDirection.SOUTHWEST.next(1) == MapDirection.WEST);
        assert(MapDirection.WEST.next(1) == MapDirection.NORTHWEST);
        assert(MapDirection.NORTHWEST.next(1) == MapDirection.NORTH);
    }

    @Test
    public void testToString() {
        assertEquals(MapDirection.NORTH.toString(), "N");
        assertEquals(MapDirection.NORTHEAST.toString(), "NE");
        assertEquals(MapDirection.EAST.toString(), "E");
        assertEquals(MapDirection.SOUTHEAST.toString(), "SE");
        assertEquals(MapDirection.SOUTH.toString(), "S");
        assertEquals(MapDirection.SOUTHWEST.toString(), "SW");
        assertEquals(MapDirection.WEST.toString(), "W");
        assertEquals(MapDirection.NORTHWEST.toString(), "NW");
    }

    @Test
    public void testToUnitVector() {
        assertEquals(MapDirection.NORTH.toUnitVector(), new Vector2d(0, 1));
        assertEquals(MapDirection.NORTHEAST.toUnitVector(), new Vector2d(1, 1));
        assertEquals(MapDirection.EAST.toUnitVector(), new Vector2d(1, 0));
        assertEquals(MapDirection.SOUTHEAST.toUnitVector(), new Vector2d(1, -1));
        assertEquals(MapDirection.SOUTH.toUnitVector(), new Vector2d(0, -1));
        assertEquals(MapDirection.SOUTHWEST.toUnitVector(), new Vector2d(-1, -1));
        assertEquals(MapDirection.WEST.toUnitVector(), new Vector2d(-1, 0));
        assertEquals(MapDirection.NORTHWEST.toUnitVector(), new Vector2d(-1, 1));
    }

    @Test
    public void testNextValue() {
        int i = ThreadLocalRandom.current().nextInt(0, 8);
        assert(MapDirection.NORTH.next(i) == MapDirection.values()[i]);
        assert(MapDirection.NORTH.next(i).next(8 - i) == MapDirection.NORTH);
        i = ThreadLocalRandom.current().nextInt(0, 8);
        assert(MapDirection.SOUTHWEST.next(i) == MapDirection.values()[(i + 5) % 8]);
        assert(MapDirection.SOUTHWEST.next(i).next(8 - i) == MapDirection.SOUTHWEST);
    }
}
