package graphics;

import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;

import base.JavaFXTestBase;

import static org.junit.jupiter.api.Assertions.*;

public class GraphicalLinkedListTest extends JavaFXTestBase{

    @Test
    void testInsertNodeAtMiddle() {
        Pane pane = new Pane();
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, pane, 0);
        list.insertNodeWODraw(1, "X");
        assertArrayEquals(new String[]{"A", "X", "B", "C"}, list.getValues());
    }

    @Test
    void testInsertNodeAtStart() {
        Pane pane = new Pane();
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B"}, pane, 0);
        list.insertNodeWODraw(0, "Start");
        assertArrayEquals(new String[]{"Start", "A", "B"}, list.getValues());
    }

    @Test
    void testInsertNodeAtEnd() {
        Pane pane = new Pane();
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B"}, pane, 0);
        list.insertNodeWODraw(2, "End");
        assertArrayEquals(new String[]{"A", "B", "End"}, list.getValues());
    }

    @Test
    void testDeleteMiddleNode() {
        Pane pane = new Pane();
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, pane, 0);
        list.deleteNodeWODraw(1);
        assertArrayEquals(new String[]{"A", "C"}, list.getValues());
    }

    @Test
    void testDeleteFirstNode() {
        Pane pane = new Pane();
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, pane, 0);
        list.deleteNodeWODraw(0);
        assertArrayEquals(new String[]{"B", "C"}, list.getValues());
    }

    @Test
    void testDeleteLastNode() {
        Pane pane = new Pane();
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, pane, 0);
        list.deleteNodeWODraw(2);
        assertArrayEquals(new String[]{"A", "B"}, list.getValues());
    }

    @Test
    void testUpdateNodeValue() {
        Pane pane = new Pane();
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, pane, 0);
        list.updateValue(1, "Updated");
        assertArrayEquals(new String[]{"A", "Updated", "C"}, list.getValues());
    }

    @Test
    void testInsertAndDeleteOperations() {
        Pane pane = new Pane();
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C", "D"}, pane, 0);
        list.draw(0, 0);
        list.addNodeAt(0, "Start");
        list.addNodeAt(2, "Middle");
        list.addNodeAt(6, "End");
        assertArrayEquals(new String[]{"Start", "A", "Middle", "B", "C", "D", "End"}, list.getValues());

        list.deleteNodeAt(0);
        list.deleteNodeAt(2);
        list.deleteNodeAt(4);
        assertArrayEquals(new String[]{"A", "Middle", "C", "D"}, list.getValues());
    }
}
