package interpreter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import interpreter.exceptions.RuntimeError;
import interpreter.exceptions.UnexpectedTypeException;

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
        throw new RuntimeError("Ne peut convertir " + this + " en entier");
    }

    // lists
    public void add(ZorvexValue element) throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new UnexpectedTypeException(this, ZorvexType.LIST);
        if (element.type == ZorvexType.LIST)
            throw new RuntimeError("Les listes de listes ne sont pas supportées.");
        ((List<ZorvexValue>)value).add(element);
    }

    public void set(int index, ZorvexValue element) throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new UnexpectedTypeException(this, ZorvexType.LIST);
        if (element.type == ZorvexType.LIST)
            throw new RuntimeError("Les listes de listes ne sont pas supportées.");
        List<ZorvexValue> list = (List<ZorvexValue>)value;
        if (index >= list.size())
            throw new RuntimeError("Dépassement de bornes");
        ((List<ZorvexValue>)value).set(index, element);
    }

    public void remove(int index) throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new UnexpectedTypeException(this, ZorvexType.LIST);
        List<ZorvexValue> list = (List<ZorvexValue>)value;
        if (index >= list.size())
            throw new RuntimeError("Dépassement de bornes");
        ((List<ZorvexValue>)value).remove(index);
    }

    public ZorvexValue get(int index) throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new UnexpectedTypeException(this, ZorvexType.LIST);
        List<ZorvexValue> list = (List<ZorvexValue>)value;
        if (index >= list.size())
            throw new RuntimeError("Dépassement de bornes");
        return list.get(index);
    }

    public void insert(int index, ZorvexValue element) throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new UnexpectedTypeException(this, ZorvexType.LIST);
        List<ZorvexValue> list = (List<ZorvexValue>)value;
        if (index >= list.size())
            throw new RuntimeError("Dépassement de bornes");
        list.add(index, element);
    }

    public int size() throws RuntimeError {
        if (type != ZorvexType.LIST)
            throw new UnexpectedTypeException(this, ZorvexType.LIST);
        List<ZorvexValue> list = (List<ZorvexValue>)value;
        return list.size();
    }
    
    public static ZorvexValue emptyStack() {
        ZorvexValue result = new ZorvexValue(0);
        result.type = ZorvexType.STACK;
        result.value = new ArrayDeque<ZorvexValue>();
        return result;
    }

    public boolean isEmpty() throws RuntimeError {
        if (type != ZorvexType.STACK)
            throw new UnexpectedTypeException(this, ZorvexType.STACK);
        ArrayDeque<ZorvexValue> stack = (ArrayDeque<ZorvexValue>)value;
        
        return stack.isEmpty();
    }

    public ZorvexValue depiler() throws RuntimeError {
        if (type != ZorvexType.STACK)
            throw new UnexpectedTypeException(this, ZorvexType.STACK);
        ArrayDeque<ZorvexValue> stack = (ArrayDeque<ZorvexValue>)value;
        
        if (isEmpty())
            throw new RuntimeError("Ne peut dépiler d'une pile vide");
        return stack.pollLast();
    }

    public void empiler(ZorvexValue element) throws RuntimeError {
        if (type != ZorvexType.STACK)
            throw new UnexpectedTypeException(this, ZorvexType.STACK);
        ArrayDeque<ZorvexValue> stack = (ArrayDeque<ZorvexValue>)value;
        stack.offerLast(element);
    }

    public String asString() {
        if (type == ZorvexType.LIST) 
            return "[" + String.join(",", ((List<ZorvexValue>)value).stream().map(x -> x.asString()).toList()) + "]";
        if (type == ZorvexType.STACK) 
            return "[" + String.join(",", ((ArrayDeque<ZorvexValue>)value).stream().map(x -> x.asString()).toList()) + "]";
        return value.toString();
    }

    public String toString() {
        return type + " " + value;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (object instanceof ZorvexValue other && type == other.type)
            return type == ZorvexType.STRING ? value.equals(other.value) : value == other.value;
        return false;
    }

    public ZorvexType type() {
        return type;
    }
}
