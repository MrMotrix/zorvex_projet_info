package graphics;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract  class AbstractGraphicalObject implements GraphicalObject {
    protected String name;
    protected Pane pane;
    protected List<Node> renderedNodes;
    private final int id;

    public AbstractGraphicalObject(String name, Pane pane) {
        this.name = name;
        this.pane = pane;
        this.renderedNodes = new ArrayList<>();
        this.id = GraphicalObjectIDGenerator.getNextId();
        
    }
    
    public int getID() {
        return id;
    }

    public void removeFromPane() {
        if (renderedNodes != null) {
            pane.getChildren().removeAll(renderedNodes);
            renderedNodes.clear();
        }
    }

    
}
