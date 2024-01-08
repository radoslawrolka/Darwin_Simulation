package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation{

    private final WorldMap map;
    private final int GRASS_DAILY_GROW;
    private final int DAILY_LOST_OF_ENERGY;

    private final MapChangeListener observer;

    private List<Animal> animals = new ArrayList<>();
    private AnimalBuilder animalBuilder;
    private List<Animal> deadAnimals = new LinkedList<>();

    public Simulation(WorldMap map, AnimalBuilder animalBuilder, MapChangeListener observer, int grassDailyGrow, int dailyLostOfEnergy, int startAnimalsNumber){
        GRASS_DAILY_GROW = grassDailyGrow;
        DAILY_LOST_OF_ENERGY = dailyLostOfEnergy;
        this.animalBuilder = animalBuilder;
        this.map = map;
        for(int k=0; k<startAnimalsNumber; k++){ //
            int x = ThreadLocalRandom.current().nextInt(0, map.getMapSize().getX());
            int y = ThreadLocalRandom.current().nextInt(0, map.getMapSize().getY());
            Vector2d position = new Vector2d(x,y);
            Animal animal = animalBuilder.spawn(position);
            animals.add(animal);
            System.out.println("Animal " + k + " position: " + position);
            map.placeAnimal(animal);
        }
        this.observer = new ConsoleMapDisplay();

    }

    public void run(){
        while (!animals.isEmpty()){
            dailyTask();
            observer.mapChanged(map,"message");
        }
    }

    public void dailyTask(){
        animalBuilder.incrementDay();
        removeDead();
        System.out.println("Animals number: " + animals.size());
        moveAnimals();
        map.eatGrasses();
        breedAnimals();
        map.plantGrass(GRASS_DAILY_GROW);
        dailyEnergyLost();
    }

    public void dailyEnergyLost(){
        for(int i=0; i<animals.size(); i++){
            animals.get(i).changeEnergy(-DAILY_LOST_OF_ENERGY);
        }
    }

    public void removeDead(){
        for (int i=0; i<animals.size(); i++){
            if (animals.get(i).getEnergy() <= 0){
                map.removeAnimal(animals.get(i));
                deadAnimals.add(animals.get(i));
                animals.remove(i);
                i--;
            }
        }
    }
    public void moveAnimals(){
        for(Animal animal : animals){
            map.moveAnimal(animal);
        }
    }

    public void breedAnimals(){
        List<Animal> children = map.breedAnimals(animalBuilder);
        animals.addAll(children);
    }

    public int getAnimalsNumber(){ // Funkcje generujące wymagane statystyki
        return animals.size();
    }

    public int getGrassNumber(){
        return map.getGrassNumber();
    }

    public int getAvailableSpace(){
        return map.getAvailableSpace();
    }

    public int getAnimalsAverageEnergy(){
        int sum = 0;
        for (Animal animal : animals){
            sum += animal.getEnergy();
        }
        return sum/animals.size();
    }

    public int getAverageLifeLength(){
        int sum = 0;
        for (Animal animal : deadAnimals){
            sum += animal.getStats().getDayOfDeath() - animal.getStats().getDayOfBirth();
        }
        return sum/deadAnimals.size();
    }

    public int getAverageChildrenNumber(){
        int sum = 0;
        for (Animal animal : deadAnimals){
            sum += animal.getStats().getChildren();
        }
        for (Animal animal : animals){
            sum += animal.getStats().getChildren();
        }
        return sum/(deadAnimals.size()+animals.size());
    }
}
