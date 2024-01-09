package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
public class EquatorTest {

    private int lowerY= 3;
    private int upperY = 6;

    private Vector2d mapSize = new Vector2d(10,10);


    @Test
    public void startPreferableTest(){
        Equator equator1 = new Equator(mapSize);
        Equator equator2 = new Equator(new Vector2d(9,9));
        Set<Vector2d> set = new HashSet<>();
        Map<Vector2d,Integer> map = new HashMap<>();
        for (int i=0; i<mapSize.getX(); i++){
            for (int j=0; j<mapSize.getY(); j++){
                if (j>=4 && j<=5){
                    map.put(new Vector2d(i,j),0);
                }
                else{
                    set.add(new Vector2d(i,j));
                }
            }
        }
        Set<Vector2d> set2 = new HashSet<>();
        Map<Vector2d,Integer> map2 = new HashMap<>();
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if (j>=3 && j<=4){
                    map2.put(new Vector2d(i,j),0);
                }
                else{
                    set2.add(new Vector2d(i,j));
                }
            }
        }
        assertEquals(map, equator1.preferable);
        assertEquals(set, equator1.not_preferable);
        assertEquals(map2, equator2.preferable);
        assertEquals(set2, equator2.not_preferable);
    }

    @Test
    public void eatGrassTest(){
        Equator equator = new Equator(mapSize);
        equator.grasses.put(new Vector2d(1,1), new Grass(new Vector2d(1,1)));
        equator.not_preferable.remove(new Vector2d(1,1));
        equator.grasses.put(new Vector2d(5,5), new Grass(new Vector2d(5,5)));
        equator.preferable.remove(new Vector2d(5,5));
        equator.eatGrass(new Vector2d(1,1));
        assertTrue(equator.not_preferable.contains(new Vector2d(1,1)));
        equator.eatGrass(new Vector2d(5,5));
        assertTrue(equator.preferable.containsKey(new Vector2d(5,5)));
        assertTrue(equator.preferable.get(new Vector2d(5,5)) == 0);
    }
}
