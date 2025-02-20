import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application{
    public static void main (String[] args) throws Exception {
        System.out.println("Hello, World!");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Crear una escena con un layout básico
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 200);  

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }
}
