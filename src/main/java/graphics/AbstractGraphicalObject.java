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
    // protected Pair<Double, Double> coordinates;
    private final int id;

    public AbstractGraphicalObject(String name, Pane pane) {
        this.name = name;
        this.pane = pane;
        this.renderedNodes = new ArrayList<>();
        this.id = GraphicalObjectIDGenerator.getNextId();
        
    }

    // public void setCoordinates(double x, double y) { 
    //     this.coordinates = new Pair<Double,Double>(x, y);
    // }

    // public Pair<Double, Double> getCoordinates(){
    //     return coordinates;
    // }
    
    public int getID() {
        return id;
    }

    // TODO : maybe check that every node is not null ? 
    public void removeFromPane() {
        if (renderedNodes != null) {
            pane.getChildren().removeAll(renderedNodes);
        }
    }

    
}
