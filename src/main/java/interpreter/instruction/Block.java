package interpreter.instruction;

import java.util.List;
import java.util.Optional;

import interpreter.Context;
import interpreter.ZorvexValue;
import interpreter.expression.Expression;
import interpreter.expression.FunctionCall;
import interpreter.expression.Literal;

public record Block(List<InstructionInfo> instructions) implements Instruction{
    public Optional<ZorvexValue> interpret(Context context) {
        for (InstructionInfo inst : instructions())
            inst.instruction().interpret(context);
        return Optional.empty();
    }

    @Override
    public Expression expression() {
        return new Literal(new ZorvexValue(0));
    }

}
