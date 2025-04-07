package graphics;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public final class GraphicalPile extends AbstractGraphicalObject{

    private ArrayList<String> values;
    private int size;
    private double currentX;
    private double currentY;

    public GraphicalPile(String name, List<String> values, Pane pane, int id) {
        super(name, pane, id);
        this.values = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            String value = values.get(i).trim(); 
            
            String[] parts = value.split(" ", 2);
            
            if (parts.length > 1) {
                
                if (i == values.size() - 1 && parts[1].endsWith("]")) {
                    this.values.add(parts[1].substring(0, parts[1].length() - 1)); 
                } else {
                    this.values.add(parts[1]); 
                }
            } else {
                this.values.add(parts[0]); 
            }
        }        size = values.size();
    }

    @Override
    public void draw(double x, double y) {
        this.currentX = x;
        this.currentY = y;
        double cellHeight = HEIGHT_ARRAY_BOX;
        double cellWidth = WIDTH_ARRAY_BOX;
        
        Text title = new Text("(pile) " + name); 
        title.setX(x);
        title.setY(y - 10); 
        
        pane.getChildren().add(title);
        renderedNodes.add(title); // index 0
    
        // Render cells
        for (int i = 0; i < values.size(); i++){
            double cx = x + i * (cellWidth); // position of each cell
    
            Rectangle cell = new Rectangle(cellWidth, cellHeight);
            cell.setX(cx);
            cell.setY(y);
    
            // style of the cell
            cell.setFill(COLOR_PILE_BOX); // background
            cell.setStroke(Color.BLACK); // line color
    
            TextField cellContent = new TextField(values.get(i).toString());
            
            Tooltip tooltip = new Tooltip();
            Tooltip.install(cellContent, tooltip);
            tooltip.setText(cellContent.getText());
    
            cellContent.setLayoutX(cx + 5); 
            cellContent.setLayoutY(y + 10); 
            cellContent.setPrefWidth(cellWidth - 10); 
            cellContent.setEditable(false); 
            cellContent.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 13; -fx-text-fill:" + COLOR_PILE_TEXT + ";"); 
    
            // Add the cell, its content, and tooltips
            pane.getChildren().addAll(cell, cellContent); 
            renderedNodes.add(cell); // index 2
            renderedNodes.add(cellContent); // index 3
        }
    
        // Add "Prochaine" index
        Text prochaineIndex = new Text("(Prochain)");
        // double cxProchaine = x + (values.size() * cellWidth) / 2 - prochaineIndex.getLayoutBounds().getWidth() / 2; // center it under the cells
        double cxProchaine = x ; // center it under the cells
        prochaineIndex.setX(cxProchaine);
        prochaineIndex.setY(y + cellHeight + 15); 
    
        pane.getChildren().add(prochaineIndex);
        renderedNodes.add(prochaineIndex); // index 4
    }
    

    @Override
    public void updateValue(int index, String value) {
        // nothing should change
        return;
    }

    @Override
    public void updateRender(int index, String value) {
        TextField field = (TextField) renderedNodes.get((index + 1) * 3);
        field.setText(value);
    }

    public void push(String value){
        values.add(0, value);
        size++;
        clearRenderedNodes(); // Clear old graphical elements
        draw(currentX, currentY);
    }

    public String pop(){
        // delete value from the underlying list
        if (size == 0) return null;
        String value = values.get(0);
        values.remove(0);
        size--;

        clearRenderedNodes();
        draw(currentX, currentY);
        return value;
    }

    public String peek(){
        if (size == 0) return null;
        return values.get(0);
    }

    public int size(){return size;}

    private void clearRenderedNodes() {
        // Clear old graphical elements
        for (Node node : renderedNodes) {
            pane.getChildren().remove(node);
        }
        renderedNodes.clear();
    }

    public List<String> getValues(){
        return values;
    }

}
