package agh.ics.oop.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigLoader {

    public ConfigData loadFromCsv(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/config/" + path + ".csv"))) {
            reader.readLine();
            String line = reader.readLine();
            if (line != null) {
                String[] values = line.split(",");
                return new ConfigData(
                        Integer.parseInt(values[0]),
                        Integer.parseInt(values[1]),
                        Integer.parseInt(values[2]),
                        Integer.parseInt(values[3]),
                        Integer.parseInt(values[4]),
                        GrassPlanterEnum.valueOf(values[5]),
                        Integer.parseInt(values[6]),
                        Integer.parseInt(values[7]),
                        Integer.parseInt(values[8]),
                        Integer.parseInt(values[9]),
                        Integer.parseInt(values[10]),
                        Integer.parseInt(values[11]),
                        Integer.parseInt(values[12]),
                        GenotypeEnum.valueOf(values[13])
                );
            } else {
                throw new IOException("CSV file is empty.");
            }
        }
    }
}
