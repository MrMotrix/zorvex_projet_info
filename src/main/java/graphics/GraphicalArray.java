package graphics;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public final class GraphicalArray implements GraphicalObject {
    //TODO : Replace the 'String[]' type by the the interface or enum that contains all the accepted types, or maybe just a String is required ?
    private String name;
    private String[] values;
    private Pane pane;
    
    public GraphicalArray(String name, String[] values, Pane pane) {
        this.name = name;
        this.values = values;
        this.pane = pane;
    }

    public void draw(double x, double y) {
        // TODO maybe we could set static constants to automate the sizes ?
        double cellHeight = 50;
        double cellWidth = 60;
        
        Text title = new Text(name); // name of the array
        title.setX(x);
        title.setY(y-10); // a little bit higher than the first cell
        pane.getChildren().add(title);


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
            cellContent.setLayoutX(cx + 5); // margin from the left
            cellContent.setLayoutY(y + 10); // margin from the top
            cellContent.setPrefWidth(cellWidth - 10); // width of the textField
            cellContent.setEditable(false); 
            cellContent.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 13;"); // supprimer le background du TextField

            Text index = new Text(String.valueOf(i)); // add the index to every cell
            index.setX(cx + cellWidth / 2 - 5);
            index.setY(y + cellHeight + 15);

            pane.getChildren().addAll(cell, cellContent, index); // add to the pane
        }


    }

}
