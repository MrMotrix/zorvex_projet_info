package controllers;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import application.App;
import graphics.GraphcalLinkedList;
import graphics.GraphicalArray;
import graphics.GraphicalRepresentation;
import graphics.GraphicalVar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import utilities.Paths;

public class duringExecutionController extends mainController {

    @FXML
    private TextArea codeContainer;

    @FXML
    private TextArea consolePanel;

    @FXML
    private Button continueButton;

    @FXML
    private Button lastLineButton;

    @FXML
    private Button nextLineButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button stopButton;

    @FXML
    private AnchorPane leftControlsPanel;

    @FXML
    private Pane canvasPane;

    private successFileUploadController successController;
    private double savedWidth;
    private double savedHeight;

    @FXML
    void continueExecution(ActionEvent event) {

    }

    @FXML
    void goLastLine(ActionEvent event) {
        System.out.println("here we should implement a way to return to the last line");
    }

    @FXML
    void goNextLine(ActionEvent event) {
        System.out.println("here we should implement a way to go to the next line");

        GraphicalRepresentation rep = new GraphicalRepresentation();
        rep.addElement(new GraphicalVar("myVar", "10", canvasPane));
        rep.addElement(new GraphicalArray("myArray", new String[]{"value1", "value2", "value3"}, canvasPane));
        rep.addElement(new GraphcalLinkedList("myLinkedList", new String[]{"v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8", "v9", "v10", "v11"}, canvasPane)); // test to see what happens if there is not enough space

        rep.renderAll();


    }

    @FXML
    void restartExecution(ActionEvent event) {
        System.out.println("here we should reexecute the code");

    }

    @FXML
    void stopExecution(ActionEvent event) {
        System.out.println("here we should return to the SuccessFileUpload scene");
        
        // successController.show();
        // mainController.codeContainer = codeContainer;
        changeScene(Paths.SUCCESS_FILE_UPLOAD);

    }

    // public void setLeftPanelDimensions(double width, double height) {
    //     this.savedWidth = width;
    //     this.savedHeight = height;
    // }

    @FXML
    void initialize() {

        codeContainer.setEditable(false);

        // leftControlsPanel.setMaxWidth(savedWidth);
        // leftControlsPanel.setMaxHeight(savedHeight);
        // leftControlsPanel.setWidth(savedWidth);  // Asegúrate de ajustar el tamaño actual
        // leftControlsPanel.setHeight(savedHeight); // Asegúrate de ajustar el tamaño actual

        // codeContainer = mainController.codeContainer;
    }
    
    // public void initController(double leftPanelWidth, successFileUploadController successFileUploadController) {
    //     successController = successFileUploadController;
    // }


}