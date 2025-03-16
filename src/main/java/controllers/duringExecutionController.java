package controllers;

import application.App;
import graphics.GraphcalLinkedList;
import graphics.GraphicalArray;
import graphics.GraphicalRepresentation;
import graphics.GraphicalVar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class duringExecutionController  {

    @FXML private VBox codeContainer;
    @FXML private TextArea consolePanel;
    @FXML private Button continueButton;
    @FXML private Button lastLineButton;
    @FXML private Button nextLineButton;
    @FXML private Button restartButton;
    @FXML private Button stopButton;
    @FXML private VBox bkpointVbox;
    @FXML private VBox nblineVbox;    
    @FXML private AnchorPane leftControlsPanel;
    @FXML private Button startButton;
    @FXML private FXMLLoader loader;
    @FXML private SplitPane splitPane;
    @FXML private ScrollPane bkScroller;
    @FXML private ScrollPane nblineScroller;
    @FXML private ScrollPane codeScroller;
    @FXML private Pane canvasPane;
    
    
    // TEST, so this can be deleteed later =================================================================
    GraphicalRepresentation rep = new GraphicalRepresentation();
    private successFileUploadController successController;
    
    // ==============================================================================

    @FXML
    void continueExecution(ActionEvent event) {
        
        consolePanel.setText("here we should continue execution until next breakpoint or stop from the user . Currently there is a test on the plot area");
        consolePanel.setText(consolePanel.getText() + "\n i am adding a new variable");
        rep.addAndRenderElement(new GraphicalVar("myVar2", "50", canvasPane));

    }

    @FXML
    void goLastLine(ActionEvent event) {
        System.out.println("here we should implement a way to return to the last line");
        
    }

    @FXML
    void goNextLine(ActionEvent event) {
        System.out.println("here we should implement a way to go to the next line");

        
        rep.addElement(new GraphicalVar("myVar", "10", canvasPane));
        rep.addElement(new GraphicalArray("myArray", new String[]{"value1", "value2", "value3"}, canvasPane));
        rep.addElement(new GraphcalLinkedList("myLinkedList", new String[]{"v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8", "v9", "v10", "v11"}, canvasPane)); // test to see what happens if there is not enough space

        rep.renderAll();

    }

    @FXML
    void restartExecution(ActionEvent event) {
        System.out.println("here we should reexecute the code");
        consolePanel.setText("i am restarting the execution, so all should be cleaned");

        
        // reinitialise the defaiult values of the position magager 
        GraphicalRepresentation.reinitialisePositioningValues();
        
        // Clean plots from panel
        rep.clear();

    }

    // @FXML
    // void stopExecution(ActionEvent event) {
    //     System.out.println("here we should return to the SuccessFileUpload scene");
    //     consolePanel.setText("i am stopping the execution, so change scene and clean panel");

    //     // reinitialise positioning and clean canvas
    //     GraphicalRepresentation.reinitialisePositioningValues();
        
    //     rep.clear();


        
    //     // successController.show();
    //     // mainController.codeContainer = codeContainer;
    //     changeScene(Paths.SUCCESS_FILE_UPLOAD);

    // }

    @FXML
    void stopExecution(ActionEvent event) {
        System.out.println("On revient a successFileUpload...");

        consolePanel.setText("We stop execution, so we return to the previous scene");

        // reset values for rendering
        GraphicalRepresentation.reinitialisePositioningValues();
        rep.clear();
        // set the positino of the divider
        MainController.setSplitPaneDividerPosition(splitPane.getDividerPositions()[0]);
        // save the current state in MainController
        save();
        successController.setPreviousState();
        
        // reuse the last scene
        App.app.getStageWindow().setScene(MainController.successScene);
        // MainController.successController.show(); 
    }

    public void save(){
        // save the current scene and controller
        MainController.setSplitPaneDividerPosition(splitPane.getDividerPositions()[0]);
        // console
        MainController.setConsole(consolePanel);
        // Scrollers
        MainController.setBkscroller(bkScroller);
        MainController.setNblineScroller(nblineScroller);
        MainController.setCodeScroller(codeScroller);
        //Vbox
        MainController.setCodeContainer(codeContainer);
        MainController.setBkpointVbox(bkpointVbox);
        MainController.setNblineVbox(nblineVbox);

    }

    @FXML
    void initialize() {

        // codeContainer.setEditable(false);
        codeContainer.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"); // supprimer le background du TextField
        // codeContainer.setWrapText(false);
        splitPane.setDividerPosition(0, MainController.splitPaneDividerPosition);

        continueButton.setText("C");
        lastLineButton.setText("<");
        nextLineButton.setText(">");   
        restartButton.setText("R");    
        stopButton.setText("A");

        setPreviousState();

        this.successController = MainController.successController;

    }
    
    void setPreviousState(){
        splitPane.setDividerPosition(0,MainController.getSplitPaneDividerPosition());
        // console
        consolePanel.setText(MainController.getConsole().getText());
        // Scrollers
        codeScroller.setVvalue(MainController.getCodeScroller().getVvalue());
        nblineScroller.setVvalue(MainController.getNblineScroller().getVvalue());
        bkScroller.setVvalue(MainController.getBkScroller().getVvalue());

        //Vbox
        codeContainer.getChildren().setAll(MainController.getCodeContainer().getChildren());
        nblineVbox.getChildren().setAll(MainController.getNblineVbox().getChildren());
        bkpointVbox.getChildren().setAll(MainController.getBkpointVbox().getChildren());

        // syncrhonize scrolls
        codeScroller.vvalueProperty().bindBidirectional(bkScroller.vvalueProperty());
        codeScroller.vvalueProperty().bindBidirectional(nblineScroller.vvalueProperty());


        bkpointVbox.setSpacing(9.5);
        bkpointVbox.setAlignment(Pos.BOTTOM_CENTER); 
        bkpointVbox.setStyle(null);

        // this is being reused so maybe it can be factorise
        double buttonSize = 8; 

        bkpointVbox.getChildren().forEach(node -> {
            if (node instanceof Button bkButton) {
                bkButton.setMinSize(buttonSize, buttonSize);
                bkButton.setMaxSize(buttonSize, buttonSize);
                bkButton.setPrefWidth(buttonSize);
                bkButton.setPrefHeight(buttonSize);
                bkButton.setStyle("-fx-background-color: black; -fx-text-fill: red; -fx-background-radius: 50%;");
            }
            });
    }

}