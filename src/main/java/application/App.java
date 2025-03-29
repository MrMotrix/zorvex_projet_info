// package application;
// import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.image.Image;
// import javafx.scene.layout.BorderPane;
// import javafx.stage.Stage;
// import utilities.Paths;

// public class App extends Application{
    
//     public static App app;
//     private Stage stageWindow;
//     public FXMLLoader loader;

//     public static void main (String[] args) throws Exception {

//         launch(args);
//     }

//         @Override
//     public void start(Stage primaryStage) throws Exception {

//         // String path = getClass().getResource("/").toString();
//          loader = new FXMLLoader(getClass().getResource(Paths.OPEN_NEW_FILE));
//          primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(Paths.LOGO_ICON)));

//         // loader = new FXMLLoader(getClass().getResource(Paths.SUCCESS_FILE_UPLOAD));
//         // loader = new FXMLLoader(getClass().getResource(Paths.DURING_EXECUTION));
        
//         // BorderPane load = FXMLLoader.load(getClass().getResource(Paths.OPEN_NEW_FILE));
//         // BorderPane load = FXMLLoader.load(getClass().getResource(Paths.DURING_EXECUTION));
//         Parent root = loader.load();
//         // Scene scene = new Scene(root);
//         Scene scene = new Scene(root);

//         // scene.getStylesheets().add(getClass().getResource(Paths.OPEN_NEW_FILE_STYLE).toExternalForm());

//         primaryStage.setMaximized(true);
//         primaryStage.setScene(scene);

//         app = this;
//         stageWindow = primaryStage;

//         primaryStage.setMinHeight(400);
//         primaryStage.setMinWidth(600);
//         primaryStage.setMaxHeight(4000);
//         primaryStage.setMaxWidth(6000);
//         primaryStage.show();
        

            
//     }

//     public void setScene(String path) {
//         // save the size of the screen before changing scene to make it fluid. Also check if it is fullscreen
//         double width = stageWindow.getWidth();
//         double height = stageWindow.getHeight();
//         // boolean isFullScreen = stageWindow.isFullScreen();
        
//         FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
//         try {
//             BorderPane pane = loader.load();
//             Scene scene = new Scene(pane);
            
//             // The scene is loaded but has not been changed yet. Resize before changing
//             stageWindow.setWidth(width);
//             stageWindow.setHeight(height);
            
//             // Change scene
//             stageWindow.setScene(scene);
    
            
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
    

//     public void show() {
//         App.app.getStageWindow().show();  
//     }


//     public Stage getStageWindow() {
//         return stageWindow;
//     }

//     public void setScene(Scene scene){
//         stageWindow.setScene(scene);
//     }
        
// }


package application;
import controllers.MainController;
import controllers.duringExecutionController;
import controllers.openNewFileController;
import controllers.successFileUploadController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utilities.Paths;

public class App extends Application{
    
    private Stage stageWindow;
    public FXMLLoader loader;
    private MainController mainController;

    public static void main (String[] args) throws Exception {

        launch(args);
    }

        @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.stageWindow = primaryStage;
        this.mainController = new MainController();
        mainController.setApp(this);

        // String path = getClass().getResource("/").toString();
         loader = new FXMLLoader(getClass().getResource(Paths.OPEN_NEW_FILE));
         Parent root = loader.load();

         openNewFileController controller = loader.getController();
         controller.setApp(this);
         controller.setMainController(mainController);
         // loader = new FXMLLoader(getClass().getResource(Paths.SUCCESS_FILE_UPLOAD));
         // loader = new FXMLLoader(getClass().getResource(Paths.DURING_EXECUTION));
         
         // BorderPane load = FXMLLoader.load(getClass().getResource(Paths.OPEN_NEW_FILE));
         // BorderPane load = FXMLLoader.load(getClass().getResource(Paths.DURING_EXECUTION));
         // Scene scene = new Scene(root);
         
         // scene.getStylesheets().add(getClass().getResource(Paths.OPEN_NEW_FILE_STYLE).toExternalForm());
         
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(Paths.LOGO_ICON)));
        primaryStage.setMaximized(true);


        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);
        primaryStage.setMaxHeight(4000);
        primaryStage.setMaxWidth(6000);
        primaryStage.show();
        

            
    }

    public void setScene(String path) {
        // save the size of the screen before changing scene to make it fluid. Also check if it is fullscreen
        double width = stageWindow.getWidth();
        double height = stageWindow.getHeight();
        // boolean isFullScreen = stageWindow.isFullScreen();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            
            if (path.equals(Paths.SUCCESS_FILE_UPLOAD)) {
                
                BorderPane pane = loader.load();
                                
                successFileUploadController successController = loader.getController();
                successController.initialize2(mainController, this);

                Scene scene = new Scene(pane);
                
                // The scene is loaded but has not been changed yet. Resize before changing
                stageWindow.setWidth(width);
                stageWindow.setHeight(height);
                
                // Change scene
                stageWindow.setScene(scene);

            } else if (path.equals(Paths.DURING_EXECUTION)) {
                BorderPane pane = loader.load();
                duringExecutionController duringController = loader.getController();
                duringController.initialize2(mainController, this);
                Scene scene = new Scene(pane);
                
                // The scene is loaded but has not been changed yet. Resize before changing
                stageWindow.setWidth(width);
                stageWindow.setHeight(height);
                
                // Change scene
                stageWindow.setScene(scene);
            } else {
                BorderPane pane = loader.load();
                Scene scene = new Scene(pane);
                
                // The scene is loaded but has not been changed yet. Resize before changing
                stageWindow.setWidth(width);
                stageWindow.setHeight(height);
                
                // Change scene
                stageWindow.setScene(scene);
            }
    
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }
    

    public void show() {
        getStageWindow().show();  
    }


    public Stage getStageWindow() {
        return stageWindow;
    }

    public void setScene(Scene scene){
        stageWindow.setScene(scene);
    }

    public MainController getMainController() {
        return mainController;
    }
        
}