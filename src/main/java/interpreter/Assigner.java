package interpreter;

import java.util.Optional;

public record Assigner(String variableName, Expression expr) implements Instruction{
    public Optional<ZorvexValue> interpret(Context context) {
        context.assignVariable(variableName, expr.value(context));
        return Optional.empty();
    } 
}
