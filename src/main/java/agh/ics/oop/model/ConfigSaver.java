package agh.ics.oop.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigSaver {

    public void saveToCsv(
            String FileName,
            int mapHeight,
            int mapWidth,
            int initialPlantCount,
            int energyPerPlant,
            int dailyPlantGrowth,
            GrassPlanterEnum plantGrowthVariant,
            int initialAnimalCount,
            int initialAnimalEnergy,
            int energyForMating,
            int breededAnimalEnergy,
            int minMutations,
            int maxMutations,
            int genomeLength,
            GenotypeEnum animalBehaviourVariant) {

        String csvFileName = "src/main/resources/config/" + FileName + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFileName))) {
            // Writing the header
            writer.write("mapHeight,mapWidth,initialPlantCount,energyPerPlant,dailyPlantGrowth," +
                    "plantGrowthVariant,initialAnimalCount,initialAnimalEnergy,energyForMating," +
                    "breededAnimalEnergy,minMutations,maxMutations,genomeLength,animalBehaviourVariant\n");

            // Writing the data
            writer.write(String.format("%d,%d,%d,%d,%d,%s,%d,%d,%d,%d,%d,%d,%d,%s\n",
                    mapHeight, mapWidth, initialPlantCount, energyPerPlant, dailyPlantGrowth,
                    plantGrowthVariant.toString(), initialAnimalCount, initialAnimalEnergy,
                    energyForMating, breededAnimalEnergy, minMutations, maxMutations, genomeLength,
                    animalBehaviourVariant.toString()));
        } catch (IOException e) {
            System.err.println("Error saving CSV file: " + e.getMessage());
        }
    }
}
