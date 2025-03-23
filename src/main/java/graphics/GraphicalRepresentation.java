package graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import interpreter.ZorvexValue;
import interpreter.expression.Variable;

public class GraphicalRepresentation {

    private List<GraphicalObject> elements;

    // public static double currentX = 10;
    // public static double currentY = 30;
    // public static double spaceBetweenPlots = 100;

    private  double currentX;
    private  double currentY;
    private  double spaceBetweenPlots;
    
    public GraphicalRepresentation(List<GraphicalObject> elements) {
        this.elements = elements;
        reinitializePositioningValues();
    }

    public GraphicalRepresentation(){
        this.elements = new ArrayList<>();
        reinitializePositioningValues();

    }

    public List<GraphicalObject> getElements() {
        return elements;
    }

    public void setElements(List<GraphicalObject> elements) {
        this.elements = elements;
    }

    public void addElement(GraphicalObject element) {
        element.draw(currentX, currentY);
        elements.add(element);
        currentY += spaceBetweenPlots;
    }

    // TODO this string should be or not replaced by the enum mentionned in the GraphicalArray class    
    public void updateElement(int id, GraphicalObject element) {
        // TODO add update
        elements.set(id, element);
        elements.get(id).draw(id, id);
    }

    public void deleteElement(GraphicalObject element) {
        if (elements.remove(element)) {
            element.removeFromPane();
            // if somethig is deleted, it shifts everyting
            reorganize();
        }
    }
    
    // private void drawElement(GraphicalObject element) {
    //     element.draw(currentX,currentY);
    //     currentY += spaceBetweenPlots;
    // }

    // public void addAndRenderElement(GraphicalVar graphicalVar) {

    //     addElement(graphicalVar);
    //     graphicalVar.draw(currentX, currentY);
    //     currentY += spaceBetweenPlots;
    // }
    
    // (re)initialise values for psitioning
    public void reinitializePositioningValues(){
        this.currentX = 10;
        this.currentY = 30;
        this.spaceBetweenPlots = 100;
    }

    public void clear() {
        for (GraphicalObject obj : elements) {
            obj.removeFromPane();
        }
        elements.clear();
        this.reinitializePositioningValues();
    }

    private void reorganize() {
        // clear the pane
        for (GraphicalObject obj : elements) {
            obj.removeFromPane();
        }
        // reinitialize positioning
        this.reinitializePositioningValues();
    
        // draw all the elements
        for (GraphicalObject obj : elements) {
            obj.draw(currentX, currentY);
            currentY += spaceBetweenPlots;
        }
    }

}

// ========================================================================================

// public class GraphicalRepresentation {

//     private List<GraphicalObject> elements;
//     private static double currentX = 10;
//     private static double currentY = 10;
//     private static double spaceBetweenPlots = 100;
    
//     // Stack for saving the history of states for undo/redo functionality
//     private Stack<Snapshot> history;
//     private Stack<Snapshot> future;

//     public GraphicalRepresentation() {
//         this.elements = new ArrayList<>();
//         this.history = new Stack<>();
//         this.future = new Stack<>();
//     }

//     public List<GraphicalObject> getElements() {
//         return elements;
//     }

//     public void setElements(List<GraphicalObject> elements) {
//         this.elements = elements;
//     }

//     // Adds an element and stores the state
//     public void addElement(GraphicalObject element) {
//         saveState(); // Save current state before adding the new element
//         element.draw(currentX, currentY);
//         elements.add(element);
//         currentY += spaceBetweenPlots;
//     }

//     // Updates an element and stores the previous state
//     public void updateElement(int id, GraphicalObject element) {
//         saveState(); // Save the previous state before updating
//         elements.set(id, element);
//         elements.get(id).draw(currentX, currentY);
//     }

//     // Deletes an element and saves the state before removal
//     public void deleteElement(GraphicalObject element) {
//         if (elements.remove(element)) {
//             saveState(); // Save state before removal
//             element.removeFromPane();
//             reorganize();
//         }
//     }

//     // Renders all elements
//     public void renderAll() {
//         for (GraphicalObject obj : elements) {
//             obj.removeFromPane();
//         }
//         reinitializePositioningValues();

//         for (GraphicalObject element : elements) {
//             drawElement(element);
//         }
//     }

//     // Draws the element at the current position
//     private void drawElement(GraphicalObject element) {
//         element.draw(currentX, currentY);
//         currentY += spaceBetweenPlots;
//     }

//     // Clears all elements and resets the state
//     public void clear() {
//         saveState(); // Save the current state before clearing
//         for (GraphicalObject obj : elements) {
//             obj.removeFromPane();
//         }
//         elements.clear();
//         reinitializePositioningValues();
//     }

//     // Reorganize elements after deletion or clearing
//     private void reorganize() {
//         currentX = 10;
//         currentY = 10;
//         for (GraphicalObject obj : elements) {
//             obj.draw(currentX, currentY);
//             currentY += spaceBetweenPlots;
//         }
//     }

//     // Save the current state of elements into history
//     private void saveState() {
//         Snapshot snapshot = new Snapshot(new ArrayList<>(elements));
//         history.push(snapshot);  // Save the current state into history
//         future.clear();  // Clear the future stack since new actions invalidate redo history
//     }

//     // Undo the last action, restoring the previous state
//     public void undo() {
//         if (!history.isEmpty()) {
//             Snapshot lastState = history.pop();
//             future.push(new Snapshot(new ArrayList<>(elements)));  // Save current state for redo
//             restoreState(lastState);
//         }
//     }

//     // Redo the last undone action
//     public void redo() {
//         if (!future.isEmpty()) {
//             Snapshot nextState = future.pop();
//             history.push(new Snapshot(new ArrayList<>(elements)));  // Save current state for undo
//             restoreState(nextState);
//         }
//     }

//     // Restore the state of elements from a snapshot
//     private void restoreState(Snapshot snapshot) {
//         elements = snapshot.getElements();
//         renderAll(); // Re-render all elements after restoring state
//     }

//     // Reinitialize the position values (for when the graphical area is cleared)
//     public static void reinitializePositioningValues() {
//         currentX = 10;
//         currentY = 10;
//         spaceBetweenPlots = 100;
//     }

//     private static class Snapshot {
//         private List<GraphicalObject> elements;

//         public Snapshot(List<GraphicalObject> elements) {
//             this.elements = elements;
//         }

//         public List<GraphicalObject> getElements() {
//             return elements;
//         }
//     }

//     // Example of a method to add and render a specific element (like a variable)
//     public void addAndRenderElement(GraphicalVar graphicalVar) {
//         addElement(graphicalVar);
//         graphicalVar.draw(currentX, currentY);
//         currentY += spaceBetweenPlots;
//     }

//     public void restoreNextState() {
//         this.redo();  // Llama a redo en GraphicalRepresentation para restaurar el siguiente estado
//     }
    
// }