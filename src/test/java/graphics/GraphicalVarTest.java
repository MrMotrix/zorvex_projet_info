package graphics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.layout.Pane;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

public class GraphicalVarTest {

    private GraphicalVar var;
    
    @BeforeAll
    static void initToolkit() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }

    @BeforeEach
    public void setUp() {
        Pane pane = new Pane(); // Create a new Pane for each test
        // Initialize the GraphicalVar class with an initial value of "5"
        var = new GraphicalVar("x", "5", pane); // The third parameter is irrelevant for logical tests
        var.draw(0, 0);
    }

    @Test
    public void testInitialValue() {
        // Check if the initial value is as expected
        assertEquals("5", var.getValue());
    }

    @Test
    public void testUpdateValue() {
        // Update the value and verify it has changed correctly
        var.updateValue(0, "10");
        assertEquals("10", var.getValue());
    }

    //TODO somehow test this
    @Test
    public void testUpdateRender() {
        var.update(0, "10000");
        assertEquals("x = 10000", var.getRenderedValue());
        var.update(0, "20000");
        assertEquals("x = 20000", var.getRenderedValue());
    }

    @Test
    public void testUpdateViaInterface() {
        // Simulate an update via the `update` method of the interface
            // there will be an error since pane is null, but the variable is still modifiedg
            var.update(0, "50"); // This method internally calls updateValue and updateRender
            
        assertEquals("50", var.getValue());
    }

    @Test
    public void testMultipleUpdates() {
        // Test multiple sequential updates
        var.updateValue(0, "100");
        assertEquals("100", var.getValue());

        var.updateValue(0, "200");
        assertEquals("200", var.getValue());

        var.updateValue(0, "300");
        assertEquals("300", var.getValue());
    }
}
