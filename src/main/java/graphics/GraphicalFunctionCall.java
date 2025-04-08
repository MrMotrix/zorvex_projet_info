package graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public final class GraphicalFunctionCall extends AbstractGraphicalObject {
    
    private static final double PADDING = 10;
    private static final double MIN_WIDTH = 100;
    private static final double MIN_HEIGHT = 50;
    private static final double BOX_HEIGHT = 40;
    private boolean alreadyDrawn = false;
    private String functionName;
    private ArrayList<GraphicalObject> parameters;
    public ArrayList<GraphicalObject> getParameters() {
        return parameters;
    }

    private ArrayList<GraphicalObject> ids;
    
    public ArrayList<GraphicalObject> getIds() {
        return ids;
    }

    public void setIds(List<GraphicalObject> ids) {
        this.ids = new ArrayList<>(ids);
    }

    public GraphicalFunctionCall(String name, Pane pane, List<GraphicalObject> parameters, int id) {
        super(name, pane, id);
        
        this.parameters = new ArrayList<>(parameters);
        this.functionName = name;
        this.ids = new ArrayList<>();
    }
    
    public void setParameters(List<GraphicalObject> parameters) {
        this.parameters = new ArrayList<>(parameters);
        if (!alreadyDrawn){
            alreadyDrawn = true;
            Text t = (Text) renderedNodes.get(1);
            t.setText(functionName + "(" + parameters.stream()
            .map(GraphicalObject::getName)
            .collect(Collectors.joining(", ")) + ")");

            Rectangle r = (Rectangle) renderedNodes.get(0);
            r.setWidth(Math.max(MIN_WIDTH, Math.max(t.getLayoutBounds().getWidth(), t.getLayoutBounds().getWidth()) + 2 * PADDING));
        }
    }
    
    @Override
    public void draw(double x, double y) {
        // y = y - 10;
        String parString = parameters.stream()
            .map(GraphicalObject::getName)
            .collect(Collectors.joining(", "));        

        Text title = new Text(functionName + "("+ parString +")");
        title.setFill(Color.DARKBLUE);
        title.setX(x + PADDING);
        title.setY(y);
        title.setFill(COLOR_ACTIVE_FUNCTION_CALL_TEXT);
        title.setLayoutX(5);
        title.setLayoutY(20);

        Rectangle titleRect = new Rectangle();
        titleRect.setFill(COLOR_ACTIVE_FUNCTION_CALL_BOX);
        titleRect.setStroke(Color.BLACK);
        titleRect.setArcWidth(10);
        titleRect.setArcHeight(10);
        titleRect.setX(x);
        titleRect.setY(y);

        double width = Math.max(MIN_WIDTH, Math.max(title.getLayoutBounds().getWidth(), title.getLayoutBounds().getWidth()) + 2 * PADDING);
        // double height = Math.max(MIN_HEIGHT, title.getLayoutBounds().getHeight() + title.getLayoutBounds().getHeight() + 3 * PADDING);

        titleRect.setWidth(width);
        titleRect.setHeight(30);
        

        pane.getChildren().addAll(titleRect, title);
        renderedNodes.add(titleRect);// index 0
        renderedNodes.add(title); // index 1
    }

    @Override
    public void updateValue(int index, String value) {
        return;
    }

    @Override
    public void updateRender(int index, String value) {
        
        return;
    }

    public void addId(GraphicalObject o){
        ids.add(o);
    }

    public void addPar(GraphicalObject par){
        parameters.add(par);
    }

    @Override
    public void removeFromPane() {
        super.removeFromPane();
        removeInternal();
        
    }

    public void removeInternal(){
        parameters.forEach(GraphicalObject::removeFromPane);
        ids.forEach(GraphicalObject::removeFromPane);
    }
}
