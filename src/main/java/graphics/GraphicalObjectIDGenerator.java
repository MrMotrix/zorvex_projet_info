package graphics;

public class GraphicalObjectIDGenerator {
    private static int currentId = -1;

    public static synchronized int getNextId() {
        return currentId--;
    }
}
