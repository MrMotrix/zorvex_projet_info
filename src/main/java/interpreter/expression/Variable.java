package interpreter.expression;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.exceptions.RuntimeError;

public record Variable(String name) implements Expression {
    public ZorvexValue value(Context context) throws RuntimeError {
        try {
            return context.getVariable(name);
        }
        catch(Exception e) {
            throw new RuntimeError("Variable "+name()+ " non trouvée");
        }
    }

    public String printValue() {
        return name;
    }

    public Expression copy() {
        return new Variable(name);
    }
}
