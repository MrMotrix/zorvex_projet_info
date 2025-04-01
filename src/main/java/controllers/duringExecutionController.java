//         // ============================================ TESTING ============================================================================================
//         // ============================================ CASE 1 : random remove of elements from a graphical representation======================================================================        
//         if (TESTING_INDEX == 1){

//             // random test to add a var, an array or a llist
//             switch (random.nextInt(3)) {
//                 case 0 -> rep.addElement(new GraphicalVar("Line", MainController.currentLine + "", canvasPane));
//                 case 1 -> rep.addElement(new GraphicalArray("myArray_done_in_line" + MainController.currentLine, new String[]{"value1", "value2", "value3"}, canvasPane));
//                 case 2 -> rep.addElement(new GraphicalLinkedList("myLinkedList", new String[]{"v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8", "v9", "v10", "v11"}, canvasPane)); // just to see what happens if there is not enough space
//             }
//         //========================================================== CASE 2 :  add and remove elements from a linked list ======================================================================
//         } else if (TESTING_INDEX == 2){

//             if(MainController.currentLine < 6) {
//                 // rep.addElement(new GraphicalArray("myArray_done_in_line" + MainController.currentLine, new String[]{"value1", "value2", "value3"}, canvasPane));
//                 rep.addElement(new GraphicalLinkedList("myLinkedList" + MainController.currentLine, new String[]{"v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8", "v9", "v10", "v11"}, canvasPane)); 
//             }
//             else if (MainController.currentLine < 12){
//                 GraphicalLinkedList g = (GraphicalLinkedList) rep.getElements().get(4);
//                 int index = MainController.currentLine + random.nextInt(4);
//                 g.addNodeAt(index, MainController.currentLine + "");
//                 // rep.addElement((GraphicalLinkedList)(rep.getElements().getLast()).get(index));
//             } else if (MainController.currentLine < 20){
//                 GraphicalLinkedList g = (GraphicalLinkedList) rep.getElements().get(4);
//                 int index = random.nextInt(12);
//                 sendMessageToConsole("Delete position" + index);
//                 g.deleteNodeAt(index);
                                
//             } else {
//                 rep.deleteElement(rep.getElements().getFirst());
//             }
//         //========================================================== CASE 3 : add and remove elements from an array  ======================================================================

//         } else if (TESTING_INDEX == 3){
//             if(MainController.currentLine < 6) {

//                 rep.addElement(new GraphicalArray("myGraphicalArray" + MainController.currentLine, "Hello worlds this is an array test".split(" "), canvasPane)); 
                
//             } else if (MainController.currentLine < 12){
//                 GraphicalArray g = (GraphicalArray) rep.getElements().get(4);
//                 g.addNodeAt(2, "test" + random.nextInt(10));

//             } else if (MainController.currentLine < 20){
//                 GraphicalArray g = (GraphicalArray) rep.getElements().get(4);
//                 int index = random.nextInt(12);
//                 sendMessageToConsole("Delete position" + index);
//                 g.deleteNodeAt(index);

//             } else {
//                 rep.deleteElement(rep.getElements().getFirst());
//             }
//         //========================================================== CASE 4 : modify the value of a variable  ======================================================================

//         } else if (TESTING_INDEX == 4){
//             if(MainController.currentLine < 6) {

//                 rep.addElement(new GraphicalVar("myVar" + MainController.currentLine, "value" + MainController.currentLine, canvasPane)); 
                
//             } else {
//                 int index = random.nextInt(5);
//                 sendMessageToConsole("Variable modified" + index);
//                 rep.getElements().get(index).update(0, MainController.currentLine + "");;

//             }
//             //========================================================== CASE 5 : modify the value of an array at a given index  ======================================================================

//         } else if (TESTING_INDEX == 5){
//             if(MainController.currentLine < 6) {

//                 rep.addElement(new GraphicalArray("myGraphicalArray" + MainController.currentLine, "Hello worlds this is an array test".split(" "), canvasPane)); 
                
//             } else if (MainController.currentLine < 12){
//                 int index = random.nextInt(7);
//                 sendMessageToConsole("Index modified in the last array" + index);
//                 rep.getElements().getLast().update(index, MainController.currentLine + "");;

//             } else if (MainController.currentLine < 20){

//                 GraphicalArray g = (GraphicalArray) rep.getElements().getLast();
//                 g.addNodeAt(random.nextInt(g.size()), "test" + MainController.currentLine);
            
//             } else if (MainController.currentLine < 25){
//                 int index = random.nextInt(7);
//                 sendMessageToConsole("Index modified in the last array" + index);
//                 rep.getElements().getLast().update(MainController.currentLine - 20, MainController.currentLine + "");;

//             }
        

//         //========================================================== CASE 6 : modify the value of a linked list at a given index  ======================================================================

//         } else if (TESTING_INDEX == 6){
//             if(MainController.currentLine < 6) {

//                 rep.addElement(new GraphicalLinkedList("myLinkedList" + MainController.currentLine, "Hello worlds this is an llist test".split(" "), canvasPane)); 
                
//             } else if (MainController.currentLine < 12){
//                 int index = random.nextInt(7);
//                 sendMessageToConsole("Index modified in the last llist" + index);
//                 rep.getElements().getLast().update(index, MainController.currentLine + "");;

//             } else if (MainController.currentLine < 20){

//                 GraphicalLinkedList g = (GraphicalLinkedList) rep.getElements().getLast();
//                 g.addNodeAt(random.nextInt(g.size()), "test" + MainController.currentLine);
            
//             } else if (MainController.currentLine < 25){
//                 int index = random.nextInt(7);
//                 sendMessageToConsole("Index modified in the last array" + index);
//                 rep.getElements().getLast().update(MainController.currentLine - 20, MainController.currentLine + "");;

//             }
//         }
            
//         //===========================================================END TEST=========================================================================
//     }


package controllers;

import java.util.Random;

import application.App;
import graphics.GraphicalRepresentation;
import graphics.GraphicalVar;
import graphics.ModificationType;
import interpreter.Interpreter;
import interpreter.instruction.Instruction;
import interpreter.instruction.Afficher;
import interpreter.instruction.Assigner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class duringExecutionController  {

    // ======================== FXML ========================
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
    @FXML private AnchorPane bottomPane;
    
    // ======================== ATTRIBUTES ========================
    private int currentHighlightedLine = 0;
    private boolean firstLineRead = false;
    private boolean reverse = false;
    private String contentString;
    private successFileUploadController successController;
    private GraphicalRepresentation rep = new GraphicalRepresentation();
    private Interpreter interpreter;
    private MainController mainController;
    private App app;

    // ======================== GETTERS ========================
    public VBox getCodeContainer() {return codeContainer;}
    public TextArea getConsolePanel() {return consolePanel;}
    public Button getContinueButton() {return continueButton;}
    public Button getLastLineButton() {return lastLineButton;}
    public Button getNextLineButton() {return nextLineButton;}
    public Button getRestartButton() {return restartButton;}
    public Button getStopButton() {return stopButton;}
    public VBox getBkpointVbox() {return bkpointVbox;}
    public VBox getNblineVbox() {return nblineVbox;}
    public AnchorPane getLeftControlsPanel() {return leftControlsPanel;}
    public Button getStartButton() {return startButton;}
    public FXMLLoader getLoader() {return loader;}
    public SplitPane getSplitPane() {return splitPane;}
    public ScrollPane getBkScroller() {return bkScroller;}
    public ScrollPane getNblineScroller() {return nblineScroller;}
    public ScrollPane getCodeScroller() {return codeScroller;}
    public Pane getCanvasPane() {return canvasPane;}
    public AnchorPane getBottomPane() {return bottomPane;}
    public int getCurrentHighlightedLine() {return currentHighlightedLine;}
    public MainController getMainController() {return mainController;} 
    public App getApp(){return this.app;}

    // =============================== SETTERS ===============================
    public void setCodeContainer(VBox codeContainer) {this.codeContainer = codeContainer;}
    public void setConsolePanel(TextArea consolePanel) {this.consolePanel = consolePanel;}
    public void setContinueButton(Button continueButton) {this.continueButton = continueButton;}
    public void setLastLineButton(Button lastLineButton) {this.lastLineButton = lastLineButton;}
    public void setNextLineButton(Button nextLineButton) {this.nextLineButton = nextLineButton;}
    public void setRestartButton(Button restartButton) {this.restartButton = restartButton;}
    public void setStopButton(Button stopButton) {this.stopButton = stopButton;}
    public void setBkpointVbox(VBox bkpointVbox) {this.bkpointVbox = bkpointVbox;}
    public void setNblineVbox(VBox nblineVbox) {this.nblineVbox = nblineVbox;}
    public void setLeftControlsPanel(AnchorPane leftControlsPanel) {this.leftControlsPanel = leftControlsPanel;}
    public void setStartButton(Button startButton) {this.startButton = startButton;}
    public void setLoader(FXMLLoader loader) {this.loader = loader;}
    public void setSplitPane(SplitPane splitPane) {this.splitPane = splitPane;}
    public void setBkScroller(ScrollPane bkScroller) {this.bkScroller = bkScroller;}
    public void setNblineScroller(ScrollPane nblineScroller) {this.nblineScroller = nblineScroller;}
    public void setCodeScroller(ScrollPane codeScroller) {this.codeScroller = codeScroller;}
    public void setCanvasPane(Pane canvasPane) {this.canvasPane = canvasPane;}
    public void setBottomPane(AnchorPane bottomPane) {this.bottomPane = bottomPane;}    
    
    // TEST, so this can be deleteed later =================================================================
    Random random = new Random();
    int TESTING_INDEX = 5;
    private ExecutionStack record;
    private int currentLine;
    // ==============================================================================


    // under normal circcumstances this constructor is never used, but if somehow it is, it will not crash the program since nothing will be null
    public duringExecutionController(MainController mainController, App app) {
        this.mainController = mainController;
        this.app = app;
        this.consolePanel = new TextArea();
        this.codeContainer = new VBox();
        this.bkpointVbox = new VBox();
        this.nblineVbox = new VBox();
        this.splitPane = new SplitPane();
        this.canvasPane = new Pane();
        this.rep = new GraphicalRepresentation();      
        this.codeScroller = new ScrollPane();
        this.bkScroller = new ScrollPane();
        this.nblineScroller = new ScrollPane();
        this.leftControlsPanel = new AnchorPane();
        this.bottomPane = new AnchorPane();
        this.continueButton = new Button();
        this.restartButton = new Button();
        this.nextLineButton = new Button();
        this.lastLineButton = new Button();
        this.stopButton = new Button();
        this.record = new ExecutionStack();

        setPreviousState();
    }

    public duringExecutionController(){}

    /**
     * This method is executed at the beginning of the program, when the scene has just been changed. It is also
     * executed when the user presses the continue button. 
     * @param event
     */
    @FXML
    void continueExecution(ActionEvent event) {
        // ================================================================OLD IMPLEMENTATION============================================================================================================================
        /*
        //  if the button was pressed when being in a breakpoint and we are not in the first line (if there is a bp in the first line we should be able to stop)
        try{
            if (mainController.currentLine == 1 && mainController.bkpoints.contains(1) && !firstLineRead) {
            highlightCurrentLine(currentHighlightedLine);
            // sendMessageToConsole("Breakpoint reached at line 1");
            firstLineRead = true;
            return;
            }
        
            if (mainController.bkpoints.contains(mainController.currentLine)) {
                goNextLine(event);
            }
        
            while (!mainController.bkpoints.contains(mainController.currentLine) && mainController.currentLine != mainController.numberOfLines + 1) {
                goNextLine(event);
            }
            // this handles the case when we have reached the end of the file. Basically, it disables the buttons and removes any possible remaining highlight. This is also treated in the goNextLine method
            if (mainController.currentLine == mainController.numberOfLines + 1){
                // sendMessageToConsole("Fin du fichier atteint");
                currentHighlightedLine++;
                highlightCurrentLine(currentHighlightedLine);
                
                // Disable buttons because we are in the end of the file
                continueButton.setDisable(true);
                nextLineButton.setDisable(true);
            } 
        } catch (Exception e){

            return;
        }
        */
        // ============================================================================================================================================================================================

                //  if the button was pressed when being in a breakpoint and we are not in the first line (if there is a bp in the first line we should be able to stop)
                currentLine = interpreter.getCurrentLine();
                try{
                    if (currentLine == 1 && mainController.bkpoints.contains(1) && !firstLineRead) {
                    highlightCurrentLine(currentLine - 1);
                    // sendMessageToConsole("Breakpoint reached at line 1");
                    firstLineRead = true;
                    // currentLine = interpreter.getCurrentLine();

                    return;
                    }
                
                    if (mainController.bkpoints.contains(currentLine)) {
                        goNextLine(event);
                        currentLine = interpreter.getCurrentLine();

                    }
                
                    while (!mainController.bkpoints.contains(currentLine) && currentLine != mainController.numberOfLines + 1) {
                        goNextLine(event);
                    currentLine = interpreter.getCurrentLine();

                    }
                    // this handles the case when we have reached the end of the file. Basically, it disables the buttons and removes any possible remaining highlight. This is also treated in the goNextLine method
                    if (currentLine == mainController.numberOfLines + 1){
                        // sendMessageToConsole("Fin du fichier atteint");
                        currentHighlightedLine++;
                        highlightCurrentLine(currentHighlightedLine);
                        
                        // Disable buttons because we are in the end of the file
                        continueButton.setDisable(true);
                        nextLineButton.setDisable(true);
                    } 
                } catch (Exception e){
        
                    return;
                }
    }
    // TODO : something has still to be implemented here. Currently it just erases something random from the plot and goes to the last line
    @FXML
    void goLastLine(ActionEvent event) {

        // ============================================ OLD IMPLEMENTATION ============================================================================================
        /*
        record.undo(rep);

        
        // DO NOT TOUCH preferably :)
        mainController.currentLine--;
        if (! reverse) {
            reverse = true;
            mainController.currentLine --;
        }
        highlightCurrentLine(mainController.currentLine);
        sendMessageToConsole("Last line executed : " + mainController.currentLine);
        */
        // ========================================================================================================================================


        record.undo(rep);

        
        // DO NOT TOUCH preferably :)
        currentLine--;
        if (! reverse) {
            reverse = true;
            currentLine --;
        }
        highlightCurrentLine(currentLine);
        sendMessageToConsole("Last line executed : " + currentLine);
        
    }

    /**
     * This method is used to go to the next line of the code. It is called when the user presses the "Next" button, and also it is called multiple times when the continue button is pressed. 
     * @param event
     */
    @FXML
    void goNextLine(ActionEvent event) {
        // ===================================================================== OLD IMPLEMENTATION============================================================================================================
        /* // if we have finished execution, we return
        if (mainController.currentLine > mainController.numberOfLines) return;
        
        if (reverse) {
            reverse = false;
            mainController.currentLine++;
        }
        // ===================================HERE GOES THE IMPLEMENTATION TO READ INSTRUCTIONS=========================================
        
        try {    
            // get the affected variable from the interpreter
            //TODO : ca serait bon que l-interpreteur ait une facon de set la ligne qu-il lit, ainsi quand on fait un retour en arriere, on va aussi a linstruction precedente que l-interpreteur a lu
            String var = interpreter.step();

            
            if (var != null && !var.isEmpty()) {

             
                String value = interpreter.getVariable(var).toString();
                value = value.substring(value.indexOf(" ") + 1);
                

                // add the variable to the graphical representation or check if it already exists and update it
                if (rep.getElements().containsKey(var)) {
                    record.push(new ModifyVarRecord(var, ((GraphicalVar)(rep.getElement(var))).getValue()));
                    rep.updateElement(var, ModificationType.UPDATE, value, 0);

                } else {
                    GraphicalVar temp = new GraphicalVar(var, value, canvasPane);
                    rep.addElement(var, temp);
                    record.push(new CreateRecord(var, temp));
                }
            }

        } catch (Exception e) {
            sendMessageToConsole("Erreur dans la ligne " + mainController.currentLine + ": " + e.getMessage());
            highlightCurrentLine(mainController.currentLine - 1);
            continueButton.setDisable(true);
            nextLineButton.setDisable(true);
            throw e;
        }
        highlightCurrentLine(mainController.currentLine);
        
        // this should be the last line of the method, we only increase the pointer to the current line once it has been correctly interpreted
        mainController.currentLine++;
    
        if (mainController.currentLine == mainController.numberOfLines + 1) {
            sendMessageToConsole("Fin du fichier atteint");
            // Disable buttons because we are in the end of the file
            continueButton.setDisable(true);
            nextLineButton.setDisable(true);
        } */

        // =========================================================================================================================================================================================
        currentLine = interpreter.getCurrentLine();

        // if we have finished execution, we return
        if (currentLine > mainController.numberOfLines) return;
        
        if (reverse) {
            reverse = false;
            currentLine++;
        }
        // ===================================HERE GOES THE IMPLEMENTATION TO READ INSTRUCTIONS=========================================
        
        try {    
            // get the affected variable from the interpreter
            //TODO : ca serait bon que l-interpreteur ait une facon de set la ligne qu-il lit, ainsi quand on fait un retour en arriere, on va aussi a linstruction precedente que l-interpreteur a lu
            Instruction inst = interpreter.step();

            if (inst instanceof Assigner assigner) {        
                String var = assigner.variableName();     
                String value = interpreter.getVariable(var).toString();
                value = value.substring(value.indexOf(" ") + 1);
                

                // add the variable to the graphical representation or check if it already exists and update it
                if (rep.getElements().containsKey(var)) {
                    record.push(new ModifyVarRecord(var, ((GraphicalVar)(rep.getElement(var))).getValue()));
                    rep.updateElement(var, ModificationType.UPDATE, value, 0);

                } else {
                    GraphicalVar temp = new GraphicalVar(var, value, canvasPane);
                    rep.addElement(var, temp);
                    record.push(new CreateRecord(var, temp));
                }
            }
            else if (inst instanceof Afficher afficher) {
                sendMessageToConsole(afficher.result().asString());
            }

        } catch (Exception e) {
            sendMessageToConsole("Erreur dans la ligne " + currentLine + ": " + e.getMessage());
            highlightCurrentLine(currentLine - 1);
            continueButton.setDisable(true);
            nextLineButton.setDisable(true);
            throw e;
        }
        highlightCurrentLine(interpreter.getCurrentLine()-1);
        
        
        if (interpreter.getCurrentLine() == mainController.numberOfLines) {
            sendMessageToConsole("Fin du fichier atteint");
            // Disable buttons because we are in the end of the file
            continueButton.setDisable(true);
            nextLineButton.setDisable(true);
        }
    }
        

    @FXML
    void restartExecution(ActionEvent event) {
        // ===================================================================== OLD IMPLEMENTATION============================================================================================================

/*         sendMessageToConsole("On redemarre l'éxécution");

        // reinitialize the defaiult values of the position magager 
        rep.reinitializePositioningValues();
        
        // Clean plots from panel
        rep.clear();

        // Reset the curerntline
        mainController.currentLine = 1;
        // clean highlights if any
        highlightCurrentLine(0); 
        // restart checker of first line breakpoint
        firstLineRead = false;

        //reset interpreter
        try {
            interpreter= new Interpreter(contentString);
        }
        catch (Exception e) {
            handleParsingException(e);
        }

        // restart state of buttons
        continueButton.setDisable(false);
        nextLineButton.setDisable(false);
        //restart execution
        continueExecution(event); */
        // =================================================================================================================================================================================

        sendMessageToConsole("On redemarre l'éxécution");

        // reinitialize the defaiult values of the position magager 
        rep.reinitializePositioningValues();
        
        // Clean plots from panel
        rep.clear();

        // clean highlights if any
        highlightCurrentLine(0); 
        // restart checker of first line breakpoint
        firstLineRead = false;

        //reset interpreter
        try {
            interpreter= new Interpreter(contentString);
        }
        catch (Exception e) {
            handleParsingException(e);
        }

        // restart state of buttons
        continueButton.setDisable(false);
        nextLineButton.setDisable(false);
        //restart execution
        continueExecution(event);

    }

    /**
     * This method is used to handle the parsing exception. It is called when the user presses the "Stop" button, and also it is called when the user presses the "Continue" button and there is an error in the code.
     * @param event
     */
    @FXML
    void stopExecution(ActionEvent event) {
        // ===================================================================== OLD IMPLEMENTATION============================================================================================================

        /* if (currentHighlightedLine >= 0 && currentHighlightedLine < codeContainer.getChildren().size()) {
            Node node = codeContainer.getChildren().get(currentHighlightedLine);
            node.setStyle("");
            currentHighlightedLine = 0;
        }

        // reset values for rendering
        rep.reinitializePositioningValues();
        rep.clear();
        // set the positino of the divider
        mainController.setSplitPaneDividerPosition(splitPane.getDividerPositions()[0]);
        // reset the state of the buttons
        continueButton.setDisable(false);
        nextLineButton.setDisable(false);

        // save the current state in mainController
        save();
        successController.setPreviousState();
        mainController.currentLine = 1;
        firstLineRead = false;

        // reset interpreter
        // ici s'il te plait il faudrait faire en sorte qu'on revienne a l'état d'avant le moment ou on a
        // creer un interpréteur, sans en créer un nouveau
        // idéalement il faudrait que le code s'affiche quand même.

        // mainController.bkpoints.forEach(System.out::println);
        // reuse the last scene
        app.setScene(mainController.successScene); */
        
        // =================================================================================================================================================================================

        if (currentHighlightedLine >= 0 && currentHighlightedLine < codeContainer.getChildren().size()) {
            Node node = codeContainer.getChildren().get(currentHighlightedLine);
            node.setStyle("");
            currentHighlightedLine = 0;
        }

        // reset values for rendering
        rep.reinitializePositioningValues();
        rep.clear();
        // set the positino of the divider
        mainController.setSplitPaneDividerPosition(splitPane.getDividerPositions()[0]);
        // reset the state of the buttons
        continueButton.setDisable(false);
        nextLineButton.setDisable(false);

        // save the current state in mainController
        save();
        successController.setPreviousState();
        firstLineRead = false;

        // reuse the last scene
        app.setScene(mainController.successScene);

    }

    private void save(){
        // save the current scene and controller
        mainController.setSplitPaneDividerPosition(splitPane.getDividerPositions()[0]);
        // console
        mainController.setConsole(consolePanel);
        mainController.setConsoleScrollPosition(consolePanel.getScrollTop());
        // Scrollers
        mainController.setBkscroller(bkScroller);
        mainController.setNblineScroller(nblineScroller);
        mainController.setCodeScroller(codeScroller);
        //Vbox
        mainController.setCodeContainer(codeContainer);
        mainController.setBkpointVbox(bkpointVbox);
        mainController.setNblineVbox(nblineVbox);

    }

    @FXML
    void initialize() {}
    
    /**
     * This method is executed when the scene is loaded. It is used to set the initial values of the scene and to set the previous state of the scene.
     * It is called after "initialize" so a mainController object can be created in the App class and passed to this controller.
     */
    public void initialize2(MainController mainController, App app) throws Exception {
        this.mainController = mainController;
        this.app = app;

        record = new ExecutionStack();

        setInitialVisuals();        
        setPreviousState();
        // this has to be initializes with this format because creating a simple strign joining the lines with \n without formatting produces an error
        contentString = """
                %s
                """.formatted(String.join("\n", mainController.content));


        this.successController = mainController.successController;
        // TODO : implementer gestion des exceptions
        // partiellement fait
        try{
            interpreter = new Interpreter(contentString);
        } catch (Exception e){
            handleParsingException(e);
            throw e; // as initialize2 is called in the main controller, we need to throw the exception to stop the execution of the program. Visuals are already charged, we just need to avoid rendering the duringExecution scene and stay in a successUpload scene
        }

        // after doing all the settings, we start the exeution by calling continueExecution
        continueExecution(null);
    }

    private void setInitialVisuals() {
        // codeContainer.setEditable(false);
        codeContainer.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"); // supprimer le background du TextField
        // TODO : probably this is factorizable with css
        codeScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
        bkScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
        nblineScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
        consolePanel.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
        canvasPane.setStyle("-fx-background-color: transparent;");
        bottomPane.setStyle("-fx-background-color : #5E7B9D");;

        
        // set the labels for every button
        continueButton.setText("C");
        lastLineButton.setText("<");
        nextLineButton.setText(">");   
        restartButton.setText("R");    
        stopButton.setText("A");

        consolePanel.setWrapText(true);

        continueButton.setStyle(continueButton.getStyle() + "-fx-background-color: green; -fx-text-fill: white;");
        lastLineButton.setStyle(lastLineButton.getStyle() + "-fx-background-color: black; -fx-text-fill: white");
        nextLineButton.setStyle(nextLineButton.getStyle() + "-fx-background-color: black; -fx-text-fill: white");
        restartButton.setStyle(restartButton.getStyle() + "-fx-background-color: yellow; -fx-text-fill: black;");
        stopButton.setStyle(stopButton.getStyle() + "-fx-background-color: red; -fx-text-fill: white;");
        
        // codeContainer.setWrapText(false);

        splitPane.setDividerPosition(0, mainController.splitPaneDividerPosition);

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

        // syncrhonize scrolls
        codeScroller.vvalueProperty().bindBidirectional(bkScroller.vvalueProperty());
        codeScroller.vvalueProperty().bindBidirectional(nblineScroller.vvalueProperty());


        bkpointVbox.setSpacing(9.5);
        bkpointVbox.setAlignment(Pos.TOP_CENTER); 

        // this is being reused so maybe it can be factorise
        double buttonSize = 8; 

        bkpointVbox.getChildren().forEach(node -> {
            if (node instanceof Button bkButton) {
                bkButton.setMinSize(buttonSize, buttonSize);
                bkButton.setMaxSize(buttonSize, buttonSize);
                bkButton.setPrefWidth(buttonSize);
                bkButton.setPrefHeight(buttonSize);
            }
            });
    }
   
    public void highlightCurrentLine(int currentLine) {
        for (int i = 0; i < codeContainer.getChildren().size(); i++) {
            Node node = codeContainer.getChildren().get(i);
            if (i == currentLine && node instanceof Label label) {
                
                label.setStyle("-fx-background-color: #fff5a3;");
                currentHighlightedLine = currentLine;
            } else {
                node.setStyle("");
            }
        }
    }

    /**
     * This method is used to send messages to the console. It already adds a prefix to the message and a 
     * line break at the begginning
     * @param message
     */
    public void sendMessageToConsole(String message){
        consolePanel.appendText("\n>>>" + message);
    }

    private void handleParsingException(Exception e) {
        sendMessageToConsole("Une erreur est survenue lors de la lecture du fichier: \n" + e.toString());
        stopExecution(null);
    }  
}
