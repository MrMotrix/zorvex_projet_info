package graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public final class GraphicalLinkedList extends AbstractGraphicalObject implements IterableGraphicalObject{

    private String[] values;
    private List<GraphicalNode> nodes = new ArrayList<>();
    private int size ;

    private double currentX;
    
    public GraphicalLinkedList(String name,  String[] values, Pane pane, int id) {
        super(name, pane, id);
        this.values = values;
        currentX = 0;
        this.size = values.length;
    }

    public int size() {return size;}


    /**
     * Draws the entire linked list starting at the given coordinates.
     * It clears any existing nodes and arrows, then rebuilds and connects all nodes
     *
     * @param x The starting X coordinate.
     * @param y The starting Y coordinate.
     */
    public void draw(double x, double y) {
        // Reset nodes list to avoid duplicates
        nodes.clear();

        // double WIDTH_LLIST_CONTENT_BOX = WIDTH_LLIST_CONTENT_BOX;
        // double POINTER_WIDTH = 30;
        // double SPACING = 20;

        // Draw the list title
        Text title = new Text("(Liste chaînée) " + name);
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
     * Return an array containing the values of the LinkedList
     * @return
     */
    public String[] getValues() {return values;}



    // /**
    //  * Updates the value of the node at the specified index.
    //  * This only updates the data model, not the visual field contents.
    //  *
    //  * @param index The index of the node to update.
    //  * @param value The new value to assign to the node.
    //  */
    // @Override
    // public void update(int index, String value) {
    //     updateValue(index, value);
    //     updateRender(index, value);
    // }
    @Override
    public void updateValue(int index, String value){
        if (index >= 0 && index < values.length) {
            values[index] = value;
        } 
        
    }
    
    @Override
    public void updateRender(int index, String value){
        nodes.get(index).update(index, value);

    }


    /**
     * Insert a node at a given position without drawing it. I.e. this is the logic to insert a node
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
    
    }

    /**
     * Delete a cell at the given position withoutt drawing it. It is just the logic behind the removal
     * @param position Index of the object to be removed
     */
    public void deleteNodeWODraw(int position){
        if (position < 0 || position >= values.length) return;
    
        // remove the value at the given position, shifting all elements left
        values = Stream.concat(
            Arrays.stream(values, 0, position),
            Arrays.stream(values, position + 1, values.length)
        ).toArray(String[]::new);
    }

    /**
     * Inserts a new node at the specified position in the linked list representation.
     * This method handles updating the visual layout of the list, i.e. shifting nodes,
     * redrawing arrows, and maintaining the internal structure.
     * @param position The index at which to insert the new node
     * @param value    The string value to assign to the new node
     */
    public void addNodeAt(int position, String value) {

        insertNodeWODraw(position, value);

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
        // TODO : this can probablly be refactored. Its almos the same code than in deleteNodeAt
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


    /**
     * Deletes node at a given index, updates values and redraw nodes
     * The feleted node has its transparency increased. It is completely removed from the pane when this is updated, i.e. when the methos reorganize from GraphicalRepresentation is called
     * @param position Index of the element that will be removed
     */
    public void deleteNodeAt(int position) {
        
        deleteNodeWODraw(position);
    
        GraphicalNode nodeToRemove = nodes.get(position);
    
        // increase transparency to show that the node is no longer available
        for (Node node : nodeToRemove.getRenderedNodes()) {
            node.setOpacity(0.3);
        }
    
        // Except for the last node, reconect all nodes to the next
        if (position > 0) {
            if (position < nodes.size() - 1) {
                GraphicalNode prev = nodes.get(position - 1);
                GraphicalNode next = nodes.get(position + 1);
                prev.clearOutgoingArrow(pane);
                // this instruction draws the arrows, and it draws one that goes over the 'deleted' node
                prev.createOutgoingArrowTo(next, pane);
            }
        }
    
        // Remove arrow from the node to delete
        if (position < nodes.size() - 1) {
            nodeToRemove.clearOutgoingArrow(pane);
        }
    
        // Remove node from list of nodes
        nodes.remove(position);
    
        // Reindex nodes from the modified position
        for (int i = position; i < nodes.size(); i++) {
            nodes.get(i).setIndex(i);
        }
    }
    
    


}
