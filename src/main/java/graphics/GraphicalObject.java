package graphics;

public sealed interface GraphicalObject permits GraphicalVar, GraphicalArray, GraphcalLinkedList {

    void draw(double x, double y);
}
