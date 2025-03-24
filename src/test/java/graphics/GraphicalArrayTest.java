package graphics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
// import graphics.GraphicalArray;

class GraphicalArrayTest {
    @Test
    void testInsertValueAtMiddle() {
        GraphicalArray array = new GraphicalArray("array", new String[]{"A", "B", "C"}, null);
        array.insertNodeWODraw(1, "X");
        assertArrayEquals(new String[]{"A", "X", "B", "C"}, array.getValues());
    }

    @Test
    void testInsertValueAtStart() {
        GraphicalArray array = new GraphicalArray("array", new String[]{"A", "B"}, null);
        array.insertNodeWODraw(0, "Start");
        assertArrayEquals(new String[]{"Start", "A", "B"}, array.getValues());
    }

    @Test
    void testInsertValueAtEnd() {
        GraphicalArray array = new GraphicalArray("array", new String[]{"A", "B"}, null);
        array.insertNodeWODraw(2, "End");
        assertArrayEquals(new String[]{"A", "B", "End"}, array.getValues());
    }

    @Test
    void testDeleteMiddle() {
        GraphicalArray array = new GraphicalArray("array", new String[]{"A", "B", "C"}, null);
        array.deleteNodeWODraw(1);
        assertArrayEquals(new String[]{"A", "C"}, array.getValues());
    }

    @Test
    void testDeleteFirst() {
        GraphicalArray array = new GraphicalArray("array", new String[]{"A", "B", "C"}, null);
        array.deleteNodeWODraw(0);
        assertArrayEquals(new String[]{"B", "C"}, array.getValues());
    }

    @Test
    void testDeleteLast() {
        GraphicalArray array = new GraphicalArray("array", new String[]{"A", "B", "C"}, null);
        array.deleteNodeWODraw(2);
        assertArrayEquals(new String[]{"A", "B"}, array.getValues());
    }
}

