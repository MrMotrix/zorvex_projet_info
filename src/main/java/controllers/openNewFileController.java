package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import utilities.Paths;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import application.*;

public class openNewFileController {

    private App app;
    private MainController mainController;
    
    // trucs pour la documentation
    @FXML private MenuItem aboutButton;
    @FXML private MenuItem openDocumentationButton;

    public void openFile() {
        
        //create a filechooser onj     
        FileChooser fileChooser = new FileChooser();

        // define the extension of the files
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers txt", "*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Toutes les fichiers", "*.*"));

        // pane to select file
        File currentDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(currentDirectory);
        File selectedFile = fileChooser.showOpenDialog(null); // i think this null can be modified

        if (selectedFile != null) {

            loadFile(selectedFile);
            app.getStageWindow().setTitle("Intepreting : " + selectedFile.getName());

            mainController.nameFile = selectedFile.getName();

            showSuccessScene();
            

        } else {

            System.out.println("Aucun fichier n'a pas été choisi.");

        }
    }


    private void loadFile(File file){
        try {
            // Lire fichier et garder les lignes dans une liste
            List<String> lines = Files.readAllLines(file.toPath());

    
            //load file
            mainController.content = lines;
            mainController.numberOfLines = lines.size();

    
        } catch (Exception e) {
            System.out.println("Erreur lors de la charge du fichier");
            e.printStackTrace();
        }
    }


    void showSuccessScene() {app.setScene(Paths.SUCCESS_FILE_UPLOAD);}

    public void setApp(App app) { this.app = app;}
    public void setMainController(MainController mainController) { this.mainController = mainController;}
}
