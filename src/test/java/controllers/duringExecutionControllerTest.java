package controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import application.App;
import javafx.scene.layout.VBox;

public class duringExecutionControllerTest {
    
    private static duringExecutionController controller;
    
    @BeforeAll
    static void initToolkit() {
        // This method is called once before all tests in this class
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }
    
    @BeforeEach
    void setUp() {
        MainController mainController = new MainController();
        List codeLines = List.of("n <- 10", "p <- 20", "p <- 10", "h <- 6");
        int numberOfLines = codeLines.size();

        mainController.setContentValues(codeLines, numberOfLines);
        controller = new duringExecutionController(new MainController() , new App());
        
    }
    
    @Test
    void testContinueExecution() {
        Platform.runLater(() -> {
            System.out.println(controller.getMainController().content);
            controller.continueExecution(null);
            assertTrue(controller.getContinueButton().isDisable()); 
        });
    }

    @Test
    void testGoNextLine() {
        Platform.runLater(() -> {
            
            int initialLine = controller.getCurrentHighlightedLine();
            controller.goNextLine(null);
            assertEquals(initialLine + 1, controller.getCurrentHighlightedLine());
        });
    }

    @Test
    void testHighlightCurrentLine() {
        Platform.runLater(() -> {

            controller.highlightCurrentLine(2);
            assertEquals(2, controller.getCurrentHighlightedLine());
        });
    }

    @Test
    void testRestartExecution() {
        Platform.runLater(() -> {
            
            controller.restartExecution(null);
            assertEquals(1, controller.getCurrentHighlightedLine());  
        });
    }

    @Test
    void testStopExecution() {
        Platform.runLater(() -> {
            
            controller.stopExecution(null);
            assertEquals(0, controller.getCurrentHighlightedLine());
        });
    }

    @Test
    void gettersTest(){
        Platform.runLater(() -> {
            
            assertNotNull(controller.getCodeContainer());
            assertNotNull(controller.getConsolePanel());
            assertNotNull(controller.getContinueButton());
            assertNotNull(controller.getLastLineButton());
            assertNotNull(controller.getNextLineButton());
            assertNotNull(controller.getRestartButton());
            assertNotNull(controller.getStopButton());
            assertNotNull(controller.getNblineScroller());
            assertNotNull(controller.getCodeScroller());
            assertNotNull(controller.getCanvasPane());
            assertNotNull(controller.getBkpointVbox());
            assertNotNull(controller.getNblineVbox());
        });
    }
}
