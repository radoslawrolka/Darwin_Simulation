package agh.ics.oop.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvDataWriter {
    private final String CSV_FILE_PATH;

    public CsvDataWriter(String filePath) {
        this.CSV_FILE_PATH = "src/main/resources/log/" + filePath + ".csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            // Write the header
            String CSV_HEADER = "Day,AnimalCount,PlantCount,AvgAnimalEnergy,AvgAnimalLifespan,AvgChildrenCount,AvgDescendantsCount,FreeSpace,OccupiedSpace";
            writer.write(CSV_HEADER);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendData(int day, int animalCount, int plantCount, int avgAnimalEnergy,
                                  int avgAnimalLifespan, int avgChildrenCount, int avgDescendantsCount,
                                  int freeSpace, int occupiedSpace) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true))) {

            // Append data to the CSV file
            writer.write(String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d",
                    day, animalCount, plantCount, avgAnimalEnergy, avgAnimalLifespan,
                    avgChildrenCount, avgDescendantsCount, freeSpace, occupiedSpace));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
