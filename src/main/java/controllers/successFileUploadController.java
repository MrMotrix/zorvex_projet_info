// package controllers;

// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.List;

// import application.App;
// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.SplitPane;
// import javafx.scene.control.TextArea;
// import javafx.scene.layout.AnchorPane;
// import javafx.scene.layout.VBox;
// import utilities.Paths;

// public class successFileUploadController {

//     @FXML private VBox bkpointVbox;
//     @FXML private VBox nblineVbox;
//     @FXML private VBox codeContainer;
//     @FXML private TextArea consolePanel;
//     @FXML private AnchorPane leftControlsPanel;
//     @FXML private Button startButton;
//     @FXML private FXMLLoader loader;
//     @FXML private SplitPane splitPane;
//     @FXML private ScrollPane bkScroller;
//     @FXML private ScrollPane nblineScroller;
//     @FXML private ScrollPane codeScroller;

//     // ================================================================================================
    
//     public static String code =  """
//         n <- 10*10 / 5
//         p <- 2
//         compose <- 0
//         n <- n+p
//         """;
    
//     // ================================================================================================



//     public SplitPane getSplitPane() {
//         return splitPane;
//     }


//     public void setSplitPaneDivider(double size) {
//         this.splitPane.setDividerPosition(0, size);
//     }


//     @FXML
//     void beginExecution(ActionEvent event) {
//         App.app.getStageWindow().setTitle("En éxécution : " + MainController.nameFile);
    
//         save();

//         MainController.successScene = App.app.getStageWindow().getScene();
//         MainController.successController = this;
//         MainController.changeScene(Paths.DURING_EXECUTION);
        
//     }


//     @FXML
//     void initialize() {

//         // supprimer le background du TextField
//         codeContainer.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-line-spacing: 60px;"); 
//         codeScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
//         bkScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
//         nblineScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
//         consolePanel.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");

//         consolePanel.setWrapText(true);
        
//         // for testing, this is executed iff we start directly on this scene
//         if (MainController.content == null ) {

//             List<String> myTestList = new ArrayList<>();

//             String testingText = code;
            
           
//             for (String line : testingText.split("\n")){
//                 myTestList.add(line);
//             }
//             MainController.numberOfLines = myTestList.size();

//             for (String line : myTestList){
                
//                 Label label = new Label(line);
//                 label.setMaxWidth(Double.MAX_VALUE);
//                 // label.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 1px;");
//                 codeContainer.getChildren().addAll(label);
//             }

//             /*
            
//             int lines = 50;
            
//             MainController.numberOfLines = lines;
            
//             for (int i= 1; i <= lines; i++){

                
//                 Label label = new Label("Line " + i);
//                 label.setMaxWidth(Double.MAX_VALUE);
//                 // label.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 1px;");
//                 myTestList.add(label.getText());
//                 codeContainer.getChildren().addAll(label);
//             }
//             */
//             MainController.content = myTestList;

//         }

//         else {
//             for (String line : MainController.content){
//                 Label label = new Label(line);
//                 label.setMaxWidth(Double.MAX_VALUE);

//                 codeContainer.getChildren().addAll(label);
//             }
//         }

//         // set nblines and breakpoints
//         setNbLines();
//         setBreakPoints();
        
//         // bkpointVbox.setMaxHeight(codeContainer.getMinHeight());
//         // bkpointVbox.setMinHeight(0);
//         // nblineVbox.setMaxHeight(codeContainer.getMinHeight());
//         // nblineVbox.setMinHeight(0);

//         // syncrhonise scrolling
//         codeScroller.vvalueProperty().bindBidirectional(bkScroller.vvalueProperty());
//         codeScroller.vvalueProperty().bindBidirectional(nblineScroller.vvalueProperty());
//     }

//     public void update(){
//         splitPane.setDividerPosition(0,MainController.getSplitPaneDividerPosition());
//         // console
//         this.consolePanel = MainController.getConsole();
//         this.consolePanel.setScrollTop(MainController.consoleScrollPosition);
//         // Scrollers
//         this.bkScroller = MainController.getBkScroller();
//         this.nblineScroller = MainController.getNblineScroller();
//         this.codeScroller = MainController.getCodeScroller();
//         //Vbox
//         this.codeContainer = MainController.getCodeContainer();
//         this.bkpointVbox = MainController.getBkpointVbox();
//         this.nblineVbox = MainController.getNblineVbox();
//     }


//     public void save(){

//         // save the current scene and controller
//         MainController.setSplitPaneDividerPosition(splitPane.getDividerPositions()[0]);
//         // console
//         MainController.setConsole(consolePanel);
//         // Scrollers
//         MainController.setBkscroller(bkScroller);
//         MainController.setNblineScroller(nblineScroller);
//         MainController.setCodeScroller(codeScroller);
//         //Vbox
//         MainController.setCodeContainer(codeContainer);
//         MainController.setBkpointVbox(bkpointVbox);
//         MainController.setNblineVbox(nblineVbox);
//         MainController.consoleScrollPosition = consolePanel.getScrollTop();
        
//     }


//     public void show() {
//         App.app.getStageWindow().show();
//     }

//     private void setNbLines() {
//         // set the minimum spacing, we can increase it though if required for the stop instructions
//         nblineVbox.setSpacing(0); 
        
//         for (int i = 1; i <= MainController.numberOfLines; i++) {
//             Label label = new Label(String.valueOf(i));
//             // label.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 1px;");
//             // label.setStyle("-fx-background-color: transparent;");
    
//             nblineVbox.getChildren().add(label);
//         }
//     }
    
//     private void setBreakPoints() {
        
//         // initialize set
//         if (MainController.bkpoints == null) MainController.bkpoints = new HashSet<>();
        
//         // slight disalignment t be fixed. Currently the best value to almost fit the other lines is 9
//         bkpointVbox.setSpacing(9.4);
//         bkpointVbox.setAlignment(Pos.TOP_CENTER); 
//         // bkScroller.setPadding(new Insets(5,0,5,0));

//         bkpointVbox.setStyle(bkpointVbox.getStyle() + "-fx-background-color: transparent;");
//         // bkpointVbox.setStyle("-fx-border-color: black;");

//         //size of the button
//         double buttonSize = 8; 


//         for (int i = 1; i <= MainController.numberOfLines; i++) {
//             Button bkButton = new Button(i + "");
//             // bkButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
            
//             // set style
//             bkButton.setMinSize(buttonSize, buttonSize);
//             bkButton.setMaxSize(buttonSize, buttonSize);
//             bkButton.setPrefWidth(buttonSize);
//             bkButton.setPrefHeight(buttonSize);
//             bkButton.setStyle("-fx-background-color: black; -fx-background-radius: 100%;");

//             // Action when a button is pressed
//             bkButton.setOnAction(event -> {

//                 // if disabled
//                 if (bkButton.getStyle().contains("black")) {
                    
//                     //set style
//                     bkButton.setStyle("-fx-background-color: red; -fx-background-radius: 50%;");
                    
//                     // add button to set of bkpoints
//                     MainController.bkpoints.add(Integer.parseInt(bkButton.getText()));
                
//                 // if enabled
//                 } else {
//                     //revert style
//                     bkButton.setStyle("-fx-background-color: black; -fx-background-radius: 50%;");
//                     // bkButton.setStyle("-fx-background-color: black; -fx-text-fill: blue; -fx-background-radius: 50%;");
//                     // remove
//                     MainController.bkpoints.remove(Integer.parseInt(bkButton.getText()));

//                 }
//             });
            
//             bkpointVbox.getChildren().add(bkButton);
//         }


//     }


//     void setPreviousState(){
//         splitPane.setDividerPosition(0,MainController.getSplitPaneDividerPosition());
//         // console
//         consolePanel.setText(MainController.getConsole().getText());
//         consolePanel.setScrollTop(MainController.consoleScrollPosition);

//         // Scrollers
//         codeScroller.setVvalue(MainController.getCodeScroller().getVvalue());
//         nblineScroller.setVvalue(MainController.getNblineScroller().getVvalue());
//         bkScroller.setVvalue(MainController.getBkScroller().getVvalue());

//         //Vbox
//         codeContainer.getChildren().setAll(MainController.getCodeContainer().getChildren());
//         nblineVbox.getChildren().setAll(MainController.getNblineVbox().getChildren());
//         bkpointVbox.getChildren().setAll(MainController.getBkpointVbox().getChildren());

//     }
    


// }

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
    @FXML private VBox codeContainer;
    @FXML private TextArea consolePanel;
    @FXML private AnchorPane leftControlsPanel;
    @FXML private Button startButton;
    @FXML private FXMLLoader loader;
    @FXML private SplitPane splitPane;
    @FXML private ScrollPane bkScroller;
    @FXML private ScrollPane nblineScroller;
    @FXML private ScrollPane codeScroller;
    @FXML private AnchorPane bottomPane;

    private MainController mainController;
    private App app;

    // ========================================TEST========================================================
    
    public static String code =  """
        n <- 10*10 / 5
        p <- 2
        compose <- 0
        n <- n+p
        """;
    
    // ================================================================================================

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public SplitPane getSplitPane() {
        return splitPane;
    }


    public void setSplitPaneDivider(double size) {
        this.splitPane.setDividerPosition(0, size);
    }


    @FXML
    void beginExecution(ActionEvent event) {
        app.getStageWindow().setTitle("En éxécution : " + mainController.nameFile);
    
        save();

        mainController.successScene = app.getStageWindow().getScene();
        mainController.successController = this;
        mainController.changeScene(Paths.DURING_EXECUTION);
        
    }


    @FXML
    void initialize() {

    }

    public void initialize2(MainController mainController, App app) {
        this.mainController = mainController;
        this.app = app; 

        // supprimer le background du TextField
        codeContainer.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-line-spacing: 60px;"); 
        codeScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
        bkScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
        nblineScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
        consolePanel.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
        bottomPane.setStyle("-fx-background-color : #5E7B9D");;

        consolePanel.setWrapText(true);
        
        // for testing, this is executed iff we start directly on this scene
        if (mainController.content == null ) {

            List<String> myTestList = new ArrayList<>();

            String testingText = code;
            
           
            for (String line : testingText.split("\n")){
                myTestList.add(line);
            }
            mainController.numberOfLines = myTestList.size();

            for (String line : myTestList){
                
                Label label = new Label(line);
                label.setMaxWidth(Double.MAX_VALUE);
                // label.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 1px;");
                codeContainer.getChildren().addAll(label);
            }

            mainController.content = myTestList;

        }

        else {
            for (String line : mainController.content){
                Label label = new Label(line);
                label.setMaxWidth(Double.MAX_VALUE);

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
        splitPane.setDividerPosition(0,mainController.getSplitPaneDividerPosition());
        // console
        this.consolePanel = mainController.getConsole();
        this.consolePanel.setScrollTop(mainController.consoleScrollPosition);
        // Scrollers
        this.bkScroller = mainController.getBkScroller();
        this.nblineScroller = mainController.getNblineScroller();
        this.codeScroller = mainController.getCodeScroller();
        //Vbox
        this.codeContainer = mainController.getCodeContainer();
        this.bkpointVbox = mainController.getBkpointVbox();
        this.nblineVbox = mainController.getNblineVbox();
    }


    public void save(){

        // save the current scene and controller
        mainController.setSplitPaneDividerPosition(splitPane.getDividerPositions()[0]);
        // console
        mainController.setConsole(consolePanel);
        mainController.consoleScrollPosition = consolePanel.getScrollTop();
        // Scrollers
        mainController.setBkscroller(bkScroller);
        mainController.setNblineScroller(nblineScroller);
        mainController.setCodeScroller(codeScroller);
        //Vbox
        mainController.setCodeContainer(codeContainer);
        mainController.setBkpointVbox(bkpointVbox);
        mainController.setNblineVbox(nblineVbox);
        
    }


    public void show() {
        app.show();
    }

    private void setNbLines() {
        // set the minimum spacing, we can increase it though if required for the stop instructions
        nblineVbox.setSpacing(0); 
        
        for (int i = 1; i <= mainController.numberOfLines; i++) {
            Label label = new Label(String.valueOf(i));
            // label.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 1px;");
            // label.setStyle("-fx-background-color: transparent;");
    
            nblineVbox.getChildren().add(label);
        }
    }
    
    private void setBreakPoints() {
        
        // initialize set
        if (mainController.bkpoints == null) mainController.bkpoints = new HashSet<>();
        
        // TODO slight disalignment t be fixed. Currently the best value to almost fit the other lines is 9
        bkpointVbox.setSpacing(9.4);
        bkpointVbox.setAlignment(Pos.TOP_CENTER); 
        // bkScroller.setPadding(new Insets(5,0,5,0));

        bkpointVbox.setStyle(bkpointVbox.getStyle() + "-fx-background-color: transparent;");
        // bkpointVbox.setStyle("-fx-border-color: black;");

        //size of the button
        double buttonSize = 8; 


        for (int i = 1; i <= mainController.numberOfLines; i++) {
            Button bkButton = new Button(i + "");
            // bkButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
            
            // set style
            bkButton.setMinSize(buttonSize, buttonSize);
            bkButton.setMaxSize(buttonSize, buttonSize);
            bkButton.setPrefWidth(buttonSize);
            bkButton.setPrefHeight(buttonSize);
            bkButton.setStyle("-fx-background-color: black; -fx-background-radius: 100%;");

            // Action when a button is pressed
            bkButton.setOnAction(event -> {

                // if disabled
                if (bkButton.getStyle().contains("black")) {
                    
                    //set style
                    bkButton.setStyle("-fx-background-color: red; -fx-background-radius: 50%;");
                    
                    // add button to set of bkpoints
                    mainController.bkpoints.add(Integer.parseInt(bkButton.getText()));
                
                // if enabled
                } else {
                    //revert style
                    bkButton.setStyle("-fx-background-color: black; -fx-background-radius: 50%;");
                    // bkButton.setStyle("-fx-background-color: black; -fx-text-fill: blue; -fx-background-radius: 50%;");
                    // remove
                    mainController.bkpoints.remove(Integer.parseInt(bkButton.getText()));

                }
            });
            
            bkpointVbox.getChildren().add(bkButton);
        }


    }


    void setPreviousState(){
        splitPane.setDividerPosition(0,mainController.getSplitPaneDividerPosition());
        // console
        consolePanel.setText(mainController.getConsole().getText());
        consolePanel.setScrollTop(mainController.consoleScrollPosition);

        // Scrollers
        codeScroller.setVvalue(mainController.getCodeScroller().getVvalue());
        nblineScroller.setVvalue(mainController.getNblineScroller().getVvalue());
        bkScroller.setVvalue(mainController.getBkScroller().getVvalue());

        //Vbox
        codeContainer.getChildren().setAll(mainController.getCodeContainer().getChildren());
        nblineVbox.getChildren().setAll(mainController.getNblineVbox().getChildren());
        bkpointVbox.getChildren().setAll(mainController.getBkpointVbox().getChildren());

    }
    
    public void setApp(App app) {
        this.app = app;
    }

    public MainController getMainController() {
        return mainController;
    }

}
