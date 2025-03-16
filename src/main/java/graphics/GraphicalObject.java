package graphics;

public sealed interface GraphicalObject permits GraphicalVar, GraphicalArray, GraphcalLinkedList {

    void draw(double x, double y);
    // String getName();
    // void update(String newValue);
    void removeFromPane();


    static double WIDTH_VARIABLE_BOX = 100;
    static double HEIGHT_VARIABLE_BOX = 50;
    
    static double WIDTH_ARRAY_BOX = 80;
    static double HEIGHT_ARRAY_BOX = 50;

    static double WIDTH_LLIST_CONTENT_BOX = 40;
    static double HEIGHT_LLIST_CONTENT_BOX = 40;

}
