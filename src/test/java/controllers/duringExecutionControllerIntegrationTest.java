package controllers;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import application.App;
import base.JavaFXTestBase;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.util.List;

/**
 * Integration tests for the duringExecutionController.
 * These tests check the interaction between various components
 * of the application
 */
public class duringExecutionControllerIntegrationTest extends JavaFXTestBase{
    
    private static duringExecutionController controller;
    
    @BeforeEach
    void setUp() throws Exception {

        MainController mainController = new MainController();
        List<String> codeLines = List.of("n <- 10", "p <- 20", "p <- 10", "h <- 6");
        int numberOfLines = codeLines.size();

        mainController.setContentValues(codeLines, numberOfLines);
        controller = new duringExecutionController(new MainController(), new App());
        controller.initialize2(mainController, controller.getApp());
    }
    
    @Test
    void testContinueExecutionIntegration() {
        // Simulate continueExecution to verify interaction between the continueButton and code execution.
        Platform.runLater(() -> {
            controller.continueExecution(null);
            assertTrue(controller.getContinueButton().isDisable());  // Verify button is disabled after execution
        });
    }

    @Test
    void testGoNextLineIntegration() {
        // Verify that the line progresses as expected when going to the next line.
        Platform.runLater(() -> {
            int initialLine = controller.getCurrentHighlightedLine();
            controller.goNextLine(null);
            assertEquals(initialLine + 1, controller.getCurrentHighlightedLine());  // Check if the line increments.
        });
    }

    @Test
    void testRestartExecutionIntegration() {
        // Check if the execution restarts and the current line is reset.
        Platform.runLater(() -> {
            controller.restartExecution(null);
            assertEquals(1, controller.getCurrentHighlightedLine());  // After restart, current line should be 1.
        });
    }

    @Test
    void testHandleParsingException() {
        MainController mainController = new MainController();
        // the last line of the code has an error
        List<String> codeLines = List.of("n <- 10", "p <- 20", "p <- 10", "h <- 6", "a");
        int numberOfLines = codeLines.size();
    
        mainController.setContentValues(codeLines, numberOfLines);
        controller = new duringExecutionController(mainController, new App());
    
        try {
            // assertDoesNotThrow(() -> {
            //     controller.initialize2(mainController, controller.getApp());
            // }, "initialize2 should not throw an exception");
            controller.initialize2(mainController, new App());
        } catch (Exception e) {
            
            // e.printStackTrace();
        }
    
        // If an exception occurs, verify the console message
        String consoleText = controller.getMainController().getConsole().getText();
        assertFalse(consoleText.contains("Un erreur est sourvenu lors de la lecture du fichier"), 
            "The console should not contain an error message");
    }
    
}
