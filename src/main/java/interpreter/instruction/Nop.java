package interpreter.instruction;

import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;

public final class Nop implements Instruction {
    public Optional<ZorvexValue> interpret(Context context) {
        return Optional.empty();
    }
}
