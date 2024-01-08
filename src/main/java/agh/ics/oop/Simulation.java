package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation{

    private final WorldMap map;
    private final int GRASS_ENERGY;
    private final int GRASS_DAILY_GROW;
    private final int BREED_ENERGY;
    private final int DAILY_LOST_OF_ENERGY;

    private final MapChangeListener observer;

    private List<Animal> animals = new ArrayList<>();
    private AnimalBuilder animalBuilder;

    public Simulation(int width, int height, int grassStartNumber, int grassEnergyNumber, int grassDailyGrow, int grassPlantType, int startAnimalsNumber, int startEnergy, int maxEnergy, int breedEnergy, int dailyLostOfEnergy, int mutationType, int genomLenght, int lowerY, int upperY){
        map = new WorldMapBuilder(width, height, grassStartNumber).build(grassPlantType, lowerY, upperY);
        GRASS_ENERGY = grassEnergyNumber;
        GRASS_DAILY_GROW = grassDailyGrow;
        BREED_ENERGY = breedEnergy;
        DAILY_LOST_OF_ENERGY = dailyLostOfEnergy;
        GenotypeEnum genotype;
        if (mutationType == 0) {
            genotype = GenotypeEnum.NORMAL;
        } else {
            genotype = GenotypeEnum.CRAZY;
        }
        animalBuilder = new AnimalBuilder(genomLenght, genotype, maxEnergy, startEnergy);
        List<Vector2d> listOfPositions = generateListOfPositions(map.getMapSize());
        for(int k=0; k<startAnimalsNumber; k++){
            Vector2d position = generateRandomPosition(listOfPositions);
            Animal animal = animalBuilder.spawn(position);
            animals.add(animal);
            map.placeAnimal(animal);
        }
        observer = new ConsoleMapDisplay();

    }

    public void run(){
        while (animals.size() > 0){
            dailyTask();
            observer.mapChanged(map,"message");
        }
    }

    public List<Vector2d> generateListOfPositions(Vector2d mapSize){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<Vector2d> listOfPositions = new ArrayList<>();
        for(int i=0; i<mapSize.getX(); i++){
            for(int j=0; j<mapSize.getY(); j++){
                listOfPositions.add(new Vector2d(i,j));
            }
        }
        return listOfPositions;
    }

    public Vector2d generateRandomPosition(List<Vector2d> listOfPositions){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int randomIndex = random.nextInt(listOfPositions.size());
        Vector2d randomPosition = listOfPositions.get(randomIndex);
        listOfPositions.remove(randomIndex);
        return randomPosition;
    }

    public void dailyTask(){
        animalBuilder.incrementDay();
        dailyEnergyLost();
        moveAnimals();
        eatGrasses();
        breedAnimals();
        map.plantGrass(GRASS_DAILY_GROW);
    }

    public void dailyEnergyLost(){
        for(int i=0; i<animals.size(); i++){
            animals.get(i).changeEnergy(-DAILY_LOST_OF_ENERGY);
            if (animals.get(i).getEnergy() <= 0){
                map.removeAnimal(animals.get(i));
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

    public void eatGrasses(){
        Map<Vector2d,List<Animal>> animalsOnMap = map.getMap();
        for(Vector2d position : animalsOnMap.keySet()){
            Grass grass = map.getGrassOnPosition(position);
            if (grass != null){
                if (map.animalsOnPlace(position) > 1){
                    List<Animal> animalsOnPlace = new ArrayList<>(animalsOnMap.get(position));
                    Animal animalToEat = conflict(animalsOnPlace);
                    animalToEat.changeEnergy(GRASS_ENERGY);
                    map.eatGrass(position);
                }
                else{
                    Animal animal = animalsOnMap.get(position).get(0);
                    animal.changeEnergy(GRASS_ENERGY);
                    map.eatGrass(position);
                }
            }
        }
    }

    public Animal conflict(List<Animal> animals){
        animals.sort(new AnimalComparator());
        return animals.get(0);
    }

    public void breedAnimals(){
        Map<Vector2d,List<Animal>> animalsOnMap = map.getMap();
        Animal animal1;
        Animal animal2;
        for (Vector2d position : animalsOnMap.keySet()){
            if (animalsOnMap.get(position).size() <= 1){
                continue;
            }
            List<Animal> copyList = new ArrayList<>(animalsOnMap.get(position));
            animal1 = conflict(copyList);
            copyList.remove(animal1);
            animal2 = conflict(copyList);
            if (animal1.getEnergy() >= BREED_ENERGY && animal2.getEnergy() >= BREED_ENERGY){
                Animal child = animalBuilder.build(animal1, animal2);
                animals.add(child);
                map.placeAnimal(child);
            }
        }
    }
}
