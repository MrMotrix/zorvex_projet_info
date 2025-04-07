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

    public static ZorvexValue nullValue() {
        ZorvexValue result = new ZorvexValue(0);
        result.type = ZorvexType.NaN;
        result.value = null;
        return result;
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
        throw new RuntimeError("Cannot convert " + this + " to integer");
    }

    public void add(ZorvexValue element) throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new RuntimeError("Cannot add to" + this + " as it is not a list.");
        if (element.type == ZorvexType.LIST)
            throw new RuntimeError("List of lists aren't supported yet.");
        ((List<ZorvexValue>)value).add(element);
    }

    public void set(int index, ZorvexValue element) throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new RuntimeError("Cannot set in" + this + " as it is not a list.");
        if (element.type == ZorvexType.LIST)
            throw new RuntimeError("List of lists aren't supported yet.");
        List<ZorvexValue> list = (List<ZorvexValue>)value;
        if (index >= list.size())
            throw new RuntimeError("Index out of bounds");
        ((List<ZorvexValue>)value).set(index, element);
    }

    public void remove(int index) throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new RuntimeError("Cannot remove in" + this + " as it is not a list.");
        List<ZorvexValue> list = (List<ZorvexValue>)value;
        if (index >= list.size())
            throw new RuntimeError("Index out of bounds");
        ((List<ZorvexValue>)value).remove(index);
    }

    public ZorvexValue get(int index) throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new RuntimeError("Cannot remove in" + this + " as it is not a list.");
        List<ZorvexValue> list = (List<ZorvexValue>)value;
        if (index >= list.size())
            throw new RuntimeError("Index out of bounds");
        return list.get(index);
    }

    public void insert(int index, ZorvexValue element) throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new RuntimeError("Cannot remove in" + this + " as it is not a list.");
        List<ZorvexValue> list = (List<ZorvexValue>)value;
        if (index >= list.size())
            throw new RuntimeError("Index out of bounds");
        list.add(index, element);
    }

    public int size() throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new RuntimeError("Cannot get " + this + "'s size as it is not a list.");
        List<ZorvexValue> list = (List<ZorvexValue>)value;
        return list.size();
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

    public ZorvexType type() {
        return type;
    }
}
