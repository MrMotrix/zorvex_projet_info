package graphics;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class GraphicalVar implements GraphicalObject {

    private String name;
    private String value;
    private Pane pane;

    public GraphicalVar(String name, String value, Pane pane) {
        this.name = name;
        this.value = value;
        this.pane = pane;
    }

    @Override
    public void draw(double x, double y) {
        
        double width = 100;
        double height = 50;

        Rectangle rect = new Rectangle(width, height);
        rect.setX(x);
        rect.setY(y);
        
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        
        rect.setFill(Color.LIGHTBLUE);
        rect.setStroke(Color.BLACK);

        // TODO give a different style so that we differenciate 'name' from 'value' ?
        TextField textContainer = new TextField(name + " = " + value);

        textContainer.setLayoutX(x + 5);
        textContainer.setLayoutY(y + 10);

        textContainer.setPrefWidth(width - 10);
        textContainer.setEditable(false);

        textContainer.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 14;");

        pane.getChildren().addAll(rect, textContainer);     
        
    }

}
