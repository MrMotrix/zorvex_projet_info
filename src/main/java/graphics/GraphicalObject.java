package graphics;

public interface GraphicalObject {
    String getName();

    /**
     * Draw the object on the pane at the specified coordinates.
     * @param x coordinate
     * @param y coordinate
     */
    void draw(double x, double y);
    // void update(String newValue);

    /**
     * Remove the object from the pane.
     * This method should be called when the object is no longer needed or when it needs to be redrawn.
     */
    void removeFromPane();
    // int getID();
    /**
     * Update the value of the variable (LinkedList, ArrayList, etc) and re-render it.
     * @param newValue The new value to set.
     * @param index The index of the value to update. (it can be any integer if the object is a variable. )
     */
    default void update(int index, String value){
        updateValue(index, value);
        updateRender(index, value);
    }
    /**
     * Update the value of the variable (LinkedList, ArrayList, etc).
     * This only updates the data model, not the visual field contents.
     * @param index
     * @param value
     */
    void updateValue(int index, String value);
    /**
     * Update the value of the rendered object (LinkedList, ArrayList, etc) and re-render it.
     * @param index The index of the value to update. (it can be any integer if the object is a variable. )
     */
    void updateRender(int index, String value);


    static double WIDTH_VARIABLE_BOX = 100;
    static double HEIGHT_VARIABLE_BOX = 50;
    
    static double WIDTH_ARRAY_BOX = 80;
    static double HEIGHT_ARRAY_BOX = 50;

    static double WIDTH_LLIST_CONTENT_BOX = 40;
    static double HEIGHT_LLIST_CONTENT_BOX = 40;

    static double POINTER_WIDTH = 30;
    static double SPACING = 20;

}
