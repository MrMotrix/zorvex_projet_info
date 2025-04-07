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
    
    
    public static String code1 =  """
    x <- [2, "coucou ca", 3]
    ajouter_liste(x, 4)
    inserer_liste(x, 1, "insertion")
    supprimer_liste(x, 2)
    recuperer_liste(x, 2)
    modifier_liste(x, 1, "modification")
    taille <- taille_liste(x)
    fonction fibo(n) {
        si n = 0 {
            retourner 0
        }
        si n = 1 {
            retourner 1
        }
        retourner fibo(n-1) + fibo(n-2)
    }
    afficher fibo(10)
            """;
            
    public static String code2 =  """
        fonction sum(a,b) {
            retourner a+b
            }
        afficher sum(1,2)
            """;

    public static String code3 =  """
        fonction maFonction(a,b) {
            retourner a * b
        }
        
        var <- 0
        monTableau <- [1,2,3,4]
        maPile <- pile()
        maListeChainee <- liste_chainee()
        
            """;
            
    public static String code = code1;
    // ================================================================================================

    public successFileUploadController() {
        // Default constructor
        this.consolePanel = new TextArea();
        this.bkScroller = new ScrollPane();
        this.nblineScroller = new ScrollPane();
        this.codeScroller = new ScrollPane();
        this.codeContainer = new VBox();
        this.bkpointVbox = new VBox();
        this.nblineVbox = new VBox();
        this.splitPane = new SplitPane();
        this.leftControlsPanel = new AnchorPane();
        this.startButton = new Button();
        this.bottomPane = new AnchorPane();
        this.splitPane = new SplitPane();
    }


    public void setMainController(MainController mainController) {this.mainController = mainController;}
    
    public SplitPane getSplitPane() {return splitPane;}

    public void setSplitPaneDivider(double size) {this.splitPane.setDividerPosition(0, size);}

    @FXML
    void beginExecution(ActionEvent event) {
        app.getStageWindow().setTitle("En éxécution : " + mainController.nameFile);
    
        save();

        mainController.successScene = app.getStageWindow().getScene();
        mainController.successController = this;
        mainController.changeScene(Paths.DURING_EXECUTION);
        
    }


    @FXML
    void initialize() {}

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
        if (mainController.content.isEmpty()) {

            List<String> myTestList = new ArrayList<>();

            String testingText = code;
            
            for (String line : testingText.split("\n")){
                myTestList.add(line);
            }

            mainController.numberOfLines = myTestList.size();
            mainController.content = myTestList;
        }
        
        setContentLabels(mainController);
        // set nblines and breakpoints
        setNbLines();
        setBreakPoints();
        
        // syncrhonise scrolling
        codeScroller.vvalueProperty().bindBidirectional(bkScroller.vvalueProperty());
        codeScroller.vvalueProperty().bindBidirectional(nblineScroller.vvalueProperty());
    }

    private void setContentLabels(MainController mainController) {
        codeContainer.getChildren().clear();
        for (String line : mainController.content){
            Label label = new Label(line);
            label.setMaxWidth(Double.MAX_VALUE);

            codeContainer.getChildren().addAll(label);
        }
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
    
            nblineVbox.getChildren().add(label);
        }
    }
    
    private void setBreakPoints() {
        
        // initialize set
        if (mainController.bkpoints == null) mainController.bkpoints = new HashSet<>();
        
        // TODO slight disalignment t be fixed. Currently the best value to almost fit the other lines is 9
        bkpointVbox.setSpacing(9.4);
        bkpointVbox.setAlignment(Pos.TOP_CENTER); 

        bkpointVbox.setStyle(bkpointVbox.getStyle() + "-fx-background-color: transparent;");

        //size of the button
        double buttonSize = 8; 


        for (int i = 1; i <= mainController.numberOfLines; i++) {
            Button bkButton = new Button(i + "");
            
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
                    bkButton.setStyle("-fx-background-color: red; -fx-background-radius: 50%;-fx-text-fill: red;");
                    // add button to set of bkpoints
                    mainController.bkpoints.add(Integer.parseInt(bkButton.getText()));
                
                // if enabled
                } else {
                    //revert style
                    bkButton.setStyle("-fx-background-color: black; -fx-background-radius: 50%;-fx-text-fill: black;");
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
