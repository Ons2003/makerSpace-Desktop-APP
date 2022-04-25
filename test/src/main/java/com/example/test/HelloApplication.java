package com.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override

    public void start(Stage stage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root, Color.LIGHTBLUE );

        Image icon = new Image("C:\\Users\\GMC informatique\\IdeaProjects\\test\\src\\main\\java\\SMU.jpg");
        stage.getIcons().add(icon);
        stage.setTitle("Makersapce Manager");
        stage.setHeight(500);
        stage.setWidth(700);
        stage.setResizable(true);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint ("You can exit by pressing e");//show for how much time?
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("e"));

//Text
        Text text = new Text();
        text.setText("This is our application where you'll be able to manage the Makerspace");
        text.setX(350);
        text.setY(100);
        text.setFont(Font.font("Verdana",20));
        text.setFill(Color.GRAY);

//Line
        Line line = new Line();
        line.setStartX(300);
        line.setStartY(150);
        line.setEndX(1100);
        line.setEndY(150);
        line.setStrokeWidth(2.5);
        line.setStroke(Color.BLACK);
        line.setOpacity(0.5);

//Rectangle
        Rectangle rectangle = new Rectangle();
        rectangle.setX(100);
        rectangle.setY(100);
        rectangle.setWidth(100);
        rectangle.setHeight(100);
        rectangle.setFill(Color.BEIGE);
        rectangle.setStrokeWidth(2.5);
        rectangle.setStroke(Color.BLACK);

//Polygon
        Polygon triangle = new Polygon();
        triangle.getPoints().setAll(200.0,200.0,
                                    300.0,300.0,
                                    200.0,300.0);
        triangle.setFill(Color.BEIGE);
        triangle.setStrokeWidth(2.5);
        triangle.setStroke(Color.BLACK);

//Circle
        Circle circle = new Circle();
        circle.setCenterX(350);
        circle.setCenterY(350);
        circle.setRadius(50);
        circle.setFill(Color.BLUEVIOLET);
        circle.setStrokeWidth(2.5);
        circle.setStroke(Color.BLACK);

//Image
        Image image = new Image("C:\\Users\\GMC informatique\\IdeaProjects\\test\\src\\main\\java\\SMU.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setX(600);
        imageView.setY(400);

        root.getChildren().add(imageView);
        root.getChildren().add(circle);
        root.getChildren().add(triangle);
        root.getChildren().add(rectangle);
        root.getChildren().add(line);
        root.getChildren().add(text);
        stage.setScene(scene);
        stage.show();


        /*FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();*/
    }

    public static void main(String[] args) {
        launch();
    }
}