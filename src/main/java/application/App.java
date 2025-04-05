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
        
        // ================= TO BE DELETED ========================================================
        int caseNumber = 1; // 0 = open new file, 1 = success file upload
        Parent root;
        // =========================================================================

        if (caseNumber == 0){
            loader = new FXMLLoader(getClass().getResource(Paths.OPEN_NEW_FILE));
            root = loader.load();
            openNewFileController controller = loader.getController();
            controller.setApp(this);
            controller.setMainController(mainController);
        }
        else {
            loader = new FXMLLoader(getClass().getResource(Paths.SUCCESS_FILE_UPLOAD));
            mainController.setNameFile("THIS IS JUST A TEST BECAUSE NO FILE HAS BEEN UPLOADED");
            root = loader.load();
            successFileUploadController controller = loader.getController();
            controller.initialize2(mainController, this);
            controller.setApp(this);
            controller.setMainController(mainController);

        } 
        
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
    /**
     * This method is used to change the scene of the application.
     * It takes the path of the fxml file as a parameter and loads it.
     * @param path the path of the fxml file to load
     */
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
    
    /**
     * This method is used to show the stage window of the application.
     * It is called when the application is launched and the stage window is created.
     */
    public void show() {
        getStageWindow().show();  
    }

    /**
     * This method is used to get the stage window of the application.
     * @return the stage window of the application
     */
    public Stage getStageWindow() {
        return stageWindow;
    }
    /**
     * This method is used to set the scene of the stage window.
     * It takes a Scene object as a parameter and sets it to the stage window.
     * @param scene the Scene object to set to the stage window
     */
    public void setScene(Scene scene){
        stageWindow.setScene(scene);
    }
    
    /**
     * This method is used to get the main controller of the application.
     * @return the main controller of the application
     */
    public MainController getMainController() {
        return mainController;
    }

    public Scene getScene() {
        return stageWindow.getScene();
    }
        
}