package controllers;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utilities.Paths;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import application.*;

public class openNewFileController {

    private Stage stage;
    private StringBuilder builder;

    public void openFile() {
        
        //create a filechooser onj     bj 
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

            // TODO : here we should implement the procedure to charge the file
            loadFile(selectedFile);
            App.app.getStageWindow().setTitle("Intepreting : " + selectedFile.getName());

            MainController.nameFile = selectedFile.getName();

            showSuccessScene();
            

        } else {
        
            // TODO : Ceci est si jamais on veux montrer un message si on ne choisit pas de fichier
            // Alert alert = new Alert(AlertType.INFORMATION);
            // alert.setTitle("Attention");
            System.out.println("nothing was openend nor chosen");
            // alert.setHeaderText(null);
            // alert.setContentText("No file selected");
            // alert.showAndWait();
        }
    }


    private void loadFile(File file){
        try {
            // Lire fichier et garder les lignes dans un stringbuilder
            List<String> lines = Files.readAllLines(file.toPath());
            // builder = new StringBuilder();
            // for (String line : lines) {
            //     builder.append(line).append("\n");
            //     MainController.numberOfLines ++;
            // }
    
            // // load file
            // System.out.println(builder.toString());
            MainController.content = lines;
            MainController.numberOfLines = lines.size();

    
        } catch (Exception e) {
            System.out.println("something went wrong");
            e.printStackTrace();
        }
    }


    void showSuccessScene() {
        // try {
        // FXMLLoader loader = App.app.loader;
        // loader = new FXMLLoader(getClass().getResource(Paths.SUCCESS_FILE_UPLOAD));
        // BorderPane root = loader.load();


        // successFileUploadController controller2 = loader.getController();

        // Scene scene = new Scene(root);
        // stage.setScene(scene);

        // controller2.init(builder);
        // stage.show();
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    
        App.app.setScene(Paths.SUCCESS_FILE_UPLOAD);
    }

    public void setStage(Stage stage) { this.stage = stage;}

}
