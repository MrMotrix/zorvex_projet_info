package graphics;

import org.junit.jupiter.api.Test;

import base.JavaFXTestBase;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class GraphicalPileTest extends JavaFXTestBase{

    private GraphicalPile pile;
    private Pane pane;

    @BeforeEach
    public void setUp() {
        pane = new Pane(); // Create a new Pane for each test
        pile = new GraphicalPile("myPile", List.of("A", "B", "C"), pane, 0);
    }

    @Test
    void testPush() {
        pile.push("Z");
        assertEquals(List.of("Z", "A", "B", "C"), pile.getValues());

        pile = new GraphicalPile("pile2",List.of("[INTEGER 3", "STRING hello world", "INTEGER 2]"),pane, 1);
        pile.push("A");
        assertEquals(List.of("A", "3", "hello world", "2"), pile.getValues());

    }

    @Test
    void testPop() {
        String val = pile.pop();
        assertEquals("A", val);
        assertEquals(List.of("B", "C"), pile.getValues());
    }

    @Test
    void testPopEmpty() {
        pile.pop();
        pile.pop();
        pile.pop();
        assertNull(pile.pop());
    }

    @Test
    void testPeek() {
        assertEquals("A", pile.peek());
    }

    @Test
    void testPeekEmpty() {
        pile.pop();
        pile.pop();
        pile.pop();
        assertNull(pile.peek());
    }

    @Test
    void testDrawDoesNotThrow() {
        assertDoesNotThrow(() -> pile.draw(10, 20));
    }


}

