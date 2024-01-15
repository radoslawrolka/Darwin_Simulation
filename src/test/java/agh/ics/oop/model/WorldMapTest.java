package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class WorldMapTest {
    @Test
    public void animalPlaceTest(){
        WorldMap map_eq = new WorldMapBuilder().build(10, 10, 10,1, GrassPlanterEnum.Equator,1);
        WorldMap map_jg = new WorldMapBuilder().build(10, 10, 10, 1,GrassPlanterEnum.Jungle,1);
        Animal animal = new Animal(new Vector2d(1,1), new Genotype(32), new Statistics(0), 10);
        map_eq.placeAnimal(animal);
        map_jg.placeAnimal(animal);
        assertEquals(map_eq.getAnimalsOnPosition(new Vector2d(1,1)).size(), 1);
        assertEquals(map_jg.getAnimalsOnPosition(new Vector2d(1,1)).first(), animal);
        Animal animal2 = new Animal(new Vector2d(1,1), new Genotype(32), new Statistics(0), 20);
        map_eq.placeAnimal(animal2);
        map_jg.placeAnimal(animal2);
        assertEquals(map_eq.getAnimalsOnPosition(new Vector2d(1,1)).size(), 2);
        assertEquals(map_jg.getAnimalsOnPosition(new Vector2d(1,1)).size(), 2);
        assertEquals(map_jg.getAnimalsOnPosition(new Vector2d(1,1)).first(), animal2);
        AnimalBuilder builder = new AnimalBuilder(32, GenotypeEnum.Normal, 100, 10,1,5);
        Animal animal3 = builder.spawn(new Vector2d(3,3));
        Animal animal4 = builder.spawn(new Vector2d(3,3));
        map_eq.placeAnimal(animal3);
        map_eq.placeAnimal(animal4);
        assertEquals(map_eq.getAnimalsOnPosition(new Vector2d(3,3)).size(), 2);
    }

    @Test
    public void moveAnimalTest(){
        WorldMap map_eq = new WorldMapBuilder().build(10, 10, 10,1, GrassPlanterEnum.Equator,1);
        Animal animal = new Animal(new Vector2d(5,5), new Genotype(32), new Statistics(0), 10);
        map_eq.placeAnimal(animal);
        MapDirection orientation = animal.getOrientation();
        map_eq.moveAnimal(animal);
        assertEquals(animal.getPosition(), new Vector2d(5,5).add(orientation.toUnitVector()));
        assertTrue(map_eq.getAnimalsOnPosition(new Vector2d(5,5).add(orientation.toUnitVector())).contains(animal));
        assertEquals(1, map_eq.getAnimalsOnPosition(new Vector2d(5, 5).add(orientation.toUnitVector())).size());
        assertNull(map_eq.getAnimalsOnPosition(new Vector2d(5, 5)));
        Animal animal2 = new Animal(animal.getPosition(), new Genotype(32), new Statistics(0), 20);
        map_eq.placeAnimal(animal2);
        Vector2d position = animal.getPosition();
        assertEquals(2, map_eq.getAnimalsOnPosition(position).size());
        map_eq.moveAnimal(animal2);
        map_eq.moveAnimal(animal);
        assertNull(map_eq.getAnimalsOnPosition(position));
    }

    @Test
    public void removeAnimalTest(){
        WorldMap map_eq = new WorldMapBuilder().build(10, 10, 10,1,GrassPlanterEnum.Equator,1);
        Animal animal = new Animal(new Vector2d(5,5), new Genotype(32), new Statistics(0), 10);
        map_eq.placeAnimal(animal);
        map_eq.removeAnimal(animal);
        assertNull(map_eq.getAnimalsOnPosition(new Vector2d(5, 5)));
        Animal animal2 = new Animal(new Vector2d(5,5), new Genotype(32), new Statistics(0), 20);
        map_eq.placeAnimal(animal);
        map_eq.placeAnimal(animal2);
        map_eq.removeAnimal(animal);
        assertEquals(1, map_eq.getAnimalsOnPosition(new Vector2d(5, 5)).size());
        assertSame(map_eq.getAnimalsOnPosition(new Vector2d(5, 5)).first(), animal2);
    }

    @Test
    public void eatGrassesTest(){
        WorldMap map = new WorldMapBuilder().build(4, 4, 10,10,GrassPlanterEnum.Equator,1);
        Animal animal = new Animal(new Vector2d(2,2), new Genotype(32), new Statistics(0), 10);
        map.plantGrass(20);
        map.placeAnimal(animal);
        Animal animal2 = new Animal(new Vector2d(2,2), new Genotype(32), new Statistics(0), 20);
        map.placeAnimal(animal2);
        Vector2d position1 = animal.getPosition();
        Vector2d position2 = animal2.getPosition();
        assertNotNull(map.getGrassOnPosition(position1));
        assertNotNull(map.getGrassOnPosition(position2));
        map.eatGrasses();
        assertEquals(animal.getEnergy(), 10);
        assertEquals(30, animal2.getEnergy());
        position1 = animal.getPosition();
        position2 = animal2.getPosition();
        assertNull(map.getGrassOnPosition(position1));

        assertNull(map.getGrassOnPosition(position2));
        map.moveAnimal(animal);
        map.eatGrasses();
        assertEquals(animal.getEnergy(), 20);
        assertEquals(30, animal2.getEnergy());

    }

    @Test
    public void breedAnimalsTest(){
        WorldMap map = new WorldMapBuilder().build(4, 4, 10,10,GrassPlanterEnum.Equator,9);
        Animal animal1 = new Animal(new Vector2d(2,2), new Genotype(32), new Statistics(0), 10);
        Animal animal2 = new Animal(new Vector2d(2,2), new Genotype(32), new Statistics(0), 20);
        map.placeAnimal(animal1);
        map.placeAnimal(animal2);
        List<Animal> children = map.breedAnimals(new AnimalBuilder(32, GenotypeEnum.Normal, 9, 10,1,5));
        assertEquals(1, children.size());
        Animal child = children.get(0);
        assertEquals(child.getPosition(), new Vector2d(2, 2));
        assertEquals(9, child.getEnergy());
        assertEquals(animal1.getEnergy(), 6);
        assertEquals(15, animal2.getEnergy());
        children = map.breedAnimals(new AnimalBuilder(32, GenotypeEnum.Normal, 11, 10,1,5));
        assertEquals(0, children.size());
    }

    @Test
    public void getAvailableSpaceTest(){
        WorldMap map = new WorldMapBuilder().build(4, 4, 10,1,GrassPlanterEnum.Equator,1);
        Animal animal = new Animal(new Vector2d(2,2), new Genotype(32), new Statistics(0), 10);
        map.placeAnimal(animal);
        assertEquals(map.getAvailableSpace(), 15);
        Animal animal2 = new Animal(new Vector2d(2,2), new Genotype(32), new Statistics(0), 20);
        map.placeAnimal(animal2);
        assertEquals(map.getAvailableSpace(), 15);
        map.moveAnimal(animal);
        assertEquals(map.getAvailableSpace(), 14);
    }
}
