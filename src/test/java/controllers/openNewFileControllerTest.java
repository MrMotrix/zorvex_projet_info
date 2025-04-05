package controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import application.App;
import base.JavaFXTestBase;
import javafx.stage.Stage;
import utilities.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class openNewFileControllerTest extends JavaFXTestBase{

    private openNewFileController controller;
    private App app;
    private MainController mainController;

    @BeforeEach
    public void setup() {
        controller = new openNewFileController();
        app = new App();
        mainController = new MainController();

        controller.setApp(app);
        controller.setMainController(mainController);
    }

    @Test
    public void testLoadFileLoadsCorrectly() throws Exception {
        File tempFile = File.createTempFile("test", ".txt");
        List<String> expectedLines = List.of("line 1", "line 2", "line 3");
        Files.write(tempFile.toPath(), expectedLines);

        var loadFileMethod = openNewFileController.class.getDeclaredMethod("loadFile", File.class);
        loadFileMethod.setAccessible(true);
        loadFileMethod.invoke(controller, tempFile);

        assertEquals(expectedLines, mainController.content);
        assertEquals(3, mainController.numberOfLines);
    }

    // @Test
    // public void testShowSuccessSceneSetsCorrectScene() {
    //     controller.showSuccessScene();
    //     assertEquals(Paths.SUCCESS_FILE_UPLOAD, app.getScene());
    // }

    // @Test
    // public void testFileNameIsSetAfterLoad() throws Exception {
    //     File tempFile = File.createTempFile("sample", ".txt");
    //     Files.write(tempFile.toPath(), List.of("test"));

    //     var loadFileMethod = openNewFileController.class.getDeclaredMethod("loadFile", File.class);
    //     loadFileMethod.setAccessible(true);
    //     loadFileMethod.invoke(controller, tempFile);

    //     app.getStageWindow().setTitle("Intepreting : " + tempFile.getName());
    //     mainController.nameFile = tempFile.getName();

    //     assertEquals(tempFile.getName(), mainController.nameFile);
    //     assertEquals("Intepreting : " + tempFile.getName(), app.getStageWindow().getTitle());
    // }
}
