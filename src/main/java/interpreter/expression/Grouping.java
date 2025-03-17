package interpreter.expression;

import interpreter.Context;
import interpreter.ZorvexValue;

public record Grouping(Expression expr) implements Expression {
    public ZorvexValue value(Context context) {
        return expr.value(context);
    }

    public String printValue() {
        return "(" + expr.printValue() + ")";
    }
}
