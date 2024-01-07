package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GrassPlanterTest {

    private Vector2d mapSize = new Vector2d(10, 10);
    private int lowerY = 3;
    private int upperY = 6;
    @Test
    public void plantGrassAtRandomPositionTest(){
        AbstractGrassPlanter planter_equator = new Equator(mapSize,lowerY,upperY);
        int size_1 = planter_equator.preferable.size();
        int size_2 = planter_equator.not_preferable.size();
        planter_equator.plantGrassAtRandomPosition(planter_equator.preferable.keySet());
        assertTrue(planter_equator.grasses.size() == 1);
        assertTrue(planter_equator.preferable.size() == size_1 - 1);
        for (Grass grass : planter_equator.grasses.values()){
            assertTrue(grass.getPosition().follows(new Vector2d(0, lowerY))&& grass.getPosition().precedes(new Vector2d(mapSize.getX(), upperY)));
        }
        planter_equator.plantGrassAtRandomPosition(planter_equator.not_preferable);
        assertTrue(planter_equator.grasses.size() == 2);
        assertTrue(planter_equator.not_preferable.size() == size_2 - 1);
    }

    @Test
    public void plantGrassTest(){
        AbstractGrassPlanter planter_equator = new Equator(mapSize,lowerY,upperY);
        Set<Vector2d> set = new HashSet<>();
        Map<Vector2d,Integer> map = new HashMap<>();
        set.addAll(planter_equator.not_preferable);
        map.putAll(planter_equator.preferable);
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
        assertTrue(diff_1 > (diff_2+diff_1)*0.75 && diff_1 < (diff_2+diff_1)*0.85);
        planter_equator.preferable.clear();
        planter_equator.plantGrass(15);
        assertTrue(set.size() - planter_equator.not_preferable.size() == 15);
        assertTrue(planter_equator.grasses.size() == 15);
        planter_equator.not_preferable.clear();
        planter_equator.plantGrass(15);
        assertTrue(planter_equator.grasses.size() == 15);
        planter_equator.preferable.putAll(map);
        planter_equator.not_preferable.addAll(set);
        System.out.println(planter_equator.preferable.size());
        System.out.println(planter_equator.not_preferable.size());
        planter_equator.grasses.clear();
        planter_equator.plantGrass(110);
        assertTrue(planter_equator.grasses.size() == 100);
        assertTrue(planter_equator.preferable.size() == 0);
        System.out.println(planter_equator.not_preferable.size());
        assertTrue(planter_equator.not_preferable.size() == 0);



    }
}
