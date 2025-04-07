package graphics;

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

    private String functionName;
    private List<GraphicalObject> parameters;
    public List<GraphicalObject> getParameters() {
        return parameters;
    }

    private List<GraphicalObject> ids;
    
    
    // public GraphicalFunctionCall(String name, Pane pane, List<GraphicalObject> parameters) {
    //     super(name, pane, id);
    //     this.parameters = parameters;
    //     this.functionName = name;
    // }

    public List<GraphicalObject> getIds() {
        return ids;
    }

    public void setIds(List<GraphicalObject> ids) {
        this.ids = ids;
    }

    public GraphicalFunctionCall(String name, Pane pane, List<GraphicalObject> parameters, int id) {
        super(name, pane, id);
        this.parameters = parameters;
        this.functionName = name;
    }
    
    public void setParameters(List<GraphicalObject> parameters) {
        this.parameters = parameters;
        Text t = (Text) renderedNodes.get(1);
        t.setText(functionName + "(" + parameters.stream()
            .map(GraphicalObject::getName)
            .collect(Collectors.joining(", ")) + ")");
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
        parameters.forEach(GraphicalObject::removeFromPane);
        ids.forEach(GraphicalObject::removeFromPane);
        
    }

}
