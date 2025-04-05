package interpreter.instruction;

import java.util.List;
import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;
import interpreter.expression.FunctionCall;
import interpreter.expression.Literal;

public record FunctionDeclaration(String name, Block block, List<String> parameters) implements Instruction {
    @Override
    public Optional<ZorvexValue> interpret(Context context) {
        return Optional.empty();
    }
    
    @Override
    public Expression expression() {
        return new Literal(new ZorvexValue(0));
    }
    
}
