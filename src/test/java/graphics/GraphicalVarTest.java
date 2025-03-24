package graphics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphicalVarTest {

    private GraphicalVar var;

    @BeforeEach
    public void setUp() {
        // Initialize the GraphicalVar class with an initial value of "5"
        var = new GraphicalVar("x", "5", null); // The third parameter is irrelevant for logical tests
    }

    @Test
    public void testInitialValue() {
        // Check if the initial value is as expected
        assertEquals("5", var.getValue());
    }

    @Test
    public void testUpdateValue() {
        // Update the value and verify it has changed correctly
        var.updateValue("10");
        assertEquals("10", var.getValue());
    }

    //TODO somehow test this
    @Test
    public void testUpdateRender() {
        
    }

    @Test
    public void testUpdateViaInterface() {
        // Simulate an update via the `update` method of the interface
        try {
            // there will be an error since pane is null, but the variable is still modifiedg
            var.update(0, "50"); // This method internally calls updateValue and updateRender
        } catch (Exception e) {
            
        }
        assertEquals("50", var.getValue());
    }

    @Test
    public void testMultipleUpdates() {
        // Test multiple sequential updates
        var.updateValue("100");
        assertEquals("100", var.getValue());

        var.updateValue("200");
        assertEquals("200", var.getValue());

        var.updateValue("300");
        assertEquals("300", var.getValue());
    }
}
