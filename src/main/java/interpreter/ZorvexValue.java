package interpreter;

import java.util.ArrayList;
import java.util.List;

import interpreter.exceptions.RuntimeError;

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

    public ZorvexValue(ZorvexValue value) {
        this.type = value.type;
        this.value = value.value;
    }

    public ZorvexValue(List<ZorvexValue> values) {
        value = new ArrayList<ZorvexValue>(values);
        type = ZorvexType.LIST;
    }
    
    public boolean isInteger() {
        return type == ZorvexType.INTEGER;
    }

    public boolean isString() {
        return type == ZorvexType.STRING;
    }
    
    public int asInteger() throws RuntimeError {
        if (isInteger())
            return (int)value;
        if (isString())
            return Integer.valueOf((String)value);
        throw new RuntimeError(-1, "Cannot convert " + this + " to integer");
    }

    public void add(ZorvexValue element) throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new RuntimeError(-1, "Cannot add to" + this + " as it is not a list.");
        if (element.type == ZorvexType.LIST)
            throw new RuntimeError(-1, "List of lists aren't supported yet.");
        ((List<ZorvexValue>)value).add(element);
    }

    public void set(int index, ZorvexValue element) throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new RuntimeError(-1, "Cannot set in" + this + " as it is not a list.");
        if (element.type == ZorvexType.LIST)
            throw new RuntimeError(-1, "List of lists aren't supported yet.");
        List<ZorvexValue> list = (List<ZorvexValue>)value;
        if (index >= list.size())
            throw new RuntimeError(-1, "Index out of bounds");
        ((List<ZorvexValue>)value).set(index, element);
    }

    public void remove(int index) throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new RuntimeError(-1, "Cannot remove in" + this + " as it is not a list.");
        List<ZorvexValue> list = (List<ZorvexValue>)value;
        if (index >= list.size())
            throw new RuntimeError(-1, "Index out of bounds");
        ((List<ZorvexValue>)value).remove(index);
    }

    public String asString() {
        if (type == ZorvexType.LIST) 
            return "[" + String.join(",", ((List<ZorvexValue>)value).stream().map(x -> x.asString()).toList()) + "]";
        
        return value.toString();
    }

    public String toString() {
        return type + " " + value;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (object instanceof ZorvexValue other) 
            return type == other.type && value == other.value;
        return false;
    }
}
