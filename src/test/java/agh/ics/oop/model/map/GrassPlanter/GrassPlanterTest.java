package agh.ics.oop.model.map.GrassPlanter;

import agh.ics.oop.model.entities.grass.Grass;
import agh.ics.oop.model.map.utilities.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GrassPlanterTest {

    private final Vector2d mapSize = new Vector2d(10, 10);

    @Test
    public void plantGrassAtRandomPositionTest(){
        AbstractGrassPlanter planter_equator = new Equator(mapSize);
        int size_1 = planter_equator.preferable.size();
        int size_2 = planter_equator.not_preferable.size();
        planter_equator.plantGrassAtRandomPosition(planter_equator.preferable.keySet());
        assertEquals(1, planter_equator.grasses.size());
        assertEquals(planter_equator.preferable.size(), size_1 - 1);
        for (Grass grass : planter_equator.grasses.values()){
            int lowerY = 3;
            int upperY = 6;
            assertTrue(grass.getPosition().follows(new Vector2d(0, lowerY))&& grass.getPosition().precedes(new Vector2d(mapSize.getX(), upperY)));
        }
        planter_equator.plantGrassAtRandomPosition(planter_equator.not_preferable);
        assertEquals(2, planter_equator.grasses.size());
        assertEquals(planter_equator.not_preferable.size(), size_2 - 1);
    }

    @Test
    public void plantGrassTest(){
        AbstractGrassPlanter planter_equator = new Equator(mapSize);
        Set<Vector2d> set = new HashSet<>(planter_equator.not_preferable);
        Map<Vector2d, Integer> map = new HashMap<>(planter_equator.preferable);
        int diff_1 = 0;
        int diff_2 = 0;
        for (int i=0; i<10; i++){
            planter_equator.plantGrass(26);
            diff_1 += map.size() - planter_equator.preferable.size();
            diff_2 += set.size() - planter_equator.not_preferable.size();
            planter_equator.grasses.clear();
            planter_equator.preferable.clear();
            planter_equator.not_preferable.clear();
            planter_equator.preferable.putAll(map);
            planter_equator.not_preferable.addAll(set);
        }
        assertTrue(diff_1 > (diff_2+diff_1)*0.70 && diff_1 < (diff_2+diff_1)*0.90);
        planter_equator.preferable.clear();
        planter_equator.plantGrass(15);
        assertEquals(15, set.size() - planter_equator.not_preferable.size());
        assertEquals(15, planter_equator.grasses.size());
        planter_equator.not_preferable.clear();
        planter_equator.plantGrass(15);
        assertEquals(15, planter_equator.grasses.size());
        planter_equator.preferable.putAll(map);
        planter_equator.not_preferable.addAll(set);
        planter_equator.grasses.clear();
        planter_equator.plantGrass(110);
        assertEquals(100, planter_equator.grasses.size());
        assertEquals(0, planter_equator.preferable.size());
        assertEquals(0, planter_equator.not_preferable.size());



    }
}
