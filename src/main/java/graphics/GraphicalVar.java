package graphics;

import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class GraphicalVar extends AbstractGraphicalObject {

    private String value;

    public GraphicalVar(String name, String value, Pane pane) {
        super(name, pane);
        this.value = value;
    }
    /**
     * Returns the content of the variable
     * @return value of the variable as a String
     */
    public String getValue() {return value;}

    @Override
    public void draw(double x, double y) {
        
        double width = WIDTH_VARIABLE_BOX;
        double height = HEIGHT_VARIABLE_BOX;

        Rectangle rect = new Rectangle(width, height);
        
        
        rect.setX(x);
        rect.setY(y);
        
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        
        // rect.setFill(Color.LIGHTBLUE);
        rect.setFill(COLOR_VARIABLE_BOX); // background

        rect.setStroke(Color.BLACK);
        
        TextField textContainer = new TextField(name + " = " + value);

        textContainer.setLayoutX(x + 5);
        textContainer.setLayoutY(y + 10);

        textContainer.setPrefWidth(width - 10);
        textContainer.setEditable(false);

        textContainer.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 12; -fx-text-fill:" + COLOR_VARIABLE_TEXT + ";"); // text color

        // important to keep this order, otherwise it may break the logic (yes i know it may not be the best)
        renderedNodes.add(rect); // index 0
        renderedNodes.add(textContainer); // index 1

        Tooltip tooltip = new Tooltip();
        Tooltip.install(textContainer, tooltip);
        tooltip.setText(textContainer.getText());
        
        pane.getChildren().addAll(rect, textContainer);

        
    }

    @Override
    public void update(int index, String value) {
    
        updateValue(index, value);
        updateRender(index, value);
    }
    
    public void updateValue(int index, String value){
        this.value = value;
        
    }
    /**
     * Changes the value of the object in the rendered value. As it is an object it is not required to rerender it
     */
    public void updateRender(int index, String value) {
        TextField field = (TextField) renderedNodes.get(1);
        field.setText(name + " = " + value);
        Tooltip.install(field, new Tooltip(name + " = " + value));

    }
    /**
     * Returns the value of the variable as it is rendered in the GUI.
     * @return
     */
    public String getRenderedValue() {
        return ((TextField) renderedNodes.get(1)).getText();
    }

}