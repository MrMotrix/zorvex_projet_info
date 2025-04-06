package interpreter;


public class LocalVariable {
    private final int id;
    private ZorvexValue value;

    public LocalVariable(ZorvexValue value, int id) {
        this.id = id;
        this.value = value;
    }

    public ZorvexValue value() {
        return value;
    }

    public int id() {
        return id;
    }

    public void setValue(ZorvexValue value) {
        this.value = value;
    }
}
