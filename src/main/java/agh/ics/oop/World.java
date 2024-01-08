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
        int moveEnergy = 1;
        int plantEnergy = 1;
        int grassNumber = 10;
        int maxEnergy = 100;
        int startAnimalsNumber = 10;
        int grassStartNumber = 10;
        int grassEnergyNumber = 10;
        int grassDailyGrow = 1;
        int grassPlantType = 0;
        int breedEnergy = 10;
        int dailyLostOfEnergy = 1;
        int mutationType = 0;
        int genomLenght = 32;
        int lowerY = 0;
        int upperY = 10;
        Simulation simulation = new Simulation(width, height, grassStartNumber, grassEnergyNumber, grassDailyGrow, grassPlantType, startAnimalsNumber, startEnergy, maxEnergy, breedEnergy, dailyLostOfEnergy, mutationType, genomLenght, lowerY, upperY);
        simulation.run();
    }
}
