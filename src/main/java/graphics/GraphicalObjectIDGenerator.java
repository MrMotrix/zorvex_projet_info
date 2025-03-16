package graphics;

public class GraphicalObjectIDGenerator {
    private static int currentId = 0;

    public static synchronized int getNextId() {
        return currentId++;
    }
}
