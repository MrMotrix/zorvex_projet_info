package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utilities.Paths;

public class App extends Application{
    
    public static App app;
    private Stage stageWindow;


    public static void main (String[] args) throws Exception {

        launch(args);
    }

    @Override
public void start(Stage primaryStage) throws Exception {

        // TODO : These lines are just for testing
            // System.out.println(System.getProperty("java.class.path").split(";")[0]);
            // String value = (getClass().getResource(Paths.OPEN_NEW_FILE)).toString();
            // System.out.println("This is the classpath : ");
            // System.out.println(value);
        // FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.OPEN_NEW_FILE));
        // AnchorPane root = loader.load();
    
        // AnchorPane load = FXMLLoader.load(getClass().getResource(Paths.OPEN_NEW_FILE));
        BorderPane load = FXMLLoader.load(getClass().getResource(Paths.DURING_EXECUTION));
        // Scene scene = new Scene(root);
        Scene scene = new Scene(load);
        primaryStage.setScene(scene);

        app = this;
        stageWindow = primaryStage;
        setScene(Paths.OPEN_NEW_FILE);



        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);
        primaryStage.setMaxHeight(4000);
        primaryStage.setMaxWidth(6000);
        primaryStage.show();
        System.out.println("SALUUUUUT");
        

        
    }

    // TODO : there is a problem when i try to use this. Even efter wrapping the App class in the package application, there is another problem that appears, the App class is not founded (but idk by who)
    public void setScene(String path){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        try {
            BorderPane pane = loader.load();
            Scene scene = new Scene(pane);
            stageWindow.setScene(scene);
            stageWindow.show();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
    
}
