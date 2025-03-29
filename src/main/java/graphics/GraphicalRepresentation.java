package graphics;

import java.util.HashMap;
import java.util.Map;

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

    public void setElements(Map<String, GraphicalObject> elements) {
        this.elements = elements;
    }

    public void addElement(String name, GraphicalObject element) {
        element.draw(currentX, currentY);
        elements.put(name, element);
        currentY += spaceBetweenPlots;
    }

    public void updateElement(String name, String value, int index) {
        if (elements.containsKey(name)) {
            elements.get(name).update(index, value);
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