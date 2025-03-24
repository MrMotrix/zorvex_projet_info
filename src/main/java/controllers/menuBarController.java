package controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import utilities.Paths;

import java.awt.Desktop;



public class menuBarController {

    @FXML private MenuItem aboutUsItem;
    @FXML private MenuItem closeItem;
    @FXML private MenuItem deleteItem;
    @FXML private MenuBar menuBar;
    @FXML private MenuItem openNewItem;
    @FXML private MenuItem voirDocumentationItem;

    @FXML void aboutUs(ActionEvent event) {
        // TODO : implement something about the creators
    }

    @FXML
    void close(ActionEvent event) {

        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();

    }

    @FXML
    void openNew(ActionEvent event) {
        // TODO : fix this because it throws an exception, as usual, Paths.OPEN_NEW_FILE returns null here ...
        try {
            System.out.println(getClass().getResource(Paths.OPEN_NEW_FILE));
            System.out.println(getClass());
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.OPEN_NEW_FILE));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setTitle("New File");
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void voirDocumentation(ActionEvent event) {
        // TODO : implement link to documentation or something like that
    
        try {
        URI uri = new URI("https://www.google.com/");
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(uri);
        }
    } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
    }
    }

}
