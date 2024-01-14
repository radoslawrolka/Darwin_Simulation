package agh.ics.oop.model;

import agh.ics.oop.Simulation;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MenuOptions implements Initializable{
    @FXML
    private TextField mapHeightTextField;
    @FXML
    private TextField mapWidthTextField;

    @FXML
    private TextField initialPlantCountTextField;
    @FXML
    private TextField energyPerPlantTextField;
    @FXML
    private TextField dailyPlantGrowthTextField;
    @FXML
    private ChoiceBox<String> plantGrowthVariantChoiceBox;

    @FXML
    private TextField initialAnimalCountTextField;
    @FXML
    private TextField initialAnimalEnergyTextField;
    @FXML
    private TextField energyForMatingTextField;
    @FXML
    private TextField breededAnimalEnergyTextField;
    @FXML
    private TextField energyLossPerDayTextField;
    @FXML
    private TextField minMutationsTextField;
    @FXML
    private TextField maxMutationsTextField;
    @FXML
    private TextField genomeLengthTextField;
    @FXML
    private ChoiceBox<String> animalBehaviourVariantChoiceBox;

    @FXML
    private ChoiceBox<String> loadConfigList;
    @FXML
    private TextField saveConfig;

    @FXML
    private ChoiceBox<String> saveLogsToCSV;
    @FXML
    private TextField moveDelayTextField;


    private int mapHeight;
    private int mapWidth;
    private int initialPlantCount;
    private int energyPerPlant;
    private int dailyPlantGrowth;
    private GrassPlanterEnum plantGrowthVariant;
    private int initialAnimalCount;
    private int initialAnimalEnergy;
    private int energyForMating;
    private int breededAnimalEnergy;
    private int energyLossPerDay;
    private int minMutations;
    private int maxMutations;
    private int genomeLength;
    private GenotypeEnum animalBehaviourVariant;
    private String saveLogs;
    private int moveDelay;

    private final ConfigSaver configSaver = new ConfigSaver();
    private final ConfigLoader configLoader = new ConfigLoader();
    private final WorldMapBuilder worldMapBuilder = new WorldMapBuilder();

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        loadConfigList();
    }

    private void loadConfigList () {
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



    private String removeFileExtension (String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }

    @FXML
    private void onClickSaveConfig() {
        String fileName = saveConfig.getText();
        boolean isValidName = false;
        if (fileName != null && !fileName.isEmpty()) {
            isValidName = true;
            for (String item : loadConfigList.getItems()) {
                if (item.equals(fileName)) {
                    isValidName = false;
                    break;
                }
            }
        }
        if (isValidName) {
            if (readData()) {
                configSaver.saveToCsv(fileName,
                        this.mapHeight,
                        this.mapWidth,
                        this.initialPlantCount,
                        this.energyPerPlant,
                        this.dailyPlantGrowth,
                        this.plantGrowthVariant,
                        this.initialAnimalCount,
                        this.initialAnimalEnergy,
                        this.energyForMating,
                        this.breededAnimalEnergy,
                        this.energyLossPerDay,
                        this.minMutations,
                        this.maxMutations,
                        this.genomeLength,
                        this.animalBehaviourVariant,
                        this.saveLogs,
                        this.moveDelay);
                loadConfigList();
            }
        }
        else {
            showInvalidDataAlert("Name is empty, or already exists.");
        }
    }

    private boolean readData() {
        try {
            this.mapHeight = Integer.parseInt(mapHeightTextField.getText());
            this.mapWidth = Integer.parseInt(mapWidthTextField.getText());
            this.initialPlantCount = Integer.parseInt(initialPlantCountTextField.getText());
            this.energyPerPlant = Integer.parseInt(energyPerPlantTextField.getText());
            this.dailyPlantGrowth = Integer.parseInt(dailyPlantGrowthTextField.getText());
            this.plantGrowthVariant = GrassPlanterEnum.valueOf(plantGrowthVariantChoiceBox.getValue());
            this.initialAnimalCount = Integer.parseInt(initialAnimalCountTextField.getText());
            this.initialAnimalEnergy = Integer.parseInt(initialAnimalEnergyTextField.getText());
            this.energyForMating = Integer.parseInt(energyForMatingTextField.getText());
            this.breededAnimalEnergy = Integer.parseInt(breededAnimalEnergyTextField.getText());
            this.energyLossPerDay = Integer.parseInt(energyLossPerDayTextField.getText());
            this.minMutations = Integer.parseInt(minMutationsTextField.getText());
            this.maxMutations = Integer.parseInt(maxMutationsTextField.getText());
            this.genomeLength = Integer.parseInt(genomeLengthTextField.getText());
            this.animalBehaviourVariant = GenotypeEnum.valueOf(animalBehaviourVariantChoiceBox.getValue());
            this.saveLogs = saveLogsToCSV.getValue();
            this.moveDelay = Integer.parseInt(moveDelayTextField.getText());
        } catch (NumberFormatException | NullPointerException e) {
            showInvalidDataAlert("Please enter valid numbers for all settings fields.");
            return false;
        }
        return validateData();
    }

    private void showInvalidDataAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Data");
        alert.setHeaderText("Error in Input");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateData() {
        if (this.mapHeight <= 0 || this.mapWidth <= 0) {
            showInvalidDataAlert("Map height and width must be positive integers.");
            return false;
        }
        if (this.initialPlantCount < 0 || this.energyPerPlant < 0 || this.dailyPlantGrowth < 0) {
            showInvalidDataAlert("Plant count, energy and growth must be non-negative integers.");
            return false;
        }
        if (this.initialAnimalCount < 0 || this.initialAnimalEnergy < 0 || this.energyForMating < 0 || this.breededAnimalEnergy < 0 || this.energyLossPerDay < 0) {
            showInvalidDataAlert("Animal count, energy, energy for mating, breeded animal energy and energy loss per day must be non-negative integers.");
            return false;
        }
        if (this.minMutations < 0 || this.maxMutations < 0 || this.genomeLength < 0) {
            showInvalidDataAlert("Min and max mutations and genome length must be non-negative integers.");
            return false;
        }
        if (this.minMutations > this.maxMutations) {
            showInvalidDataAlert("Min mutations must be less than or equal to max mutations.");
            return false;
        }
        if (this.saveLogs == null) {
            showInvalidDataAlert("Please select whether to save logs.");
            return false;
        }
        if (this.moveDelay < 0) {
            showInvalidDataAlert("Move delay must be non-negative integer.");
            return false;
        }
        return true;
    }

    @FXML
    private void onClickLoadConfig() {
        String fileName = loadConfigList.getValue();
        if (fileName != null && !fileName.isEmpty()) {
            ConfigLoader configLoader = new ConfigLoader();
            ConfigData configData = null;
            try {
                configData = configLoader.loadFromCsv(fileName);
            }
            catch (IOException e) {
                showInvalidDataAlert("Error loading config.");
            }

            if (configData != null) {
                mapHeightTextField.setText(String.valueOf(configData.mapHeight()));
                mapWidthTextField.setText(String.valueOf(configData.mapWidth()));
                initialPlantCountTextField.setText(String.valueOf(configData.initialPlantCount()));
                energyPerPlantTextField.setText(String.valueOf(configData.energyPerPlant()));
                dailyPlantGrowthTextField.setText(String.valueOf(configData.dailyPlantGrowth()));
                plantGrowthVariantChoiceBox.setValue(configData.plantGrowthVariant().toString());
                initialAnimalCountTextField.setText(String.valueOf(configData.initialAnimalCount()));
                initialAnimalEnergyTextField.setText(String.valueOf(configData.initialAnimalEnergy()));
                energyForMatingTextField.setText(String.valueOf(configData.energyForMating()));
                breededAnimalEnergyTextField.setText(String.valueOf(configData.breededAnimalEnergy()));
                energyLossPerDayTextField.setText(String.valueOf(configData.energyLossPerDay()));
                minMutationsTextField.setText(String.valueOf(configData.minMutations()));
                maxMutationsTextField.setText(String.valueOf(configData.maxMutations()));
                genomeLengthTextField.setText(String.valueOf(configData.genomeLength()));
                animalBehaviourVariantChoiceBox.setValue(configData.animalBehaviourVariant().toString());
                saveLogsToCSV.setValue(configData.saveLogs());
                moveDelayTextField.setText(String.valueOf(configData.moveDelay()));
            }
        }
        else {
            showInvalidDataAlert("Please select a config to load.");
        }
    }

    private SimulationPresenter openSimulationWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("simulation.fxml"));
            Parent root = loader.load();

            SimulationPresenter simulationController = loader.getController();

            Stage simulationStage = new Stage();
            simulationStage.setTitle("Simulation Window");
            simulationStage.setScene(new Scene(root));
            simulationStage.show();
            simulationStage.setOnCloseRequest(event -> onClose(simulationController, simulationStage));


            return simulationController;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void onClose(SimulationPresenter simulationController, Stage simulationStage) {
        if (simulationController != null) {
            simulationController.stopSimulation();
        }
        simulationStage.close();
    }


    @FXML
    private void onClickPlay() {
        if (readData()) {
            WorldMap map = worldMapBuilder.build(this.mapWidth, this.mapHeight, this.initialPlantCount, this.energyPerPlant, this.dailyPlantGrowth, this.plantGrowthVariant, this.energyForMating);
            AnimalBuilder animalBuilder = new AnimalBuilder(this.genomeLength, this.animalBehaviourVariant, this.breededAnimalEnergy, this.initialAnimalEnergy, this.minMutations, this.maxMutations);
            Simulation simulation = new Simulation(map, animalBuilder, this.dailyPlantGrowth, this.energyLossPerDay, this.initialAnimalCount, this.saveLogs, this.moveDelay);
            SimulationPresenter simulationController = openSimulationWindow();
            simulationController.setDataFromMenu(map, simulation);
        }
    }
}
