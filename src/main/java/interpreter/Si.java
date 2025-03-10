package interpreter;

import java.util.List;
import java.util.Optional;

public record Si(Expression condition, List<Instruction> block) implements Instruction{
    public Optional<ZorvexValue> interpret(Context context) {
        if (!condition.value(context).isInteger())
            // on devrait avoir une erreur ici
            return Optional.empty();
        if (condition.value(context).asInteger() != 0) {
            for (Instruction inst : block)
                inst.interpret(context);
        } 
        return Optional.empty();
    } 
}
