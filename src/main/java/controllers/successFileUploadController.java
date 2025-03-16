package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import utilities.Paths;

public class successFileUploadController {

    @FXML private VBox bkpointVbox;
    @FXML private VBox nblineVbox;
    // @FXML private TextArea codeContainer;
    @FXML private VBox codeContainer;
    @FXML private TextArea consolePanel;
    @FXML private AnchorPane leftControlsPanel;
    @FXML private Button startButton;
    // @FXML // private VBox nblinesPanel;
    @FXML private FXMLLoader loader;
    @FXML private SplitPane splitPane;
    @FXML private ScrollPane bkScroller;
    @FXML private ScrollPane nblineScroller;
    @FXML private ScrollPane codeScroller;

    

    public SplitPane getSplitPane() {
        return splitPane;
    }


    public void setSplitPaneDivider(double size) {
        this.splitPane.setDividerPosition(0, size);
    }


    @FXML
    void beginExecution(ActionEvent event) {
        App.app.getStageWindow().setTitle("Executing : " + MainController.nameFile);
    
        save();

        MainController.successScene = App.app.getStageWindow().getScene();
        MainController.successController = this;
        MainController.changeScene(Paths.DURING_EXECUTION);
        
    }


    @FXML
    void initialize() {

        // supprimer le background du TextField
        codeContainer.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-line-spacing: 60px;"); 
        // codeContainer.setSpacing(0);

        if (MainController.content == null ) {
            // mainController.content = new StringBuilder("this is only for testing, usually CodeContainer is not null, its content would be the content of the file. this is just a test ●●●●●●●");
            // codeContainer.setText("this is only for testing, usually CodeContainer is not null, its content would be the content of the file");

            List<String> myTestList = new ArrayList<>();
            int lines = 120;
            
            MainController.numberOfLines = lines;
            
            for (int i= 1; i <= lines; i++){

                
                Label label = new Label("Line " + i);
                // label.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 1px;");
                myTestList.add(label.getText());
                codeContainer.getChildren().addAll(label);
            }
            
            MainController.content = myTestList;


        }
        else {
            for (String line : MainController.content){
                Label label = new Label(line);
                codeContainer.getChildren().addAll(label);
            }
        }

        // set nblines and breakpoints
        setNbLines();
        setBreakPoints();
        
        // syncrhonise scrolling
        codeScroller.vvalueProperty().bindBidirectional(bkScroller.vvalueProperty());
        codeScroller.vvalueProperty().bindBidirectional(nblineScroller.vvalueProperty());
    }

    public void update(){
        splitPane.setDividerPosition(0,MainController.getSplitPaneDividerPosition());
        // console
        this.consolePanel = MainController.getConsole();
        // Scrollers
        this.bkScroller = MainController.getBkScroller();
        this.nblineScroller = MainController.getNblineScroller();
        this.codeScroller = MainController.getCodeScroller();
        //Vbox
        this.codeContainer = MainController.getCodeContainer();
        this.bkpointVbox = MainController.getBkpointVbox();
        this.nblineVbox = MainController.getNblineVbox();
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


    public void show() {
        App.app.getStageWindow().show();
    }

    private void setNbLines() {
        // set the minimum spacing, we can increase it though if required for the stop instructions
        nblineVbox.setSpacing(0); 
    
        for (int i = 1; i <= MainController.numberOfLines; i++) {
            Label label = new Label(String.valueOf(i));
            // label.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 1px;");
            // label.setStyle("-fx-background-color: transparent;");
    
            nblineVbox.getChildren().add(label);
        }
    }
    
    private void setBreakPoints() {
        
        // initialize set
        if (MainController.bkpoints == null) MainController.bkpoints = new HashSet<>();
        
        // TODO slight disalignment t be fixed. Currently the best value to almost fit the other lines is 9
        bkpointVbox.setSpacing(9.5);
        bkpointVbox.setAlignment(Pos.BOTTOM_CENTER); 

        //size of the button
        double buttonSize = 8; 


        for (int i = 1; i <= MainController.numberOfLines; i++) {
            Button bkButton = new Button(i + "");
            // bkButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
            
            // set style
            bkButton.setMinSize(buttonSize, buttonSize);
            bkButton.setMaxSize(buttonSize, buttonSize);
            bkButton.setPrefWidth(buttonSize);
            bkButton.setPrefHeight(buttonSize);
            bkButton.setStyle("-fx-background-color: black; -fx-background-radius: 50%;");

            // Action when a button is pressed
            bkButton.setOnAction(event -> {

                // if disabled
                if (bkButton.getStyle().contains("black")) {
                    
                    //set style
                    bkButton.setStyle("-fx-background-color: red; -fx-background-radius: 50%;");
                    
                    // add button to set of bkpoints
                    MainController.bkpoints.add(Integer.parseInt(bkButton.getText()));
                
                // if enabled
                } else {
                    //revert style
                    bkButton.setStyle("-fx-background-color: black; -fx-background-radius: 50%;");
                    // bkButton.setStyle("-fx-background-color: black; -fx-text-fill: blue; -fx-background-radius: 50%;");
                    // remove
                    MainController.bkpoints.remove(Integer.parseInt(bkButton.getText()));

                }
            });
            
            bkpointVbox.getChildren().add(bkButton);
        }
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

        bkpointVbox.getChildren().forEach(n -> System.out.println(n.getStyle()));

    }
    

}
