package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JungleTest {

     @Test
     public void addPreferableTest(){
         Jungle jungle = new Jungle(new Vector2d(10,10));
         jungle.addPreferable(new Vector2d(1,1));
         assertTrue(jungle.preferable.containsKey(new Vector2d(0,0)));
         assertTrue(jungle.preferable.get((new Vector2d(0,0))) == 1);
         assertTrue(jungle.preferable.containsKey(new Vector2d(0,1)));
         assertTrue(jungle.preferable.containsKey(new Vector2d(0,2)));
         assertTrue(jungle.preferable.containsKey(new Vector2d(1,2)));
         assertTrue(jungle.preferable.get((new Vector2d(1,2))) == 1);
         assertTrue(jungle.preferable.containsKey(new Vector2d(2,2)));
         assertTrue(jungle.preferable.containsKey(new Vector2d(2,1)));
         assertTrue(jungle.preferable.containsKey(new Vector2d(2,0)));
         assertTrue(jungle.preferable.get((new Vector2d(2,0))) == 1);
         assertTrue(jungle.preferable.containsKey(new Vector2d(1,0)));
         jungle.addPreferable(new Vector2d(1,2));
         assertTrue(jungle.preferable.containsKey(new Vector2d(0,3)));
         assertTrue(jungle.preferable.containsKey(new Vector2d(1,3)));
         assertTrue(jungle.preferable.containsKey(new Vector2d(2,3)));
         assertTrue(jungle.preferable.get((new Vector2d(0,2))) == 2);
         assertTrue(jungle.preferable.get((new Vector2d(0,1))) == 2);
         assertTrue(jungle.preferable.get((new Vector2d(2,2))) == 2);
         jungle.addPreferable(new Vector2d(2,2));
         assertTrue(jungle.preferable.get((new Vector2d(2,1))) == 3);
     }

     @Test
    public void eatGrassTest(){
         Jungle jungle = new Jungle(new Vector2d(10,10));
         jungle.grasses.put(new Vector2d(1,1), new Grass(new Vector2d(1,1)));
         jungle.addPreferable(new Vector2d(1,1));
         jungle.grasses.put(new Vector2d(1,2), new Grass(new Vector2d(1,2)));
         jungle.addPreferable(new Vector2d(1,2));
         jungle.preferable.remove(new Vector2d(1,2));
         jungle.grasses.put(new Vector2d(2,2), new Grass(new Vector2d(2,2)));
         jungle.addPreferable(new Vector2d(2,2));
         jungle.preferable.remove(new Vector2d(2,2));
         assertTrue(jungle.grasses.containsKey(new Vector2d(1,2)));
         jungle.eatGrass(new Vector2d(1,1));
         assertFalse(jungle.grasses.containsKey(new Vector2d(1,1)));
         assertFalse(jungle.preferable.containsKey(new Vector2d(0,0)));
         assertFalse(jungle.preferable.containsKey(new Vector2d(1,0)));
         assertTrue(jungle.preferable.containsKey(new Vector2d(1,1)));
         assertFalse(jungle.preferable.containsKey(new Vector2d(1,2)));
         assertTrue(jungle.preferable.get((new Vector2d(1,1))) == 2);
         assertTrue(jungle.preferable.get((new Vector2d(2,1))) == 2);
         jungle.eatGrass(new Vector2d(1,2));
         assertTrue(jungle.preferable.get((new Vector2d(1,2))) == 1);
         assertFalse(jungle.preferable.containsKey(new Vector2d(2,2)));
         jungle.eatGrass(new Vector2d(2,2));
         assertFalse(jungle.preferable.containsKey(new Vector2d(2,2)));
         assertTrue(jungle.preferable.size() == 0);
     }
}
