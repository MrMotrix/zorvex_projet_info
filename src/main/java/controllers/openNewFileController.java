package controllers;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.File;

public class openNewFileController {

    public void openFile() {
        //create a filechooser onj
        FileChooser fileChooser = new FileChooser();

        // only some types are accepted
        // TODO : define the extension of the files
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers txt", "*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Toutes les fichiers", "*.*"));

        // pane to select file
        File selectedFile = fileChooser.showOpenDialog(null); // i think this null can be modified

        if (selectedFile != null) {
            // TODO : just for testing, shows the path 
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());

            // here we should implement the procedure to charge the file
        } else {
        
            // TODO : Ceci est si jamais on veux montrer un message si on ne choisit pas de fichier
            // Alert alert = new Alert(AlertType.INFORMATION);
            // alert.setTitle("Attention");
            // alert.setHeaderText(null);
            // alert.setContentText("No file selected");
            // alert.showAndWait();
        }
    }
}
