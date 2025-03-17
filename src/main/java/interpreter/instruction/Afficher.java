package interpreter.instruction;

import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;

public record Afficher(Expression expr) implements Instruction{
    public Optional<ZorvexValue> interpret(Context context) {
        System.out.println(expr.value(context));
        return Optional.empty();
    } 
}
