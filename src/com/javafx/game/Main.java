package com.javafx.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        //scene.getStylesheets().add(getClass().getResource("root.css").toExternalForm());
        primaryStage.setTitle("Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        /*WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        File file = new File("D:\\Work\\Java\\src\\com\\javafx\\game\\rootTemplate.html");
        if (file.canRead())
        {
            URL url = file.toURI().toURL();
            webEngine.load(url.toString());
        }


        VBox root = new VBox();
        root.setPadding(new Insets(5));
        root.setSpacing(5);
        root.getChildren().add(webView);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Application");
        primaryStage.setScene(scene);
        primaryStage.setWidth(width);
        primaryStage.setHeight(hight);

        primaryStage.show();*/
    }
}
