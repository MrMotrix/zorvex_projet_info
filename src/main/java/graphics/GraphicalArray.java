package graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public final class GraphicalArray extends AbstractGraphicalObject {
    
    //TODO : Replace the 'String[]' type by the the interface or enum that contains all the accepted types, or maybe just a String is required ?
    private String[] values;
        
    public GraphicalArray(String name, String[] values, Pane pane) {
        super(name, pane);
        this.values = values;
    }

    public void draw(double x, double y) {  
        // TODO maybe we could set static constants to automate the sizes ?
        double cellHeight = HEIGHT_ARRAY_BOX;
        double cellWidth = WIDTH_ARRAY_BOX;
        
        Text title = new Text(name); // name of the array
        title.setX(x);
        title.setY(y-10); // a little bit higher than the first cell
        
        
        pane.getChildren().add(title);
        renderedNodes.add(title);

        for (int i = 0; i < values.length; i++){

            // nouvelle position de chaque carre
            double cx = x + i * (cellWidth ); // we could add some gab between cells, but i thing it looks better this way

            Rectangle cell = new Rectangle(cellWidth, cellHeight);
            
            // set coordinates
            cell.setX(cx);
            cell.setY(y);
            // just for style, round corners // Better not
            // cell.setArcWidth(10);
            // cell.setArcHeight(10);
            // stle of the cell
            cell.setFill(Color.LIGHTBLUE); // background
            cell.setStroke(Color.BLACK); // line color

            TextField cellContent = new TextField(values[i].toString());
            
            // when you put the mouse over a textfiled that is truncated due to size restrictions, this shows the whole content of it in a little box
            Tooltip tooltip = new Tooltip();
            Tooltip.install(cellContent, tooltip);
            tooltip.setText(cellContent.getText());

            cellContent.setLayoutX(cx + 5); // margin from the left
            cellContent.setLayoutY(y + 10); // margin from the top
            cellContent.setPrefWidth(cellWidth - 10); // width of the textField
            cellContent.setEditable(false); 
            cellContent.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 13;"); // supprimer le background du TextField

            Text index = new Text(String.valueOf(i)); // add the index to every cell
            index.setX(cx + cellWidth / 2 - 5);
            index.setY(y + cellHeight + 15);

            pane.getChildren().addAll(cell, cellContent, index); // add to the pane
            renderedNodes.add(index);
            renderedNodes.add(cell);
            renderedNodes.add(cellContent);
        }


    }

}

// public final class GraphicalArray implements GraphicalObject {

//     private String name;
//     private String[] values;
//     private Pane pane;

//     private Rectangle[] rectangles;
//     private TextField[] textFields;
//     private Text[] indexLabels;
//     private Text title;

//     public GraphicalArray(String name, String[] values, Pane pane) {
//         this.name = name;
//         this.values = values;
//         this.pane = pane;
//     }

//     @Override
//     public void draw(double x, double y) {
//         double cellHeight = HEIGHT_ARRAY_BOX;
//         double cellWidth = WIDTH_ARRAY_BOX;

//         title = new Text(name);
//         title.setX(x);
//         title.setY(y - 10);
//         pane.getChildren().add(title);

//         rectangles = new Rectangle[values.length];
//         textFields = new TextField[values.length];
//         indexLabels = new Text[values.length];

//         for (int i = 0; i < values.length; i++) {
//             double cx = x + i * cellWidth;

//             Rectangle cell = new Rectangle(cellWidth, cellHeight);
//             cell.setX(cx);
//             cell.setY(y);
//             cell.setFill(Color.LIGHTBLUE);
//             cell.setStroke(Color.BLACK);

//             TextField content = new TextField(values[i]);
//             Tooltip tooltip = new Tooltip();
//             Tooltip.install(content, tooltip);
//             tooltip.setText(content.getText());
//             content.setLayoutX(cx + 5);
//             content.setLayoutY(y + 10);
//             content.setPrefWidth(cellWidth - 10);
//             content.setEditable(false);
//             content.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 13;");

//             Text index = new Text(String.valueOf(i));
//             index.setX(cx + cellWidth / 2 - 5);
//             index.setY(y + cellHeight + 15);

//             rectangles[i] = cell;
//             textFields[i] = content;
//             indexLabels[i] = index;

//             pane.getChildren().addAll(cell, content, index);
//         }
//     }

//     @Override
//     public String getName() {
//         return name;
//     }

//     @Override
//     public void update(String value) {
//         throw new UnsupportedOperationException("Use updateAt(index, value) for arrays.");
//     }

//     public void updateAt(int index, String newValue) {
//         if (index < 0 || index >= values.length) return;
//         values[index] = newValue;
//         if (textFields != null && textFields[index] != null) {
//             textFields[index].setText(newValue);
//         }
//     }

//     @Override
//     public void removeFromPane() {
//         pane.getChildren().remove(title);
//         for (int i = 0; i < values.length; i++) {
//             pane.getChildren().removeAll(rectangles[i], textFields[i], indexLabels[i]);
//         }
//     }
// }
