// package controllers;

// import java.util.Arrays;
// import java.util.Random;

// import application.App;
// import graphics.GraphicalLinkedList;
// import graphics.GraphicalArray;
// import graphics.GraphicalRepresentation;
// import graphics.GraphicalVar;
// import interpreter.Interpreter;
// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.geometry.Pos;
// import javafx.scene.Node;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.SplitPane;
// import javafx.scene.control.TextArea;
// import javafx.scene.layout.AnchorPane;
// import javafx.scene.layout.Pane;
// import javafx.scene.layout.VBox;

// public class duringExecutionController  {

//     @FXML private VBox codeContainer;
//     @FXML private TextArea consolePanel;
//     @FXML private Button continueButton;
//     @FXML private Button lastLineButton;
//     @FXML private Button nextLineButton;
//     @FXML private Button restartButton;
//     @FXML private Button stopButton;
//     @FXML private VBox bkpointVbox;
//     @FXML private VBox nblineVbox;    
//     @FXML private AnchorPane leftControlsPanel;
//     @FXML private Button startButton;
//     @FXML private FXMLLoader loader;
//     @FXML private SplitPane splitPane;
//     @FXML private ScrollPane bkScroller;
//     @FXML private ScrollPane nblineScroller;
//     @FXML private ScrollPane codeScroller;
//     @FXML private Pane canvasPane;
    
//     private int currentHighlightedLine = 0;
//     private boolean firstLineRead = false;
//     private boolean reverse = false;
//     private String contentString;
//     private successFileUploadController successController;
//     private GraphicalRepresentation rep = new GraphicalRepresentation();
//     private Interpreter interpreter;
    
//     // TEST, so this can be deleteed later =================================================================
//     Random random = new Random();
//     int TESTING_INDEX = 5;
//     // ==============================================================================

//     @FXML
//     void continueExecution(ActionEvent event) {
        
//         // sendMessageToConsole("here we should continue execution until next breakpoint or stop from the user . Currently there is a test on the plot area");
//         // sendMessageToConsole("i am adding a new variable");
//         // sendMessageToConsole("Last line completely executed" + MainController.currentLine);
//         // rep.addAndRenderElement(new GraphicalVar("myVar2", "50", canvasPane));

//         //  if the button was pressed when being in a breakpoint and we are not in the first line (if there is a bp in the first line we should be able to stop)
//         try{
//             if (MainController.currentLine == 1 && MainController.bkpoints.contains(1) && !firstLineRead) {
//             highlightCurrentLine(currentHighlightedLine);
//             // sendMessageToConsole("Breakpoint reached at line 1");
//             firstLineRead = true;
//             return;
//         }
    
//         if (MainController.bkpoints.contains(MainController.currentLine)) {
//             goNextLine(event);
//         }
    
//         while (!MainController.bkpoints.contains(MainController.currentLine) && MainController.currentLine != MainController.numberOfLines + 1) {
//             goNextLine(event);
//         }
//         // this handles the case when we have reached the end of the file. Basically, it disables the buttons and removes any possible remaining highlight. This is also treated in the goNextLine method
//         if (MainController.currentLine == MainController.numberOfLines + 1){
//             sendMessageToConsole("Fin du fichier atteint");
//             currentHighlightedLine++;
//             highlightCurrentLine(currentHighlightedLine);
            
//             // Disable buttons because we are in the end of the file
//             continueButton.setDisable(true);
//             nextLineButton.setDisable(true);
//         } 
// } catch (Exception e){

//     stopExecution(event);
// }
//     }

//     @FXML
//     void goLastLine(ActionEvent event) {

//         // ============================================ TESTING ============================================================================================

//         if (TESTING_INDEX == 1){
            
//             int index = random.nextInt(rep.getElements().size());
//             sendMessageToConsole("Erased element in position " + index);
//             rep.deleteElement(rep.getElements().get(index).getName());

//         }

//         // ============================================ END TEST ============================================================================================
        
//         // DO NOT TOUCH preferably :)
//         MainController.currentLine--;
//         if (! reverse) {
//             reverse = true;
//             MainController.currentLine --;
//         }
//         highlightCurrentLine(MainController.currentLine);
//         sendMessageToConsole("Last line executed : " + MainController.currentLine);

//         // rep.getElements().get(0).update
//     }

//     @FXML
//     void goNextLine(ActionEvent event) {
        
        
//         // if we have finished execution, we return
//         if (MainController.currentLine > MainController.numberOfLines) return;

//         if (reverse){
//             reverse = false;
//             MainController.currentLine++;
//         }

//         // sendMessageToConsole("Last executed line : " + MainController.currentLine);


//         // ===================================HERE GOES THE IMPLEMENTATION TO READ INSTRUCTIONS=========================================
        
//         // interpret the next line ang get the variables that are modified or added. 
//         String var = interpreter.step();
        
//         if (var != "" && var != null){

//             try {
                
//                 String value = interpreter.getVariable(var).toString();
//                 value = value.substring(value.indexOf(" ") + 1);
                
                
//                 // add the variable to the graphical representation or check if it already exists and update it
                
//                 if (rep.getElements().keySet().contains(var)){
//                     rep.updateElement(var, value, 0);
//                 } else {
//                     rep.addElement(var, new GraphicalVar(var, value, canvasPane));
//                 }
                
//             } catch (Exception e) {
//                 // Arrays.stream(e.getStackTrace()).forEach(x -> sendMessageToConsole(x.toString()));
//                 // sendMessageToConsole(e.getStackTrace().toString());
//                 sendMessageToConsole("Un probleme est survenu lors de l'exécution de la ligne " + MainController.currentLine + "\n" + e.getMessage());
//                 stopExecution(event);
//                 throw e;
//                 // initialize();
//             }

//         }


//         // rep.addElement(null);
        
//         // ============================================================================

//         /*
//         if (false){

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
//         */
//         highlightCurrentLine(MainController.currentLine);

//         // this should be the last line of the method, we only increase the pointer to the current line once it has been correctly interpreted
//         MainController.currentLine++;


//         if (MainController.currentLine == MainController.numberOfLines + 1){
//             sendMessageToConsole("Fin du fichier atteint");
            
//             // Disable buttons because we are in the end of the file
//             continueButton.setDisable(true);
//             nextLineButton.setDisable(true);
//         }
    
//     }

//     @FXML
//     void restartExecution(ActionEvent event) {
        
//         sendMessageToConsole("On redemarre l'éxécution");

//         // reinitialize the defaiult values of the position magager 
//         rep.reinitializePositioningValues();
        
//         // Clean plots from panel
//         rep.clear();

//         // Reset the curerntline
//         MainController.currentLine = 1;
//         // clean highlights if any
//         highlightCurrentLine(0); 
//         // restart checker of first line breakpoint
//         firstLineRead = false;

//         //reset interpreter
//         interpreter= new Interpreter(contentString);


//         // restart state of buttons
//         continueButton.setDisable(false);
//         nextLineButton.setDisable(false);
//         //restart execution
//         continueExecution(event);

//     }


//     @FXML
//     void stopExecution(ActionEvent event) {

//         // sendMessageToConsole("We stop execution, so we return to the previous scene");


//         if (currentHighlightedLine >= 0 && currentHighlightedLine < codeContainer.getChildren().size()) {
//             Node node = codeContainer.getChildren().get(currentHighlightedLine);
//             node.setStyle("");
//             currentHighlightedLine = 0;
//         }


//         // reset values for rendering
//         rep.reinitializePositioningValues();
//         rep.clear();
//         // set the positino of the divider
//         MainController.setSplitPaneDividerPosition(splitPane.getDividerPositions()[0]);
//         // reset the state of the buttons
//         continueButton.setDisable(false);
//         nextLineButton.setDisable(false);

//         // save the current state in MainController
//         save();
//         successController.setPreviousState();
//         MainController.currentLine = 1;
//         firstLineRead = false;

//         // reset interpreter
//         interpreter= new Interpreter(contentString);

//         // MainController.bkpoints.forEach(System.out::println);
//         // reuse the last scene
//         App.app.getStageWindow().setScene(MainController.successScene);
//         // MainController.successController.show(); 
//     }

//     //  : this can be private i think
//     public void save(){
//         // save the current scene and controller
//         MainController.setSplitPaneDividerPosition(splitPane.getDividerPositions()[0]);
//         // console
//         MainController.setConsole(consolePanel);
//         MainController.setConsoleScrollPosition(consolePanel.getScrollTop());
//         // Scrollers
//         MainController.setBkscroller(bkScroller);
//         MainController.setNblineScroller(nblineScroller);
//         MainController.setCodeScroller(codeScroller);
//         //Vbox
//         MainController.setCodeContainer(codeContainer);
//         MainController.setBkpointVbox(bkpointVbox);
//         MainController.setNblineVbox(nblineVbox);

//     }

//     @FXML
//     void initialize() {

//         setInitialVisuals();        
//         setPreviousState();
//         // this has to be initializes with this format because creating a simple strign joining the lines with \n without formatting produces an error
//         contentString = """
//                 %s
//                 """.formatted(String.join("\n", MainController.content));


//         this.successController = MainController.successController;
//         // System.out.println(contentString);
//         // System.out.println(successController.code);
//         interpreter = new Interpreter(contentString);

//         // after doing all the settings, we start the exeution by calling continueExecution
//         continueExecution(null);
//     }

//     private void setInitialVisuals() {
//         // codeContainer.setEditable(false);
//         codeContainer.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"); // supprimer le background du TextField
//         //  : probably this is factorizable with css
//         codeScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
//         bkScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
//         nblineScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
//         consolePanel.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
//         canvasPane.setStyle("-fx-background-color: transparent;");
        
//         // set the labels for every button
//         continueButton.setText("C");
//         lastLineButton.setText("<");
//         nextLineButton.setText(">");   
//         restartButton.setText("R");    
//         stopButton.setText("A");
//         consolePanel.setWrapText(true);
        
//         // codeContainer.setWrapText(false);

//         splitPane.setDividerPosition(0, MainController.splitPaneDividerPosition);

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

//         // syncrhonize scrolls
//         codeScroller.vvalueProperty().bindBidirectional(bkScroller.vvalueProperty());
//         codeScroller.vvalueProperty().bindBidirectional(nblineScroller.vvalueProperty());


//         bkpointVbox.setSpacing(9.5);
//         bkpointVbox.setAlignment(Pos.TOP_CENTER); 

//         // this is being reused so maybe it can be factorise
//         double buttonSize = 8; 

//         bkpointVbox.getChildren().forEach(node -> {
//             if (node instanceof Button bkButton) {
//                 bkButton.setMinSize(buttonSize, buttonSize);
//                 bkButton.setMaxSize(buttonSize, buttonSize);
//                 bkButton.setPrefWidth(buttonSize);
//                 bkButton.setPrefHeight(buttonSize);
//                 // bkButton.setStyle("-fx-background-color: black; -fx-background-radius: 50%;");
//             }
//             });
//     }
   
//     private void highlightCurrentLine(int currentLine) {
//         for (int i = 0; i < codeContainer.getChildren().size(); i++) {
//             Node node = codeContainer.getChildren().get(i);
//             if (i == currentLine && node instanceof Label label) {
//                 //  : maybe this can be added when the labels are added so we dont have to update every time ?
//                 // label.setMaxWidth(codeContainer.getMaxWidth());
                
//                 // label.setStyle("-fx-background-color: #fff5a3; -fx-border-color: black;");
//                 label.setStyle("-fx-background-color: #fff5a3;");
//                 // VBox.setVgrow(label, Priority.NEVER);
//                 currentHighlightedLine = currentLine;
//                 // consolePanel.appendText(codeContainer.getWidth() + "\n");
//             } else {
//                 node.setStyle("");
//             }
//         }
//     }

//     /**
//      * This method is used to send messages to the console. It already adds a prefix to the message and a 
//      * line break at the begginning
//      * @param message
//      */
//     private void sendMessageToConsole(String message){
//         consolePanel.appendText("\n>>>" + message);
//     }


// }


package controllers;

import java.util.Random;

import application.App;
import graphics.GraphicalRepresentation;
import graphics.GraphicalVar;
import interpreter.Interpreter;
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
    
    private int currentHighlightedLine = 0;
    private boolean firstLineRead = false;
    private boolean reverse = false;
    private String contentString;
    private successFileUploadController successController;
    private GraphicalRepresentation rep = new GraphicalRepresentation();
    private Interpreter interpreter;
    private MainController mainController;
    private App app;
    
    // TEST, so this can be deleteed later =================================================================
    Random random = new Random();
    int TESTING_INDEX = 5;
    // ==============================================================================

    @FXML
    void continueExecution(ActionEvent event) {
        
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
    }

    @FXML
    void goLastLine(ActionEvent event) {

        // ============================================ TESTING ============================================================================================

        if (TESTING_INDEX == 1){
            
            int index = random.nextInt(rep.getElements().size());
            sendMessageToConsole("Erased element in position " + index);
            rep.deleteElement(rep.getElements().get(index).getName());

        }

        // ============================================ END TEST ============================================================================================
        
        // DO NOT TOUCH preferably :)
        mainController.currentLine--;
        if (! reverse) {
            reverse = true;
            mainController.currentLine --;
        }
        highlightCurrentLine(mainController.currentLine);
        sendMessageToConsole("Last line executed : " + mainController.currentLine);

        // rep.getElements().get(0).update
    }

    @FXML
    void goNextLine(ActionEvent event) {
        // if we have finished execution, we return
        if (mainController.currentLine > mainController.numberOfLines) return;
        
        if (reverse) {
            reverse = false;
            mainController.currentLine++;
        }
        // ===================================HERE GOES THE IMPLEMENTATION TO READ INSTRUCTIONS=========================================
        
        try {    
            // get the affected variable from the interpreter
            String var = interpreter.step();

            /*
             * ObjectType var = interpreter.getVariable(var);
             *  // var contains the type and the name of the variable, if its a Var, ArrayList, Llist
             * 
             */
            if (var != null && !var.isEmpty()) {

                /*
                 * switch (var) : 
                 * case "Var" :
                 *      String value = interpreter.getVariable(var).toString();
                 *      value = value.substring(value.indexOf(" ") + 1);
                 *      break;
                 * case "Array" :
                 *      String [] values = interpreter.getVariable(var).toString().split(" ");
                 *       break;
                 * case "Llist" :
                 *      // etc
                 * 
                 */
                String value = interpreter.getVariable(var).toString();
                value = value.substring(value.indexOf(" ") + 1);
                

                // add the variable to the graphical representation or check if it already exists and update it
                if (rep.getElements().containsKey(var)) {
                    rep.updateElement(var, value, 0);
                } else {
                    rep.addElement(var, new GraphicalVar(var, value, canvasPane));
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
        }
    }
        

    @FXML
    void restartExecution(ActionEvent event) {
        
        sendMessageToConsole("On redemarre l'éxécution");

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
        interpreter= new Interpreter(contentString);


        // restart state of buttons
        continueButton.setDisable(false);
        nextLineButton.setDisable(false);
        //restart execution
        continueExecution(event);

    }


    @FXML
    void stopExecution(ActionEvent event) {

        // sendMessageToConsole("We stop execution, so we return to the previous scene");


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
        mainController.currentLine = 1;
        firstLineRead = false;

        // reset interpreter
        interpreter= new Interpreter(contentString);

        // mainController.bkpoints.forEach(System.out::println);
        // reuse the last scene
        app.setScene(mainController.successScene);
        
        // mainController.successController.show(); 
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

    public void initialize2(MainController mainController, App app) {
        this.mainController = mainController;
        this.app = app;
        setInitialVisuals();        
        setPreviousState();
        // this has to be initializes with this format because creating a simple strign joining the lines with \n without formatting produces an error
        contentString = """
                %s
                """.formatted(String.join("\n", mainController.content));


        this.successController = mainController.successController;
        // TODO : implementer gestion des exceptions
        try{

            interpreter = new Interpreter(contentString);

        } catch (Exception e){
            sendMessageToConsole("Un probleme est survenu lors de l'exécution de la ligne " + mainController.currentLine + "\n" + e.getMessage());
            stopExecution(null);
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
   
    private void highlightCurrentLine(int currentLine) {
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
    private void sendMessageToConsole(String message){
        consolePanel.appendText("\n>>>" + message);
    }


}
