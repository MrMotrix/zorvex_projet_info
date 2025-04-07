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
        GraphicalObject obj = new TestGraphicalObject("Test1", 0);
        graphRep.addElement(0, obj);
        
        Map<Integer, GraphicalObject> elements = graphRep.getElements();
        assertEquals(1, elements.size());
        assertTrue(elements.containsKey(0));
    }

    @Test
    void testUpdateElement() {
        TestGraphicalObject obj = new TestGraphicalObject("Test1",0);
        graphRep.addElement(0, obj);
        graphRep.updateElement(0, ModificationType.UPDATE, "NewValue", 0);
        
        assertEquals("NewValue", obj.getLastUpdatedValue());
    }

    @Test
    void testDeleteElement() {
        GraphicalObject obj = new TestGraphicalObject("Test1",0 );
        graphRep.addElement(0, obj);
        graphRep.deleteElement(0);
        
        assertTrue(graphRep.getElements().isEmpty());
    }

    @Test
    void testClear() {
        graphRep.addElement(0, new TestGraphicalObject("Test1", 0));
        graphRep.addElement(1, new TestGraphicalObject("Test2", 1));
        graphRep.clear();
        
        assertTrue(graphRep.getElements().isEmpty());
    }

    @Test
    void reorganize() {
        graphRep.addElement(0, new TestGraphicalObject("Test1", 0));
        graphRep.addElement(1, new TestGraphicalObject("Test2", 1));
        double y = ((TestGraphicalObject)(graphRep.getElements().get(0))).getY();
        graphRep.addElement(2, new TestGraphicalObject("Test3",2));
        graphRep.deleteElement(1);
        
        assertTrue(((TestGraphicalObject)(graphRep.getElements().get(0))).getY() == y);
    }
    
    private class TestGraphicalObject implements GraphicalObject {
        private String name;
        private String lastUpdatedValue;
        private double x;
        private double y;
        private int id;
        
        public TestGraphicalObject(String name, int id) {
            this.name = name;
            this.id = id;
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