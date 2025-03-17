package interpreter.instruction;

import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;

public record Assigner(String variableName, Expression expr) implements Instruction{
    public Optional<ZorvexValue> interpret(Context context) {
        context.assignVariable(variableName, expr.value(context));
        return Optional.empty();
    } 
}
