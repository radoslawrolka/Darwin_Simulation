package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.application.Application;

import java.util.LinkedList;
import java.util.List;

public class World {
    public static void main(String[] arg) {
        System.out.println("system wystartowal");

        int width = 10;
        int height = 10;
        int startEnergy = 10;
        int maxEnergy = 100;
        int startAnimalsNumber = 4;
        int grassStartNumber = 4;
        int grassEnergyNumber = 0;
        int grassDailyGrow = 1;
        int grassPlantType = 0;
        int breedEnergy = 5;
        int dailyLostOfEnergy = 1;
        int mutationType = 0;
        int genomLenght = 32;
        WorldMap map = new WorldMapBuilder(width, height, grassEnergyNumber, breedEnergy).build(grassPlantType, grassStartNumber);
        AnimalBuilder animalBuilder;
        if (mutationType == 0){
            animalBuilder = new AnimalBuilder(genomLenght, GenotypeEnum.NORMAL, maxEnergy, startEnergy);
        }
        else {
            animalBuilder = new AnimalBuilder(genomLenght, GenotypeEnum.CRAZY, maxEnergy, startEnergy);
        }
        Simulation simulation = new Simulation(map, animalBuilder, new ConsoleMapDisplay(), grassDailyGrow, dailyLostOfEnergy, startAnimalsNumber);
        simulation.run();
    }
}
