package interpreter.instruction;

import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;

public record TantQue(Expression condition, Block block) implements Instruction{
    public Optional<ZorvexValue> interpret(Context context) {
        if (!condition.value(context).isInteger())
            // on devrait avoir une erreur ici
            return Optional.empty();

        while (condition.value(context).asInteger() != 0) {
            block.interpret(context);
        } 
        return Optional.empty();
    } 
}
