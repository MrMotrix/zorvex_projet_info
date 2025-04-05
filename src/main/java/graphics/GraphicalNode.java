package graphics;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public final class GraphicalNode extends AbstractGraphicalObject{
    private String value;
    private int index;
    private double x;
    private double y;

    private Line outgoingArrow;
    private Polygon outgoingArrowHead;

    public GraphicalNode(String value, int index, Pane pane) {
        super(value, pane);
        this.value = value;
        this.index = index;

    }

    double cellWidth = WIDTH_LLIST_CONTENT_BOX;
    double cellHeight = HEIGHT_LLIST_CONTENT_BOX;
    double pointerWidth = POINTER_WIDTH;

    @Override
    public void draw(double x, double y) {
        // make sure renderedNodes is clear to avoid duplicates
        renderedNodes.clear();
    
        this.x = x;
        this.y = y;
        
        // draw ValueBox in blue
        Rectangle valueBox = new Rectangle(cellWidth, cellHeight);
        valueBox.setX(x);
        valueBox.setY(y);
        valueBox.setFill(COLOR_LLIST_CONTENT_BOX);
        valueBox.setStroke(Color.BLACK);
    
        // Set the text inside the valuebox
        TextField valueField = new TextField(value);
        valueField.setLayoutX(x + 5);
        valueField.setLayoutY(y + 10);
        valueField.setPrefWidth(cellWidth - 10);
        valueField.setEditable(false);
        valueField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 13; -fx-text-fill:" + COLOR_LLIST_CONTENT_TEXT + ";"); // text color
        // if there is too much text, this allos to show it in a little screen
        Tooltip tooltip = new Tooltip(value);
        Tooltip.install(valueField, tooltip);
    
        // PinterBox in yellow
        Rectangle pointerBox = new Rectangle(pointerWidth, cellHeight);
        pointerBox.setX(x + cellWidth);
        pointerBox.setY(y);
        pointerBox.setFill(COLOR_LLIST_POINTER_BOX);
        pointerBox.setStroke(Color.BLACK);
    
        // add everything that has been created to the renderedNodes list
        renderedNodes.add(valueBox); // index 0
        renderedNodes.add(valueField); // index 1
        renderedNodes.add(pointerBox); // index 2
    
        // Add eveything to the
        pane.getChildren().addAll(valueBox, valueField, pointerBox);
    }

    // create a new arrow
    public void createOutgoingArrowTo(GraphicalNode toNode, Pane pane) {
        double startX = this.x + WIDTH_LLIST_CONTENT_BOX + pointerWidth;
        double startY = this.y + cellHeight / 2;
        double endX = toNode.x;
        double endY = toNode.y + cellHeight / 2;
    
        Line line = new Line(startX, startY, endX, endY);
        Polygon arrowHead = new Polygon(
            endX, endY,
            endX - 5, endY - 5,
            endX - 5, endY + 5
        );
        arrowHead.setFill(Color.BLACK);
        // add arrow to the pane
        pane.getChildren().addAll(line, arrowHead);
        
        this.outgoingArrow = line;
        this.outgoingArrowHead = arrowHead;
    
        renderedNodes.add(line); // index3 
        renderedNodes.add(arrowHead); // index 4
    }

    public List<Node> getRenderedNodes() {
        return renderedNodes;
    }

    public String getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Line getOutgoingArrow() {
        return outgoingArrow;
    }

    public Polygon getOutgoingArrowHead() {
        return outgoingArrowHead;
    }

    public void setOutgoingArrow(Line outgoingArrow) {
        this.outgoingArrow = outgoingArrow;
    }

    public void setOutgoingArrowHead(Polygon arrowHead) {
        this.outgoingArrowHead = arrowHead;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    // deletes the outgoing arrow from the pane and from the rendereddNodes
    public void clearOutgoingArrow(Pane pane) {
        if (outgoingArrow != null) {
            pane.getChildren().remove(outgoingArrow);
            renderedNodes.remove(outgoingArrow);
            outgoingArrow = null;
        }
        if (outgoingArrowHead != null) {
            pane.getChildren().remove(outgoingArrowHead);
            renderedNodes.remove(outgoingArrowHead);
            outgoingArrowHead = null;
        }
    }
    
    // used when the reorganize method is used, it shifts everything to the new place. TODO : probably can be improved but the current implementation does not give problems with arrows
    public void redraw(Pane pane) {
        for (Node node : renderedNodes) {
            if (node instanceof TextField) {
                TextField nodeText = (TextField) node;
                nodeText.setLayoutX(x + 5);
                nodeText.setLayoutY(y + 10);
            }
            if (node instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) node;
                if (rectangle.getFill().equals(COLOR_LLIST_CONTENT_BOX)) {
                    rectangle.setX(x);
                    rectangle.setY(y);
                } else if (rectangle.getFill().equals(COLOR_LLIST_POINTER_BOX)) {
                    rectangle.setX(x + GraphicalObject.WIDTH_LLIST_CONTENT_BOX);
                    rectangle.setY(y);
                }
            }
        }
    
        // if the arrow was deleted because the node is on the right of a new node, this redraws it
        if (outgoingArrow != null && outgoingArrowHead != null) {
            outgoingArrow.setStartX(x + WIDTH_LLIST_CONTENT_BOX);
            outgoingArrow.setStartY(y + cellHeight / 2);
            outgoingArrow.setEndX(x + WIDTH_LLIST_CONTENT_BOX + 30);
            outgoingArrow.setEndY(y + cellHeight / 2);
    
            outgoingArrowHead.getPoints().clear();
            outgoingArrowHead.getPoints().addAll(
                x + WIDTH_LLIST_CONTENT_BOX + 30, y + cellHeight / 2,
                x + WIDTH_LLIST_CONTENT_BOX + 25, y + cellHeight / 2 - 5,
                x + WIDTH_LLIST_CONTENT_BOX + 25, y + cellHeight / 2 + 5
            );
        }
    }

    // @Override
    // public void update(int index, String value) {
    //     updateValue(index, value);
    //     updateRender(index, value);
    // }
    @Override
    public void updateValue(int index, String value){
        this.value = value;
        
    }
    
    @Override
    public void updateRender(int index, String value){
        TextField field = (TextField) renderedNodes.get(1);
        field.setText(value);

        Tooltip.install(field, new Tooltip(value));

    }    
    
    
}

// package graphics;

// import java.util.ArrayList;
// import java.util.List;

// import javafx.scene.Node;
// import javafx.scene.control.TextField;
// import javafx.scene.control.Tooltip;
// import javafx.scene.layout.Pane;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Line;
// import javafx.scene.shape.Polygon;
// import javafx.scene.shape.Rectangle;
// import javafx.scene.text.Text;

// public final class GraphicalLinkedList extends AbstractGraphicalObject {

//     private String[] values;


    
//     public GraphicalLinkedList(String name,  String[] values, Pane pane) {
//         super(name, pane);
//         this.values = values;
//     }

//     public void draw(double x, double y){
        
//         double cellWidth = WIDTH_LLIST_CONTENT_BOX;
//         double cellHeight = cellHeight;
//         double pointerWidth = 30;

//         Text title = new Text(name); // name of the list
//         title.setX(x);
//         title.setY(y-10); 

//         pane.getChildren().add(title);
//         renderedNodes.add(title);
//         double cx = 0;

//         //for each value, create a cell divided in two
//         for (int i = 0; i < values.length; i++) {
//             //decalage entre cellules
//             cx = x + i * (cellWidth + pointerWidth + 20);

//             // there are two rectangles, one that contains the value and other that points to the next cell

//             // ValueBox
//             Rectangle valueBox = new Rectangle(cellWidth, cellHeight);
            
//             valueBox.setX(cx);
//             valueBox.setY(y);

//             valueBox.setFill(Color.LIGHTBLUE);
//             valueBox.setStroke(Color.BLACK);

//             TextField value = new TextField(values[i]);
            

//             Tooltip tooltip = new Tooltip();
//             Tooltip.install(value, tooltip);
//             tooltip.setText(value.getText());

//             value.setLayoutX(cx + 5); // left margin
//             value.setLayoutY(y + 10); // top margin
            
//             value.setPrefWidth(cellWidth - 10); // width of the textField
//             value.setEditable(false);
//             value.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 13;");

//             // PointerBox
//             Rectangle pointerBox = new Rectangle(pointerWidth, cellHeight);
            
//             pointerBox.setX(cx + cellWidth);
//             pointerBox.setY(y);
            
//             pointerBox.setFill(Color.LIGHTYELLOW);
//             pointerBox.setStroke(Color.BLACK);

//             // fleche
//             if (i < values.length - 1) {
                
//                 double startX = cx + cellWidth + pointerWidth; // position de la ligne gauche + largeur de la cellule + largeur de la cellule du pointeur
//                 double startY = y + cellHeight / 2;
//                 double endX = cx + cellWidth + pointerWidth + 20; // 20 is the distance between boxes
//                 double endY = startY;

//                 Line line = new Line(startX, startY, endX, endY);

//                 Polygon arrowHead = new Polygon();

//                 // get all the points of the polygon and moify them
//                 arrowHead.getPoints().addAll(
//                     endX, endY, // sommet
//                     endX - 5, endY - 5,
//                     endX - 5, endY + 5
//                 );
//                 arrowHead.setFill(Color.BLACK);

//                 pane.getChildren().addAll(line, arrowHead);
//                 renderedNodes.add(arrowHead);
//                 renderedNodes.add(line);
//             }
//             renderedNodes.add(valueBox);
//             renderedNodes.add(value);
//             renderedNodes.add(pointerBox);
            
//             pane.getChildren().addAll(valueBox, value, pointerBox);
//         }
//     }

//     @Override
//     public void update(int index, String value) {
//         try{

//             values[index] = value;
//         } catch(Exception e) {
//             System.out.println("Index out of range");
//         }
//     }