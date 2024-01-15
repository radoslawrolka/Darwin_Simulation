package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class SimulationPresenter implements MapChangeListener {
    @FXML
    private GridPane mapGrid;
    @FXML
    private LineChart<Number, Number> ChartAnimalsPlants;
    @FXML
    private LineChart<Number, Number> ChartEnergyLife;

    @FXML
    private Label dayLabel;
    @FXML
    private Label animalCountLabel;
    @FXML
    private Label plantCountLabel;
    @FXML
    private Label averageAnimalEnergyLabel;
    @FXML
    private Label averageAnimalLifespanLabel;
    @FXML
    private Label averageChildrenCountLabel;
    @FXML
    private Label averageDescendantsCountLabel;
    @FXML
    private Label freeSpaceLabel;
    @FXML
    private Label occupiedSpaceLabel;

    @FXML
    private Label followedAnimalEnergyLabel;
    @FXML
    private Label followedAnimalBirthDayLabel;
    @FXML
    private Label followedAnimalDeathDayLabel;
    @FXML
    private Label followedAnimalChildrenCountLabel;
    @FXML
    private Label followedAnimalDescendantsCountLabel;
    @FXML
    private Label followedAnimalGenotypeLabel;
    @FXML
    private Label followedAnimalActiveGeneIndexLabel;
    @FXML
    private Label followedAnimalPlantsEatenLabel;

    private final XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series4 = new XYChart.Series<>();

    private boolean preferred = false;
    private Simulation simulation;
    private WorldMap map;
    private int xMin = 1;
    private int yMin = 1;
    private int xMax;
    private int yMax;
    private int mapWidth;
    private int mapHeight;
    private int width;
    private int height;
    private Thread simulationThread;
    private boolean isRunning = false;
    private CsvDataWriter csvDataWriter;
    private final GuiElement guiElement = new GuiElement();
    private Animal followedAnimal = null;

    @FXML
    private void initialize() {
        series1.setName("Animals");
        series2.setName("Grass");
        series3.setName("Avg Animal Energy");
        series4.setName("Avg Animal Lifespan");
        ChartAnimalsPlants.getData().addAll(series1,series2);
        ChartEnergyLife.getData().addAll(series3,series4);
        series1.getNode().setStyle("-fx-stroke: orange;");
        series2.getNode().setStyle("-fx-stroke: green;");
        series3.getNode().setStyle("-fx-stroke: orange;");
        series4.getNode().setStyle("-fx-stroke: green;");
    }

    public void setDataFromMenu(WorldMap map, Simulation simulation, String logName) {
        this.map = map;
        this.simulation = simulation;
        simulation.setObserver(this);
        simulationThread = new Thread(simulation);
        xMin = 1;
        yMin = 1;
        xMax = map.getCurrentBounds().upperRight().getX();
        yMax = map.getCurrentBounds().upperRight().getY();
        mapWidth = xMax;
        mapHeight = yMax;
        width = 500/(mapWidth+1);
        height = 500/(mapHeight+1);
        csvDataWriter = (logName != null) ? new CsvDataWriter(logName) : null;

    }

    public void xyLabel(){
        mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
        mapGrid.getRowConstraints().add(new RowConstraints(height));
        Label label = new Label("y/x");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
    }

    public void columnsFunction(){
        for (int i = 0; i < mapWidth; i++){
            Label label = new Label(Integer.toString(xMin+i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
            mapGrid.add(label, i+1, 0);
        }
    }

    public void rowsFunction(){
        for (int i = 0; i < mapHeight; i++){
            Label label = new Label(Integer.toString(yMax-i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.getRowConstraints().add(new RowConstraints(height));
            mapGrid.add(label, 0, i+1);
        }
    }

    public void addElements() {
        int maxEnergy = simulation.maxEnergy();
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMax; j >= yMin; j--) {
                Vector2d pos = new Vector2d(i,j);
                if (preferred) {
                    if (map.getPrefferredPositions().contains(pos)) {
                        mapGrid.add(guiElement.drawGrassPrefferable(width, height), i - xMin + 1, yMax - j + 1);
                    }
                }
                if(map.getGrassOnPosition(pos) != null) {
                    mapGrid.add(guiElement.drawGrass(width, height), i - xMin + 1, yMax - j + 1);
                }
                if(map.getAnimalsOnPosition(pos) != null) {
                    if (map.getAnimalsOnPosition(pos).size() > 0) {
                        Circle circle = guiElement.drawAnimal((double) map.getAnimalsOnPosition(pos).first().getEnergy() /maxEnergy, width, height);
                        mapGrid.add(circle,i-xMin+1,yMax-j+1);
                        circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                            followedAnimal = map.getAnimalsOnPosition(pos).first();
                            followAnimal();
                        });
                    }
                }
                mapGrid.setHalignment(mapGrid.getChildren().get(mapGrid.getChildren().size()-1), HPos.CENTER);
            }
        }
    }

    private void updateStats() {
        dayLabel.setText(Integer.toString(simulation.getDay()));
        animalCountLabel.setText(Integer.toString(simulation.getAnimalsNumber()));
        plantCountLabel.setText(Integer.toString(simulation.getGrassNumber()));
        averageAnimalEnergyLabel.setText(Integer.toString(simulation.getAnimalsAverageEnergy()));
        averageAnimalLifespanLabel.setText(Integer.toString(simulation.getAverageLifeLength()));
        averageChildrenCountLabel.setText(Integer.toString(simulation.getAverageChildrenNumber()));
        averageDescendantsCountLabel.setText(Integer.toString(simulation.getAverageDescendantNumber()));
        freeSpaceLabel.setText(Integer.toString(simulation.getAvailableSpace()));
        occupiedSpaceLabel.setText(Integer.toString(mapHeight*mapWidth - simulation.getAvailableSpace()));
        series1.getData().add(new XYChart.Data<>(simulation.getDay(), simulation.getAnimalsNumber()));
        series2.getData().add(new XYChart.Data<>(simulation.getDay(), simulation.getGrassNumber()));
        series3.getData().add(new XYChart.Data<>(simulation.getDay(), simulation.getAnimalsAverageEnergy()));
        series4.getData().add(new XYChart.Data<>(simulation.getDay(), simulation.getAverageLifeLength()));
        saveLog();
    }

    private void followAnimal() {
        if (followedAnimal != null) {
            AnimalData animalData = followedAnimal.getAnimalData();
            followedAnimalEnergyLabel.setText(Integer.toString(animalData.energy()));
            followedAnimalBirthDayLabel.setText(Integer.toString(animalData.dayOfBirth()));
            followedAnimalDeathDayLabel.setText(Integer.toString(animalData.dayOfDeath()));
            followedAnimalChildrenCountLabel.setText(Integer.toString(animalData.children()));
            followedAnimalDescendantsCountLabel.setText(Integer.toString(animalData.descendants()));
            followedAnimalGenotypeLabel.setText(animalData.genotype());
            followedAnimalActiveGeneIndexLabel.setText(Integer.toString(animalData.activeGeneIndex()));
            followedAnimalPlantsEatenLabel.setText(Integer.toString(animalData.plantsEaten()));
        }
    }

    private void saveLog() {
        if (csvDataWriter != null) {
            csvDataWriter.appendData(simulation.getDay(), simulation.getAnimalsNumber(), simulation.getGrassNumber(),
                    simulation.getAnimalsAverageEnergy(), simulation.getAverageLifeLength(),
                    simulation.getAverageChildrenNumber(), simulation.getAverageDescendantNumber(),
                    simulation.getAvailableSpace(), mapHeight*mapWidth - simulation.getAvailableSpace());
        }

    }

    private void drawMap() {
        xyLabel();
        columnsFunction();
        rowsFunction();
        addElements();
        updateStats();
        followAnimal();
        mapGrid.setGridLinesVisible(true);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @Override
    public void mapChanged(WorldMap map) {
        Platform.runLater(() -> {
            clearGrid();
            drawMap();
        });
    }

    @FXML
    private void onClickStart() {
        if (!isRunning) {
            isRunning = true;
            simulationThread.start();
        }
    }

    public void stopSimulation() {
        simulation.breakSimulation();
        simulationThread.interrupt();
    }

    @FXML
    private void onClickPausePlay() {
        if (isRunning) {
            if (simulation.isPaused()) {
                simulation.resumeSimulation();
            } else {
                simulation.pauseSimulation();
            }
        }
    }

    @FXML
    private void onClickShow() {
        preferred = !preferred;
    }
}
