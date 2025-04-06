package interpreter;

import java.util.HashMap;

public class Context {
    private HashMap<String, LocalVariable> variables = new HashMap<>();
    private static int id = 0;

    public ZorvexValue getVariable(String name) {
        return variables.get(name).value();
    }
    
    public int getId(String name) {
        return variables.get(name).id();
    }

    public void assignVariable(String name, ZorvexValue value) {
        if (!variables.containsKey(name)) {
            variables.put(name, new LocalVariable(value, id));
            id += 1;
        }
        variables.get(name).setValue(value);
    }

    public ZorvexValue getValueById(int id) {
        for (LocalVariable lVar : variables.values())
            if (lVar.id() == id)
                return lVar.value();
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        variables.keySet().forEach(x -> sb.append(x + " = " + variables.get(x) + "\n"));
        return sb.toString();
    }
}
