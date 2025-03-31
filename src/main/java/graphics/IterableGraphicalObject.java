package graphics;

public interface IterableGraphicalObject {
    void deleteNodeWODraw(int index);
    void insertNodeWODraw(int position, String value);
    void addNodeAt(int position, String value);
    void deleteNodeAt(int position);
    
}
