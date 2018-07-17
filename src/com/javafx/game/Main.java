package com.javafx.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        int width = 800;
        int hight = 600;

        Parent root = FXMLLoader.load(getClass().getResource("webView.fxml"));

        Scene scene = new Scene(root, width, hight);
        primaryStage.setTitle("Application");
        primaryStage.getIcons().add(new Image(Main.class.getClassLoader().getResourceAsStream("icnons/ico.jpg")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
