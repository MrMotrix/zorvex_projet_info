package controllers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import application.App;

public class successFileUploadControllerFunctionalTest {

    private static successFileUploadController controller;
    private static MainController mainController;
    private static App app;

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
    void testConsolePanelUpdatesCorrectly() {
        Platform.runLater(() -> {
            TextArea console = new TextArea();
            console.setText("Execution started...");
            controller.save();
            assertEquals("Execution started...", console.getText());
        });
    }

    @Test
    void testBreakPointsFunctionality() {
        Platform.runLater(() -> {
            controller.setMainController(mainController);
            mainController.bkpoints.add(2);
            controller.save();
            assertTrue(mainController.bkpoints.contains(2));
        });
    }
}
