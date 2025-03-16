package graphics;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public final class GraphcalLinkedList implements GraphicalObject {

    private String name;
    private Pane pane;
    private String[] values;
    private List<Node> renderedNodes;

    
    public GraphcalLinkedList(String name,  String[] values, Pane pane) {
        this.name = name;
        this.pane = pane;
        this.values = values;
        renderedNodes = new ArrayList<>();
    }

    public void draw(double x, double y){
        
        double cellWidth = WIDTH_LLIST_CONTENT_BOX;
        double cellHeight = HEIGHT_LLIST_CONTENT_BOX;
        double pointerWidth = 30;

        Text title = new Text(name); // name of the list
        title.setX(x);
        title.setY(y-10); 

        pane.getChildren().add(title);
        renderedNodes.add(title);

        //for each value, create a cell divided in two
        for (int i = 0; i < values.length; i++) {
            //decalage entre cellules
            double cx = x + i * (cellWidth + pointerWidth + 20);

            // there are two rectangles, one that contains the value and other that points to the next cell

            // ValueBox
            Rectangle valueBox = new Rectangle(cellWidth, cellHeight);
            
            valueBox.setX(cx);
            valueBox.setY(y);

            valueBox.setFill(Color.LIGHTBLUE);
            valueBox.setStroke(Color.BLACK);

            TextField value = new TextField(values[i]);
            

            Tooltip tooltip = new Tooltip();
            Tooltip.install(value, tooltip);
            tooltip.setText(value.getText());

            value.setLayoutX(cx + 5); // left margin
            value.setLayoutY(y + 10); // top margin
            
            value.setPrefWidth(cellWidth - 10); // width of the textField
            value.setEditable(false);
            value.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 13;");

            // PointerBox
            Rectangle pointerBox = new Rectangle(pointerWidth, cellHeight);
            
            pointerBox.setX(cx + cellWidth);
            pointerBox.setY(y);
            
            pointerBox.setFill(Color.LIGHTYELLOW);
            pointerBox.setStroke(Color.BLACK);

            // fleche
            if (i < values.length - 1) {
                
                double startX = cx + cellWidth + pointerWidth; // position de la ligne gauche + largeur de la cellule + largeur de la cellule du pointeur
                double startY = y + cellHeight / 2;
                double endX = cx + cellWidth + pointerWidth + 20; // 20 is the distance between boxes
                double endY = startY;

                Line line = new Line(startX, startY, endX, endY);

                Polygon arrowHead = new Polygon();

                // get all the points of the polygon and moify them
                arrowHead.getPoints().addAll(
                    endX, endY, // sommet
                    endX - 5, endY - 5,
                    endX - 5, endY + 5
                );
                arrowHead.setFill(Color.BLACK);

                pane.getChildren().addAll(line, arrowHead);
                renderedNodes.add(arrowHead);
                renderedNodes.add(line);
            }
            renderedNodes.add(valueBox);
            renderedNodes.add(value);
            renderedNodes.add(pointerBox);
            
            pane.getChildren().addAll(valueBox, value, pointerBox);
        }
    }

    public void removeFromPane() {
        if (renderedNodes != null) {
            pane.getChildren().removeAll(renderedNodes);
        }
    }

}

// public final class GraphcalLinkedList implements GraphicalObject {

//     private String name;
//     private Pane pane;
//     private String[] values;

//     // Referencias necesarias
//     private Rectangle[] valueBoxes;
//     private Rectangle[] pointerBoxes;
//     private TextField[] valueFields;
//     private Line[] arrowLines;
//     private Polygon[] arrowHeads;
//     private Text title;

//     public GraphcalLinkedList(String name, String[] values, Pane pane) {
//         this.name = name;
//         this.pane = pane;
//         this.values = values;
//     }

//     @Override
//     public void draw(double x, double y) {
//         double cellWidth = WIDTH_LLIST_CONTENT_BOX;
//         double cellHeight = HEIGHT_LLIST_CONTENT_BOX;
//         double pointerWidth = 30;

//         title = new Text(name);
//         title.setX(x);
//         title.setY(y - 10);
//         pane.getChildren().add(title);

//         int n = values.length;

//         valueBoxes = new Rectangle[n];
//         pointerBoxes = new Rectangle[n];
//         valueFields = new TextField[n];
//         arrowLines = new Line[n - 1];
//         arrowHeads = new Polygon[n - 1];

//         for (int i = 0; i < n; i++) {
//             double cx = x + i * (cellWidth + pointerWidth + 20);

//             Rectangle valueBox = new Rectangle(cellWidth, cellHeight);
//             valueBox.setX(cx);
//             valueBox.setY(y);
//             valueBox.setFill(Color.LIGHTBLUE);
//             valueBox.setStroke(Color.BLACK);

//             TextField value = new TextField(values[i]);
//             Tooltip tooltip = new Tooltip();
//             Tooltip.install(value, tooltip);
//             tooltip.setText(value.getText());
//             value.setLayoutX(cx + 5);
//             value.setLayoutY(y + 10);
//             value.setPrefWidth(cellWidth - 10);
//             value.setEditable(false);
//             value.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 13;");

//             Rectangle pointerBox = new Rectangle(pointerWidth, cellHeight);
//             pointerBox.setX(cx + cellWidth);
//             pointerBox.setY(y);
//             pointerBox.setFill(Color.LIGHTYELLOW);
//             pointerBox.setStroke(Color.BLACK);

//             valueBoxes[i] = valueBox;
//             pointerBoxes[i] = pointerBox;
//             valueFields[i] = value;

//             pane.getChildren().addAll(valueBox, value, pointerBox);

//             if (i < n - 1) {
//                 double startX = cx + cellWidth + pointerWidth;
//                 double startY = y + cellHeight / 2;
//                 double endX = startX + 20;
//                 double endY = startY;

//                 Line line = new Line(startX, startY, endX, endY);
//                 Polygon arrowHead = new Polygon(
//                     endX, endY,
//                     endX - 5, endY - 5,
//                     endX - 5, endY + 5
//                 );
//                 arrowHead.setFill(Color.BLACK);

//                 arrowLines[i] = line;
//                 arrowHeads[i] = arrowHead;

//                 pane.getChildren().addAll(line, arrowHead);
//             }
//         }
//     }

//     @Override
//     public String getName() {
//         return name;
//     }

//     @Override
//     public void update(String value) {
//         throw new UnsupportedOperationException("Use updateNode(index, value) for linked list.");
//     }

//     public void updateNode(int index, String newValue) {
//         if (index < 0 || index >= values.length) return;
//         values[index] = newValue;
//         if (valueFields != null && valueFields[index] != null) {
//             valueFields[index].setText(newValue);
//         }
//     }

//     @Override
//     public void removeFromPane() {
//         pane.getChildren().remove(title);
//         for (int i = 0; i < values.length; i++) {
//             pane.getChildren().removeAll(valueBoxes[i], valueFields[i], pointerBoxes[i]);
//         }
//         for (int i = 0; i < arrowLines.length; i++) {
//             pane.getChildren().removeAll(arrowLines[i], arrowHeads[i]);
//         }
//     }
// }
