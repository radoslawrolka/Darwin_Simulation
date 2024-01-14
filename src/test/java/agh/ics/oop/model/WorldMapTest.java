package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class WorldMapTest {

    @Test
    public void animalPlaceTest(){
        WorldMap map_eq = new WorldMapBuilder().build(10, 10, 10,1,4, GrassPlanterEnum.Equator,1);
        WorldMap map_jg = new WorldMapBuilder().build(10, 10, 10, 1,4, GrassPlanterEnum.Jungle,1);
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
        WorldMap map_eq = new WorldMapBuilder().build(10, 10, 10,1,4, GrassPlanterEnum.Equator,1);
        Animal animal = new Animal(new Vector2d(5,5), new Genotype(32), new Statistics(0), 10);
        map_eq.placeAnimal(animal);
        MapDirection orientation = animal.getOrientation();
        map_eq.moveAnimal(animal);
        assertEquals(animal.getPosition(), new Vector2d(5,5).add(orientation.toUnitVector()));
        assertTrue(map_eq.getAnimalsOnPosition(new Vector2d(5,5).add(orientation.toUnitVector())).contains(animal));
        assertTrue(map_eq.getAnimalsOnPosition(new Vector2d(5,5).add(orientation.toUnitVector())).size() == 1);
        assertTrue(map_eq.getAnimalsOnPosition(new Vector2d(5,5)) == null);
        Animal animal2 = new Animal(animal.getPosition(), new Genotype(32), new Statistics(0), 20);
        map_eq.placeAnimal(animal2);
        Vector2d position = animal.getPosition();
        assertTrue(map_eq.getAnimalsOnPosition(position).size() == 2);
        map_eq.moveAnimal(animal2);
        map_eq.moveAnimal(animal);
        assertTrue(map_eq.getAnimalsOnPosition(position) == null);
    }

    @Test
    public void removeAnimalTest(){
        WorldMap map_eq = new WorldMapBuilder().build(10, 10, 10,1,4, GrassPlanterEnum.Equator,1);
        Animal animal = new Animal(new Vector2d(5,5), new Genotype(32), new Statistics(0), 10);
        map_eq.placeAnimal(animal);
        map_eq.removeAnimal(animal);
        assertTrue(map_eq.getAnimalsOnPosition(new Vector2d(5,5)) == null);
        Animal animal2 = new Animal(new Vector2d(5,5), new Genotype(32), new Statistics(0), 20);
        map_eq.placeAnimal(animal);
        map_eq.placeAnimal(animal2);
        map_eq.removeAnimal(animal);
        assertTrue(map_eq.getAnimalsOnPosition(new Vector2d(5,5)).size() == 1);
        assertTrue(map_eq.getAnimalsOnPosition(new Vector2d(5,5)).first() == animal2);
    }

    @Test
    public void eatGrassesTest(){
        WorldMap map = new WorldMapBuilder().build(4, 4, 10,10,4, GrassPlanterEnum.Equator,1);
        Animal animal = new Animal(new Vector2d(2,2), new Genotype(32), new Statistics(0), 10);
        map.plantGrass(20);
        map.placeAnimal(animal);
        Animal animal2 = new Animal(new Vector2d(2,2), new Genotype(32), new Statistics(0), 20);
        map.placeAnimal(animal2);
        Vector2d position1 = animal.getPosition();
        Vector2d position2 = animal2.getPosition();
        assertTrue(map.getGrassOnPosition(position1) != null);
        assertTrue(map.getGrassOnPosition(position2) != null);
        map.eatGrasses();
        assertEquals(animal.getEnergy(), 10);
        assertTrue(animal2.getEnergy() == 30);
        position1 = animal.getPosition();
        position2 = animal2.getPosition();
        assertTrue(map.getGrassOnPosition(position1) == null);
        assertTrue(map.getGrassOnPosition(position2) == null);
        map.moveAnimal(animal);
        map.eatGrasses();
        assertEquals(animal.getEnergy(), 20);
        assertTrue(animal2.getEnergy() == 30);

    }

    @Test
    public void breedAnimalsTest(){
        WorldMap map = new WorldMapBuilder().build(4, 4, 10,10,4, GrassPlanterEnum.Equator,9);
        Animal animal1 = new Animal(new Vector2d(2,2), new Genotype(32), new Statistics(0), 10);
        Animal animal2 = new Animal(new Vector2d(2,2), new Genotype(32), new Statistics(0), 20);
        map.placeAnimal(animal1);
        map.placeAnimal(animal2);
        List<Animal> children = map.breedAnimals(new AnimalBuilder(32, GenotypeEnum.Normal, 9, 10,1,5));
        assertTrue(children.size() == 1);
        Animal child = children.get(0);
        assertTrue(child.getPosition().equals(new Vector2d(2,2)));
        assertTrue(child.getEnergy() == 9);
        assertEquals(animal1.getEnergy(), 6);
        assertTrue(animal2.getEnergy() == 15);
        children = map.breedAnimals(new AnimalBuilder(32, GenotypeEnum.Normal, 11, 10,1,5));
        assertTrue(children.size() == 0);
    }

    @Test
    public void getAvailableSpaceTest(){
        WorldMap map = new WorldMapBuilder().build(4, 4, 10,1,4, GrassPlanterEnum.Equator,1);
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
