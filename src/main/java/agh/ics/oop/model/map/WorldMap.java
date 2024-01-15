package agh.ics.oop.model.map;

import agh.ics.oop.model.entities.animal.Animal;
import agh.ics.oop.model.entities.animal.AnimalBuilder;
import agh.ics.oop.model.entities.grass.Grass;
import agh.ics.oop.model.map.Borders.Borders;
import agh.ics.oop.model.map.GrassPlanter.GrassPlanter;
import agh.ics.oop.model.map.utilities.AnimalComparator;
import agh.ics.oop.model.map.utilities.Boundary;
import agh.ics.oop.model.map.utilities.Vector2d;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public class WorldMap{
    public final Map<Vector2d, TreeSet<Animal>> animals = new HashMap<>();
    private final Vector2d mapSize;
    private GrassPlanter field;
    private Borders<Vector2d> borders;
    private final int GRASS_ENERGY;
    private final int BREED_ENERGY;

    private final AnimalComparator comparator = new AnimalComparator();

    protected WorldMap(Vector2d mapSize, int grassEnergy, int breedEnergy){
        this.mapSize = mapSize;
        this.GRASS_ENERGY = grassEnergy;
        this.BREED_ENERGY = breedEnergy;

    }

    protected void addPlanter(GrassPlanter planter, int grassNumber){ // Dodaje plantera i sadzi trawe
        this.field = planter;
        field.plantGrass(grassNumber);
    }

    protected void addBorders(Borders<Vector2d> borders){ // Dodaje granice
        this.borders = borders;
    }

    public void placeAnimal(Animal animal) {  // Umieszcza zwierze na mapie (zwierze musi mieć juz przypisaną pozycje)
        Vector2d position = animal.getPosition();
        if (!animals.containsKey(position)){
            TreeSet<Animal> animalsSet = new TreeSet<>(comparator.reversed());
            animalsSet.add(animal);
            animals.put(position, animalsSet);
        }
        else {
            animals.get(position).add(animal);
        }
    }

    public void moveAnimal(Animal animal) { // Przesuwa zwierze na mapie
        this.removeAnimal(animal);
        animal.move(borders);
        placeAnimal(animal);
    }

    public void removeAnimal(Animal animal) { // Usuwa zwierze z mapy
        Vector2d position = animal.getPosition();
        if (getAnimalsOnPosition(position).size() == 1) {
            animals.remove(position);
        } else {
            animals.get(position).remove(animal);
        }
    }

    public Vector2d getMapSize(){
        return mapSize;
    } // Zwraca rozmiar mapy

    public void eatGrasses(){ // Dla wszystkich pozycji zwierząt znajdujących się na mapie najsilniejsze zwierze je trawe
        for(Vector2d position : animals.keySet()){
            Grass grass = this.getGrassOnPosition(position);
            if (grass != null){
                Animal animalToEat = animals.get(position).first();
                animals.get(position).remove(animalToEat);
                animalToEat.changeEnergy(GRASS_ENERGY);
                animalToEat.getStats().addPlant();
                field.eatGrass(position);
                animals.get(position).add(animalToEat);
            }
        }
    }

    public List<Animal> breedAnimals(AnimalBuilder animalBuilder){ // Dla wszystkich pozycji zwierząt znajdujących się na mapie jeśli jest więcej niż jedno zwierze na pozycji to je rozmnaza z drugim najsilniejszym
        List<Animal> children = new ArrayList<>();                 // Zwraca listę dzieci
        for (Vector2d position : animals.keySet()){
            if (animals.get(position).size() <= 1){
                continue;
            }
            Animal animal1 = animals.get(position).first();
            animals.get(position).remove(animal1);
            Animal animal2 = animals.get(position).first();
            animals.get(position).remove(animal2);
            if (animal1.getEnergy() >= BREED_ENERGY && animal2.getEnergy() >= BREED_ENERGY){
                Animal child = animalBuilder.build(animal1, animal2);
                placeAnimal(child);
                children.add(child);
                int breed_energy1 = BREED_ENERGY/2;
                animal2.changeEnergy(-breed_energy1);
                animal1.changeEnergy(-(BREED_ENERGY - breed_energy1));
            }
            animals.get(position).add(animal1);
            animals.get(position).add(animal2);
        }
        return children;
    }

    public TreeSet<Animal> getAnimalsOnPosition(Vector2d position){
        return animals.get(position);
    } //Zwraca listę zwierząt na danej pozycji

    public int getGrassNumber(){ // Zwraca liczbę traw na mapie
        return field.getGrassNumber();
    }

    public int getAvailableSpace(){ // Zwraca liczbę wolnych miejsc na mapie (zajęte liczy tylko przez zwierzęta, bo nie pisało doładnie)
        return mapSize.getX() * mapSize.getY() - animals.size();
    }

    public Grass getGrassOnPosition(Vector2d position){ // Zwraca trawe na danej pozycji (potrzebne do MapVisualizera)
        return field.grassAtPosition(position);
    }
    public void plantGrass(int grassNumber){ // Sadzi podaną liczbę traw na mapie
        field.plantGrass(grassNumber);
    }

    public Boundary getCurrentBounds(){
        return new Boundary(new Vector2d(0,0), mapSize);
    } // Zwraca aktualne granice mapy ( potrzebne do MapVisualizera)

    @Override
    public String toString() {
        Boundary bounds = getCurrentBounds();
        return new MapVisualizer(this).draw(bounds.lowerLeft(), bounds.upperRight());
    }

    public String getId() {
        return Integer.toString(this.hashCode());
    }

    public List<Vector2d> getPrefferredPositions() {
        return field.getPrefferredPositions();
    }
}
