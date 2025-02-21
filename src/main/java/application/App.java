package main.java.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.utilities.Paths;

public class App extends Application{
    public static void main (String[] args) throws Exception {
        System.out.println("Hello, World!");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // TODO : These lines are just for testing
            // System.out.println(System.getProperty("java.class.path").split(";")[0]);
            // String value = (getClass().getResource(Paths.OPEN_NEW_FILE)).toString();
        // FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.OPEN_NEW_FILE));
        // AnchorPane root = loader.load();
    
        AnchorPane load = FXMLLoader.load(getClass().getResource(Paths.OPEN_NEW_FILE));
        
        // Scene scene = new Scene(root);
        Scene scene = new Scene(load);
        primaryStage.setScene(scene);
        primaryStage.show();

        
    }
    
}
