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


    private Simulation simulation;
    private WorldMap map;
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    private  int mapWidth;
    private  int mapHeight;

    private int width;
    private int height;
    private Thread simulationThread;
    private final boolean isPaused = false;
    private boolean isRunning = false;
    private final Object lock = new Object();
    private final GuiElement guiElement = new GuiElement();
    private Animal followedAnimal = null;

    @FXML
    private void initialize() {

        // Populate LineChart 1 with sample data
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.getData().add(new XYChart.Data<>(1, 10));
        series1.getData().add(new XYChart.Data<>(2, 20));
        series1.getData().add(new XYChart.Data<>(3, 15));
        ChartAnimalsPlants.getData().add(series1);

        // Populate LineChart 2 with sample data
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.getData().add(new XYChart.Data<>(1, 30));
        series2.getData().add(new XYChart.Data<>(2, 15));
        series2.getData().add(new XYChart.Data<>(3, 25));
        ChartEnergyLife.getData().add(series2);
    }

    public void setDataFromMenu(WorldMap map, Simulation simulation){
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
    public void mapChanged(WorldMap map, String message) {
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
}
