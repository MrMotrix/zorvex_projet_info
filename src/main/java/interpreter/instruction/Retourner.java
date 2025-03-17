package interpreter.instruction;

import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;

public record Retourner(Expression expression) implements Instruction {
    public Optional<ZorvexValue> interpret(Context context) {
        return Optional.of(expression.value(context));
    }
}
