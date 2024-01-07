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
        for(Animal animal : animals){
            Vector2d position = animal.getPosition();
            Grass grass = map.getGrassOnPosition(position);
            if (grass != null){
                if (map.animalsOnPlace(position) > 1){
                    List<Animal> animalsOnPlace = map.getAnimalsOnPosition(position);
                    Animal animalToEat = conflict(animalsOnPlace);
                    animalToEat.changeEnergy(GRASS_ENERGY);
                    map.eatGrass(position);
                }
                else{
                    animal.changeEnergy(GRASS_ENERGY);
                    map.eatGrass(position);
                }
            }
        }
    }

    public Animal conflict(List<Animal> animals){
        Animal animaltoEat = null;
        AnimalComparator comparator = new AnimalComparator();
        for (int i=0; i<animals.size()-1; i++) {
            int compare = comparator.compare(animals.get(i), animals.get(i + 1));
            if (compare < 0) {
                animaltoEat = animals.get(i + 1);
            } else if (compare > 0) {
                animaltoEat = animals.get(i);
            } else {
                animaltoEat = animals.get(ThreadLocalRandom.current().nextInt(2));
            }
        }
        return animaltoEat;
    }

    public void breedAnimals(){
        for (Animal animal: animals){
        }
    }
}
