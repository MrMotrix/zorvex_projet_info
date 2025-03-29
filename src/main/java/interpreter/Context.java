package interpreter;

import java.util.HashMap;

public class Context {
    private HashMap<String, ZorvexValue> variables = new HashMap<>();

    public ZorvexValue getVariable(String name) {
        return variables.get(name);
    }

    public void assignVariable(String name, ZorvexValue value) {
        variables.put(name, value);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        variables.keySet().forEach(x -> sb.append(x + " = " + variables.get(x) + "\n"));
        return sb.toString();
    }
}
