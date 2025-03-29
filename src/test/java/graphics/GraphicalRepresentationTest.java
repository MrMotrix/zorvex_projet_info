package graphics;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;

class GraphicalRepresentationTest {

    private GraphicalRepresentation graphRep;

    @BeforeEach
    void setUp() {
        graphRep = new GraphicalRepresentation();
    }

    @Test
    void testAddElement() {
        GraphicalObject obj = new TestGraphicalObject("Test1");
        graphRep.addElement("Test1", obj);
        
        Map<String, GraphicalObject> elements = graphRep.getElements();
        assertEquals(1, elements.size());
        assertTrue(elements.containsKey("Test1"));
    }

    @Test
    void testUpdateElement() {
        TestGraphicalObject obj = new TestGraphicalObject("Test1");
        graphRep.addElement("Test1", obj);
        graphRep.updateElement("Test1", "NewValue", 0);
        
        assertEquals("NewValue", obj.getLastUpdatedValue());
    }

    @Test
    void testDeleteElement() {
        GraphicalObject obj = new TestGraphicalObject("Test1");
        graphRep.addElement("Test1", obj);
        graphRep.deleteElement("Test1");
        
        assertTrue(graphRep.getElements().isEmpty());
    }

    @Test
    void testClear() {
        graphRep.addElement("Test1", new TestGraphicalObject("Test1"));
        graphRep.addElement("Test2", new TestGraphicalObject("Test2"));
        graphRep.clear();
        
        assertTrue(graphRep.getElements().isEmpty());
    }

    @Test
    void reorganize() {
        graphRep.addElement("Test1", new TestGraphicalObject("Test1"));
        graphRep.addElement("Test2", new TestGraphicalObject("Test2"));
        double y = ((TestGraphicalObject)(graphRep.getElements().get("Test2"))).getY();
        graphRep.addElement("Test3", new TestGraphicalObject("Test3"));
        graphRep.deleteElement("Test2");
        
        assertTrue(((TestGraphicalObject)(graphRep.getElements().get("Test3"))).getY() == y);
    }
    
    private class TestGraphicalObject implements GraphicalObject {
        private String name;
        private String lastUpdatedValue;
        private double x;
        private double y;
        
        public TestGraphicalObject(String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return name;
        }

        @Override
        public void draw(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public void removeFromPane() {
            // Simulación de eliminación
        }
        
        @Override
        public void updateValue(int index, String value) {
            this.lastUpdatedValue = value;
        }
        
        @Override
        public void updateRender(int index, String value) {
            // Simulación de renderizado
        }
        
        public String getLastUpdatedValue() {
            return lastUpdatedValue;
        }

        public double getY() {
            return y;
        }
    }

}