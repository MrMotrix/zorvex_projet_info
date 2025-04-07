package graphics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import base.JavaFXTestBase;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;

public class GraphicalVarTest extends JavaFXTestBase{

    private GraphicalVar var;
    
    // @BeforeAll
    // static void initToolkit() {
    //     if (!Platform.isFxApplicationThread()) {
    //         final CountDownLatch latch = new CountDownLatch(1);
    //         Platform.startup(latch::countDown);
    //         try {
    //             latch.await();
    //         } catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    @BeforeEach
    public void setUp() {
        Pane pane = new Pane(); // Create a new Pane for each test
        
        var = new GraphicalVar("x", "5", pane, 0); 
        var.draw(0, 0);
    }

    @Test
    public void testInitialValue() {
        
        assertEquals("5", var.getValue());
    }

    @Test
    public void testUpdateValue() {
        
        var.updateValue(0, "10");
        assertEquals("10", var.getValue());
    }

    
    @Test
    public void testUpdateRender() {
        var.update(0, "10000");
        assertEquals("x = 10000", var.getRenderedValue());
        var.update(0, "20000");
        assertEquals("x = 20000", var.getRenderedValue());
    }

    @Test
    public void testUpdateViaInterface() {
    
            var.update(0, "50");
            
        assertEquals("50", var.getValue());
    }

    @Test
    public void testMultipleUpdates() {
        
        var.updateValue(0, "100");
        assertEquals("100", var.getValue());

        var.updateValue(0, "200");
        assertEquals("200", var.getValue());

        var.updateValue(0, "300");
        assertEquals("300", var.getValue());
    }
}
