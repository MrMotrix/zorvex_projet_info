package graphics;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public final class GraphicalFunctionDeclaration extends AbstractGraphicalObject{

    private final double PADDING = 10;
    private final double MIN_WIDTH = 10;
    private final double MIN_HEIGHT = 10;

    private String parameters;
    private String variables;
    public GraphicalFunctionDeclaration(String name, Pane pane, String parameters, String variables) {
        super(name, pane);
        this.variables = variables;
        this.parameters = parameters.equals("") ? "vide" : parameters;
    }

    @Override
    public void draw(double x, double y) {
        y=  y - 10;
        Text titleText = new Text(name);
        titleText.setX(x);
        titleText.setY(y);     
        titleText.setX(PADDING);
        titleText.setFill(COLOR_TITLE_FUNCTION);
        titleText.setLayoutX(5);
        titleText.setLayoutY(20);

        
        Rectangle titleRect = new Rectangle();
        titleRect.setFill(COLOR_TITLE_BOX_FUNCTION);
        titleRect.setStroke(Color.BLACK);
        titleRect.setArcWidth(10);
        titleRect.setArcHeight(10);
        titleRect.setX(x);
        titleRect.setY(y);
        Text paramText = new Text("Paramétres : " + parameters);
        if (variables != null) paramText.setText(paramText.getText() + "\nVars: " + variables);

        paramText.setFont(Font.font( 12));
        paramText.setFill(COLOR_CONTENT_TEXT_FUNCTION);
        paramText.setX(x);
        paramText.setY(y);

        Rectangle contentRect = new Rectangle();
        contentRect.setFill(COLOR_CONTENT_BOX_FUNCTION);
        contentRect.setStroke(Color.BLACK);
        contentRect.setArcWidth(10);
        contentRect.setArcHeight(10);
        paramText.setX(x + PADDING);
        paramText.setY(y+50);

        double width = Math.max(MIN_WIDTH, Math.max(titleText.getLayoutBounds().getWidth(), paramText.getLayoutBounds().getWidth()) + 2 * PADDING);
        double height = Math.max(MIN_HEIGHT, titleText.getLayoutBounds().getHeight() + paramText.getLayoutBounds().getHeight() + 3 * PADDING);

        titleRect.setWidth(width);
        titleRect.setHeight(30);
        
        contentRect.setWidth(width);
        contentRect.setHeight(height - 30);
        contentRect.setY(y+30);
        contentRect.setX(x);
        

        pane.getChildren().addAll(titleRect, titleText, contentRect, paramText);

        renderedNodes.add(titleText); // index 0
        renderedNodes.add(titleRect); // index 1
        renderedNodes.add(contentRect); // index 2
        renderedNodes.add(paramText); // index3
    }

    @Override
    public void updateValue(int index, String value) {
        // normally we should not be able to update the definition of a funciont, so nothing is done
        return;
    }

    @Override
    public void updateRender(int index, String value) {
        // there is nothing to update
        return;
    }
    
}
