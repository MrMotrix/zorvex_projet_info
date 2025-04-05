package controllers;

import javafx.application.Platform;

import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.stage.Window;
import org.junit.jupiter.api.*;

import base.JavaFXTestBase;

import java.awt.Desktop;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class menuBarControllerTest extends JavaFXTestBase{

    private menuBarController controller;


    @BeforeEach
    public void setUp() throws Exception {
        controller = new menuBarController();
        MenuBar menuBar = new MenuBar();
        controller.getClass().getDeclaredField("menuBar").setAccessible(true);
        controller.getClass().getDeclaredField("menuBar").set(controller, menuBar);
    }

    // @Test
    // public void testAboutUsLaunchesAlert() throws Exception {
    //     CountDownLatch latch = new CountDownLatch(1);
    //     Platform.runLater(() -> {
    //         try {
    //             Method method = controller.getClass().getDeclaredMethod("aboutUs", javafx.event.ActionEvent.class);
    //             method.setAccessible(true);
    //             method.invoke(controller, new javafx.event.ActionEvent());
    //         } catch (Exception e) {
    //             fail("Exception thrown during aboutUs execution: " + e.getMessage());
    //         } finally {
    //             latch.countDown();
    //         }
    //     });
    //     boolean executed = latch.await(5, TimeUnit.SECONDS);
    //     assertTrue(executed, "Platform.runLater did not complete in time");
    // }
    

    @Test
    public void testCloseClosesStage() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Stage stage = new Stage();
            StackPane root = new StackPane(controller.getMenuBar());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            try {
                Method method = controller.getClass().getDeclaredMethod("close", javafx.event.ActionEvent.class);
                method.setAccessible(true);
                method.invoke(controller, new javafx.event.ActionEvent());
                assertFalse(stage.isShowing());
            } catch (Exception e) {
                fail(e);
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(3, TimeUnit.SECONDS));
    }

    @Test
    public void testOpenNewDoesNotThrow() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                Method method = controller.getClass().getDeclaredMethod("openNew", javafx.event.ActionEvent.class);
                method.setAccessible(true);
                method.invoke(controller, new javafx.event.ActionEvent());
            } catch (Exception e) {
                fail(e);
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    @Test
    public void testVoirDocumentationOpensBrowser() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                boolean supported = Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
                if (supported) {
                    Method method = controller.getClass().getDeclaredMethod("voirDocumentation", javafx.event.ActionEvent.class);
                    method.setAccessible(true);
                    method.invoke(controller, new javafx.event.ActionEvent());
                }
            } catch (Exception e) {
                fail(e);
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(3, TimeUnit.SECONDS));
    }
}