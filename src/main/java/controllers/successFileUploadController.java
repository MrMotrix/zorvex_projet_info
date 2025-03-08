package controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import utilities.Paths;

public class successFileUploadController extends mainController{

    @FXML
    private VBox bkpointsPanel;
    @FXML
    protected TextArea codeContainer;
    @FXML
    private TextArea consolePanel;
    @FXML
    private AnchorPane leftControlsPanel;
    @FXML
    private Button startButton;
    @FXML
    private VBox nblinesPanel;
    @FXML
    private FXMLLoader loader;
    
    
    private duringExecutionController executionController;

    private double leftPanelWidth;
    private double leftPanelHeight;

    // TODO ici on change de scene et on commence avec l-execution du code
    @FXML
    void beginExecution(ActionEvent event) {
        // changeScene(Paths.DURING_EXECUTION);
            leftPanelWidth = leftControlsPanel.getWidth();
            leftPanelHeight = leftControlsPanel.getHeight();
            
            // executionController.setLeftPanelDimensions(leftPanelWidth, leftPanelHeight);
            App.app.getStageWindow().setTitle("Executing : " + mainController.nameFile);

            changeScene(Paths.DURING_EXECUTION);
            // showDuringExecution(Paths.DURING_EXECUTION);
    }

    @FXML
    void initialize() {
        codeContainer.setEditable(false);
        codeContainer.setWrapText(true);


        if (mainController.content == null ) {
            codeContainer.setText("this is only for testing, usually CodeContainer is not null, its content would be the content of the file");
            // mainController.codeContainer = codeContainer;
        }
        else {
            codeContainer.setText(mainController.content.toString());

            // codeContainer = mainController.codeContainer;
        }

    }

    @FXML
    void showDuringExecution(String path) {
    //     try {
            
        
    //     codeContainer.setText("en teoria esto no deber[ia cambiar]");
    //     FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.DURING_EXECUTION));
    //     BorderPane root = loader.load();
    //     successFileUploadController controller2 = loader.getController();
        
    //     // executionController.setLeftPanelDimensions(leftPanelWidth, leftPanelHeight);
        
    //     Scene scene = new Scene(root);
    //     App.app.getStageWindow().setScene(scene);
    //     // executionController.initController(leftPanelWidth, this);
    //     // App.app.getStageWindow().show();
    //     } catch (Exception e) {
    //         // TODO: handle exception
    //     }
    }

    public void show() {
        App.app.getStageWindow().show();
    }

    public void init(StringBuilder builder) {
        
        throw new UnsupportedOperationException("Unimplemented method 'init'");
    }

}
