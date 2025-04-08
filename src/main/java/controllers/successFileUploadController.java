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
        fonction fibo(n) {
                afficher \"hello world\"
                si n = 0 {
                    retourner 0
                }
                si n = 1 {
                    retourner 1
                }
                retourner fibo(n-1) + fibo(n-2)
            }
            fonction ack(m,n) {
                    si m = 0 {
                        retourner n+1
                    }
                    si n = 0 {
                        retourner ack(m-1, 1)
                    }
                    retourner ack(m-1, ack(m, n-1))
                }
            fonction tri(l) {
                i <- taille_liste(l)-1
                tant que i >= 1 {
                    j <- 0
                    tant que j < i {
                        si recuperer_liste(l, j+1) < recuperer_liste(l, j) {
                            x <- recuperer_liste(l, j)
                            y <- recuperer_liste(l, j+1)
                            modifier_liste(l, j, y)
                            modifier_liste(l, j+1, x)
                        }
                        j <- j + 1
                    }
                    i <- i-1
                }
            }

            fonction argmax(l) {
                n <- taille_liste(l)
                x <- 0
                i <- 0
                tant que i < n {
                    si recuperer_liste(l, i) > recuperer_liste(l, x) {
                        x <- i
                    }
                    i <- i+1
                }
                retourner x
            }

            x <- pile_vide()
            empiler(x, 5)
            empiler(x, 2)
            empiler(x, 3)
            y <- depiler(x)
            depiler(x)
            depiler(x)
            si est_pile_vide(x) {
                afficher \"ok\"
            }
            z <- x+\" coucou\"
            afficher y
            afficher x
            afficher z
            """;

    public static String code3 =  """
        maPile <- pile_vide()
        empiler(maPile, 1)
        afficher(est_pile_vide(maPile))
        empiler(maPile, 2)
        depiler(maPile)
        depiler(maPile)
        afficher(est_pile_vide(maPile))

        
            """;
    public static String code4 =  """
            fonction fibo(n) {
            afficher "hello world"
            si n = 0 {
                retourner 0   
            }
            si n = 1 {
                retourner 1
            }
            retourner fibo(n-1) + fibo(n-2)
        }
        fonction ack(m,n) {
            si m = 0 {
                retourner n+1
            }
            si n = 0 {
                retourner ack(m-1, 1)
            }
            retourner ack(m-1, ack(m, n-1))
        } 

        fonction tri(l) {
            i <- taille_liste(l)-1
            tant que i >= 1 {
                j <- 0
                tant que j < i {
                    si recuperer_liste(l, j+1) < recuperer_liste(l, j) {
                        x <- recuperer_liste(l, j)
                        y <- recuperer_liste(l, j+1)
                        modifier_liste(l, j, y)
                        modifier_liste(l, j+1, x)
                    }
                    j <- j + 1
                }
                i <- i-1
            }
        }

        fonction argmax(l) {
            n <- taille_liste(l)
            x <- 0
            i <- 0
            tant que i < n {
                si recuperer_liste(l, i) > recuperer_liste(l, x) {
                    x <- i
                }
                i <- i+1
            }
            retourner x
        }

        x <- [7,8,52,3,4,15]
        tri(x)
        afficher(x)
    """;
          
    
    public static String code5 = """
            fonction bidon(l){
                afficher(l)
            }
            a <- [1,2,3,4]
            bidon(a)     
            afficher(a)       
            """;
            
            
    public static String code = code2;
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


    public MainController getMainController() {return mainController;}
    public SplitPane getSplitPane() {return splitPane;}
    public void setMainController(MainController mainController) {this.mainController = mainController;}
    public void setSplitPaneDivider(double size) {this.splitPane.setDividerPosition(0, size);}
    public void setApp(App app) {this.app = app;}
    /**
     * Start the execution of the program.
     * @param event
     */
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
    /**
     * * This method is used to initialize the controller with the main controller and the application instance. Shuld be called after the FXML file is loaded.
     * @param mainController
     * @param app
     */
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

    /**
     * Set the content of the code container with the lines of code.
     * @param mainController
     */
    private void setContentLabels(MainController mainController) {
        codeContainer.getChildren().clear();
        for (String line : mainController.content){
            Label label = new Label(line);
            label.setMaxWidth(Double.MAX_VALUE);

            codeContainer.getChildren().addAll(label);
        }
    }

    /**
     * Update the current state of the application. Called when the user changes the scene from another tho this one.
     */
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

    /**
     * Save the current state of the application.
     */
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
    /**
     * Set the breakpoints in the code container. The breakpoints are represented by buttons that can be clicked to enable or disable them.
     */
    private void setBreakPoints() {
        
        // initialize set
        if (mainController.bkpoints == null) mainController.bkpoints = new HashSet<>();
        
        // slight disalignment t be fixed. Currently the best value to almost fit the other lines is 9
        bkpointVbox.setSpacing(9.45);
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
    /**
     * Set the previous state of the application.
     */
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
}
