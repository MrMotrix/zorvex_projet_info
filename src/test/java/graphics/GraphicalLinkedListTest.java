package graphics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GraphicalLinkedListTest {

    @Test
    void testInsertNodeAtMiddle() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, null);
        list.insertNodeWODraw(1, "X");
        assertArrayEquals(new String[]{"A", "X", "B", "C"}, list.getValues());
    }

    @Test
    void testInsertNodeAtStart() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B"}, null);
        list.insertNodeWODraw(0, "Start");
        assertArrayEquals(new String[]{"Start", "A", "B"}, list.getValues());
    }

    @Test
    void testInsertNodeAtEnd() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B"}, null);
        list.insertNodeWODraw(2, "End");
        assertArrayEquals(new String[]{"A", "B", "End"}, list.getValues());
    }

    @Test
    void testDeleteMiddleNode() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, null);
        list.deleteNodeWODraw(1);
        assertArrayEquals(new String[]{"A", "C"}, list.getValues());
    }

    @Test
    void testDeleteFirstNode() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, null);
        list.deleteNodeWODraw(0);
        assertArrayEquals(new String[]{"B", "C"}, list.getValues());
    }

    @Test
    void testDeleteLastNode() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, null);
        list.deleteNodeWODraw(2);
        assertArrayEquals(new String[]{"A", "B"}, list.getValues());
    }
    // TODO : this 3 tests require a bit of attention, i-ll fix that later
    @Test
    void testUpdateValueAtMiddle() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, null);
        list.update(1, "Updated");
        assertArrayEquals(new String[]{"A", "Updated", "C"}, list.getValues());
    }

    @Test
    void testUpdateValueAtStart() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, null);
        list.update(0, "Updated");
        assertArrayEquals(new String[]{"Updated", "B", "C"}, list.getValues());
    }

    @Test
    void testupdateAtEnd() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, null);
        list.update(2, "Updated");
        assertArrayEquals(new String[]{"A", "B", "Updated"}, list.getValues());
    }

    @Test
    void testInsertAndDeleteOperations() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C", "D"}, null);
        
        // Insert at the beginning
        list.insertNodeWODraw(0, "Start");
        assertArrayEquals(new String[]{"Start", "A", "B", "C", "D"}, list.getValues());

        // Insert in the middle
        list.insertNodeWODraw(2, "Middle");
        assertArrayEquals(new String[]{"Start", "A", "Middle", "B", "C", "D"}, list.getValues());

        // Insert at the end
        list.insertNodeWODraw(6, "End");
        assertArrayEquals(new String[]{"Start", "A", "Middle", "B", "C", "D", "End"}, list.getValues());

        // Delete the start node
        list.deleteNodeWODraw(0);
        assertArrayEquals(new String[]{"A", "Middle", "B", "C", "D", "End"}, list.getValues());

        // Delete the middle node
        list.deleteNodeWODraw(2);
        assertArrayEquals(new String[]{"A", "Middle", "C", "D", "End"}, list.getValues());

        // Delete the last node
        list.deleteNodeWODraw(5);
        assertArrayEquals(new String[]{"A", "Middle", "C", "D"}, list.getValues());
    }

    @Test
    void testEmptyListInsert() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{}, null);
        list.insertNodeWODraw(0, "NewNode");
        assertArrayEquals(new String[]{"NewNode"}, list.getValues());
    }

    @Test
    void testEmptyListDelete() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A"}, null);
        list.deleteNodeWODraw(0);
        assertArrayEquals(new String[]{}, list.getValues());
    }

    @Test
    void testUpdateOutOfBounds() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, null);
        list.update(5, "OutOfBounds");
        assertArrayEquals(new String[]{"A", "B", "C"}, list.getValues());  // Ensure no changes were made
    }

    @Test
    void testDeleteOutOfBounds() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, null);
        list.deleteNodeWODraw(5);
        assertArrayEquals(new String[]{"A", "B", "C"}, list.getValues());  // Ensure no changes were made
    }

    @Test
    void testInsertNodeWithShift() {
        GraphicalLinkedList list = new GraphicalLinkedList("List", new String[]{"A", "B", "C"}, null);
        list.insertNodeWODraw(1, "ShiftedNode");
        assertArrayEquals(new String[]{"A", "ShiftedNode", "B", "C"}, list.getValues());
    }
}
