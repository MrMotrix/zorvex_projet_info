package graphics;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphicalNodeTest {

    // Asegura que JavaFX esté inicializado antes de ejecutar los tests
    @BeforeAll
    static void initToolkit() {
        if (!javafx.application.Platform.isFxApplicationThread()) {
            javafx.application.Platform.startup(() -> {});
        }
    }

    @Test
    void testDraw() {
        Pane pane = new Pane();
        GraphicalNode node = new GraphicalNode("TestValue", 0, pane);
        node.draw(50, 50);

        assertEquals(3, node.getRenderedNodes().size());
        assertTrue(node.getRenderedNodes().get(0) instanceof Rectangle); // ValueBox
        assertTrue(node.getRenderedNodes().get(1) instanceof TextField); // TextField
        assertTrue(node.getRenderedNodes().get(2) instanceof Rectangle); // PointerBox
    }

    @Test
    void testCreateOutgoingArrowTo() {
        Pane pane = new Pane();
        GraphicalNode fromNode = new GraphicalNode("From", 0, pane);
        GraphicalNode toNode = new GraphicalNode("To", 1, pane);

        fromNode.draw(50, 50);
        toNode.draw(150, 50);

        fromNode.createOutgoingArrowTo(toNode, pane);

        // check that arrows were created correctly
        assertNotNull(fromNode.getOutgoingArrow());
        assertNotNull(fromNode.getOutgoingArrowHead());
        assertTrue(fromNode.getOutgoingArrow() instanceof Line);
        assertTrue(fromNode.getOutgoingArrowHead() instanceof Polygon);
    }

    @Test
    void testClearOutgoingArrow() {
        Pane pane = new Pane();
        GraphicalNode fromNode = new GraphicalNode("From", 0, pane);
        GraphicalNode toNode = new GraphicalNode("To", 1, pane);

        fromNode.draw(50, 50);
        toNode.draw(150, 50);

        fromNode.createOutgoingArrowTo(toNode, pane);
        fromNode.clearOutgoingArrow(pane);

        // Verifica que las flechas hayan sido eliminadas
        assertNull(fromNode.getOutgoingArrow());
        assertNull(fromNode.getOutgoingArrowHead());
    }

    @Test
    void testUpdate() {
        Pane pane = new Pane();
        GraphicalNode node = new GraphicalNode("OldValue", 0, pane);
        node.draw(50, 50);

        node.update(0, "NewValue");

        // Verifica que el valor del nodo haya sido actualizado
        assertEquals("NewValue", node.getValue());
        TextField textField = (TextField) node.getRenderedNodes().get(1);
        assertEquals("NewValue", textField.getText());
    }

    @Test
    void testRedraw() {
        Pane pane = new Pane();
        GraphicalNode node = new GraphicalNode("TestValue", 0, pane);
        node.draw(50, 50);

        node.setX(100);
        node.setY(100);
        node.redraw(pane);

        // Verifica que el nodo haya sido redibujado en la nueva posición
        Rectangle valueBox = (Rectangle) node.getRenderedNodes().get(0);
        assertEquals(100, valueBox.getX());
        assertEquals(100, valueBox.getY());
    }
}
