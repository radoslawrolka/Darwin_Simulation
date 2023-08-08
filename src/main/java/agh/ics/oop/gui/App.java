package agh.ics.oop.gui;

import agh.ics.oop.SimulationEngine;
import agh.ics.oop.Vector2d;
import agh.ics.oop.map.*;
import agh.ics.oop.elements.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application{
    private World map;
    private IMapBorder borders;
    private IMapGrounds grounds;
    private SimulationEngine engine;
    private int mapHeight;
    private int mapWidth;
    private GridPane gridPane = new GridPane();
    int height = 50;
    int width = 50;
    private Thread thread;
    private boolean start = true;
    private VBox stats = new VBox();

    public void init() {
        this.mapHeight = 10;
        this.mapWidth = 10;
        this.borders = new Globe(mapWidth,mapHeight);
        this.grounds = new ForestedEquator(4,4,mapWidth,mapHeight);
        this.map = new World(mapWidth, mapHeight, borders, grounds, 1, 8, 3, 50, 5, 8, 1, 3, 4, 1000, MovingStyle.FULLY_PREDESTINED, MutationStyle.FULLY_RANDOM);
        this.engine = new SimulationEngine(this, map, 1000, 1);
        this.map.addEngine(engine);
        this.thread = new Thread(this.engine);
    }

    public void xyLabel(){
        GridPane.setHalignment(new Label("y/x"), HPos.CENTER);
        gridPane.getColumnConstraints().add(new ColumnConstraints(width));
        gridPane.getRowConstraints().add(new RowConstraints(height));
        gridPane.add(new Label("y/x"), 0, 0);
    }

    public void columnsFunction(){
        for (int i = 1; i <= mapWidth; i++){
            Label label = new Label(Integer.toString(i));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getColumnConstraints().add(new ColumnConstraints(width));
            gridPane.add(label, i, 0);
        }
    }

    public void rowsFunction(){
        for (int i = mapHeight; i >= 1; i--){
            Label label = new Label(Integer.toString(i));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getRowConstraints().add(new RowConstraints(height));
            gridPane.add(label, 0, mapHeight -i + 1);
        }
    }

    public void addElements() {
        for (int i = 1; i <= mapWidth; i++) {
            for (int j = mapHeight; j >= 1; j--) {
                Vector2d pos = new Vector2d(i,j);

                //Label label = new Label(map.isAt(pos));
                //GridPane.setHalignment(label, HPos.CENTER);
                //gridPane.add(label, i, mapHeight-j+1);

                String element = map.isAt(pos);
                String background = map.groundType(pos);

                GuiElementBox elementBox = new GuiElementBox(element, background);
                gridPane.add(elementBox.getvBox(),i,mapHeight-j+1);
                GridPane.setHalignment(elementBox.getvBox(),HPos.CENTER);


            }
        }
    }

    public void updateStatistics() {
        stats.getChildren().clear();
        Label day = new Label("Day: " + engine.getDay());
        Label animals = new Label("Animals: " + engine.getAnimalsNum());
        Label plants = new Label("Plants: " + map.getPlantsNum());
        Label avgEnergy = new Label("Average energy: " + engine.getAvgEnergy());
        Label avgLife = new Label("Average life: " + engine.getAvgLife());
        Label avgDeath = new Label("Average death: " + map.getAvgDeath());

        stats.getChildren().addAll(day, animals, plants, avgEnergy, avgLife, avgDeath);
        stats.setAlignment(javafx.geometry.Pos.CENTER);
    }

    public void prepareScene(){
        xyLabel();
        columnsFunction();
        rowsFunction();
        addElements();
        updateStatistics();
        gridPane.setGridLinesVisible(true);
    }

    public void refreshMap() {
        Platform.runLater(() -> {
            gridPane.getChildren().clear();
            gridPane.setGridLinesVisible(false);
            gridPane.getColumnConstraints().clear();
            gridPane.getRowConstraints().clear();
            prepareScene();

        });
    }

    @Override
    public void start(Stage gameStage) {
        gameStage.setTitle("Evolution Simulator");

        Button newgame = new Button("New Game");
        HBox hbox2 = new HBox(newgame);
        newgame.setOnAction(event -> {
            Stage newGame = new Stage();
            App nextGame = new App();
            try {
                nextGame.init();
                nextGame.start(newGame);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button exit = new Button("Exit");
        HBox hbox3 = new HBox(exit);
        exit.setOnAction(event -> {
            Platform.exit();
        });

        Button button = new Button("Stop");
        button.setOnAction(event -> {
            this.start = !this.start;
            if(this.start){
                button.setText("Stop");
                this.thread.resume();
            }
            else{
                button.setText("Start");
                this.thread.suspend();
            }
        });
        HBox hbox4 = new HBox(button);

        Button butt = new Button("Start");
        HBox hbox = new HBox(butt);
        butt.setOnAction(event -> {
            thread.start();
        });

        prepareScene();
        VBox map = new VBox(gridPane);
        VBox buttonsAndStats = new VBox(hbox, hbox2, hbox3, hbox4, stats);
        HBox mapAndButtons = new HBox(buttonsAndStats, map);

        Scene scene = new Scene(mapAndButtons,1000,800);
        gameStage.setScene(scene);
        gameStage.show();
    }
}
