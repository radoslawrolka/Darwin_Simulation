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

public class SimulationPresenter implements MapChangeListener {
    @FXML
    private GridPane mapGrid;
    /*@FXML
    private LineChart<Number, Number> ChartAnimalsPlants;
    @FXML
    private LineChart<Number, Number> ChartEnergyLife;
*/
    private Simulation simulation;
    private WorldMap map;

    @FXML
    private void initialize() {
        /*
        // Populate LineChart 1 with sample data
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.getData().add(new XYChart.Data<>(1, 10));
        series1.getData().add(new XYChart.Data<>(2, 20));
        series1.getData().add(new XYChart.Data<>(3, 15));
        ChartAnimalsPlants.getData().add(series1);
        ChartAnimalsPlants.setLegendVisible(true);

        // Populate LineChart 2 with sample data
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.getData().add(new XYChart.Data<>(1, 30));
        series2.getData().add(new XYChart.Data<>(2, 15));
        series2.getData().add(new XYChart.Data<>(3, 25));
        ChartEnergyLife.getData().add(series2);

         */
    }

    public void setDataFromMenu(WorldMap map, Simulation simulation){
        this.map = map;
        this.simulation = simulation;
        simulationThread = new Thread(simulation);
    }


    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    private  int mapWidth;
    private  int mapHeight;

    private final int width = 50;
    private final int height = 50;
    private Thread simulationThread;
    private boolean isPaused = false;
    private boolean isRunning = false;
    private Object lock = new Object();

    public void xyLabel(){
        mapGrid.getColumnConstraints().add(new ColumnConstraints(width));
        mapGrid.getRowConstraints().add(new RowConstraints(height));
        Label label = new Label("y/x");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
    }

    public void updateBounds(){
        xMin = map.getCurrentBounds().lowerLeft().getX();
        yMin = map.getCurrentBounds().lowerLeft().getY();
        xMax = map.getCurrentBounds().upperRight().getX();
        yMax = map.getCurrentBounds().upperRight().getY();
        mapWidth = xMax - xMin + 1;
        mapHeight = yMax - yMin + 1;
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
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMax; j >= yMin; j--) {
                Vector2d pos = new Vector2d(i,j);
                if(map.getAnimalsOnPosition(pos) != null) {
                    if (map.getAnimalsOnPosition(pos).size() > 0) {
                        mapGrid.add(new Label(map.getAnimalsOnPosition(pos).first().toString()),i-xMin+1,yMax-j+1);
                    }
                    else {
                        mapGrid.add(new Label(" "),i-xMin+1,yMax-j+1);
                    }
                }
                else {
                    mapGrid.add(new Label(" "),i-xMin+1,yMax-j+1);
                }
                mapGrid.setHalignment(mapGrid.getChildren().get(mapGrid.getChildren().size()-1), HPos.CENTER);
            }
        }
    }

    private void drawMap() {
        updateBounds();
        xyLabel();
        columnsFunction();
        rowsFunction();
        addElements();
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
        simulation.setObserver(this);
        isRunning = true;
        simulationThread.start();
    }

    public void stopSimulation() {
        simulation.breakSimulation();
        simulationThread.interrupt();
    }

    // Inside your SimulationPresenter class
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
