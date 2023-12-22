package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalBuilderTest {
    private final int genotypeLength = 32;
    private final int MAX_ENERGY = 100;
    private final int START_ENERGY = 50;
    private final int day = 0;
    private final GenotypeEnum genotype = GenotypeEnum.NORMAL;
    private final AnimalBuilder animalBuilder = new AnimalBuilder(this.genotypeLength, this.genotype, this.MAX_ENERGY, this.START_ENERGY);

    @Test
    public void testSpawn() {
        Vector2d initialPosition = new Vector2d(1, 1);
        Animal animal = this.animalBuilder.spawn(initialPosition);
        assertEquals(initialPosition, animal.getPosition());
        assertEquals(this.genotypeLength, animal.getGenotype().getGenes().length);
        assertEquals(this.START_ENERGY, animal.getEnergy());
        assertEquals(this.day, animal.getStats().getDayOfBirth());
    }

    @Test
    public void testBuild() {
        Vector2d initialPosition = new Vector2d(3, 1);
        Animal parent1 = this.animalBuilder.spawn(initialPosition);
        Animal parent2 = this.animalBuilder.spawn(initialPosition);
        Animal animal = this.animalBuilder.build(parent1, parent2);
        assertEquals(initialPosition, animal.getPosition());
        assertEquals(this.genotypeLength, animal.getGenotype().getGenes().length);
        assertEquals(this.START_ENERGY, animal.getEnergy());
        assertEquals(this.day, animal.getStats().getDayOfBirth());
    }

    @Test
    public void testIncrementDay() {
        this.animalBuilder.incrementDay();
        Animal animal = this.animalBuilder.spawn(new Vector2d(1, 13));
        assertEquals(1, animal.getStats().getDayOfBirth());
    }
}
