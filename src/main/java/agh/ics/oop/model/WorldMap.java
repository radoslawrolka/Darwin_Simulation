package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public class WorldMap{
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private List<MapChangeListener> observers = new ArrayList<>();
    private final HashMap<Vector2d,Grass> grasses = new HashMap<>();

    private final Vector2d mapSize;
    private int grassDailyGrowth;
    private GrassGrow field;
    private Borders borders;

    public WorldMap(int grassDailyGrowth, Vector2d mapSize, GrassGrow field, Borders borders, int grassNumber) {
        this.mapSize = mapSize;
        this.grassDailyGrowth = grassDailyGrowth;
        this.field = field;
        this.borders = borders;
        field.plantGrass(grassNumber, grasses);
    }

    public void setField(GrassGrow field){
        this.field = field;
    }

    public void setBorders(Borders borders){
        this.borders = borders;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public boolean place(WorldElement object) {
        try {
            if (canMoveTo(object.getPosition())) {
                if (object instanceof Animal) {
                    animals.put(object.getPosition(), (Animal) object);
                    notifyObservers("Dodano zwierze na pozycji " + object.getPosition());
                }
                return true;
            }
            else {
                throw new PositionAlreadyOccupiedException(object.getPosition());
            }

        } catch (PositionAlreadyOccupiedException e) {
            System.err.println("Błąd: " + e.getMessage());
            return false;
        }
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public void move(WorldElement object, MapDirection direction) {
        Vector2d oldPosition = object.getPosition();
        if (object instanceof Animal) {
            MapDirection oldOrientation =  ((Animal) object).getOrientation();
            ((Animal) object).move(this);
            Vector2d newPosition = object.getPosition();
            if (oldPosition != newPosition) {
                animals.remove(oldPosition);
                animals.put(newPosition, (Animal) object);
                notifyObservers("Zwierze przemiescilo sie z " + oldPosition + " na " + newPosition);
            }
            else if (oldOrientation != ((Animal) object).getOrientation()) {
                notifyObservers("Zwierze zmienilo orientacje z " + oldOrientation + " na " + ((Animal) object).getOrientation() + " na pozycji " + object.getPosition());
            }
        }
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> elements = new ArrayList<>(animals.values());
        return elements;
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
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    public String getId() {
        return Integer.toString(this.hashCode());
    }
}
