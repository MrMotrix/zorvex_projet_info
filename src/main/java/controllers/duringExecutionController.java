package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class duringExecutionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void continueExecution(ActionEvent event) {

    }

    @FXML
    void goLastLine(ActionEvent event) {
        System.out.println("here we should implement a way to return to the last line");
    }

    @FXML
    void goNextLine(ActionEvent event) {
        System.out.println("here we should implement a way to go to the next line");

    }

    @FXML
    void restartExecution(ActionEvent event) {
        System.out.println("here we should reexecute the code");

    }

    @FXML
    void stopExecution(ActionEvent event) {
        System.out.println("here we should return to the SuccessFileUpload scene");

    }

    @FXML
    void initialize() {

    }

}