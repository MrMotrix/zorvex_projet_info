package controllers;

import application.App;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class mainController {

    static double leftPaneSavedWidth;
    static AnchorPane anchorpane;
    static TextArea codeContainer;
    static StringBuilder content;
    static String nameFile;

    public static double getLeftPaneSavedWidth() {
        return leftPaneSavedWidth;
    }

    protected void changeScene(String path) {
        // AnchorPane leftControlsPanel = (AnchorPane) App.app.getStageWindow().getScene().lookup("#leftControlsPanel");
        // leftPaneSavedWidth = leftControlsPanel.getWidth();
        App.app.setScene(path);

    }

    
}

