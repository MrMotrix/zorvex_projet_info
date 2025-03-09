package graphics;

import javafx.scene.control.TextField;
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
    
    public GraphcalLinkedList(String name,  String[] values, Pane pane) {
        this.name = name;
        this.pane = pane;
        this.values = values;
    }

    public void draw(double x, double y){
        
        double cellWidth = 40;
        double cellHeight = 40;
        double pointerWidth = 30;

        Text title = new Text(name); // name of the list
        title.setX(x);
        title.setY(y-10); 
        pane.getChildren().add(title);


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
            }

            pane.getChildren().addAll(valueBox, value, pointerBox);
        }
    }

}
