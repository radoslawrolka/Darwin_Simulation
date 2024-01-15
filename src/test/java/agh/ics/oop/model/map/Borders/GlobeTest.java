package agh.ics.oop.model.map.Borders;

import agh.ics.oop.model.map.utilities.MapDirection;
import agh.ics.oop.model.map.utilities.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GlobeTest {

    private final Vector2d mapSize = new Vector2d(10, 10);

    @Test
    public void getPositionTest(){
        Globe globe = new Globe(mapSize);
        Vector2d position = new Vector2d(9, 9);
        MapDirection orientation = MapDirection.NORTH;
        assertEquals(new Vector2d(9, 10), globe.getPosition(position, orientation.toUnitVector()));
        position = position.add(orientation.toUnitVector());
        assertEquals(new Vector2d(9, 10), globe.getPosition(position, orientation.toUnitVector()));
        Vector2d position2 = new Vector2d(1, 1);
        assertEquals(new Vector2d(10, 1), globe.getPosition(position2, MapDirection.WEST.toUnitVector()));
        assertEquals(new Vector2d(1, 1), globe.getPosition(position2, MapDirection.SOUTHEAST.toUnitVector()));
    }
}
