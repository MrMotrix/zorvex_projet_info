package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import application.App;
import graphics.AbstractGraphicalObject;
import graphics.GraphicalArray;
import graphics.GraphicalFunctionCall;
import graphics.GraphicalFunctionDeclaration;
import graphics.GraphicalLinkedList;
import graphics.GraphicalRepresentation;
import graphics.GraphicalVar;
import graphics.ModificationType;
import interpreter.Interpreter;
import interpreter.instruction.Instruction;
import interpreter.instruction.Retourner;
import interpreter.instruction.Afficher;
import interpreter.instruction.Assigner;
import interpreter.instruction.Function;
import interpreter.instruction.FunctionDeclaration;
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

import graphics.GraphicalObject;
import graphics.GraphicalObjectIDGenerator;
import graphics.GraphicalPile;
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
    private int lastCalledFunctionID;
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
                    // if (currentLine == mainController.numberOfLines + 1){
                    //     // sendMessageToConsole("Fin du fichier atteint");
                    //     currentHighlightedLine++;
                    //     highlightCurrentLine(currentHighlightedLine);
                        
                    //     // Disable buttons because we are in the end of the file
                    //     continueButton.setDisable(true);
                    //     nextLineButton.setDisable(true);
                    // } 
                } catch (Exception e){
        
                    return;
                }
    }
    // TODO : something has still to be implemented here. Currently it just erases something random from the plot and goes to the last line
    @FXML
    void goLastLine(ActionEvent event) {

        record.undo(rep);
        for (ExecutionRecord content : record.getHistory()) {
            sendMessageToConsole(content.toString());
        }        
        // DO NOT TOUCH preferably :)
        currentLine--;
        if (! reverse) {
            reverse = true;
            // currentLine --;
            continueButton.setDisable(false);
            nextLineButton.setDisable(false);
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
        
        currentLine = interpreter.getCurrentLine();

        // if we have finished execution, we return
        if (currentLine > mainController.numberOfLines) return;
        
        if (reverse) {
            reverse = false;
            currentLine++;
        }
        // ===================================HERE GOES THE IMPLEMENTATION TO READ INSTRUCTIONS=========================================


        /* 
        
        if assigner
            interpreter should send : 
                name of object
                id of the object
                type of objext (variable, array, linkedlist)
                value of object
                index of assignation (if applicable, otherwise send 0 or -1 as index)
        if afficher : 
            send content to console (and potentially create 
        if function declaration : 
            interpreter should send : 
                name of function
                if of the function
                string of parameters it takes (ex : sum(a,b) should send "a,b" their names), or null or "" if it does not take anu parameters
                string of variables, same contraints as parameters
        if function call : 
            interpreter should send : 
                name of called function (optional ? )
                id of the function
                ""list"" of the parameters it takes, like sum(3,5) should send ArrayList<>{"3,5"}, or if variables, a list of the variables themselves
        if return instruction :
            interpreter should return 
                name of the function 
                id of the function 
                value or variable that is returned
        */ 
        
        try {    
            // get the affected variable from the interpreter
            Instruction inst = interpreter.step();
            
            // if (inst == null) return;
             if (inst instanceof Assigner assigner) {    
                
                // switch(assigner.()){

                // }
                String name = assigner.variableName(); // get the name of the variable
                if (name.equals("")) {
                    try{
                        // we try to get the current line of the interpreter, it will raise an exception if there is no more lines to read, i.e. we have just executed the last line of code
                        highlightCurrentLine(interpreter.getCurrentLine()-1);
            
                    } catch(Exception e){
                        highlightCurrentLine(-1);
                        sendMessageToConsole("Fin du fichier atteint");
                        // Disable buttons because we are in the end of the file
                        continueButton.setDisable(true);
                        nextLineButton.setDisable(true);
                        return;
                    }
                    return;
                }; // anonuymous variable, nothing to display
                String value = interpreter.getVariable(name).toString(); // get the value of the variable
                String type = interpreter.getVariable(name).type().toString(); // get the type of the variable
                value = value.substring(value.indexOf(" ") + 1); // remove the type of variable
                
                switch (type){
                    case "LIST" : 
                        if (rep.getElements().containsKey(interpreter.getId(name))) { // if the variable already exists
                            record.push(new IterableModifyRecord(interpreter.getId(name), ModificationType.UPDATE,0, value)); // TODO fix index
                            rep.updateElement(interpreter.getId(name), ModificationType.UPDATE, value, 0);
                        } else {
                            GraphicalArray array = new GraphicalArray(name, value.split(","), canvasPane, interpreter.getId(name));
                            // GraphicalPile array = new GraphicalPile(name, List.of(value.split(",")), canvasPane, interpreter.getId(name));
                            rep.addElement(interpreter.getId(name), array );
                            record.push(new CreateRecord(array.getID(), array));
                        }
                        break;
                    case "LLIST" : 
                        if (rep.getElements().containsKey(interpreter.getId(name))) { // if the variable already exists
                            record.push(new IterableModifyRecord(interpreter.getId(name), ModificationType.UPDATE,0, value)); // TODO fix index
                            rep.updateElement(interpreter.getId(name), ModificationType.UPDATE, value, 0);
                        } else {
                            GraphicalLinkedList llist = new GraphicalLinkedList(name, value.split(","), canvasPane, interpreter.getId(name));
                            rep.addElement(interpreter.getId(name), llist );
                            record.push(new CreateRecord(llist.getID(), llist));
                        }
                        break;
                    case "STRING":
                    case "INTEGER" :
                        if (rep.getElements().containsKey(interpreter.getId(name))) { // if the variable already exists
                            record.push(new ModifyVarRecord(interpreter.getId(name), value)); // TODO fix index
                            rep.updateElement(interpreter.getId(name), ModificationType.UPDATE, value, 0);
                        } else {
                            GraphicalVar var = new GraphicalVar(name, value, canvasPane, interpreter.getId(name));
                            rep.addElement(interpreter.getId(name), var );
                            record.push(new CreateRecord(var.getID(), var));
                        }
                }   
                

                    

                // add the variable to the graphical representation or check if it already exists and update it

                // } else { 
                    // if we are creating a variable
                    // TODO : this is the switch case that should handle the tupe of assignation
                    // AbstractGraphicalObject thisIsTheTypeOfAssignationReturned;
                    // switch (thisIsTheTypeOfAssignationReturned){
                    //     case GraphicalArray ARRAY -> {
                    //         GraphicalArray array = new GraphicalArray(var, new String[]{value}, canvasPane);
                    //         rep.addElement(var, array);
                    //         record.push(new CreateRecord(var, array));
                    //     }
                    //     case GraphicalLinkedList LINKEDLIST -> {
                    //         GraphicalLinkedList linkedlist = new GraphicalLinkedList(var, new String[]{value}, canvasPane);
                    //         rep.addElement(var, linkedlist);
                    //         record.push(new CreateRecord(var, linkedlist));
                    //     }
                    //     case GraphicalVar VARIABLE -> {
                    //         GraphicalVar variable = GraphicalVar(var, value, canvasPane);
                    //         rep.addElement(var, variable);
                    //         record.push(new CreateRecord(var, variable));
                    //     }
                    //     default -> {sendMessageToConsole("Un type de retour est erronnee");}
                    // }
                        
                        // GraphicalVar temp = new GraphicalVar(name, value, canvasPane, interpreter.getId(name));
                        // rep.addElement(interpreter.getId(var), temp );
                        // record.push(new CreateRecord(temp.getID(), temp));
                        // ArrayList<GraphicalObject> tempA = new ArrayList<>();
                        // tempA.add(temp);
                
            }
            else if (inst instanceof Afficher afficher) {
                sendMessageToConsole(afficher.result().asString());
            }
            else if (inst instanceof Retourner retourner) {
                String returnValue = retourner.result().asString();
                GraphicalVar temp = new GraphicalVar("(Renv)", returnValue, canvasPane, GraphicalObjectIDGenerator.getNextId());
                rep.addElement(temp.getID(), temp);
                record.push(new CreateRecord(temp.getID(), temp));
            
                // Delete the current function
                int finishedFunctionId = rep.fcalls().pop();
                GraphicalFunctionCall finished = (GraphicalFunctionCall)(rep.getElement(finishedFunctionId));
            
                // Delete ids and vars from the function that returned a value
                finished.getParameters().forEach(p -> rep.deleteElement(((AbstractGraphicalObject) p).getID()));
                finished.getIds().forEach(id -> rep.deleteElement(((AbstractGraphicalObject) id).getID()));
                rep.deleteElement(finishedFunctionId);
            
                // Add return variable to the last function if possible
                if (!rep.fcalls().isEmpty()) {
                    int previousFunctionId = rep.fcalls().peek();
                    GraphicalFunctionCall previous = (GraphicalFunctionCall)(rep.getElement(previousFunctionId));
            
                    // previous.addPar(temp);
                    previous.addId(temp);
                    // previous.setParameters(previous.getParameters()); // Redibuja el nombre con los nuevos parámetros
                }
       
            }
            else if (inst instanceof Function function) {
                // get the name of the function
                List<String> parsNames;
                List<GraphicalObject> pars = new ArrayList<>();
                String f = function.name();
                
                if (f.equals("ajouter_liste")){

                //     String oldValue = ((GraphicalArray) rep.getElement(interpreter.getId(function.args().get(0)))).getValues()[1];
                //     record.push(new IterableModifyRecord(interpreter.getId(function.args().get(1).asString()), 
                //         ModificationType.INSERT, Integer.parseInt(function.args().get(1).asString()),oldValue));
                //     rep.updateElement(interpreter.getId(f), ModificationType.INSERT, f, Integer.parseInt(function.args().get(1).asString()));
    
                }
                else if (f.equals("supprimer_liste")){
                    // int index = Integer.parseInt(function.args().get(1).asString());
                    // String oldValue = ((GraphicalArray) rep.getElement(interpreter.getId(function.args().get(0)))).getValues()[index];
                    
                    rep.updateElement(interpreter.getId("x"), ModificationType.REMOVE,"0" , 0);
                    
                    // record.push(new IterableModifyRecord(interpreter.getId(function.args().get(1).asString()), 
                        // ModificationType.REMOVE, index,oldValue));
                    // rep.updateElement(interpreter.getId(function.args().get(0)), ModificationType.REMOVE, oldValue, index);
    
                } else if (f.equals("recuperer_liste")){
                    // int index = Integer.parseInt(function.args().get(1).asString());
                    // String oldValue = ((GraphicalArray) rep.getElement(interpreter.getId(function.args().get(0)))).getValues()[index];
                    
                    // rep.updateElement(interpreter.getId("x"), ModificationType.REMOVE,"0" , 0);
    
                    // record.push(new IterableModifyRecord(interpreter.getId(function.args().get(1).asString()), 
                        // ModificationType.REMOVE, index,oldValue));
                    // rep.updateElement(interpreter.getId(function.args().get(0)), ModificationType.REMOVE, oldValue, index);
    
                }else if (f.equals("taille_liste")){

                    // nothing to do, it just returns a value

                }else if (rep.getFunction(f) instanceof GraphicalFunctionDeclaration fd){
                    parsNames = fd.getParameters();

                    
                    //get function parameters names
                    GraphicalFunctionCall fc = new GraphicalFunctionCall(f , canvasPane, pars, GraphicalObjectIDGenerator.getNextId());
                    rep.addElement(fc.getID(), fc);
                    rep.increaseX(40);
                    
                    ArrayList<GraphicalObject> ids = new ArrayList<>();
                    
                    for (int i = 0; i < parsNames.size(); i++){
                        
                        GraphicalVar temp = new GraphicalVar(parsNames.get(i),
                        function.args().get(i).asString() ,
                        canvasPane, 
                        // interpreter.getId(function.args().get(i).asString()));
                        interpreter.getId(parsNames.get(i)));
                        pars.add(temp);
                        rep.addElement(interpreter.getId(parsNames.get(i)), temp);
                        record.push(new CreateRecord(temp.getID(), temp));
                        ids.add(temp);
                    }

                    fc.setParameters(pars);
                    fc.setIds(ids);
                    rep.fcalls().push(fc.getID());
                }
                
                else {throw new Exception("Mismatch between given arguments and function arguments");}
                // lastCalledFunctionID = fc.getID();
            }

            else if (inst instanceof FunctionDeclaration fdeclaration) {
                GraphicalFunctionDeclaration fd = new GraphicalFunctionDeclaration(fdeclaration.name(), canvasPane,  fdeclaration.parameters(), null, GraphicalObjectIDGenerator.getNextId());
                rep.addElement(fd.getID(), fd);
                rep.addFunction(fdeclaration.name(), fd);
            }

        } catch (Exception e) {
            sendMessageToConsole("Erreur dans la ligne " + currentLine + ": " + e.getMessage());
            highlightCurrentLine(currentLine - 1);
            continueButton.setDisable(true);
            nextLineButton.setDisable(true);
            // throw e;
        }
        
        
        try{
            // we try to get the current line of the interpreter, it will raise an exception if there is no more lines to read, i.e. we have just executed the last line of code
            highlightCurrentLine(interpreter.getCurrentLine()-1);

        } catch(Exception e){
            highlightCurrentLine(-1);
            sendMessageToConsole("Fin du fichier atteint");
            // Disable buttons because we are in the end of the file
            continueButton.setDisable(true);
            nextLineButton.setDisable(true);
            return;
        }

        if (interpreter.getCurrentLine() == mainController.numberOfLines + 1) {
            sendMessageToConsole("Fin du fichier atteint");
            // Disable buttons because we are in the end of the file
            continueButton.setDisable(true);
            nextLineButton.setDisable(true);
        }
    }
        

    @FXML
    void restartExecution(ActionEvent event) {

        sendMessageToConsole("On redemarre l'éxécution");

        // reinitialize the defaiult values of the position magager 
        rep.reinitializePositioningValues();
        
        // Clean plots from panel
        rep.clear();

        // clean highlights if any
        highlightCurrentLine(-1); 
        // restart checker of first line breakpoint
        firstLineRead = false;
        lastCalledFunctionID = 0;


        record.clear(); 
        
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

        continueButton.setMaxWidth(26);
        lastLineButton.setMaxWidth(26);
        nextLineButton.setMaxWidth(26);
        restartButton.setMaxWidth(26);
        stopButton.setMaxWidth(26);

        consolePanel.setWrapText(true);

        continueButton.setStyle(continueButton.getStyle() + "-fx-background-color: green; -fx-text-fill: white;");
        lastLineButton.setStyle(lastLineButton.getStyle() + "-fx-background-color: black; -fx-text-fill: white;");
        nextLineButton.setStyle(nextLineButton.getStyle() + "-fx-background-color: black; -fx-text-fill: white;");
        restartButton.setStyle(restartButton.getStyle() + "-fx-background-color: yellow; -fx-text-fill: black;");
        stopButton.setStyle(stopButton.getStyle() + "-fx-background-color: red; -fx-text-fill: white;");
        
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
                
                label.setStyle("-fx-background-color: #fff5a3; fx-text-fill: black;");
                currentHighlightedLine = currentLine;
            } else {
                node.setStyle("");
            }
        }
    }

    /**
     * Send messages to the console. It already adds a prefix to the message and a 
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
