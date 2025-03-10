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
}
