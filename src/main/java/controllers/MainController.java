// package controllers;

// import java.util.List;
// import java.util.Set;

// import application.App;
// import javafx.scene.Scene;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.TextArea;
// import javafx.scene.layout.VBox;

// public class MainController {

//     static double splitPaneDividerPosition = 0.33;

//     static List<String> content; // used
//     // static String content;
//     static String nameFile; // used
//     static int numberOfLines;
//     static int currentLine = 1;
    
//     static Set<Integer> bkpoints;
    
//     static TextArea consolePanel;
//     static ScrollPane bkScroller;
//     static ScrollPane codeScroller;
//     static ScrollPane nblineScroller;
//     static VBox codeContainer;
//     static VBox bkpointVbox;
//     static VBox nblineVbox;
//     public static Scene successScene;
//     public static successFileUploadController successController;

//     public static double consoleScrollPosition;
    


//     public static TextArea getConsole() {
//         return consolePanel;
//     }

//     public static ScrollPane getBkScroller() {
//         return bkScroller;
//     }

//     public static ScrollPane getCodeScroller() {
//         return codeScroller;
//     }

//     public static ScrollPane getNblineScroller() {
//         return nblineScroller;
//     }  
    
//     public static VBox getCodeContainer() {
//         return codeContainer;
//     }
    
//     public static VBox getBkpointVbox() {
//         return bkpointVbox;
//     }

    
//     public static VBox getNblineVbox() {
//         return nblineVbox;
//     }

//     public static void setConsoleScrollPosition(double value) {
//         consoleScrollPosition = value;
//     }

//     public static void setNblineVbox(VBox nblineVbox) {
//         MainController.nblineVbox = nblineVbox;
//     }

//     public static void setBkpointVbox(VBox bkpointVbox) {
//         MainController.bkpointVbox = bkpointVbox;
//     }

//     public static void setSplitPaneDividerPosition(double value){splitPaneDividerPosition = value;}
    
//     static void changeScene(String path) {
//         App.app.setScene(path);

//     }

//     public static void setConsole(TextArea consolePanel) {
//         MainController.consolePanel = consolePanel;
//     }

//     public static void setBkscroller(ScrollPane bkScroller) {
//         MainController.bkScroller = bkScroller;
//     }
    
//     public static void setCodeScroller(ScrollPane codeScroller) {
//         MainController.codeScroller = codeScroller;
//     }

//     public static void setNblineScroller(ScrollPane nblineScroller) {
//         MainController.nblineScroller = nblineScroller;
//     }

//     public static void setCodeContainer(VBox codeContainer) {
//         MainController.codeContainer = codeContainer;
//     }

//     public static double getSplitPaneDividerPosition() {
//         return splitPaneDividerPosition;
//     }
    
    
// }

package controllers;

import java.util.List;
import java.util.Set;

import application.App;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class MainController {

    double splitPaneDividerPosition = 0.33;

    List<String> content; // used
    // String content;
    String nameFile; // used
    int numberOfLines;
    int currentLine = 1;
    
    Set<Integer> bkpoints;
    
    TextArea consolePanel;
    ScrollPane bkScroller;
    ScrollPane codeScroller;
    ScrollPane nblineScroller;
    VBox codeContainer;
    VBox bkpointVbox;
    VBox nblineVbox;
    public Scene successScene;
    public successFileUploadController successController;
    private App app;
    public double consoleScrollPosition;
    


    public TextArea getConsole() {
        return consolePanel;
    }

    public ScrollPane getBkScroller() {
        return bkScroller;
    }

    public ScrollPane getCodeScroller() {
        return codeScroller;
    }

    public ScrollPane getNblineScroller() {
        return nblineScroller;
    }  
    
    public VBox getCodeContainer() {
        return codeContainer;
    }
    
    public VBox getBkpointVbox() {
        return bkpointVbox;
    }

    
    public VBox getNblineVbox() {
        return nblineVbox;
    }

    public void setConsoleScrollPosition(double value) {
        consoleScrollPosition = value;
    }

    public void setNblineVbox(VBox nblineVbox) {
        this.nblineVbox = nblineVbox;
    }

    public void setBkpointVbox(VBox bkpointVbox) {
        this.bkpointVbox = bkpointVbox;
    }

    public void setSplitPaneDividerPosition(double value){splitPaneDividerPosition = value;}
    
    void changeScene(String path) {
        app.setScene(path);

    }

    public void setConsole(TextArea consolePanel) {
        this.consolePanel = consolePanel;
    }

    public void setBkscroller(ScrollPane bkScroller) {
        this.bkScroller = bkScroller;
    }
    
    public void setCodeScroller(ScrollPane codeScroller) {
        this.codeScroller = codeScroller;
    }

    public void setNblineScroller(ScrollPane nblineScroller) {
        this.nblineScroller = nblineScroller;
    }

    public void setCodeContainer(VBox codeContainer) {
        this.codeContainer = codeContainer;
    }

    public double getSplitPaneDividerPosition() {
        return splitPaneDividerPosition;
    }
    
    public void setApp(App app) {
        this.app = app;
    }
}

