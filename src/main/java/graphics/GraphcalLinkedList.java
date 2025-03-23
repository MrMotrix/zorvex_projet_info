package graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public final class GraphcalLinkedList extends AbstractGraphicalObject {

    private String[] values;
    private List<GraphicalNode> nodes = new ArrayList<>();

    double currentX;
    
    public GraphcalLinkedList(String name,  String[] values, Pane pane) {
        super(name, pane);
        this.values = values;
        currentX = 0;
    }

    /**
     * Draws the entire linked list starting at the given coordinates.
     * It clears any existing nodes and arrows, then rebuilds and connects all nodes
     *
     * @param x The starting X coordinate.
     * @param y The starting Y coordinate.
     */
    public void draw(double x, double y) {
        // Reset nodes list to avoid duplicates TODO : this may be improved to avoid double calculations ? 
        nodes.clear();

        // double WIDTH_LLIST_CONTENT_BOX = WIDTH_LLIST_CONTENT_BOX;
        // double POINTER_WIDTH = 30;
        // double SPACING = 20;

        // Draw the list title
        Text title = new Text(name);
        title.setX(x);
        title.setY(y - 10);
        pane.getChildren().add(title);
        renderedNodes.add(title);

        currentX = x;

        // Create and draw each node
        for (int i = 0; i < values.length; i++) {
            GraphicalNode node = new GraphicalNode(values[i], i, pane);
            node.draw(currentX, y); // Title already drawn, skip here
            renderedNodes.addAll(node.getRenderedNodes());
            nodes.add(node);
            currentX += WIDTH_LLIST_CONTENT_BOX + POINTER_WIDTH + SPACING;
        }

        // Clear and recreate arrows between nodes
        nodes.forEach(z -> z.clearOutgoingArrow(pane));
        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).createOutgoingArrowTo(nodes.get(i + 1), pane);
        }
    }

    /**
     * Updates the value of the node at the specified index.
     * This only updates the data model, not the visual field contents.
     *
     * @param index The index of the node to update.
     * @param value The new value to assign to the node.
     */
    @Override
    public void update(int index, String value) {
        if (index >= 0 && index < values.length) {
            values[index] = value;
            nodes.get(index).setValue(value);
        } else {
            System.out.println("Index out of range");
        }
    }


    // public void addNodeAt(int position, String value) {
    //     if (position < 0 || position > nodes.size()) return;
    
    //     double WIDTH_LLIST_CONTENT_BOX = GraphicalObject.WIDTH_LLIST_CONTENT_BOX;
    //     double POINTER_WIDTH = 30;
    //     double SPACING = 20;
    
    //     double newX = (position < nodes.size()) ? nodes.get(position).getX() : currentX;
    //     double newY = (position < nodes.size()) ? nodes.get(position).getY() + GraphicalRepresentation.spaceBetweenPlots : 100;
    
    //     newNode.draw(pane, "");
    //     renderedNodes.addAll(newNode.getRenderedNodes());
    //     if (position > 0) {
    //         GraphicalNode prev = nodes.get(position - 1);
    //         prev.clearOutgoingArrow(pane);
    //         prev.createOutgoingArrowTo(newNode, pane);
    //     }
    
    //     for (int i = position; i < nodes.size(); i++) {
    //         GraphicalNode node = nodes.get(i);
    //         node.clearOutgoingArrow(pane); 
    
    //         node.setX(node.getX() + WIDTH_LLIST_CONTENT_BOX + POINTER_WIDTH + SPACING);
    //         node.setIndex(node.getIndex() + 1);
    //         node.redraw(pane); 
    
    //         renderedNodes.addAll(node.getRenderedNodes());
    //     }
    
    //     if (position < nodes.size()) {
    //         newNode.createOutgoingArrowTo(nodes.get(position), pane);
    //     }
    
    //     for (int i = position + 1; i < nodes.size(); i++) {
    //         GraphicalNode prevNode = nodes.get(i - 1);
    //         GraphicalNode currentNode = nodes.get(i);
    
    //         prevNode.clearOutgoingArrow(pane); 
    //         prevNode.createOutgoingArrowTo(currentNode, pane); 
    //     }
    
    //     nodes.add(position, newNode);
    //     currentX += WIDTH_LLIST_CONTENT_BOX + POINTER_WIDTH + SPACING;
    // }

    

    /**
     * Inserts a new node at the specified position in the linked list representation.
     * This method handles updating the visual layout of the list, i.e. shifting nodes,
     * redrawing arrows, and maintaining the internal structure.
     * 
     * 
     * @param position The index at which to insert the new node
     * @param value    The string value to assign to the new node
     */
    public void addNodeAt(int position, String value) {
        if (position < 0 || position > nodes.size()) return;

        // Update internal value array to reflect insertion
        values = Stream.concat(
            Stream.concat(Arrays.stream(values, 0, position), Stream.of(value)),
            Arrays.stream(values, position, values.length)
        ).toArray(String[]::new);

        double WIDTH_LLIST_CONTENT_BOX = GraphicalObject.WIDTH_LLIST_CONTENT_BOX;
        double POINTER_WIDTH = 30;
        double SPACING = 20;

        // Set the position of the new node. When nodes are inserted, the others are shifted horizontally
        double newX = (position < nodes.size()) ? nodes.get(position).getX() : currentX;
        // Maintain the same vertical position
        double newY = nodes.get(0).getY(); 

        // create a new node
        GraphicalNode newNode = new GraphicalNode(value, position, pane);
        newNode.draw(newX, newY);
        renderedNodes.addAll(newNode.getRenderedNodes());

        // If not the first node, reconnect the arrow from the previous node to the new node
        if (position > 0) {
            GraphicalNode prev = nodes.get(position - 1);
            prev.clearOutgoingArrow(pane);
            prev.createOutgoingArrowTo(newNode, pane);
        }

        // Shift and reindex all subsequent nodes (always only horizontally)
        for (int i = position; i < nodes.size(); i++) {
            GraphicalNode node = nodes.get(i);
            // Remove current arrow
            node.clearOutgoingArrow(pane); 

            node.setX(node.getX() + WIDTH_LLIST_CONTENT_BOX + POINTER_WIDTH + SPACING);
            node.setIndex(node.getIndex() + 1);
            // Redraw node in new position
            node.redraw(pane); 

            renderedNodes.addAll(node.getRenderedNodes());
        }

        // Connect new node to the next one if not inserted at the end
        if (position < nodes.size()) {
            newNode.createOutgoingArrowTo(nodes.get(position), pane);
            renderedNodes.add(nodes.get(position).getOutgoingArrow());
            renderedNodes.add(nodes.get(position).getOutgoingArrowHead());
        }

        // Reconnect arrows for all shifted nodes
        for (int i = position + 1; i < nodes.size(); i++) {
            GraphicalNode prevNode = nodes.get(i - 1);
            GraphicalNode currentNode = nodes.get(i);

            prevNode.clearOutgoingArrow(pane);
            prevNode.createOutgoingArrowTo(currentNode, pane);
        }

        nodes.add(position, newNode);
        currentX += WIDTH_LLIST_CONTENT_BOX + POINTER_WIDTH + SPACING;
    }

    /**
     * Removes all graphical elements from the pane and clears the rendered list.
     * This includes all visual nodes and arrows from the current list representation.
     */
    @Override
    public void removeFromPane() {
        if (renderedNodes != null) {
            // Remove all node visuals and their arrows
            nodes.forEach(GraphicalNode::removeFromPane);
            pane.getChildren().removeAll(renderedNodes);
            renderedNodes.clear();
        }
    }



    // public void deleteNodeAt(int position) {
    //     if (position < 0 || position >= nodes.size()) return;
    
    //     GraphicalNode nodeToRemove = nodes.get(position);
    
    //     if (position > 0) {
    //         GraphicalNode prev = nodes.get(position - 1);
    //         prev.clearOutgoingArrow(pane);
    //         if (position < nodes.size() - 1) {
    //             prev.createOutgoingArrowTo(nodes.get(position + 1), pane);
    //         }
    //     }
    
    //     for (Node node : nodeToRemove.getRenderedNodes()) {
    //         node.setOpacity(0.3);
    //     }
    
    //     for (int i = position + 1; i < nodes.size(); i++) {
    //         GraphicalNode node = nodes.get(i);
    //         node.clearOutgoingArrow(pane);
    
    //         node.setX(node.getX() - (GraphicalObject.WIDTH_LLIST_CONTENT_BOX + 30 + 20));
    //         node.setIndex(node.getIndex() - 1);
    //         node.redraw(pane);
    
    //         renderedNodes.addAll(node.getRenderedNodes());
    //     }
    
    //     // Reconstruir las flechas entre los nodos desplazados
    //     for (int i = position; i < nodes.size() - 1; i++) {
    //         GraphicalNode prevNode = nodes.get(i);
    //         GraphicalNode nextNode = nodes.get(i + 1);
    
    //         prevNode.clearOutgoingArrow(pane);
    //         prevNode.createOutgoingArrowTo(nextNode, pane);
    //     }
    
    //     nodes.remove(position);
    // }
    public void deleteNodeAt(int position) {
        if (position < 0 || position >= nodes.size()) return;

        values = Stream.concat(
            Arrays.stream(values, 0, position),
            Arrays.stream(values, position + 1, values.length)
        ).toArray(String[]::new);
    
        GraphicalNode nodeToRemove = nodes.get(position);
    
        // increase transparency to show that the node is no longer available
        for (Node node : nodeToRemove.getRenderedNodes()) {
            node.setOpacity(0.3);
        }
    
        // Except for the last node, reconect all nodes to the next
        if (position > 0) {
            GraphicalNode prev = nodes.get(position - 1);
            prev.clearOutgoingArrow(pane);
            if (position < nodes.size() - 1) {
                GraphicalNode next = nodes.get(position + 1);
                // this instruction draws the arrows, and it draws one that goes over the 'deleted' node
                prev.createOutgoingArrowTo(next, pane);
            }
        }
    
        // Si no es el último nodo, eliminar la flecha de salida del nodo eliminado
        if (position < nodes.size() - 1) {
            nodeToRemove.clearOutgoingArrow(pane);
        }
    
        // Eliminar el nodo de la lista
        nodes.remove(position);
    
        // Reindexar los nodos restantes
        for (int i = position; i < nodes.size(); i++) {
            nodes.get(i).setIndex(i);
        }
    }
    
    

    
//     public void addNodeAt(int index, String value) {
// double prevNodeX = getPositionX() + (index - 1) * (WIDTH_LLIST_CONTENT_BOX + 20); 
// double prevNodeY = getPositionY();
// double nextNodeX = getPositionX() + index * (WIDTH_LLIST_CONTENT_BOX + 20); 
// double nextNodeY = getPositionY();

// double newNodeX = (prevNodeX + nextNodeX) / 2; // En el medio
// double newNodeY = prevNodeY + HEIGHT_LLIST_CONTENT_BOX * 2; 
// createNode(newNodeX, newNodeY, index); // Crear nodo

// updateArrows(index - 1);
// updateArrows(index);
//     }
    
//     private void addNodeAtEnd(int index) {
//         double lastNodeX = getPositionX() + index * WIDTH_LLIST_CONTENT_BOX;
//         double lastNodeY = getPositionY();
        
//         double newNodeX = lastNodeX + WIDTH_LLIST_CONTENT_BOX + 20; 
//         createNode(newNodeX, lastNodeY, index); // Crear nodo y flecha
        
//         updateArrows(index);
//     }
    
    
//     private void addNodeAtPosition(int index) {
//         double prevNodeX = getPositionX() + (index - 1) * WIDTH_ARRAY_BOX;
//         double prevNodeY = getPositionY();
//         double nextNodeX = getPositionX() + index * WIDTH_ARRAY_BOX;
//         double nextNodeY = getPositionY();
        
//         double newNodeX = (prevNodeX + nextNodeX) / 2; 
//         double newNodeY = prevNodeY + HEIGHT_ARRAY_BOX * 2; 
//         createNode(newNodeX, newNodeY, index); // Crear nodo
    
//         updateArrows(index - 1);
//         updateArrows(index);
//     }
    
//     private void createNode(double x, double y, int index) {
//         Rectangle cell = new Rectangle(WIDTH_LLIST_CONTENT_BOX, HEIGHT_LLIST_CONTENT_BOX);
//         cell.setX(x);
//         cell.setY(y);
//         cell.setFill(Color.LIGHTBLUE);
//         cell.setStroke(Color.BLACK);
        
//         TextField cellContent = new TextField(values[index]);
//         cellContent.setLayoutX(x + 5);
//         cellContent.setLayoutY(y + 10);
//         cellContent.setPrefWidth(WIDTH_LLIST_CONTENT_BOX - 10); 
//         cellContent.setEditable(false);
//         cellContent.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 13;");
        
//         Text indexText = new Text(String.valueOf(index));
//         indexText.setX(x + WIDTH_LLIST_CONTENT_BOX / 2 - 5);
//         indexText.setY(y + HEIGHT_LLIST_CONTENT_BOX + 15);
        
//         pane.getChildren().addAll(cell, cellContent, indexText);
//         renderedNodes.add(cell);
//         renderedNodes.add(cellContent);
//         renderedNodes.add(indexText);
//     }
    
    
//     private void updateArrows(int index) {
//         if (index < 0 || index >= values.length - 1) return;
    
//         double startX = getPositionX() + index * (WIDTH_LLIST_CONTENT_BOX + 20) + WIDTH_LLIST_CONTENT_BOX; 
//         double startY = getPositionY() + HEIGHT_LLIST_CONTENT_BOX / 2;
//         double endX = getPositionX() + (index + 1) * (WIDTH_LLIST_CONTENT_BOX + 20); 
//         double endY = startY;
        
//         Line line = new Line(startX, startY, endX, endY);
//         Polygon arrowHead = new Polygon(endX, endY, endX - 5, endY - 5, endX - 5, endY + 5);
//         arrowHead.setFill(Color.BLACK);
        
//         pane.getChildren().addAll(line, arrowHead);
//         renderedNodes.add(line);
//         renderedNodes.add(arrowHead);
//     }
    
    
//     private double getPositionX() {
//         return 50;  
//     }
    
//     private double getPositionY() {
//         return 100; 
//     }


}

// public final class GraphcalLinkedList extends AbstractGraphicalObject {

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
//         double WIDTH_LLIST_CONTENT_BOX = WIDTH_LLIST_CONTENT_BOX;
//         double cellHeight = HEIGHT_LLIST_CONTENT_BOX;
//         double POINTER_WIDTH = 30;

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
//             double cx = x + i * (WIDTH_LLIST_CONTENT_BOX + POINTER_WIDTH + 20);

//             Rectangle valueBox = new Rectangle(WIDTH_LLIST_CONTENT_BOX, cellHeight);
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
//             value.setPrefWidth(WIDTH_LLIST_CONTENT_BOX - 10);
//             value.setEditable(false);
//             value.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 13;");

//             Rectangle pointerBox = new Rectangle(POINTER_WIDTH, cellHeight);
//             pointerBox.setX(cx + WIDTH_LLIST_CONTENT_BOX);
//             pointerBox.setY(y);
//             pointerBox.setFill(Color.LIGHTYELLOW);
//             pointerBox.setStroke(Color.BLACK);

//             valueBoxes[i] = valueBox;
//             pointerBoxes[i] = pointerBox;
//             valueFields[i] = value;

//             pane.getChildren().addAll(valueBox, value, pointerBox);

//             if (i < n - 1) {
//                 double startX = cx + WIDTH_LLIST_CONTENT_BOX + POINTER_WIDTH;
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
