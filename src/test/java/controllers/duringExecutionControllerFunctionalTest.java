package controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import application.App;
import base.JavaFXTestBase;
import javafx.application.Platform;

class duringExecutionControllerFunctionalTest extends JavaFXTestBase{

    private duringExecutionController controller;

    @BeforeEach
    void setUp() {
        MainController mainController = new MainController();
        List codeLines = List.of("n <- 10", "p <- 20", "p <- 10", "h <- 6");
        int numberOfLines = codeLines.size();

        mainController.setContentValues(codeLines, numberOfLines);
        controller = new duringExecutionController(mainController , new App());
        
    }

    @Test
    void testExecutionFlow() {
        Platform.runLater(() -> {
        try{// Simulates execution step by step
            controller.goNextLine(null);
            assertEquals(2, controller.getMainController().getCurrentLine());

            controller.goNextLine(null);
            assertEquals(3, controller.getMainController().getCurrentLine());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
        });
        
    }

    @Test
    void testBreakpointHandling() {
        Platform.runLater(() -> {
        // Ensures that execution stops at breakpoints
        controller.getMainController().bkpoints.add(3);
        controller.continueExecution(null);
        assertEquals(3, controller.getMainController().getCurrentLine());
        });
    }
}
