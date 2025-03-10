package interpreter;

import java.util.Optional;

public record Retourner(Expression expression) implements Instruction {
    public Optional<ZorvexValue> interpret(Context context) {
        return Optional.of(expression.value(context));
    }
}
