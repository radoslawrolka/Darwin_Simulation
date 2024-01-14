package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

public class AnimalTest {
    int startEnergy = 10;
    int maxEnergy = 20;
    int moveEnergy = 1;
    private final Animal animal1 = new Animal(new Vector2d(2, 2),
                                              new Genotype(6),
                                              new Statistics(7),
                                              maxEnergy,
                                              startEnergy);
    private final Animal animal2 = new Animal(new Vector2d(2, 3),
                                              new Genotype(6),
                                              new Statistics(9),
                                              maxEnergy,
                                              startEnergy);
    private final Animal animal3 = new Animal(new Vector2d(9,7),
                                              new Genotype(8),
                                              new Statistics(1),
                                              maxEnergy,
                                              startEnergy);

    @Test
    public void testGetGenotype() {
        assert animal1.getGenotype().getGenes().length == 6;
        assert animal2.getGenotype().getGenes().length == 6;
        assert animal3.getGenotype().getGenes().length == 8;
    }

    @Test
    public void testGetEnergy() {
        assert animal1.getEnergy() == startEnergy;
        assert animal2.getEnergy() == startEnergy;
        assert animal3.getEnergy() == startEnergy;
    }

    @Test
    public void testChangeEnergy() {
        animal1.changeEnergy(-moveEnergy);
        assert animal1.getEnergy() == startEnergy - moveEnergy;
        animal1.changeEnergy(moveEnergy);
        assert animal1.getEnergy() == startEnergy;
        animal2.changeEnergy(maxEnergy+4);
        assert animal2.getEnergy() == maxEnergy;
    }

    @Test
    public void testGetStats() {
        assert animal1.getStats().getChildren() == 0;
        assert animal2.getStats().getChildren() == 0;
        assert animal3.getStats().getChildren() == 0;
    }


    @Test
    public void testGetPosition() {
        assert animal1.getPosition().equals(new Vector2d(2, 2));
        assert animal2.getPosition().equals(new Vector2d(2, 3));
        assert animal3.getPosition().equals(new Vector2d(9, 7));
    }

    @Test
    public void testIsAt() {
        assert animal1.isAt(new Vector2d(2, 2));
        assert animal2.isAt(new Vector2d(2, 3));
        assert animal3.isAt(new Vector2d(9, 7));
        assert !animal1.isAt(new Vector2d(2, 3));
        assert !animal2.isAt(new Vector2d(2, 2));
        assert !animal3.isAt(new Vector2d(2, 3));
    }

    @Test
    public void testMove() {
        animal1.move(new Borders<Vector2d>() {
                        @Override
                        public Vector2d getPosition(Vector2d position, Vector2d move) {
                            return new Vector2d(9, 9);
                        }
                    });
        assert animal1.getPosition().equals(new Vector2d(9, 9));
    }
}
