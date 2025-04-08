package controllers;

import java.util.ArrayList;
import java.util.List;
import application.App;
import controllers.record.AfficherRecord;
import controllers.record.CreateRecord;
import controllers.record.DeleteRecord;
import controllers.record.ExecutionStack;
import controllers.record.IterableModifyRecord;
import controllers.record.ModifyVarRecord;
import controllers.record.StackModifyRecord;
import graphics.AbstractGraphicalObject;
import graphics.GraphicalArray;
import graphics.GraphicalFunctionCall;
import graphics.GraphicalFunctionDeclaration;
import graphics.GraphicalLinkedList;
import graphics.GraphicalRepresentation;
import graphics.GraphicalVar;
import graphics.IterableGraphicalObject;
import graphics.ModificationType;
import interpreter.Interpreter;
import interpreter.exceptions.RuntimeError;
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

        //  if the button was pressed when being in a breakpoint and we are not in the first line (if there is a bp in the first line we should be able to stop)
        currentLine = interpreter.getCurrentLine();
        try{
            if (currentLine == 1 && mainController.bkpoints.contains(1) && !firstLineRead) {
                highlightCurrentLine(currentLine - 1);
                firstLineRead = true;
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

        } catch (Exception e){
            if ((e instanceof RuntimeError)){
                sendMessageToConsole(e.getMessage());

            }
            return;
        }
    }

    @FXML
    void goLastLine(ActionEvent event) {

        record.undo(rep);
        // DO NOT TOUCH preferably :)
        currentLine = record.currentLine();
        if (! reverse) {
            reverse = true;
            continueButton.setDisable(true);
            nextLineButton.setDisable(true);
        }
        if (currentLine == 1) {
            stopExecution(event);
        }
        highlightCurrentLine(currentLine - 1);
    }

    /**
     * This method is used to go to the next line of the code. It is called when the user presses the "Next" button, and also it is called multiple times when the continue button is pressed. 
     * @param event
     * @throws Exception 
     */
    @FXML
    void goNextLine(ActionEvent event) throws Exception {
        
        currentLine = interpreter.getCurrentLine();

        // if we have finished execution, we return
        if (currentLine > mainController.numberOfLines) return;
        
        if (reverse) {
            reverse = false;
            currentLine++;
        }

        try {    
            // get the affected variable from the interpreter
            Instruction inst = interpreter.step();
            if (inst == null) {goNextLine(event);}
             else if (inst instanceof Assigner assigner) {        
                String name = assigner.variableName(); // get the name of the variable
                if (name.equals("")) {
                    try{
                        highlightCurrentLine(interpreter.getCurrentLine()-1); // we try to get the current line of the interpreter, it will raise an exception if there is no more lines to read, i.e. we have just executed the last line of code
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
                handleVariableAssignment(name, value, type, currentLine);
            }
            else if (inst instanceof Afficher afficher) {
                sendMessageToConsole(afficher.result().asString());
                record.push(new AfficherRecord(Integer.MAX_VALUE, afficher.result().asString(), currentLine));
            }
            else if (inst instanceof Retourner retourner) {
                String returnValue = retourner.result().asString();
                GraphicalVar temp = new GraphicalVar("(Renv)", returnValue, canvasPane, GraphicalObjectIDGenerator.getNextId());
                rep.addElement(temp.getID(), temp);
                record.push(new CreateRecord(temp.getID(), temp, currentLine));
            
                // Delete the current function
                int finishedFunctionId = rep.fcalls().pop();
                GraphicalFunctionCall finished = (GraphicalFunctionCall)(rep.getElement(finishedFunctionId));
            
                // Delete ids and vars from the function that returned a value
                finished.getParameters().forEach(p -> rep.deleteElement(((AbstractGraphicalObject) p).getID()));
                finished.getIds().forEach(id -> {
                    rep.deleteElement(((AbstractGraphicalObject) id).getID());
                    record.push(new DeleteRecord(((AbstractGraphicalObject) id).getID(), (AbstractGraphicalObject) id, currentLine));
                });
                rep.deleteElement(finishedFunctionId);
                record.push(new DeleteRecord(finishedFunctionId, finished, currentLine));
                // Add return variable to the last function if possible
                if (!rep.fcalls().isEmpty()) {
                    int previousFunctionId = rep.fcalls().peek();
                    GraphicalFunctionCall previous = (GraphicalFunctionCall)(rep.getElement(previousFunctionId));
                    previous.addId(temp);
                }
            }
            else if (inst instanceof Function function) {
                // get the name of the function
                List<String> parsNames;
                List<GraphicalObject> pars = new ArrayList<>();
                String f = function.name();
                
                if (f.equals("ajouter_liste")){ // id, value

                    int id = Integer.parseInt(function.args().get(0).asString());
                    String value = function.args().get(1).asString();
                    int index = ((IterableGraphicalObject) rep.getElement(id)).size() - 1; 
                    String oldValue = ((IterableGraphicalObject) rep.getElement(id)).getValues()[index];
                    record.push(new IterableModifyRecord(id, 
                        ModificationType.INSERT, 
                        index,
                        oldValue, 
                        currentLine));
                    rep.updateElement(id, ModificationType.INSERT, value, index + 1);
                }
                else if (f.equals("inserer_liste")){ // id, index, value
                    int id = Integer.parseInt(function.args().get(0).asString());
                    int index = Integer.parseInt(function.args().get(1).asString());
                    String value = function.args().get(2).asString();
                    String oldValue = ((IterableGraphicalObject) rep.getElement(id)).getValues()[index];
                    record.push(new IterableModifyRecord(id, 
                        ModificationType.INSERT, 
                        index,
                        oldValue, currentLine));
                    rep.updateElement(id, ModificationType.INSERT, value, index);
                }
                else if (f.equals("supprimer_liste")){ // id, index
                    int id = Integer.parseInt(function.args().get(0).asString());
                    int index = Integer.parseInt(function.args().get(1).asString());
                    String oldValue = ((IterableGraphicalObject) rep.getElement(id)).getValues()[index];
                    record.push(new IterableModifyRecord(id, 
                        ModificationType.REMOVE, 
                        index,
                        oldValue, currentLine));
                    rep.updateElement(id, ModificationType.REMOVE, oldValue, index); // here old value should be given as parameter but it wont be used
                }
                else if (f.equals("recuperer_liste")){ // id, index
                    goNextLine(event);
                    return;
                    // NOTHING TO DO SINCE ANY MODIFICATION IS DONE TO ANY GRAPHICAL OBJECT
                }else if (f.equals("taille_liste")){ // id
                    goNextLine(event);
                    return;
                    // nothing to do, it just returns a value
                } else if(f.equals("modifier_liste")){ // id, index, newValue
                    int id = Integer.parseInt(function.args().get(0).asString());
                    int index = Integer.parseInt(function.args().get(1).asString());
                    String value = function.args().get(2).asString();
                    String oldValue = ((IterableGraphicalObject) rep.getElement(id)).getValues()[index];
                    record.push(new IterableModifyRecord(id, 
                        ModificationType.UPDATE, 
                        index,
                        oldValue, 
                        currentLine));
                    rep.updateElement(id, ModificationType.UPDATE, value, index);

                } else if (f.equals("pile_vide")){ // list.of trucs
                    // nothing to do, assignation is done later
                    goNextLine(event);
                    return;
                } else if (f.equals("empiler")){ // id, value
                    int id = Integer.parseInt(function.args().get(0).asString());
                    String value = function.args().get(1).asString();

                    String oldValue = ((GraphicalPile) rep.getElement(id)).peek();
                    record.push(new StackModifyRecord(id, 
                        oldValue, 
                        ModificationType.PUSH, 
                        currentLine));
                    rep.updateElement(id, ModificationType.INSERT, value, 0);
                } else if (f.equals("depiler")){ // id
                    int id = Integer.parseInt(function.args().get(0).asString());
                    String oldValue = ((GraphicalPile) rep.getElement(id)).peek();
                    record.push(new StackModifyRecord(id, 
                        oldValue, 
                        ModificationType.POP, 
                        currentLine));
                    rep.updateElement(id, ModificationType.POP, null, 0);
                } else if (f.equals("est_pile_vide")){ // list.of ids ?
                    // nothing to do
                    goNextLine(event);
                    return;
                }
                else if (rep.getFunction(f) instanceof GraphicalFunctionDeclaration fd){
                    parsNames = fd.getParameters();   
                    //get function parameters names
                    GraphicalFunctionCall fc = new GraphicalFunctionCall(f , canvasPane, pars, GraphicalObjectIDGenerator.getNextId());
                    rep.addElement(fc.getID(), fc);
                    rep.increaseX(40);
                    ArrayList<GraphicalObject> ids = new ArrayList<>();
                    String type;
                    for (int i = 0; i < parsNames.size(); i++){
                        type = function.args().get(i).type().toString();
                        AbstractGraphicalObject temp = createParameterObject(type, parsNames.get(i), function.args().get(i).asString(), interpreter.getId(parsNames.get(i)));
                        pars.add(temp);
                        rep.addElement(interpreter.getId(parsNames.get(i)), temp);
                        record.push(new CreateRecord(temp.getID(), temp, currentLine));
                        ids.add(temp);
                    }
                    fc.setParameters(pars);
                    fc.setIds(ids);
                    rep.fcalls().push(fc.getID());
                    record.push(new CreateRecord(fc.getID(), fc, currentLine));
                }
                
                else {throw new Exception("Mismatch between given arguments and function arguments");}
            }

            else if (inst instanceof FunctionDeclaration fdeclaration) {
                GraphicalFunctionDeclaration fd = new GraphicalFunctionDeclaration(fdeclaration.name(), canvasPane,  fdeclaration.parameters(), null, GraphicalObjectIDGenerator.getNextId());
                rep.addElement(fd.getID(), fd);
                rep.addFunction(fdeclaration.name(), fd);

                record.push(new CreateRecord(fd.getID(), fd, currentLine));
            }

        } catch (Exception e) {
            sendMessageToConsole("Erreur dans la ligne " + currentLine + ": " + e.getMessage());
            highlightCurrentLine(currentLine - 1);
            continueButton.setDisable(true);
            nextLineButton.setDisable(true);        
            throw e;
        }
        try{ // we try to get the current line of the interpreter, it will raise an exception if there is no more lines to read, i.e. we have just executed the last line of code
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
    /**
     * Save the current state of the scene in the mainController. Used before changing the scene
     */
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

    /**
     * This method is used to handle the variable assignment. It is called when the user presses the "next" button and there is a variable assignment in the code.
     * @param name of the variable
     * @param value of the variable
     * @param type of the variable (INTEGER, STRING, LIST, STACK)
     * @param currentLine on the code to keep track of the execution
     * @throws Exception
     */
    void handleVariableAssignment(String name, String value, String type, int currentLine) throws Exception {
        int id = interpreter.getId(name);
        AbstractGraphicalObject obj;
    
        switch (type) {
            case "LIST":
                obj = new GraphicalArray(name, value.split(","), canvasPane, id);
                break;
            case "LLIST":
                obj = new GraphicalLinkedList(name, value.split(","), canvasPane, id);
                break;
            case "STACK":
                obj = new GraphicalPile(name, List.of(value.split(",")), canvasPane, id);
                break;
            case "STRING":
            case "INTEGER":
                obj = new GraphicalVar(name, value, canvasPane, id);
                break;
            default:
                throw new Exception("Unknown variable type: " + type);
        }
    
        if (rep.getElements().containsKey(id)) {
            if (obj instanceof IterableGraphicalObject) {
                record.push(new IterableModifyRecord(id, ModificationType.UPDATE, 0, value, currentLine));
            } else {
                record.push(new ModifyVarRecord(id, value, currentLine));
            }
            rep.updateElement(id, ModificationType.UPDATE, value, 0);
        } else {
            rep.addElement(id, obj);
            record.push(new CreateRecord(id, obj, currentLine));
        }
    }

    /**
     * This method is used to create the parameter object of a function. It is called when the user presses the "next" button and there is a function call in the code.
     * @param type of the parameter (INTEGER, STRING, LIST, STACK)
     * @param name of the parameter
     * @param rawValue of the parameter (the value of the parameter as a string)
     * @param id of the parameter (the id of the parameter in the interpreter)
     * @return the parameter object
     * @throws Exception 
     */
    protected AbstractGraphicalObject createParameterObject(String type, String name, String rawValue, int id) throws Exception {
        switch (type) {
            case "LIST":
                return new GraphicalArray(name, rawValue.substring(1, rawValue.length() - 1).split(","), canvasPane, id);
            case "LLIST":
                return new GraphicalLinkedList(name, rawValue.split(","), canvasPane, id);
            case "PILE":
                return new GraphicalPile(name, rawValue.split(","), canvasPane, id);
            case "INTEGER":
            case "STRING":
                return new GraphicalVar(name, rawValue, canvasPane, id);
            default:
                throw new Exception("Unknown type of variable : " + type);
        }
    }
    
}
