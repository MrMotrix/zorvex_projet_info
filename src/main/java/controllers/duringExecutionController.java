package controllers;

import java.util.Random;

import application.App;
import graphics.GraphcalLinkedList;
import graphics.GraphicalArray;
import graphics.GraphicalRepresentation;
import graphics.GraphicalVar;
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
import javafx.scene.layout.Priority;
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
    
    private int currentHighlightedLine = -1;
    private boolean reverse = false;
    private successFileUploadController successController;
    private GraphicalRepresentation rep = new GraphicalRepresentation();
    
    // TEST, so this can be deleteed later =================================================================
    Random random = new Random();
    int TESTING_INDEX = 2;
    // ==============================================================================

    @FXML
    void continueExecution(ActionEvent event) {
        
        consolePanel.setText("here we should continue execution until next breakpoint or stop from the user . Currently there is a test on the plot area");
        consolePanel.appendText("\n i am adding a new variable");
        consolePanel.appendText("\n Currently on line" + MainController.currentLine);
        // rep.addAndRenderElement(new GraphicalVar("myVar2", "50", canvasPane));

        //  if the button was pressed when being in a breakpoint
        if (MainController.bkpoints.contains(MainController.currentLine)){
            MainController.bkpoints.forEach(System.out::println);
            goNextLine(event);
        }
        
        // continue execution until a new breakpoint is found or the end of the file is reached
        while (!MainController.bkpoints.contains(MainController.currentLine) && MainController.currentLine != MainController.numberOfLines) {
            MainController.bkpoints.forEach(System.out::println);
            goNextLine(event);
        }

    }

    @FXML
    void goLastLine(ActionEvent event) {

        // ============================================ TESTING ============================================================================================

        if (TESTING_INDEX == 1){
            
            int index = random.nextInt(rep.getElements().size());
            consolePanel.appendText("Erased element in position " + index);
            rep.deleteElement(rep.getElements().get(index));

        }

        // ============================================ END TEST ============================================================================================
        
        // DO NOT TOUCH preferably :)
        MainController.currentLine--;
        if (! reverse) {
            reverse = true;
            MainController.currentLine --;
        }
        highlightCurrentLine(MainController.currentLine);
        consolePanel.appendText("\n Currently on line" + MainController.currentLine);

        // rep.getElements().get(0).update
    }

    @FXML
    void goNextLine(ActionEvent event) {
        
        
        // if we have finished execution, we return
        if (MainController.currentLine > MainController.numberOfLines) return;

        if (reverse){
            reverse = false;
            MainController.currentLine++;
        }

        consolePanel.appendText("\nCurrent line : " + MainController.currentLine);

        // ============================================ TESTING ============================================================================================
        // ============================================ CASE 1 : add and remove elements from a linked list ============================================================================================
        
        if (TESTING_INDEX == 1){

            // random test to add a var, an array or a llist
            switch (random.nextInt(3)) {
                case 0 -> rep.addElement(new GraphicalVar("Line", MainController.currentLine + "", canvasPane));
                case 1 -> rep.addElement(new GraphicalArray("myArray_done_in_line" + MainController.currentLine, new String[]{"value1", "value2", "value3"}, canvasPane));
                case 2 -> rep.addElement(new GraphcalLinkedList("myLinkedList", new String[]{"v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8", "v9", "v10", "v11"}, canvasPane)); // just to see what happens if there is not enough space
            }
        //========================================================== CASE 2 : random remove of elements from a graphical representation ======================================================================
        } else if (TESTING_INDEX == 2){

            if(MainController.currentLine < 6) {
                // rep.addElement(new GraphicalArray("myArray_done_in_line" + MainController.currentLine, new String[]{"value1", "value2", "value3"}, canvasPane));
                rep.addElement(new GraphcalLinkedList("myLinkedList" + MainController.currentLine, new String[]{"v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8", "v9", "v10", "v11"}, canvasPane)); 
            }
            else if (MainController.currentLine < 12){
                GraphcalLinkedList g = (GraphcalLinkedList) rep.getElements().get(4);
                int index = MainController.currentLine + random.nextInt(4);
                g.addNodeAt(index, MainController.currentLine + "");
                // rep.addElement((GraphcalLinkedList)(rep.getElements().getLast()).get(index));
            } else if (MainController.currentLine < 20){
                GraphcalLinkedList g = (GraphcalLinkedList) rep.getElements().get(4);
                int index = random.nextInt(12);
                consolePanel.appendText("Delete position" + index);
                g.deleteNodeAt(index);
                                
            } else {
                rep.deleteElement(rep.getElements().getFirst());
            }
        }
        //===========================================================END TEST=========================================================================

        highlightCurrentLine(MainController.currentLine);

        // this should be the last line of the method, we only increase the pointer to the current line once it has been correctly interpreted
        MainController.currentLine++;
    
    }

    @FXML
    void restartExecution(ActionEvent event) {
        
        consolePanel.appendText("i am restarting the execution, so all should be cleaned");

        
        // reinitialize the defaiult values of the position magager 
        rep.reinitializePositioningValues();
        
        // Clean plots from panel
        rep.clear();

        // Reset the curerntline
        MainController.currentLine = 1;
        // clean highlights if any
        highlightCurrentLine(-1); 
        //restart execution
        continueExecution(event);

    }


    @FXML
    void stopExecution(ActionEvent event) {
        System.out.println("On revient a successFileUpload...");

        consolePanel.setText("We stop execution, so we return to the previous scene");


        if (currentHighlightedLine >= 0 && currentHighlightedLine < codeContainer.getChildren().size()) {
            Node node = codeContainer.getChildren().get(currentHighlightedLine);
            node.setStyle("");
            currentHighlightedLine = -1;
        }


        // reset values for rendering
        rep.reinitializePositioningValues();
        rep.clear();
        // set the positino of the divider
        MainController.setSplitPaneDividerPosition(splitPane.getDividerPositions()[0]);
        // save the current state in MainController
        save();
        successController.setPreviousState();
        MainController.currentLine = 1;

        MainController.bkpoints.forEach(System.out::println);
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
        // TODO : probably this is factorizable with css
        codeScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
        bkScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
        nblineScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
        consolePanel.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
        
        
        // set the labels for every button
        continueButton.setText("C");
        lastLineButton.setText("<");
        nextLineButton.setText(">");   
        restartButton.setText("R");    
        stopButton.setText("A");
        consolePanel.setWrapText(true);
        
        // codeContainer.setWrapText(false);

        splitPane.setDividerPosition(0, MainController.splitPaneDividerPosition);
        
        setPreviousState();

        this.successController = MainController.successController;

        // after doing all the settings, we start the exeution by calling continueExecution
        // TODO : still this should probable be moved to another place
        continueExecution(null);
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
        // bkpointVbox.setStyle(null);

        // this is being reused so maybe it can be factorise
        double buttonSize = 8; 

        bkpointVbox.getChildren().forEach(node -> {
            if (node instanceof Button bkButton) {
                bkButton.setMinSize(buttonSize, buttonSize);
                bkButton.setMaxSize(buttonSize, buttonSize);
                bkButton.setPrefWidth(buttonSize);
                bkButton.setPrefHeight(buttonSize);
                // bkButton.setStyle("-fx-background-color: black; -fx-background-radius: 50%;");
            }
            });
    }
   
    private void highlightCurrentLine(int currentLine) {
        for (int i = 0; i < codeContainer.getChildren().size(); i++) {
            Node node = codeContainer.getChildren().get(i);
            if (i == currentLine && node instanceof Label label) {
                // TODO : maybe this can be added when the labels are added so we dont have to update every time ?
                label.setMaxWidth(Double.MAX_VALUE);
                // label.setMaxWidth(codeContainer.getMaxWidth());
                
                // label.setStyle("-fx-background-color: #fff5a3; -fx-border-color: black;");
                label.setStyle("-fx-background-color: #fff5a3;");
                // VBox.setVgrow(label, Priority.NEVER);
                currentHighlightedLine = currentLine;
                // consolePanel.appendText(codeContainer.getWidth() + "\n");
            } else {
                node.setStyle("");
            }
        }
    }


}


// package controllers;

// import java.util.Random;

// import application.App;
// import graphics.GraphcalLinkedList;
// import graphics.GraphicalArray;
// import graphics.GraphicalRepresentation;
// import graphics.GraphicalVar;
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
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.VBox;

// public class duringExecutionController {
    

//         @FXML private VBox codeContainer;
//         @FXML private TextArea consolePanel;
//         @FXML private Button continueButton;
//         @FXML private Button lastLineButton;
//         @FXML private Button nextLineButton;
//         @FXML private Button restartButton;
//         @FXML private Button stopButton;
//         @FXML private VBox bkpointVbox;
//         @FXML private VBox nblineVbox;    
//         @FXML private AnchorPane leftControlsPanel;
//         @FXML private Button startButton;
//         @FXML private FXMLLoader loader;
//         @FXML private SplitPane splitPane;
//         @FXML private ScrollPane bkScroller;
//         @FXML private ScrollPane nblineScroller;
//         @FXML private ScrollPane codeScroller;
//         @FXML private Pane canvasPane;
        
//         private int currentHighlightedLine = -1;
//         private boolean reverse = false;
    
//         // Test, can be deleted later =================================================================
//         private GraphicalRepresentation rep = new GraphicalRepresentation();
//         private successFileUploadController successController;
//         private Random random = new Random();
//         // ==============================================================================
    
//         @FXML
//         void continueExecution(ActionEvent event) {
//             consolePanel.setText("Continuing execution until next breakpoint or stop from the user.\n");
    
//             // If the button was pressed when being at a breakpoint, continue to next
//             if (MainController.bkpoints.contains(MainController.currentLine)) {
//                 goNextLine(event);
//             }
            
//             // Continue execution until a new breakpoint or end of the file
//             while (!MainController.bkpoints.contains(MainController.currentLine) && MainController.currentLine != MainController.numberOfLines) {
//                 goNextLine(event);
//             }
//         }
    
//         @FXML
//         void goLastLine(ActionEvent event) {
//             if (MainController.currentLine <= 1) return;
            
//             // Go to the previous line, restoring the previous state
//             MainController.currentLine--;
//             rep.restoreNextState();
//             highlightCurrentLine(MainController.currentLine);
//             consolePanel.appendText("Currently on line " + MainController.currentLine + "\n");
//         }
    
        
//         @FXML
//         void goNextLine(ActionEvent event) {
//             if (MainController.currentLine > MainController.numberOfLines) return;
            
//             // Avanzar a la siguiente línea
//             consolePanel.appendText("\nCurrent line: " + MainController.currentLine);
            
//             // Ejemplo: agregar un nuevo gráfico o variable
//             switch (random.nextInt(3)) {
//                 case 0 -> rep.addElement(new GraphicalVar("Done in line", String.valueOf(MainController.currentLine), canvasPane));
//                 case 1 -> rep.addElement(new GraphicalArray("myArray_" + MainController.currentLine, new String[]{"value1", "value2", "value3"}, canvasPane));
//                 case 2 -> rep.addElement(new GraphcalLinkedList("myList_" + MainController.currentLine, new String[]{"v1", "v2", "v3"}, canvasPane));
//             }
            
//             // Restaurar el estado de la siguiente línea si ya está calculado
//             rep.restoreNextState();  // Llama a redo() para restaurar el siguiente estado
            
//             highlightCurrentLine(MainController.currentLine);
//             MainController.currentLine++;
//         }
        
    
//         @FXML
//         void restartExecution(ActionEvent event) {
//             consolePanel.setText("Restarting the execution... resetting everything.\n");
            
//             // Reinitialize graphical state
//             GraphicalRepresentation.reinitializePositioningValues();
//             rep.clear();
            
//             // Reset current line
//             MainController.currentLine = 1;
//             highlightCurrentLine(-1);  // Remove highlighting
            
//             continueExecution(event);  // Restart execution
//         }
    
//         @FXML
//         void stopExecution(ActionEvent event) {
//             consolePanel.setText("Execution stopped. Returning to the previous scene.\n");
    
//             // Reset the highlighted line, if any
//             if (currentHighlightedLine >= 0 && currentHighlightedLine < codeContainer.getChildren().size()) {
//                 Node node = codeContainer.getChildren().get(currentHighlightedLine);
//                 node.setStyle("");
//                 currentHighlightedLine = -1;
//             }
    
//             // Clean up rendering and UI elements
//             GraphicalRepresentation.reinitializePositioningValues();
//             rep.clear();
//             MainController.setSplitPaneDividerPosition(splitPane.getDividerPositions()[0]);
//             save();  // Save current state
//             successController.setPreviousState();  // Go back to success scene
    
//             MainController.currentLine = 1;  // Reset current line
//             App.app.getStageWindow().setScene(MainController.successScene);  // Switch scene
//         }
    
//         public void save() {
//             // Save current scene and controller state
//             MainController.setSplitPaneDividerPosition(splitPane.getDividerPositions()[0]);
//             MainController.setConsole(consolePanel);
//             MainController.setBkscroller(bkScroller);
//             MainController.setNblineScroller(nblineScroller);
//             MainController.setCodeScroller(codeScroller);
//             MainController.setCodeContainer(codeContainer);
//             MainController.setBkpointVbox(bkpointVbox);
//             MainController.setNblineVbox(nblineVbox);
//         }
    
//         @FXML
//         void initialize() {
//             // Set style for UI elements
//             codeContainer.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
//             codeScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
//             bkScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
//             nblineScroller.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
//             consolePanel.setStyle("-fx-focus-color: lightgrey; -fx-faint-focus-color: transparent;");
            
//             splitPane.setDividerPosition(0, MainController.splitPaneDividerPosition);  // Restore divider position
            
//             // Set button labels
//             continueButton.setText("C");
//             lastLineButton.setText("<");
//             nextLineButton.setText(">");
//             restartButton.setText("R");
//             stopButton.setText("A");
    
//             setPreviousState();
//             successController = MainController.successController;
    
//             // Start execution when everything is set
//             continueExecution(null);
//         }
    
//         private void setPreviousState() {
//             // Restore previous state (scroll position, vbox contents, etc.)
//             splitPane.setDividerPosition(0, MainController.getSplitPaneDividerPosition());
//             consolePanel.setText(MainController.getConsole().getText());
//             codeScroller.setVvalue(MainController.getCodeScroller().getVvalue());
//             nblineScroller.setVvalue(MainController.getNblineScroller().getVvalue());
//             bkScroller.setVvalue(MainController.getBkScroller().getVvalue());
    
//             // Restore UI elements
//             codeContainer.getChildren().setAll(MainController.getCodeContainer().getChildren());
//             nblineVbox.getChildren().setAll(MainController.getNblineVbox().getChildren());
//             bkpointVbox.getChildren().setAll(MainController.getBkpointVbox().getChildren());
    
//             // Synchronize scrolls
//             codeScroller.vvalueProperty().bindBidirectional(bkScroller.vvalueProperty());
//             codeScroller.vvalueProperty().bindBidirectional(nblineScroller.vvalueProperty());
    
//             // Style VBoxes and buttons
//             bkpointVbox.setSpacing(9.5);
//             bkpointVbox.setAlignment(Pos.BOTTOM_CENTER);
    
//             double buttonSize = 8;
//             bkpointVbox.getChildren().forEach(node -> {
//                 if (node instanceof Button bkButton) {
//                     bkButton.setMinSize(buttonSize, buttonSize);
//                     bkButton.setMaxSize(buttonSize, buttonSize);
//                     bkButton.setPrefWidth(buttonSize);
//                     bkButton.setPrefHeight(buttonSize);
//                 }
//             });
//         }
    
//         private void highlightCurrentLine(int currentLine) {
//             for (int i = 0; i < codeContainer.getChildren().size(); i++) {
//                 Node node = codeContainer.getChildren().get(i);
//                 if (i == currentLine && node instanceof Label label) {
//                     label.setStyle("-fx-background-color: #fff5a3;");
//                     currentHighlightedLine = currentLine;
//                 } else {
//                     node.setStyle("");
//                 }
//             }
//         }
        
// }