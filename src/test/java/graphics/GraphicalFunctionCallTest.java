package graphics;

import base.JavaFXTestBase;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphicalFunctionCallTest extends JavaFXTestBase {

    private GraphicalFunctionCall call;
    private GraphicalVar param1;
    private GraphicalVar param2;
    private GraphicalVar id1;
    private Pane pane;

    @BeforeEach
    void setUp() {
        pane = new Pane();
        param1 = new GraphicalVar("x", "1", pane, 0);
        param2 = new GraphicalVar("y", "2", pane, 1);
        id1 = new GraphicalVar("z", "3", pane, 2);
        call = new GraphicalFunctionCall("f", pane, List.of(param1, param2), 99);
    }

    @Test
    void testInitialParameters() {
        assertEquals(List.of(param1, param2), call.getParameters());
    }

    @Test
    void testSetParameters() {
        GraphicalVar p = new GraphicalVar("a", "10", pane, 3);
        call.draw(0, 0);
        call.setParameters(List.of(p));
        assertEquals(List.of(p), call.getParameters());
    }

    @Test
    void testAddParameter() {
        GraphicalVar p = new GraphicalVar("b", "20", pane, 4);
        call.addPar(p);
        assertTrue(call.getParameters().contains(p));
    }

    @Test
    void testSetIds() {
        call.setIds(List.of(id1));
        assertEquals(List.of(id1), call.getIds());
    }

    @Test
    void testAddId() {
        call.addId(id1);
        assertTrue(call.getIds().contains(id1));
    }

    @Test
    void testDraw() {
        call.draw(10, 20);
        assertEquals(2, call.renderedNodes.size());
    }

    @Test
    void testRemoveInternal() {
        call.addPar(param1);
        call.addId(id1);
        call.draw(0, 0);
        call.removeInternal();
        assertTrue(param1.renderedNodes.isEmpty());
        assertTrue(id1.renderedNodes.isEmpty());
    }

    @Test
    void testRemoveFromPane() {
        call.addPar(param1);
        call.addId(id1);
        call.draw(0, 0);
        call.removeFromPane();
        assertTrue(call.renderedNodes.isEmpty());
        assertTrue(param1.renderedNodes.isEmpty());
        assertTrue(id1.renderedNodes.isEmpty());
    }
}
