package graphics;

import java.util.ArrayList;
import java.util.List;

public class GraphicalRepresentation {

    public List<GraphicalObject> elements;
    
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
    }

    public void deleteElement(GraphicalObject element) {  
        elements.remove(element);
    }

    public void renderAll(){

        //TODO implement coordinates management

        double x = 10;
        double y = 10;

        for (GraphicalObject element : elements){
            element.draw(x,y);
            y += 100;
        }
    }
    
}
