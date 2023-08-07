package agh.ics.oop;

import agh.ics.oop.elements.Animal;
import agh.ics.oop.gui.App;
import agh.ics.oop.map.World;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements Runnable{
    private final App app;
    private final int MoveDelay;
    private final World map;
    private List<Animal> animals = new ArrayList<>();
    private final int dayCost;
    private int day = 0;

    public SimulationEngine(App app, World map, int moveDelay, int dayCost) {
        this.app = app;
        this.map = map;
        this.MoveDelay = moveDelay;
        this.dayCost = dayCost;
    }

    @Override
    public void run() {
        map.draw();
        while (animals.size() > 0) {
            removeCorpses();
            moveAnimals();
            map.eatPlants();
            map.addDay();
            this.day++;
            System.out.println(" Animals: " + animals.size() );
            map.reproduce();
            System.out.println(" Animals: " + animals.size() );
            map.growPlants();
            addDay();
            map.draw();

            try {
                Thread.sleep(MoveDelay);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeCorpses() {
        List<Animal> newList = new ArrayList<>();
        for (Animal animal: animals) {
            if (animal.getEnergy() > 0) {
                newList.add(animal);
            }
            else {
                map.removeAnimal(animal);
                animal.setDeathDay(day);
            }
        }
        animals = newList;
    }

    public void moveAnimals() {
        for (Animal animal: animals) {
            animal.move();
        }
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void addDay() {
        for (Animal animal: animals) {
            animal.addAge();
            animal.removeEnergy(dayCost);
        }
    }
}
