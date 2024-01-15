package agh.ics.oop.gui.utilities;

import agh.ics.oop.model.entities.animal.genotype.GenotypeEnum;
import agh.ics.oop.model.map.GrassPlanter.GrassPlanterEnum;
import javafx.scene.control.ChoiceBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

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
                        Integer.parseInt(values[13]),
                        GenotypeEnum.valueOf(values[14]),
                        String.valueOf(values[15]),
                        Integer.parseInt(values[16])
                );
            } else {
                throw new IOException("CSV file is empty.");
            }
        }
    }

    public void loadConfigList(ChoiceBox<String> loadConfigList) {
        loadConfigList.getItems().clear();
        File configDirectory = Paths.get("src", "main", "resources", "config").toFile();
        if (configDirectory.exists() && configDirectory.isDirectory()) {
            File[] files = configDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    String fileNameWithoutExtension = removeFileExtension(file.getName());
                    loadConfigList.getItems().add(fileNameWithoutExtension);
                }
            }
        }
    }

    public boolean loadLogList (String fileName) {
        File configDirectory = Paths.get("src", "main", "resources", "log").toFile();
        if (configDirectory.exists() && configDirectory.isDirectory()) {
            File[] files = configDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    String fileNameWithoutExtension = removeFileExtension(file.getName());
                    if (fileNameWithoutExtension.equals(fileName)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private String removeFileExtension (String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }
}
