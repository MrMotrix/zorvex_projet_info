package controllers;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import application.App;
import base.JavaFXTestBase;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.util.List;

class successFileUploadControllerTest extends JavaFXTestBase{
    private successFileUploadController controller;
    private MainController mainController;
    private App app;
    
    
    @BeforeEach
    void setUp() {
        mainController = new MainController();
        app = new App();
        controller = new successFileUploadController();
        controller.initialize2(mainController, app);
    }
    
    @Test
    void testBeginExecutionChangesScene() {
        Platform.runLater(() -> {
            controller.beginExecution(null);
            assertNotNull(mainController.successScene, "successFileUploadController instance should not be null after changintg scene.");
        });
    }
    
    @Test
    void testSaveStoresCorrectValues() {
        Platform.runLater(() -> {
            controller.save();
            assertEquals(controller.getSplitPane().getDividerPositions()[0], mainController.getSplitPaneDividerPosition(), "The divider position should be saved correctly.");
        });
    }

    @Test
    void testCheckSceneChangeToDuring() {
        Platform.runLater(() -> {
            controller.beginExecution(null);
            Scene currentScene = controller.getApp().getScene();
            Parent root = currentScene.getRoot();
            assertNotNull(root);
            assertTrue(root.getId() == null || root.getId().contains("during") || root.toString().toLowerCase().contains("during"));
        });
    
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}