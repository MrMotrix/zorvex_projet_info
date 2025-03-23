package interpreter;

public class ZorvexValue {
    public enum Type {
        STRING, INTEGER
    }

    private Type type;
    private Object value;

    public ZorvexValue(int value) {
        this.type = Type.INTEGER;
        this.value = value;
    }

    public ZorvexValue(String value) {
        this.type = Type.STRING;
        this.value = value;
    }

    public boolean isInteger() {
        return type == Type.INTEGER;
    }

    public boolean isString() {
        return type == Type.STRING;
    }
    
    public int asInteger() {
        if (isInteger())
            return (int)value;
        if (isString())
            return Integer.valueOf((String)value);
        // erreur ici normalement
        return -1;
    }

    public String asString() {
        return value.toString();
    }

    public String toString() {
        return type + " " + value;
    }
}
