package graphics;

import java.util.HashMap;
import java.util.Map;
import graphics.IterableGraphicalObject;

public class GraphicalRepresentation {

    private Map<String, GraphicalObject> elements;
    
    private double currentX;
    private double currentY;
    private double spaceBetweenPlots;
    
    public GraphicalRepresentation() {
        this.elements = new HashMap<>();
        reinitializePositioningValues();
    }

    public Map<String, GraphicalObject> getElements() {
        return elements;
    }

    public GraphicalObject getElement(String name){
        return elements.get(name);
    }

    public void setElements(Map<String, GraphicalObject> elements) {
        this.elements = elements;
    }

    public void addElement(String name, GraphicalObject element) {
        element.draw(currentX, currentY);
        elements.put(name, element);
        currentY += spaceBetweenPlots;
    }

    public void updateElement(String name, ModificationType type, String value, int index) {
        if (elements.containsKey(name)) {
            switch (type) {
                case INSERT:
                    IterableGraphicalObject obj1 = ((IterableGraphicalObject)elements.get(name));
                    obj1.addNodeAt(index, value);
                    break;
                case REMOVE:
                    IterableGraphicalObject obj2 = ((IterableGraphicalObject)elements.get(name));
                    obj2.deleteNodeAt(index);
                    break;
                case UPDATE: 
                    elements.get(name).update(index, value);
                default:
                    break;
            }
        }
    }

    public void deleteElement(String name) {
        if (elements.containsKey(name)) {
            elements.get(name).removeFromPane();
            elements.remove(name);
            reorganize();
        }
    }

    public void reinitializePositioningValues() {
        this.currentX = 10;
        this.currentY = 30;
        this.spaceBetweenPlots = 100;
    }

    public void clear() {
        for (GraphicalObject obj : elements.values()) {
            obj.removeFromPane();
        }
        elements.clear();
        this.reinitializePositioningValues();
    }

    private void reorganize() {
        for (GraphicalObject obj : elements.values()) {
            obj.removeFromPane();
        }
        this.reinitializePositioningValues();
    
        for (GraphicalObject obj : elements.values()) {
            obj.draw(currentX, currentY);
            currentY += spaceBetweenPlots;
        }
    }
}