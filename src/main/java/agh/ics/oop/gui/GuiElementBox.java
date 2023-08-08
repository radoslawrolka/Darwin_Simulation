package agh.ics.oop.gui;


import agh.ics.oop.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GuiElementBox {
    protected Image image;
    protected ImageView imageView;
    protected Image background;
    protected ImageView backgroundView;
    protected VBox vBox = new VBox();

    public GuiElementBox(String element, String background) {
        //System.out.println(element);
        System.out.println(background);
        if (element.equals("plant")) {
            this.image = new Image("/" + element + ".png");
            this.imageView = new ImageView(image);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            this.background = new Image("/" + background + ".png");
            this.backgroundView = new ImageView(this.background);
            backgroundView.setFitHeight(50);
            backgroundView.setFitWidth(50);
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(backgroundView, imageView);
            vBox.getChildren().addAll(stackPane);
        }
        else if (element.equals(" ")) {
            this.image = new Image("/" + background + ".png");
            this.imageView = new ImageView(image);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            vBox.getChildren().addAll(imageView);
        }
        else {
            this.image = new Image("/animal.png");
            this.imageView = new ImageView(image);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            Label label = new Label(element);
            this.background = new Image("/" + background + ".png");
            this.backgroundView = new ImageView(this.background);
            backgroundView.setFitHeight(50);
            backgroundView.setFitWidth(50);
            StackPane stackPane = new StackPane();
            VBox vbox = new VBox();
            vbox.getChildren().addAll(imageView, label);
            vbox.setAlignment(javafx.geometry.Pos.CENTER);
            stackPane.getChildren().addAll(backgroundView, vbox);
            vBox.getChildren().addAll(stackPane);
        }
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
    }

    public VBox getvBox() {
        return vBox;
    }
}



