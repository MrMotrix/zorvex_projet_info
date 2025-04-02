package interpreter;

public class ZorvexValue {
    private ZorvexType type;
    private Object value;

    public ZorvexValue(int value) {
        this.type = ZorvexType.INTEGER;
        this.value = value;
    }

    public ZorvexValue(String value) {
        this.type = ZorvexType.STRING;
        this.value = value;
    }

    public boolean isInteger() {
        return type == ZorvexType.INTEGER;
    }

    public boolean isString() {
        return type == ZorvexType.STRING;
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
