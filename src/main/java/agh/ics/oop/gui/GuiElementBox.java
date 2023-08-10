package agh.ics.oop.gui;

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

    public GuiElementBox(String element, String background, int width, int height) {
        //System.out.println(element);
        //System.out.println(background);
        if (element.equals("plant")) {
            this.image = new Image("/" + element + ".png");
            this.imageView = new ImageView(image);
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
            this.background = new Image("/" + background + ".png");
            this.backgroundView = new ImageView(this.background);
            backgroundView.setFitHeight(height);
            backgroundView.setFitWidth(width);
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(backgroundView, imageView);
            vBox.getChildren().addAll(stackPane);
        }
        else if (element.equals(" ")) {
            this.image = new Image("/" + background + ".png");
            this.imageView = new ImageView(image);
            imageView.setFitHeight(height);
            imageView.setFitWidth(width);
            vBox.getChildren().addAll(imageView);
        }
        else {
            this.image = new Image("/animal.png");
            this.imageView = new ImageView(image);
            imageView.setFitHeight(height-(0.2*height));
            imageView.setFitWidth(width-(0.2*width));
            Label label = new Label(element);
            this.background = new Image("/" + background + ".png");
            this.backgroundView = new ImageView(this.background);
            backgroundView.setFitHeight(height);
            backgroundView.setFitWidth(width);
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



