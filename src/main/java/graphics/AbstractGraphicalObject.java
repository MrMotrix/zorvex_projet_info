package graphics;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public abstract sealed class AbstractGraphicalObject implements GraphicalObject permits 
        GraphicalArray,
        GraphicalFunctionCall,
        GraphicalFunctionDeclaration,
        GraphicalLinkedList, 
        GraphicalVar, 
        GraphicalNode, 
        GraphicalPile
         {
    protected String name;
    protected Pane pane;
    protected List<Node> renderedNodes;
    protected final int id;

    public final Color COLOR_ARRAY_BOX = Color.web("#D3D3D3"); // #D3D3D3
    public final String COLOR_ARRAY_TEXT = "#000000"; // #000000


    public final Color COLOR_VARIABLE_BOX = Color.web("#5E7B9D"); // #5E7B9D
    public final String COLOR_VARIABLE_TEXT = "#ffffff"; // #ffffff


    public final Color COLOR_LLIST_CONTENT_BOX = Color.web("#CAEB0E"); // #CAEB0E
    public final Color COLOR_LLIST_POINTER_BOX = Color.web("#96423B"); // #96423B
    public final String COLOR_LLIST_CONTENT_TEXT = "#000000"; // #000000


    public final Color COLOR_TITLE_BOX_FUNCTION = Color.DARKBLUE; // #00008B
    public final Color COLOR_TITLE_FUNCTION = Color.web("#ffffff"); // #ffffff
    public final Color COLOR_CONTENT_BOX_FUNCTION = Color.LIGHTGRAY; // #D3D3D3
    public final Color COLOR_CONTENT_TEXT_FUNCTION = Color.web("#000000"); // #000000
    
    public final Color COLOR_ACTIVE_FUNCTION_CALL_BOX = Color.RED; // #FF0000
    public final Color COLOR_ACTIVE_FUNCTION_CALL_TEXT = Color.web("#ffffff"); // #000000

    public final Color COLOR_PILE_BOX = Color.YELLOW; // #FFFF00
    public final Color COLOR_PILE_TEXT = Color.web("#ffffff"); // #000000



    // public final Color COLOR_ARRAY_BOX = Color.web("ffffff"); // #646B3C
    // public final Color COLOR_VARIABLE_BOX = Color.web("ffffff"); // #5E7B9D
    // public final Color COLOR_LLIST_CONTENT_BOX = Color.web("ffffff"); // #CAEB0E
    // public final Color COLOR_LLIST_POINTER_BOX = Color.web("ffffff"); // #96423B
    // public final Color COLOR_TITLE_BOX_FUNCTION = Color.DARKBLUE; // #00008B
    // public final Color COLOR_CONTENT_BOX_FUNCTION = Color.WHITE; // #D3D3D3
    // public final Color COLOR_ACTIVE_FUNCTION_CALL_BOX = Color.WHITE; // #FF0000

    // public final String COLOR_VARIABLE_TEXT = "#000000"; // #ffffff
    // public final String COLOR_LLIST_CONTENT_TEXT = "#000000"; // #000000
    // public final String COLOR_ARRAY_TEXT = "#000000"; // #ffffff
    // public final Color COLOR_TITLE_FUNCTION = Color.web("#000000"); // #000000
    // public final Color COLOR_CONTENT_TEXT_FUNCTION = Color.web("#000000"); // #000000
    // public final Color COLOR_ACTIVE_FUNCTION_CALL_TEXT = Color.web("#ffffff"); // #000000


    public AbstractGraphicalObject(String name, Pane pane, int id) {
        this.name = name;
        this.pane = pane;
        this.renderedNodes = new ArrayList<>();
        this.id = id;
        
    }
    
    /**
     * Returns an integer that contains the ID of the object.
     * This ID is unique for each object and can be used to identify the object in the GUI.
     * @return ID of the object as an int
     */
    public int getID() {
        return id;
    }
    /**
     * Removes the object from the pane. This method should be called when the object is no longer needed.
     * It clears the rendered nodes list and removes all nodes from the pane.
     */
    public void removeFromPane() {
        if (renderedNodes != null) {
            pane.getChildren().removeAll(renderedNodes);
            renderedNodes.clear();
        }
    }
    /**
     * Returns the list of rendered nodes. This can be used to access the nodes that were drawn on the pane.
     * @return List of rendered nodes as a List<Node>
     */
    public List<Node> getRenderedNodes() {return renderedNodes;}

    public String getName() {
        return name;
    }
    
}
