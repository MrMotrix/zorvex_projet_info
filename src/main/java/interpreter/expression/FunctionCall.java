package interpreter.expression;

import java.util.List;

import interpreter.Context;
import interpreter.ZorvexValue;

public final class FunctionCall implements Expression {
    private final String name;
    private final List<Expression> args;
    private ZorvexValue returnValue;

    public FunctionCall(String name, List<Expression> args) {
        this.name = name;
        this.args = args;
    }

    public List<Expression> args() {
        return args;
    }
    public ZorvexValue value(Context context) {
        return returnValue;
    }

    public String printValue() {
        return returnValue.asString();
    }
}
