package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation implements Runnable {
    private final WorldMap map;
    private final int MOVE_DELAY;
    private final int GRASS_DAILY_GROW;
    private final int DAILY_LOST_OF_ENERGY;
    private final Object pauseLock = new Object();

    private MapChangeListener observer;
    private boolean breakSimulation = false;
    private boolean isPaused = false;
    private int day = 0;

    private final List<Animal> animals = new ArrayList<>();
    private final List<Animal> deadAnimals = new LinkedList<>();
    private final AnimalBuilder animalBuilder;

    public Simulation(WorldMap map, AnimalBuilder animalBuilder, int grassDailyGrow, int dailyLostOfEnergy, int startAnimalsNumber, int moveDelay){
        GRASS_DAILY_GROW = grassDailyGrow;
        DAILY_LOST_OF_ENERGY = dailyLostOfEnergy;
        MOVE_DELAY = moveDelay;
        this.animalBuilder = animalBuilder;
        this.map = map;
        spawnAnimals(startAnimalsNumber);
    }

    private void spawnAnimals(int startAnimalsNumber) {
        for(int k=0; k<startAnimalsNumber; k++){
            int x = ThreadLocalRandom.current().nextInt(0, map.getMapSize().getX());
            int y = ThreadLocalRandom.current().nextInt(0, map.getMapSize().getY());
            Vector2d position = new Vector2d(x,y);
            Animal animal = animalBuilder.spawn(position);
            animals.add(animal);
            map.placeAnimal(animal);
        }
    }

    public void run(){
        while (!animals.isEmpty()) {
            if (breakSimulation) break;
            synchronized (pauseLock) {
                while (isPaused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }

            try {
                Thread.sleep(MOVE_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dailyTask();
            observer.mapChanged(map);
        }
        observer.mapChanged(map);
    }

    public void pauseSimulation() {
        isPaused = true;
    }

    public void resumeSimulation() {
        isPaused = false;
        synchronized (pauseLock) {
            pauseLock.notify();
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void breakSimulation(){
        breakSimulation = true;
    }

    public void setObserver(MapChangeListener observer){
        this.observer = observer;
        observer.mapChanged(map);
    }

    public void dailyTask(){
        animalBuilder.incrementDay();
        day++;
        removeDead();
        moveAnimals();
        map.eatGrasses();
        breedAnimals();
        map.plantGrass(GRASS_DAILY_GROW);
        dailyEnergyLost();
    }

    public int getDay(){
        return day;
    }

    public void dailyEnergyLost(){
        for (Animal animal : animals) {
            animal.changeEnergy(-DAILY_LOST_OF_ENERGY);
        }
    }

    public void removeDead(){
        for (int i=0; i<animals.size(); i++){
            if (animals.get(i).getEnergy() <= 0){
                animals.get(i).getStats().setDayOfDeath(day);
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
        if (animals.isEmpty()) return 0;
        for (Animal animal : animals){
            sum += animal.getEnergy();
        }
        return sum/animals.size();
    }

    public int maxEnergy(){
        int max = 0;
        for (Animal animal : animals){
            if (animal.getEnergy() > max) max = animal.getEnergy();
        }
        return max;
    }

    public String getBestGenotype() {
        HashMap<Integer[], Integer> temp = new HashMap<>();
        for (Animal animal : animals) {
            Integer[] genotype = animal.getGenotype().getGenes();
            if (temp.containsKey(genotype)) {
                temp.put(genotype, temp.get(genotype) + 1);
            } else {
                temp.put(genotype, 1);
            }
        }
        int max = 0;
        Integer[] bestGenotype = null;
        for (Integer[] genotype : temp.keySet()) {
            if (temp.get(genotype) > max) {
                max = temp.get(genotype);
                bestGenotype = genotype;
            }
        }
        return Arrays.toString(bestGenotype);
    }

    public int getAverageLifeLength(){
        int sum = 0;
        if (deadAnimals.isEmpty()) return 0;
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

    public int getAverageDescendantNumber(){
        int sum = 0;
        for (Animal animal : deadAnimals){
            sum += animal.getStats().getDescendants();
        }
        for (Animal animal : animals){
            sum += animal.getStats().getDescendants();
        }
        return sum/(deadAnimals.size()+animals.size());
    }
}
