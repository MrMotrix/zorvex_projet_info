package graphics;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
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
        
        rect.setFill(Color.LIGHTBLUE);
        rect.setStroke(Color.BLACK);
        
        TextField textContainer = new TextField(name + " = " + value);

        textContainer.setLayoutX(x + 5);
        textContainer.setLayoutY(y + 10);

        textContainer.setPrefWidth(width - 10);
        textContainer.setEditable(false);

        textContainer.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 12;");

        // renderedNodes = new ArrayList<>();
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
    
        updateValue(value);
        updateRender();
    }
    
    public void updateValue(String value){
        this.value = value;
        
    }
    /**
     * Changes the value of the object in the rendered value. As it is an object it is not required to rerender it
     */
    public void updateRender(){
        TextField field = (TextField) renderedNodes.get(1);
        field.setText(name + " = " + value);
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
