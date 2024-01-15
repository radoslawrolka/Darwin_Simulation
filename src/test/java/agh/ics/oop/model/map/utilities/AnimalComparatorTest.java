package agh.ics.oop.model.map.utilities;

import agh.ics.oop.model.entities.animal.Animal;
import agh.ics.oop.model.entities.animal.genotype.Genotype;
import agh.ics.oop.model.entities.animal.stats.Statistics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalComparatorTest {
    Vector2d v = new Vector2d(1, 1);
    Genotype g = new Genotype(5);
    Animal a1 = new Animal(v, g, new Statistics(0), 90);
    Animal a2 = new Animal(v, g, new Statistics(7), 90);
    Animal a3 = new Animal(v, g, new Statistics(100), 100);
    Animal a4 = new Animal(v, g, new Statistics(50), 40);

    @Test
    public void testCompare() {
        AnimalComparator ac = new AnimalComparator();
        assertEquals(0, ac.compare(a1, a1));
        assertEquals(1, ac.compare(a1, a2));
        assertEquals(-1, ac.compare(a2, a1));
        assertEquals(-1, ac.compare(a1, a3));
        assertEquals(1, ac.compare(a3, a1));
        assertEquals(1, ac.compare(a1, a4));
        assertEquals(-1, ac.compare(a4, a1));
    }


}
