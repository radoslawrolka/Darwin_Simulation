package agh.ics.oop.model;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ConfigLoader implements Initializable {

    @FXML
    private ChoiceBox<String> loadConfigList;

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        // Populate the ChoiceBox with the names of files in the "config" directory
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

    private String removeFileExtension (String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }
}