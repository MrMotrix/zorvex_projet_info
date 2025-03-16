package graphics;

import java.util.ArrayList;
import java.util.List;

public class GraphicalRepresentation {

    public List<GraphicalObject> elements;

    public static double initialX = 10;
    public static double initialY = 10;
    public static double spaceBetweenPlots = 100;
    
    public GraphicalRepresentation(List<GraphicalObject> elements) {
        this.elements = elements;
    }

    public GraphicalRepresentation(){
        this.elements = new ArrayList<>();
    }

    public List<GraphicalObject> getElements() {
        return elements;
    }

    public void setElements(List<GraphicalObject> elements) {
        this.elements = elements;
    }

    public void addElement(GraphicalObject element) {
        elements.add(element);
    }

    // TODO this string should be or not replaced by the enum mentionned in the GraphicalArray class    
    public void updateVariable(String name, String value) {
        // TODO add update
        elements.get(0);
    }

    public void deleteElement(GraphicalObject element) {  
        elements.remove(element);
    }

    public void renderAll(){
        for (GraphicalObject obj : elements) {
            obj.removeFromPane();
        }
        reinitializePositioningValues();
        //TODO implement coordinates management

        // double x = 10;
        // double y = 10;

        for (GraphicalObject element : elements){
            element.draw(initialX,initialY);
            initialY += spaceBetweenPlots;
        }
    }

    public void addAndRenderElement(GraphicalVar graphicalVar) {
        // TODO Auto-generated method stub
        addElement(graphicalVar);
        graphicalVar.draw(initialX, initialY);
        initialY += spaceBetweenPlots;
    }
    
    // this could be made non static, and wouuld probably be better
    public static void reinitializePositioningValues(){
        initialX = 10;
        initialY = 10;
        spaceBetweenPlots = 100;
    }

    public void clear() {
        for (GraphicalObject obj : elements) {
            obj.removeFromPane();
        }
        elements.clear();
        reinitializePositioningValues();
    }

}