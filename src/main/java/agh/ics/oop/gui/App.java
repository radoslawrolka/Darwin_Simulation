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
    private int mapHeight = 6;
    private int mapWidth = 6;
    private GridPane gridPane = new GridPane();
    int height = 50;
    int width = 50;
    private Thread thread;
    private boolean start = true;
    private VBox stats = new VBox();
    private int penalty;
    private String border = "Globe";
    private String ground = "Equator";
    private int equatorWidth = 2 ;
    private int equatorHeigth = 2;
    private int plantsEnergy = 10;
    private int startingPlants = 10;
    private int plantsGrowth =  3;
    private int animalsStartingEnergy = 10;
    private int animalsReproductionEnergy = 2;
    private int animalsStartNum = 3;
    private int animalMaxEnergy = 100;
    private int Geneslen= 4;
    private int maxMutations= 3;
    private int minMutations = 1;
    private MovingStyle movingStyle = MovingStyle.FULLY_PREDESTINED;
    private MutationStyle mutationStyle = MutationStyle.FULLY_RANDOM;
    private int moveDelay= 1000;
    private int dayCost=1;
    private boolean smooth = true;

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

                if (this.smooth) {
                    Label label = new Label(map.isAt(pos));
                    GridPane.setHalignment(label, HPos.CENTER);
                    gridPane.add(label, i, mapHeight-j+1);
                }
                else {
                    String element = map.isAt(pos);
                    String background = map.groundType(pos);

                    GuiElementBox elementBox = new GuiElementBox(element, background);
                    gridPane.add(elementBox.getvBox(),i,mapHeight-j+1);
                    GridPane.setHalignment(elementBox.getvBox(),HPos.CENTER);
                }
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
    public void start(Stage menu) {
        menu.setTitle("Menu");

        Label label = new Label("Map width");
        TextField width = new TextField();
        Label label2 = new Label("Map height");
        TextField height = new TextField();

        Label label3 = new Label("Equator width");
        TextField equatorWidth = new TextField();
        Label label4 = new Label("Equator height");
        TextField equatorHeight = new TextField();

        Label label5 = new Label("Penalty");
        TextField penalty = new TextField();

        HBox b = new HBox(label3, equatorWidth, label4, equatorHeight);

        Button border = new Button("[Globe] - Change to hell portal");
        border.setOnAction(event -> {
            if(this.border.equals("Globe")){
                border.setText("[Hell] - Change to Globe");
                this.border = "Hell";
                b.getChildren().clear();
                b.getChildren().addAll(label5, penalty);
            }
            else{
                border.setText("[Globe] - Change to Hell Portal");
                this.border = "Globe";
                b.getChildren().clear();
            }
        });

        HBox b99 = new HBox(label3, equatorWidth, label4, equatorHeight);
        Button ground = new Button("[Equator] - Change to toxic corpses");
        ground.setOnAction(event -> {
            if(this.ground.equals("Equator")){
                ground.setText("[Toxic corpses] - Change to Equator");
                this.ground = "Toxic corpses";
                b99.getChildren().clear();
            }
            else{
                ground.setText("[Equator] - Change to Toxic corpses");
                this.ground = "Equator";
                b99.getChildren().clear();
                b99.getChildren().addAll(label3, equatorWidth, label4, equatorHeight);
            }
        });

        Label label6 = new Label("Plants energy");
        TextField plantsEnergy = new TextField();
        Label label7 = new Label("Starting plants");
        TextField startingPlants = new TextField();
        Label label8 = new Label("Plants growth per day");
        TextField plantsGrowth = new TextField();

        Label label9 = new Label("Animals starting energy");
        TextField animalsStartingEnergy = new TextField();
        Label label10 = new Label("Animals reproduction energy");
        TextField animalsReproductionEnergy = new TextField();
        Label label11 = new Label("Animals starting number");
        TextField animalsStartNum = new TextField();
        Label label12 = new Label("Animal max energy");
        TextField animalMaxEnergy = new TextField();

        Label label13 = new Label("Genes length");
        TextField Geneslen = new TextField();
        Label label14 = new Label("Max mutations");
        TextField maxMutations = new TextField();
        Label label15 = new Label("Min mutations");
        TextField minMutations = new TextField();

        Label label16 = new Label("Move delay");
        TextField moveDelay = new TextField();
        Label label17 = new Label("Day cost");
        TextField dayCost = new TextField();

        Button moveStyl = new Button("[Fully predestined] - Change to bit of randomness");
        moveStyl.setOnAction(event -> {
            if(this.movingStyle.equals(MovingStyle.FULLY_PREDESTINED)){
                moveStyl.setText("[Randness] - Change to fully predestined");
                this.movingStyle = MovingStyle.BIT_OF_RANDOMNESS;
            }
            else{
                moveStyl.setText("[Fully predestined] - Change to randomness");
                this.movingStyle = MovingStyle.FULLY_PREDESTINED;
            }
        });

        Button mutationStyl = new Button("[Fully random] - Change to little correction");
        mutationStyl.setOnAction(event -> {
            if(this.mutationStyle.equals(MutationStyle.FULLY_RANDOM)){
                mutationStyl.setText("[Little Correction] - Change to fully random");
                this.mutationStyle = MutationStyle.LITTLE_CORRECTION;
            }
            else{
                mutationStyl.setText("[Fully random] - Change to little correction");
                this.mutationStyle = MutationStyle.FULLY_RANDOM;
            }
        });

        Button play = new Button("Play");
        play.setOnAction(event -> {
            this.mapHeight = Integer.parseInt(height.getText());
            this.mapWidth = Integer.parseInt(width.getText());

            if (this.border.equals("Hell")) {
                this.penalty = Integer.parseInt(penalty.getText());
                this.borders = new HellPortal(this.mapWidth, this.mapHeight, this.penalty);
            }
            else {this.borders = new Globe(this.mapWidth, this.mapHeight);}

            if (this.ground.equals("Equator")) {
                this.equatorWidth = Integer.parseInt(equatorWidth.getText());
                this.equatorHeigth = Integer.parseInt(equatorHeight.getText());
                this.grounds = new ForestedEquator(this.equatorWidth, this.equatorHeigth, this.mapWidth, this.mapHeight);
            }
            else {this.grounds = new ToxicCorpses(this.mapWidth, this.mapHeight);}

            this.plantsEnergy = Integer.parseInt(plantsEnergy.getText());
            this.startingPlants = Integer.parseInt(startingPlants.getText());
            this.plantsGrowth = Integer.parseInt(plantsGrowth.getText());

            this.animalsStartingEnergy = Integer.parseInt(animalsStartingEnergy.getText());
            this.animalsReproductionEnergy = Integer.parseInt(animalsReproductionEnergy.getText());
            this.animalsStartNum = Integer.parseInt(animalsStartNum.getText());
            this.animalMaxEnergy = Integer.parseInt(animalMaxEnergy.getText());

            this.Geneslen = Integer.parseInt(Geneslen.getText());
            this.maxMutations = Integer.parseInt(maxMutations.getText());
            this.minMutations = Integer.parseInt(minMutations.getText());

            this.moveDelay = Integer.parseInt(moveDelay.getText());
            this.dayCost = Integer.parseInt(dayCost.getText());

            this.map = new World(this.mapWidth, this.mapHeight, this.borders, this.grounds, this.plantsEnergy, this.startingPlants, this.plantsGrowth, this.animalsStartingEnergy, this.animalsReproductionEnergy, this.animalsStartNum, this.minMutations, this.maxMutations, this.Geneslen, this.animalMaxEnergy, this.movingStyle, this.mutationStyle);
            this.engine = new SimulationEngine(this, this.map, this.moveDelay, this.dayCost);
            map.addEngine(this.engine);
            this.thread = new Thread(this.engine);
            Stage gameStage = new Stage();
            startGame(gameStage);
        });

        Button smuth = new Button("[Smooth] - Change to Beautiful");
        smuth.setOnAction(event -> {
            if(this.smooth){
                smuth.setText("[Beautiful] - Change to smooth");
                this.smooth = false;
            }
            else{
                smuth.setText("[Smooth] - Change to Beautiful");
                this.smooth = true;
            }
        });
        //HBox a = new HBox(label, width, label2, height);
        //HBox b0 = new HBox(border);
        //HBox c = new HBox(label6, plantsEnergy, label7, startingPlants, label8, plantsGrowth);
        //HBox d = new HBox(label9, animalsStartingEnergy, label10, animalsReproductionEnergy, label11, animalsStartNum, label12, animalMaxEnergy);
        //HBox e = new HBox(label13, Geneslen, label14, maxMutations, label15, minMutations);
        //HBox f = new HBox(label16, moveDelay, label17, dayCost);
        //HBox g = new HBox(moveStyl, mutationStyl);
        //VBox all = new VBox(a, b0, b, c, d, e, f, g, play);

        VBox all = new VBox(label, width, label2, height, border, b,ground, b99, label6, plantsEnergy, label7, startingPlants, label8, plantsGrowth, label9, animalsStartingEnergy, label10, animalsReproductionEnergy, label11, animalsStartNum, label12, animalMaxEnergy, label13, Geneslen, label14, maxMutations, label15, minMutations, label16, moveDelay, label17, dayCost, moveStyl, mutationStyl, smuth, play);
        Scene scene = new Scene(all,1000, 800);
        menu.setScene(scene);
        menu.show();
    }

    public void startGame(Stage gameStage) {
        gameStage.setTitle("Evolution Simulator");

        Button newgame = new Button("New Game");
        HBox hbox2 = new HBox(newgame);
        newgame.setOnAction(event -> {
            Stage newGame = new Stage();
            App nextGame = new App();
            try {
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

        Button smuth = new Button("[Smooth] - Change to Beautiful");
        smuth.setOnAction(event -> {
            if(this.smooth){
                smuth.setText("[Beautiful] - Change to smooth");
                this.smooth = false;
            }
            else{
                smuth.setText("[Smooth] - Change to Beautiful");
                this.smooth = true;
            }
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
        VBox buttonsAndStats = new VBox(hbox, hbox2, hbox3, smuth, hbox4, stats);
        HBox mapAndButtons = new HBox(buttonsAndStats, map);

        Scene scene = new Scene(mapAndButtons,1000,800);
        gameStage.setScene(scene);
        gameStage.show();
    }
}
