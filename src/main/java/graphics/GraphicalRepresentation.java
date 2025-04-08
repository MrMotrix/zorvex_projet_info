package graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GraphicalRepresentation {

    private Map<Integer, GraphicalObject> elements;
    private Map<String, GraphicalFunctionDeclaration> functions;
    private ArrayList<Integer> graphicalRenderOrderIds;
    
    private double currentX;
    private double currentY;
    private double spaceBetweenPlots;
    private Stack<Integer> graphicalFCOrder;
    
    public GraphicalRepresentation() {
        this.elements = new HashMap<>();
        this.graphicalRenderOrderIds = new ArrayList<>();
        this.graphicalFCOrder = new Stack<>();
        this.functions = new HashMap<>();
        reinitializePositioningValues();
    }

    public Stack<Integer> fcalls(){
        return graphicalFCOrder;

    }

    public Map<Integer, GraphicalObject> getElements() {
        return elements;
    }

    public Map<String, GraphicalFunctionDeclaration> getFunctions() {
        return functions;
    }

    public GraphicalFunctionDeclaration getFunction(String name) {
        return functions.get(name);
    }

    public void addFunction(String name, GraphicalFunctionDeclaration function){
        if (functions == null) {
            functions = new HashMap<>();
        }
        functions.put(name, function);
    }

    public GraphicalObject getElement(int id){
        return elements.get(id);
    }

    public void setElements(Map<Integer, GraphicalObject> elements) {
        this.elements = elements;
    }

    public void addElement(int id, GraphicalObject element) {
            
        element.draw(currentX, currentY);
        elements.put(id, element);
        graphicalRenderOrderIds.add(id);
        
        adjustSpacing(element);
    }

    private void adjustSpacing(GraphicalObject element) {
        if (element instanceof GraphicalFunctionDeclaration){
            currentY += 80;
        }
        else if (element instanceof GraphicalArray){
            currentY += spaceBetweenPlots;
        }
        else if (element instanceof GraphicalPile){
            currentY += spaceBetweenPlots;
        }        
        else if (element instanceof GraphicalLinkedList){
            currentY += spaceBetweenPlots;
        }
        else if (element instanceof GraphicalVar){
            currentY += 80;
        }
        else if(element instanceof GraphicalFunctionCall){
            currentY +=60;
        }
        else if (element != null){
            currentY += spaceBetweenPlots;
        }
    }

    public void updateElement(int id, ModificationType type, String value, int index) {
        if (elements.containsKey(id)) {
            switch (type) {
                case INSERT:
                if(elements.get(id) instanceof GraphicalPile pile){
                    pile.push(value);
                    return;
                }
                    IterableGraphicalObject obj1 = ((IterableGraphicalObject)elements.get(id));
                    obj1.addNodeAt(index, value);
                    break;
                case REMOVE:
                    if(elements.get(id) instanceof GraphicalPile pile){
                        pile.pop();
                        return;
                    }
                    IterableGraphicalObject obj2 = ((IterableGraphicalObject)elements.get(id));
                    obj2.deleteNodeAt(index);
                    break;
                case UPDATE: 
                    elements.get(id).update(index, value);
                    break;
                case POP :
                    if(elements.get(id) instanceof GraphicalPile pile){
                        pile.pop();
                        return;
                    }
                case PUSH :
                    if(elements.get(id) instanceof GraphicalPile pile){
                        pile.push(value);
                        return;
                    }
                    throw new IllegalArgumentException("Invalid modification type for this element.");
                    
                default:
                getElements().get(id).update(index, value);
                    break;
            }
        }
    }

    public void deleteElement(int id) {
        if (elements.containsKey(id)) {
            elements.get(id).removeFromPane();
            elements.remove(id);
            graphicalRenderOrderIds.remove(graphicalRenderOrderIds.indexOf(id));
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
        graphicalRenderOrderIds.clear();
        graphicalFCOrder.clear();
        functions.clear();
        this.reinitializePositioningValues();
    }

    private void reorganize() {
        for (GraphicalObject obj : elements.values()) {
            obj.removeFromPane();
        }
        this.reinitializePositioningValues();
    
        // for (GraphicalObject obj : elements.values()) {
        //     obj.draw(currentX, currentY);
        //     adjustSpacing(obj);
        // }

        for (int id : graphicalRenderOrderIds) {
            GraphicalObject obj = elements.get(id);
            if (obj != null) {
                obj.draw(currentX, currentY);
                adjustSpacing(obj);
                if (obj instanceof GraphicalFunctionCall){
                    increaseX(40);
                }
                else if (obj instanceof GraphicalVar v && v.getID() < 0){
                    increaseX(-40);
                }
            }
        }
    }

    public void increaseX(double x) {
        this.currentX += x;
    }
}