package interpreter.expression;

import interpreter.Context;
import interpreter.ZorvexValue;

public record Literal(ZorvexValue value) implements Expression {
    public ZorvexValue value(Context context) {
        return this.value;
    }

    public String printValue() {
        return this.value().asString();
    }

    public Expression copy() {
        return new Literal(new ZorvexValue(value));
    }
}
