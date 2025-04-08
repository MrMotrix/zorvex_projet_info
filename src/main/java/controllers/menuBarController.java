package controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utilities.Paths;

import java.awt.Desktop;



public class menuBarController {
    

    @FXML public MenuItem aboutUsItem;
    @FXML public MenuItem closeItem;
    @FXML public MenuItem deleteItem;
    @FXML public MenuBar menuBar;
    public MenuBar getMenuBar() {
        return menuBar;
    }

    @FXML public MenuItem openNewItem;
    @FXML public MenuItem voirDocumentationItem;

    @FXML
    void aboutUs(ActionEvent event) {
        // Creer fenetre emergente
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("A propos");
        alert.setHeaderText("A propos de ZorvexInterpreteur");
    
        // set content
        StringBuilder content = new StringBuilder();
        content.append("Developpeurs:\n");
        content.append("• AHAMADA Houzaime \n");
        content.append("• OJEDA Martin \n");
        content.append("• NDOUR Mouhamed \n\n");
        content.append("Version du projet: 1.0.0\n");
        content.append("Date de création: Mars 2025\n\n");
        // TODO : add more information about the project ??
        content.append("Description: Cette application a été conçue pour héberger l'interprète du langage de programmation Zorvex.\n");
    
        alert.setContentText(content.toString());
        Image logoImage = new Image(getClass().getResourceAsStream(Paths.LOGO_ICON_EXTERNAL));
        
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(50); 
        logoImageView.setFitHeight(50); 
        logoImageView.setPreserveRatio(true);
        alert.setGraphic(logoImageView);        

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(logoImage);

        ButtonType okButton = alert.getButtonTypes().get(0);
        Button button = (Button) alert.getDialogPane().lookupButton(okButton);
        button.setText("Fermer"); 
        // show
        alert.showAndWait();
    }

    @FXML
    void close(ActionEvent event) {

        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();

    }

    @FXML
    void openNew(ActionEvent event) throws Exception {

        try {
            App app = new App();
            app.start(new Stage());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void voirDocumentation(ActionEvent event) {
        // TODO : implement link to documentation or something like that
    
        try {
        URI uri = new URI("https://github.com/MrMotrix/zorvex_projet_info/blob/main/README.md");
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(uri);
        }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
