package interpreter.instruction;

import java.util.List;
import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;

public record Function(List<ZorvexValue> args, String name) implements Instruction {
    @Override
    public Optional<ZorvexValue> interpret(Context context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'interpret'");
    }

    @Override
    public Expression expression() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'expression'");
    }

}
