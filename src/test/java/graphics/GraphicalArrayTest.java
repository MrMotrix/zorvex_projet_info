package graphics;

import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.layout.Pane;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class GraphicalArrayTest {

    private GraphicalArray array;
    

    @BeforeAll
    static void initToolkit() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }

    @BeforeEach
    public void setUp() {
        Pane pane = new Pane(); // Create a new Pane for each test
        array = new GraphicalArray("myArray", new String[]{"A", "B", "C"}, pane);
    }

    @Test
    void testInsertValueAtMiddle() {
        // GraphicalArray array = new GraphicalArray("array", new String[]{"A", "B", "C"}, pane);
        array.addNodeAt(1, "X");
        assertArrayEquals(new String[]{"A", "X", "B", "C"}, array.getValues());
    }

    @Test
    void testInsertValueAtStart() {
        // GraphicalArray array = new GraphicalArray("array", new String[]{"A", "B"}, pane);
        array.addNodeAt(0, "Start");
        assertArrayEquals(new String[]{"Start", "A", "B", "C"}, array.getValues());
    }

    @Test
    void testInsertValueAtEnd() {
        // GraphicalArray array = new GraphicalArray("array", new String[]{"A", "B"}, pane);
        array.addNodeAt(2, "End");
        assertArrayEquals(new String[]{"A", "B", "End", "C"}, array.getValues());
    }

    @Test
    void testDeleteMiddle() {
        // GraphicalArray array = new GraphicalArray("array", new String[]{"A", "B", "C"}, pane);
        array.deleteNodeAt(1);
        assertArrayEquals(new String[]{"A", "C"}, array.getValues());
    }

    @Test
    void testDeleteFirst() {
        // GraphicalArray array = new GraphicalArray("array", new String[]{"A", "B", "C"}, pane);
        array.deleteNodeAt(0);
        assertArrayEquals(new String[]{"B", "C"}, array.getValues());
    }

    @Test
    void testDeleteLast() {
        // GraphicalArray array = new GraphicalArray("array", new String[]{"A", "B", "C"}, pane);
        array.deleteNodeAt(2);
        assertArrayEquals(new String[]{"A", "B"}, array.getValues());
    }
}

