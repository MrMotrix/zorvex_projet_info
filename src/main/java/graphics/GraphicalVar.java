package graphics;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class GraphicalVar implements GraphicalObject {

    private String name;
    private String value;
    private Pane pane;
    private List<Node> renderedNodes;

    public GraphicalVar(String name, String value, Pane pane) {
        this.name = name;
        this.value = value;
        this.pane = pane;
        renderedNodes = new ArrayList<>();
    }

    @Override
    public void draw(double x, double y) {
        
        double width = WIDTH_VARIABLE_BOX;
        double height = HEIGHT_VARIABLE_BOX;

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

        renderedNodes = new ArrayList<>();
        renderedNodes.add(rect);
        renderedNodes.add(textContainer);

    pane.getChildren().addAll(rect, textContainer);

        
    }
    public void removeFromPane() {
        if (renderedNodes != null) {
            pane.getChildren().removeAll(renderedNodes);
        }
    }

}


// public final class GraphicalVar implements GraphicalObject {

//     private String name;
//     private String value;
//     private Pane pane;

//     private Rectangle rect;
//     private TextField textContainer;

//     public GraphicalVar(String name, String value, Pane pane) {
//         this.name = name;
//         this.value = value;
//         this.pane = pane;
//     }

//     @Override
//     public void draw(double x, double y) {
//         double width = WIDTH_VARIABLE_BOX;
//         double height = HEIGHT_VARIABLE_BOX;

//         rect = new Rectangle(width, height);
//         rect.setX(x);
//         rect.setY(y);
//         rect.setArcWidth(10);
//         rect.setArcHeight(10);
//         rect.setFill(Color.LIGHTBLUE);
//         rect.setStroke(Color.BLACK);

//         textContainer = new TextField(name + " = " + value);
//         textContainer.setLayoutX(x + 5);
//         textContainer.setLayoutY(y + 10);
//         textContainer.setPrefWidth(width - 10);
//         textContainer.setEditable(false);
//         textContainer.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 14;");

//         pane.getChildren().addAll(rect, textContainer);
//     }

//     @Override
//     public String getName() {
//         return name;
//     }

//     @Override
//     public void update(String newValue) {
//         this.value = newValue;
//         if (textContainer != null) {
//             textContainer.setText(name + " = " + value);
//         }
//     }

//     @Override
//     public void removeFromPane() {
//         pane.getChildren().removeAll(rect, textContainer);
//     }
// }
