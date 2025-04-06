package interpreter.expression;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.exceptions.RuntimeError;

public record Literal(ZorvexValue value) implements Expression {
    public ZorvexValue value(Context context) throws RuntimeError {
        return this.value;
    }

    public String printValue() {
        return this.value().asString();
    }

    public Expression copy() {
        return new Literal(new ZorvexValue(value));
    }
}
