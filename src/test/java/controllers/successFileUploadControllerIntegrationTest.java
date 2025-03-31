package controllers;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import application.App;

public class successFileUploadControllerIntegrationTest {

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
    void testIntegrationWithMainController() {
        Platform.runLater(() -> {
            List<String> sampleCode = List.of("x <- 5", "y <- 10", "x <- x + y");
            mainController.setContentValues(sampleCode, sampleCode.size());
            controller.setMainController(mainController);
            assertEquals(sampleCode, mainController.content);
        });
    }

    @Test
    void testIntegrationWithApp() {
        Platform.runLater(() -> {
            controller.setApp(app);
            assertNotNull(app);
        });
    }
}
