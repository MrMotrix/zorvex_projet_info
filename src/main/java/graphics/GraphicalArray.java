package graphics;

import java.util.Arrays;
import java.util.stream.Stream;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public final class GraphicalArray extends AbstractGraphicalObject implements IterableGraphicalObject{
    
    private String[] values;
    private double currentX;
    private double currentY;
    private int size ;
    public GraphicalArray(String name, String[] values, Pane pane, int id) {
        super(name, pane, id);
        this.values = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            String[] parts = values[i].trim().split(" ", 2);
            if (i!= values.length -1) this.values[i] = parts.length > 1 ? parts[1] : parts[0]; 
            // if (i!= values.length -1) this.values[i] = parts[1]; 
            else this.values[i] = parts.length > 1 ? parts[1].substring(0, parts[1].length() - 1) : parts[0]; 
        }
        this.size = this.values.length;
    }
    @Override
    public String[] getValues(){return values;}
    /**
     * Returns the size of the array
     * @return size of the array as an int
     */
    public int size() {return size;}

    public void draw(double x, double y) {  
    
        this.currentX = x;
        this.currentY = y;
        double cellHeight = HEIGHT_ARRAY_BOX;
        double cellWidth = WIDTH_ARRAY_BOX;
        
        Text title = new Text( "(Tableau) "+ name); // name of the array
        title.setX(x);
        title.setY(y-10); // a little bit higher than the first cell
        
        
        pane.getChildren().add(title);
        renderedNodes.add(title); // index 0

        for (int i = 0; i < values.length; i++){

            // nouvelle position de chaque carre
            double cx = x + i * (cellWidth ); // we could add some gap between cells, but i thing it looks better this way

            Rectangle cell = new Rectangle(cellWidth, cellHeight);
            
            // set coordinates
            cell.setX(cx);
            cell.setY(y);

            // stle of the cell
            cell.setFill(COLOR_ARRAY_BOX); // background
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
            cellContent.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 13; -fx-text-fill:" + COLOR_ARRAY_TEXT + ";"); // supprimer le background du TextField

            Text index = new Text(String.valueOf(i)); // add the index to every cell
            index.setX(cx + cellWidth / 2 - 5);
            index.setY(y + cellHeight + 15);

            pane.getChildren().addAll(cell, cellContent, index); // add to the pane
            renderedNodes.add(index); // index 1 
            renderedNodes.add(cell); // index 2
            renderedNodes.add(cellContent); // index 3
        }


    }

    /**
     * Adds a node at the given position and redraws the array. It also updates the size of the array
     * @param position Index of the value to be added
     * @param value String value
     */
    public void addNodeAt(int position, String value) {
        
        insertNodeWODraw(position, value);
        clearRenderedNodes(); 
        draw(currentX, currentY);
        
    }

    private void clearRenderedNodes() {
        // Clear old graphical elements
        for (Node node : renderedNodes) {
            pane.getChildren().remove(node);
        }
        renderedNodes.clear();
    }

    /**
     * Insert a node at a given position without drawing it. I.e. this is the logic to insert a cell. It also updates the size of the array
     * @param position Index of the value to be added
     * @param value String value 
     */
    public void insertNodeWODraw(int position, String value){
        if (position < 0 || position > values.length) return;
    
        // Insert new value into internal array with shift
        values = Stream.concat(
            Stream.concat(Arrays.stream(values, 0, position), Stream.of(value)),
            Arrays.stream(values, position, values.length)
        ).toArray(String[]::new);
    
        this.size++;

    }

    public void updateValue(int index, String value){
        this.values[index] = value;
        
    }

    public void updateRender(int index, String value){
        TextField field = (TextField) renderedNodes.get((index + 1) * 3);
        field.setText(value);

        Tooltip.install(field, new Tooltip(value));

    }

    /**
     * Deletes the cell at the given position
     * @param position index of the cell to remove
     */
    public void deleteNodeAt(int position) {
        deleteNodeWODraw(position);    
        clearRenderedNodes();
        draw(currentX, currentY);

    }

    /**
     * Deletes the value from the given position without affecting the drawn array. It also updates the size of the array
     * @param position position of the element that will be deleted
     */
    public void deleteNodeWODraw(int position){
        if (position < 0 || position >= values.length) return;
    
        // remove the value at the given position, shifting all elements left
        values = Stream.concat(
            Arrays.stream(values, 0, position),
            Arrays.stream(values, position + 1, values.length)
        ).toArray(String[]::new);

        this.size--;
    }
    

}
