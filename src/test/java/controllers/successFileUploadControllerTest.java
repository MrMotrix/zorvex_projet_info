package controllers;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import application.App;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.util.List;

class successFileUploadControllerTest{
    private successFileUploadController controller;
    private MainController mainController;
    private App app;
    
    @BeforeAll
    static void initToolkit() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }
    
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
            assertNotNull(mainController.successScene, "La escena de éxito no debe ser nula después de la ejecución");
        });
    }
    
    @Test
    void testSaveStoresCorrectValues() {
        Platform.runLater(() -> {
            controller.save();
            assertEquals(controller.getSplitPane().getDividerPositions()[0], mainController.getSplitPaneDividerPosition(), "El divisor debe guardarse correctamente");
        });
    }
}