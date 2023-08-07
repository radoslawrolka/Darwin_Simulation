package agh.ics.oop.gui;

import agh.ics.oop.SimulationEngine;
import agh.ics.oop.map.*;
import agh.ics.oop.elements.*;

import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application{
    private World map;
    private IMapBorder borders;
    private IMapGrounds grounds;
    private SimulationEngine engine;
    private int mapHeight;
    private int mapWidth;
    private GridPane gridPane = new GridPane();

    public void init() {
        this.mapHeight = 10;
        this.mapWidth = 10;
        this.borders = new Globe(mapWidth,mapHeight);
        this.grounds = new ForestedEquator(4,4,mapWidth,mapHeight);
        this.map = new World(mapWidth, mapHeight, borders, grounds, 1, 10, 3, 10, 5, 8, 1, 3, 4, 1000, MovingStyle.FULLY_PREDESTINED, MutationStyle.FULLY_RANDOM);
        this.engine = new SimulationEngine(this, map, 10, 1);
        this.map.addEngine(engine);
        this.map.draw();

    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Evolution Simulator");
        Thread thread = new Thread((Runnable) engine);
        thread.start();
        stage.show();
    }
}
