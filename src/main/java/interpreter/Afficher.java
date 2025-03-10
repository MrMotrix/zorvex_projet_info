package interpreter;

import java.util.Optional;

public record Afficher(Expression expr) implements Instruction{
    public Optional<ZorvexValue> interpret(Context context) {
        System.out.println(expr.value(context));
        return Optional.empty();
    } 
}
