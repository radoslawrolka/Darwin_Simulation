package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public class WorldMap{
    private final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private List<MapChangeListener> observers = new ArrayList<>();
    private final Vector2d mapSize;
    private int grassDailyGrowth;
    private GrassPlanter field;
    private Borders borders;

    public WorldMap(int grassDailyGrowth, Vector2d mapSize, GrassPlanter field, Borders borders, int grassNumber) {
        this.mapSize = mapSize;
        this.grassDailyGrowth = grassDailyGrowth;
        this.field = field;
        this.borders = borders;
        field.plantGrass(grassNumber);
    }

    public void placeAnimal(Animal animal) {
        if (animalsOnPlace(animal.getPosition()) == 0) {
            List<Animal> animalsList = new ArrayList<>();
            animalsList.add(animal);
            animals.put(animal.getPosition(), animalsList);
        }
        else {
            animals.get(animal.getPosition()).add(animal);
        }
    }

    public void moveAnimal(Animal animal) {
        Vector2d oldPosition = animal.getPosition();
        animal.move(borders);
        Vector2d newPosition = animal.getPosition();
        if (animalsOnPlace(oldPosition) == 1) {
            animals.remove(oldPosition);
        } else {
            animals.get(oldPosition).remove(animal);
        }
        List<Animal> animalsList = animals.getOrDefault(newPosition, new ArrayList<>());
        animalsList.add(animal);
        animals.put(newPosition, animalsList);

    }

    public void removeAnimal(Animal animal) {
        Vector2d position = animal.getPosition();
        if (animalsOnPlace(position) == 1) {
            animals.remove(position);
        } else {
            animals.get(position).remove(animal);
        }
    }

    public int animalsOnPlace(Vector2d position) {
        if (animals.get(position) != null) {
            return animals.get(position).size();
        } else {
            return 0;
        }
    }

    public boolean availablePlace(Vector2d position) {
        return (position.follows(new Vector2d(0,0)) && position.precedes(mapSize));
    }

    public Animal getAnimalOnPosition(Vector2d position){
        return animals.get(position).get(0);
    }

    public Grass getGrassOnPosition(Vector2d position){
        return field.grassAtPosition(position);
    }

    public Boundary getCurrentBounds(){
        return new Boundary(new Vector2d(0,0), mapSize);
    }

    @Override
    public String toString() {
        Boundary bounds = getCurrentBounds();
        return new MapVisualizer(this).draw(bounds.lowerLeft(), bounds.upperRight());
    }

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (MapChangeListener observer : observers){
            observer.mapChanged(this, message);
        }
    }

    public String getId() {
        return Integer.toString(this.hashCode());
    }
}
