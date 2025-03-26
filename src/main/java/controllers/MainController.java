package controllers;

import java.util.List;
import java.util.Set;

import application.App;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class MainController {

    static double splitPaneDividerPosition = 0.33;

    static List<String> content; // used
    static String nameFile; // used
    static int numberOfLines;
    static int currentLine = 1;
    
    static Set<Integer> bkpoints;
    
    static TextArea consolePanel;
    static ScrollPane bkScroller;
    static ScrollPane codeScroller;
    static ScrollPane nblineScroller;
    static VBox codeContainer;
    static VBox bkpointVbox;
    static VBox nblineVbox;
    public static Scene successScene;
    public static successFileUploadController successController;

    public static double consoleScrollPosition;
    


    public static TextArea getConsole() {
        return consolePanel;
    }

    public static ScrollPane getBkScroller() {
        return bkScroller;
    }

    public static ScrollPane getCodeScroller() {
        return codeScroller;
    }

    public static ScrollPane getNblineScroller() {
        return nblineScroller;
    }  
    
    public static VBox getCodeContainer() {
        return codeContainer;
    }
    
    public static VBox getBkpointVbox() {
        return bkpointVbox;
    }

    
    public static VBox getNblineVbox() {
        return nblineVbox;
    }

    public static void setConsoleScrollPosition(double value) {
        consoleScrollPosition = value;
    }

    public static void setNblineVbox(VBox nblineVbox) {
        MainController.nblineVbox = nblineVbox;
    }

    public static void setBkpointVbox(VBox bkpointVbox) {
        MainController.bkpointVbox = bkpointVbox;
    }

    public static void setSplitPaneDividerPosition(double value){splitPaneDividerPosition = value;}
    
    static void changeScene(String path) {
        App.app.setScene(path);

    }

    public static void setConsole(TextArea consolePanel) {
        MainController.consolePanel = consolePanel;
    }

    public static void setBkscroller(ScrollPane bkScroller) {
        MainController.bkScroller = bkScroller;
    }
    
    public static void setCodeScroller(ScrollPane codeScroller) {
        MainController.codeScroller = codeScroller;
    }

    public static void setNblineScroller(ScrollPane nblineScroller) {
        MainController.nblineScroller = nblineScroller;
    }

    public static void setCodeContainer(VBox codeContainer) {
        MainController.codeContainer = codeContainer;
    }

    public static double getSplitPaneDividerPosition() {
        return splitPaneDividerPosition;
    }
    
    
}

