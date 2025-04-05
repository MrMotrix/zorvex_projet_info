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

    public String name() {
        return name;
    }
    
    public ZorvexValue value(Context context) {
        // exception ici
        return returnValue;
    }

    public String printValue() {
        return returnValue.asString();
    }

    public Expression copy() {
        return new FunctionCall(name, args.stream().map(x -> x.copy()).toList());
    }
}
