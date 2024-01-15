package agh.ics.oop.model.map.GrassPlanter;

import agh.ics.oop.model.entities.grass.Grass;
import agh.ics.oop.model.map.utilities.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JungleTest {

     @Test
     public void addPreferableTest(){
         Jungle jungle = new Jungle(new Vector2d(10,10));
         jungle.addPreferable(new Vector2d(2,2));
         assertTrue(jungle.preferable.containsKey(new Vector2d(1,1)));
         assertEquals(1, (int) jungle.preferable.get((new Vector2d(1, 1))));
         assertTrue(jungle.preferable.containsKey(new Vector2d(1,2)));
         assertTrue(jungle.preferable.containsKey(new Vector2d(1,3)));
         assertTrue(jungle.preferable.containsKey(new Vector2d(2,3)));
         assertEquals(1, (int) jungle.preferable.get((new Vector2d(2, 3))));
         assertTrue(jungle.preferable.containsKey(new Vector2d(3,3)));
         assertTrue(jungle.preferable.containsKey(new Vector2d(3,2)));
         assertTrue(jungle.preferable.containsKey(new Vector2d(3,1)));
         assertEquals(1, (int) jungle.preferable.get((new Vector2d(3, 1))));
         assertTrue(jungle.preferable.containsKey(new Vector2d(2,1)));
         jungle.addPreferable(new Vector2d(2,3));
         assertTrue(jungle.preferable.containsKey(new Vector2d(1,4)));
         assertTrue(jungle.preferable.containsKey(new Vector2d(2,4)));
         assertTrue(jungle.preferable.containsKey(new Vector2d(3,4)));
         assertEquals(2, (int) jungle.preferable.get((new Vector2d(1, 3))));
         assertEquals(2, (int) jungle.preferable.get((new Vector2d(1, 2))));
         assertEquals(2, (int) jungle.preferable.get((new Vector2d(3, 3))));
         jungle.addPreferable(new Vector2d(2,2));
         assertEquals(3, (int) jungle.preferable.get((new Vector2d(3, 2))));
     }

     @Test
    public void eatGrassTest(){
         Jungle jungle = new Jungle(new Vector2d(10,10));
         jungle.grasses.put(new Vector2d(2,2), new Grass(new Vector2d(2,2)));
         jungle.addPreferable(new Vector2d(2,2));
         jungle.grasses.put(new Vector2d(2,3), new Grass(new Vector2d(2,3)));
         jungle.addPreferable(new Vector2d(2,3));
         jungle.preferable.remove(new Vector2d(2,3));
         jungle.grasses.put(new Vector2d(3,3), new Grass(new Vector2d(3,3)));
         jungle.addPreferable(new Vector2d(3,3));
         jungle.preferable.remove(new Vector2d(3,3));
         assertTrue(jungle.grasses.containsKey(new Vector2d(2,3)));
         jungle.eatGrass(new Vector2d(2,2));
         assertFalse(jungle.grasses.containsKey(new Vector2d(2,2)));
         assertFalse(jungle.preferable.containsKey(new Vector2d(1,1)));
         assertFalse(jungle.preferable.containsKey(new Vector2d(2,1)));
         assertTrue(jungle.preferable.containsKey(new Vector2d(2,2)));
         assertFalse(jungle.preferable.containsKey(new Vector2d(2,3)));
         assertEquals(2, (int) jungle.preferable.get((new Vector2d(2, 2))));
         assertEquals(2, (int) jungle.preferable.get((new Vector2d(3, 2))));
         jungle.eatGrass(new Vector2d(2,3));
         assertEquals(1, (int) jungle.preferable.get((new Vector2d(2, 3))));
         assertFalse(jungle.preferable.containsKey(new Vector2d(3,3)));
         jungle.eatGrass(new Vector2d(3,3));
         assertFalse(jungle.preferable.containsKey(new Vector2d(3,3)));
         assertEquals(0, jungle.preferable.size());
     }
}
