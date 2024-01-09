package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
public class GlobeTest {

    private final Vector2d mapSize = new Vector2d(10, 10);

    @Test
    public void getPositionTest(){
        Globe globe = new Globe(mapSize);
        Vector2d position = new Vector2d(8, 8);
        MapDirection orientation = MapDirection.NORTH;
        assertEquals(new Vector2d(8, 9), globe.getPosition(position, orientation.toUnitVector()));
        position = position.add(orientation.toUnitVector());
        assertEquals(new Vector2d(8, 9), globe.getPosition(position, orientation.toUnitVector()));
        Vector2d position2 = new Vector2d(0, 0);
        assertEquals(new Vector2d(9, 0), globe.getPosition(position2, MapDirection.WEST.toUnitVector()));
        assertEquals(new Vector2d(0, 0), globe.getPosition(position2, MapDirection.SOUTHEAST.toUnitVector()));
    }
}
